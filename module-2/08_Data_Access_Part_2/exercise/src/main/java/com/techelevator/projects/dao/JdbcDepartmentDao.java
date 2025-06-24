package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Department;

public class JdbcDepartmentDao implements DepartmentDao {

	private final String DEPARTMENT_SELECT = "SELECT d.department_id, d.name FROM department d ";
	
	private final JdbcTemplate jdbcTemplate;

	public JdbcDepartmentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Department getDepartmentById(int id) {
		Department department = null;
		String sql = DEPARTMENT_SELECT +
				" WHERE d.department_id=?";

		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
			if (results.next()) {
				department = mapRowToDepartment(results);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get department information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}
		return department;
	}

	@Override
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<>();
		String sql = DEPARTMENT_SELECT;
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				departments.add(mapRowToDepartment(results));
			}
		}catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		} catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get department information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}
		
		return departments;
	}

	@Override
	public Department createDepartment(Department department) {
		Department output = null;
		String sql = "insert into department(name) values(?) returning department_id;";
		try{
			int newDepartmentId = jdbcTemplate.queryForObject(sql,int.class,
					department.getName());
			output = getDepartmentById(newDepartmentId);
		}catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Can't get a reference to the source of data.",e);
		} catch(DataIntegrityViolationException e){
			throw new DaoException("Data breaks the rules.",e);
		}
		return output;
	}

	@Override
	public Department updateDepartment(Department department) {
		Department updatedDepartment = null;
		String sql = "update department\n" +
				"set name = ?\n" +
				"where department_id = ?;";
		try{
			int numberOfRows = jdbcTemplate.update(sql, department.getName(), department.getId());
			if(numberOfRows == 0){
				throw new DaoException("No departments were updated");
			} else {
				updatedDepartment = getDepartmentById(department.getId());
			}
		} catch (Exception e) {
			throw new DaoException("Cannot update department",e);
		}
		return updatedDepartment;
	}

	@Override
	public int deleteDepartmentById(int id) {
		int numberOfRowsImpacted = 0;
		String projectEmployeeSql = "delete from project_employee \n" +
				"where employee_id in (select employee_id from employee where department_id = ?);";
		String employeeSql = "delete from employee\n" +
				"where department_id in (select department_id from department where department_id = ?);";
		String departmentSql = "delete from department where department_id = ?;";
		try{
			jdbcTemplate.update(projectEmployeeSql,id);
			jdbcTemplate.update(employeeSql,id);
			numberOfRowsImpacted = jdbcTemplate.update(departmentSql,id);
		} catch(Exception e){
			throw new DaoException("Cannot remove department for some reason.",e);
		}
		return numberOfRowsImpacted;
	}

	private Department mapRowToDepartment(SqlRowSet results) {
		Department department = new Department();
		department.setId(results.getInt("department_id"));
		department.setName(results.getString("name"));
		return department;
	}

}
