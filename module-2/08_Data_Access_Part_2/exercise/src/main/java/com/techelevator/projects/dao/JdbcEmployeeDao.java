package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Project;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Employee;

public class JdbcEmployeeDao implements EmployeeDao {

	private final String EMPLOYEE_SELECT = "SELECT e.employee_id, e.department_id, e.first_name, e.last_name, " +
				"e.birth_date, e.hire_date FROM employee e ";

	private final JdbcTemplate jdbcTemplate;

	public JdbcEmployeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Employee getEmployeeById(int id) {
		Employee employee = null;
		String sql = EMPLOYEE_SELECT +
				" WHERE e.employee_id=?";
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
			if (results.next()) {
				employee = mapRowToEmployee(results);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get employee information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}

		return employee;
	}

	@Override
	public List<Employee> getEmployees() {
		List<Employee> allEmployees = new ArrayList<>();
		String sql = EMPLOYEE_SELECT;
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				Employee employeeResult = mapRowToEmployee(results);
				allEmployees.add(employeeResult);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get employee information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}

		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesByFirstNameLastName(String firstName, String lastName) {
		List<Employee> allEmployees = new ArrayList<>();
		String sql = EMPLOYEE_SELECT +
				" WHERE e.first_name ILIKE ? AND e.last_name ILIKE ?";
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + firstName + "%", "%" + lastName + "%");
			while (results.next()) {
				Employee employeeResult = mapRowToEmployee(results);
				allEmployees.add(employeeResult);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get employee information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}

		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(int projectId) {
		List<Employee> allEmployees = new ArrayList<>();
		String sql =  EMPLOYEE_SELECT +
				"JOIN project_employee pe ON e.employee_id = pe.employee_id " +
				"WHERE pe.project_id = ?";
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
			while (results.next()) {
				Employee employeeResult = mapRowToEmployee(results);
				allEmployees.add(employeeResult);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get employee information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}

		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> allEmployees = new ArrayList<>();
		String sql = EMPLOYEE_SELECT +
				" WHERE e.employee_id NOT IN (SELECT DISTINCT employee_id FROM project_employee)";
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				Employee employeeResult = mapRowToEmployee(results);
				allEmployees.add(employeeResult);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get employee information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}
		return allEmployees;
	}

	@Override
	public Employee createEmployee(Employee employee) {
		Employee output = null;
		String sql = "insert into employee(department_id, first_name, last_name, birth_date, hire_date) \n" +
				"values(?,?,?,?,?) returning employee_id;";
		try{
			int newProjectId = jdbcTemplate.queryForObject(sql,int.class,
					employee.getDepartmentId(), employee.getFirstName(),
					employee.getLastName(), employee.getBirthDate(),
					employee.getHireDate());
			output = getEmployeeById(newProjectId);
		}catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Can't get a reference to the source of data.",e);
		}catch(DataIntegrityViolationException e){
			throw new DaoException("Data breaks the rules.",e);
		}
		return output;
	}
	
	@Override
	public Employee updateEmployee(Employee employee) {
		Employee updatedEmployee = null;
		String sql = "update employee\n" +
				"set department_id = ?, first_name = ?, last_name = ?, birth_date = ?, hire_date= ?\n" +
				"where employee_id = ?;";
		try{
			int numberOfRows = jdbcTemplate.update(sql, employee.getDepartmentId(), employee.getFirstName(), employee.getLastName(),
					employee.getBirthDate(), employee.getHireDate(), employee.getId());
			if(numberOfRows == 0){
				throw new DaoException("No employees were updated");
			} else {
				updatedEmployee = getEmployeeById(employee.getId());
			}
		} catch (Exception e) {
			throw new DaoException("Cannot update employee",e);
		}
		return updatedEmployee;
	}

	@Override
	public int deleteEmployeeById(int id) {
		int numberOfRowsImpacted = 0;
		String projectEmployeeSql = "delete from project_employee where employee_id = ?;";
		String employeeSql = "delete from employee where employee_id = ?;";
		try{
			jdbcTemplate.update(projectEmployeeSql,id);

			numberOfRowsImpacted = jdbcTemplate.update(employeeSql,id);
		} catch(Exception e) {
			throw new DaoException("Cannot remove department for some reason.", e);
		}
		return numberOfRowsImpacted;
	}

	@Override
	public int deleteEmployeesByDepartmentId(int departmentId) {
		int numberOfRowsImpacted = 0;
		String projectEmployeeSql =
				"delete from project_employee\n" +
						" where employee_id IN (\n" +
						"   select employee_id\n" +
						"     from employee\n" +
						"    where department_id = ?);";

		String employeeSql =
				"delete from employee\n" +
						" where department_id = ?;";
		try{
			jdbcTemplate.update(projectEmployeeSql, departmentId);

			// 2. Remove the employees themselves
			numberOfRowsImpacted = jdbcTemplate.update(employeeSql, departmentId);
		} catch(Exception e) {
			throw new DaoException("Cannot remove department for some reason.", e);
		}
		return numberOfRowsImpacted;
	}

	private Employee mapRowToEmployee(SqlRowSet result) {
		Employee employee = new Employee();
		employee.setId(result.getInt("employee_id"));
		employee.setDepartmentId(result.getInt("department_id"));
		employee.setFirstName(result.getString("first_name"));
		employee.setLastName(result.getString("last_name"));
		employee.setBirthDate(result.getDate("birth_date").toLocalDate());
		employee.setHireDate(result.getDate("hire_date").toLocalDate());
		return employee;
	}
}
