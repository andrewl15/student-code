package com.techelevator.projects.dao;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcProjectDaoTest extends BaseDaoTest {
    private static final Employee EMPLOYEE_2 =
            new Employee(2, 2, "First2", "Last2",
                    LocalDate.parse("1982-02-01"), LocalDate.parse("2002-02-03"));
    private static final Employee EMPLOYEE_3 =
            new Employee(3, 1, "First3", "Last3",
                    LocalDate.parse("1983-03-01"), LocalDate.parse("2003-03-04"));

    private static final Project PROJECT_1 =
            new Project(1, "Project 1", LocalDate.parse("2000-01-02"), LocalDate.parse("2000-12-31"));
    private static final Project PROJECT_2 =
            new Project(2, "Project 2", null, null);

    private JdbcProjectDao sut;
    private JdbcProjectDao invalidConnectionDao;

    @BeforeEach
    public void setup() {
        sut = new JdbcProjectDao(dataSource);
        invalidConnectionDao = new JdbcProjectDao(invalidDataSource);
    }

    @Test
    public void createProject_creates_project() {
        Project newProject = new Project();
        newProject.setName("Project Ultima");
        newProject.setFromDate(LocalDate.of(2023, 02, 01));
        newProject.setToDate(LocalDate.of(2023, 04, 01));

        Project createdProject = sut.createProject(newProject);
        assertNotNull(createdProject, "createProject returned a null project.");
        assertTrue(createdProject.getId() > 0, "createProject did not return a project with id set.");
        assertEquals(newProject.getName(), createdProject.getName(), "createProject did not return a project with the correct name.");
        assertEquals(newProject.getFromDate(), createdProject.getFromDate(), "createProject did not return a project with the correct fromDate.");
        assertEquals(newProject.getToDate(), createdProject.getToDate(), "createProject did not return a project with the correct toDate.");

        // verify value was saved to database, retrieve it and compare values
        Project retrievedProject = getProjectByIdForTestVerification(createdProject.getId());
        assertNotNull(retrievedProject, "createProject does not appear to have correctly persisted the newly created project. It could not be found by id.");
        assertProjectsMatch(createdProject, retrievedProject,
                "createProject does not appear to have fully persisted the newly created project. The retrieved project is incorrect/incomplete.");
    }

    @Test
    public void createProject_throws_dao_exception_for_data_integrity_violation() {
        Project newProject = new Project();
        newProject.setName(PROJECT_1.getName()); // non-unique name

        String methodName = "createProject";
        try {
            sut.createProject(newProject);
            fail(didNotThrowAnyExceptionForDataIntegrity(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForDataIntegrity(methodName));
        }
    }

    @Test
    public void linkProjectEmployee_adds_employee_to_list_of_employees_for_project() {
        // Get list of employees before link
        int preLinkEmployeeCount = getProjectEmployeesForTestVerification(1).size();

        sut.linkProjectEmployee(1, 3);
        List<Employee> projectEmployees = getProjectEmployeesForTestVerification(1);
        int postLinkEmployeeCount = projectEmployees.size();

        assertEquals(preLinkEmployeeCount + 1, postLinkEmployeeCount,
                "linkProjectEmployee did not increase number of employees in project.");
        assertProjectEmployeesMatch(EMPLOYEE_3, projectEmployees.get(1),
                "linkProjectEmployee did not add correct employee to project.");
    }

    @Test
    public void unlinkProjectEmployee_removes_employee_from_list_of_employees_for_project() {
        // Get list of employees before unlink
        int preUnlinkEmployeeCount = getProjectEmployeesForTestVerification(2).size();

        sut.unlinkProjectEmployee(2, 3);
        List<Employee> projectEmployees = getProjectEmployeesForTestVerification(2);
        int postUnlinkEmployeeCount = projectEmployees.size();

        assertEquals(preUnlinkEmployeeCount - 1, postUnlinkEmployeeCount,
                "unlinkProjectEmployee did not decrease number of employees in project.");
        assertProjectEmployeesMatch(EMPLOYEE_2, projectEmployees.get(0), "unlinkProjectEmployee did not remove correct employee from project.");
    }

    @Test
    public void updateProject_updates_project() {
        Project existingProject = new Project();
        existingProject.setId(PROJECT_2.getId());
        existingProject.setName("Test Project Update");
        existingProject.setFromDate(LocalDate.parse("2003-02-21"));
        existingProject.setToDate(LocalDate.parse("2023-02-21"));

        Project updatedProject = sut.updateProject(existingProject);
        assertNotNull(updatedProject, "updateProject returned a null project.");
        assertProjectsMatch(updatedProject, existingProject, "updateProject returned an incorrect/incomplete project.");

        // verify value was saved to database, retrieve it and compare values
        Project retrievedProject = getProjectByIdForTestVerification(PROJECT_2.getId());
        assertProjectsMatch(updatedProject, retrievedProject,
                "updateProject does not appear to have fully persisted the updated project. The retrieved project is incorrect/incomplete.");
    }

    @Test
    public void updateProject_throws_dao_exception_for_data_integrity_violation() {
        Project existingProject = new Project();
        existingProject.setId(PROJECT_2.getId());
        existingProject.setName(PROJECT_1.getName()); // non-unique name

        String methodName = "updateProject";
        try {
            sut.updateProject(existingProject);
            fail(didNotThrowAnyExceptionForDataIntegrity(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForDataIntegrity(methodName));
        }
    }

    @Test
    public void deleteProjectById_deletes_project() {
        int recordsAffected = sut.deleteProjectById(PROJECT_1.getId());
        assertEquals(1, recordsAffected, "deleteProjectById did not return the correct number of rows affected.");
        Project retrievedProject = getProjectByIdForTestVerification(PROJECT_1.getId());
        assertNull(retrievedProject, "deleteProjectById did not remove the project from database.");
    }

    @Test
    public void deleteProjectById_with_invalid_id_returns_zero_rows_affected() {
        int recordsAffected = sut.deleteProjectById(999); // non-existent project_id
        assertEquals(0, recordsAffected, "deleteProjectById with invalid id did not return the correct number of rows affected.");
    }

    @Test
    public void project_dao_get_methods_throw_dao_exception_for_invalid_connection() {
        String methodName = "";

        methodName = "getProjectById";
        try {
            invalidConnectionDao.getProjectById(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "getProjects";
        try {
            invalidConnectionDao.getProjects();
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }
    }

    @Test
    public void project_dao_insert_update_delete_methods_throw_dao_exception_for_invalid_connection() {
        String methodName = "";

        methodName = "createProject";
        try {
            invalidConnectionDao.createProject(PROJECT_1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "linkProjectEmployee";
        try {
            invalidConnectionDao.linkProjectEmployee(1,1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "unlinkProjectEmployee";
        try {
            invalidConnectionDao.unlinkProjectEmployee(1,1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "updateProject";
        try {
            invalidConnectionDao.updateProject(PROJECT_1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "deleteProjectById";
        try {
            invalidConnectionDao.deleteProjectById(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }
    }

    private void assertProjectsMatch(Project expected, Project actual, String message) {
        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getName(), actual.getName(), message);
        assertEquals(expected.getFromDate(), actual.getFromDate(), message);
        assertEquals(expected.getToDate(), actual.getToDate(), message);
    }

    public static void assertProjectEmployeesMatch(Employee expected, Employee actual, String message) {
        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getDepartmentId(), actual.getDepartmentId(), message);
        assertEquals(expected.getFirstName(), actual.getFirstName(), message);
        assertEquals(expected.getLastName(), actual.getLastName(), message);
        assertEquals(expected.getBirthDate(), actual.getBirthDate(), message);
        assertEquals(expected.getHireDate(), actual.getHireDate(), message);
    }

    // test-specific implementation of getProjectById to be independent of DAO class
    private Project getProjectByIdForTestVerification(int id) {
        Project project = null;
        String sql = "SELECT project_id, name, from_date, to_date FROM project WHERE project_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            Project mappedProject = new Project();
            mappedProject.setId(results.getInt("project_id"));
            mappedProject.setName(results.getString("name"));
            if(results.getDate("from_date") != null) {
                mappedProject.setFromDate(results.getDate("from_date").toLocalDate());
            }
            if(results.getDate("to_date") != null) {
                mappedProject.setToDate(results.getDate("to_date").toLocalDate());
            }
            project = mappedProject;
        }
        return project;
    }

    private List<Employee> getProjectEmployeesForTestVerification(int id) {
        List<Employee> projectEmployees = new ArrayList<>();
        String sql = "SELECT e.employee_id, e.department_id, e.first_name, e.last_name, e.birth_date, e.hire_date FROM employee e "+
                "JOIN project_employee pe ON e.employee_id = pe.employee_id "+
                "WHERE pe.project_id = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while (results.next()) {
            Employee mappedEmployee = new Employee();
            mappedEmployee.setId(results.getInt("employee_id"));
            mappedEmployee.setDepartmentId(results.getInt("department_id"));
            mappedEmployee.setFirstName(results.getString("first_name"));
            mappedEmployee.setLastName(results.getString("last_name"));
            mappedEmployee.setBirthDate(results.getDate("birth_date").toLocalDate());
            mappedEmployee.setHireDate(results.getDate("hire_date").toLocalDate());
            projectEmployees.add(mappedEmployee);
        }
        return projectEmployees;
    }
}
