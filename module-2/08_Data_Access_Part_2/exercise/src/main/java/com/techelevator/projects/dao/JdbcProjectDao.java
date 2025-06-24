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
import com.techelevator.projects.model.Project;

public class JdbcProjectDao implements ProjectDao {

	private final String PROJECT_SELECT = "SELECT p.project_id, p.name, p.from_date, p.to_date FROM project p";

	private final JdbcTemplate jdbcTemplate;

	public JdbcProjectDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Project getProjectById(int projectId) {
		Project project = null;
		String sql = PROJECT_SELECT +
				" WHERE p.project_id=?";
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
			if (results.next()) {
				project = mapRowToProject(results);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get project information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}

		return project;
	}

	@Override
	public List<Project> getProjects() {
		List<Project> allProjects = new ArrayList<>();
		String sql = PROJECT_SELECT;
		try{
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				Project projectResult = mapRowToProject(results);
				allProjects.add(projectResult);
			}
		}catch(CannotGetJdbcConnectionException e){
			throw new DaoException("Unable to access the information", e);
		}catch (BadSqlGrammarException e){
			throw new DaoException("Unable to get project information",e);
		} catch (Exception e){
			throw new DaoException("I have no idea what went wrong.",e);
		}
		return allProjects;
	}

	@Override
	public Project createProject(Project newProject) {
		Project output = null;
		String sql = "insert into project(name, from_date, to_date) values(?,?,?) returning project_id;";
		try{
			int newProjectId = jdbcTemplate.queryForObject(sql,int.class,
					newProject.getName(), newProject.getFromDate(), newProject.getToDate());
			output = getProjectById(newProjectId);
		}catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Can't get a reference to the source of data.",e);
		} catch(DataIntegrityViolationException e){
			throw new DaoException("Data breaks the rules.",e);
		}
		return output;
	}
	
	@Override
	public void linkProjectEmployee(int projectId, int employeeId) {
		String sql = "insert into project_employee VALUES(?,?);";
		try{
			jdbcTemplate.update(sql,projectId,employeeId);
		} catch (CannotGetJdbcConnectionException e){
			throw new DaoException("Can't get at the data",e);
		}
	}

	@Override
	public void unlinkProjectEmployee(int projectId, int employeeId) {
		String sql = "delete from project_employee where project_id = ? and employee_id = ?";
		try {
			int rowsAffected = jdbcTemplate.update(sql, projectId, employeeId);
			if (rowsAffected == 0) {
				throw new DaoException("No matching project-employee link found to delete.");
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Can't get at the data", e);
		} catch (Exception e) {
			throw new DaoException("Error while unlinking project and employee", e);
		}

	}
	@Override
	public Project updateProject(Project project) {
		Project updatedProject = null;
		String sql = "update project\n" +
				"set name = ?, from_date = ?, to_date = ?\n" +
				"where project_id = ?;";
		try{
			int numberOfRows = jdbcTemplate.update(sql, project.getName(), project.getFromDate(),
					project.getToDate(), project.getId());
			if(numberOfRows == 0){
				throw new DaoException("No departments were updated");
			} else {
				updatedProject = getProjectById(project.getId());
			}
		} catch (Exception e) {
			throw new DaoException("Cannot update department",e);
		}
		return updatedProject;
	}
	@Override
	public int deleteProjectById(int projectId) {
		int numberOfRowsImpacted = 0;
		String projectEmployeeSql = "delete from project_employee where project_id = ?;";
		String projectSql = "delete from project where project_id = ?;";
		try{
			jdbcTemplate.update(projectEmployeeSql,projectId);

			numberOfRowsImpacted = jdbcTemplate.update(projectSql,projectId);
		} catch(Exception e) {
			throw new DaoException("Cannot remove department for some reason.", e);
		}
		return numberOfRowsImpacted;
	}
	
	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		project.setId(results.getInt("project_id"));
		project.setName(results.getString("name"));
		if (results.getDate("from_date") != null) {
			project.setFromDate(results.getDate("from_date").toLocalDate());
		}
		if (results.getDate("to_date") != null) {
			project.setToDate(results.getDate("to_date").toLocalDate());
		}
		return project;
	}

}
