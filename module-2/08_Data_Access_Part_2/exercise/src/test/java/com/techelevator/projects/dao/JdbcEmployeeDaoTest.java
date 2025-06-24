package com.techelevator.projects.dao;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;

public class JdbcEmployeeDaoTest extends BaseDaoTest {

    private static final Employee EMPLOYEE_1 =
            new Employee(1, 1, "First1", "Last1",
                    LocalDate.parse("1981-01-01"), LocalDate.parse("2001-01-02"));
    private static final Employee EMPLOYEE_2 =
            new Employee(2, 2, "First2", "Last2",
                    LocalDate.parse("1982-02-01"), LocalDate.parse("2002-02-03"));

    private JdbcEmployeeDao sut;
    private JdbcEmployeeDao invalidConnectionDao;

    @BeforeEach
    public void setup() {
        sut = new JdbcEmployeeDao(dataSource);
        invalidConnectionDao = new JdbcEmployeeDao(invalidDataSource);
    }

    @Test
    public void createEmployee_creates_employee() {
        Employee newEmployee = new Employee();
        newEmployee.setDepartmentId(1);
        newEmployee.setFirstName("Test");
        newEmployee.setLastName("Testerson");
        newEmployee.setBirthDate(LocalDate.parse("2021-02-21"));
        newEmployee.setHireDate(LocalDate.parse("2022-02-21"));

        Employee createdEmployee = sut.createEmployee(newEmployee);

        assertNotNull(createdEmployee, "createEmployee returned a null employee.");
        assertTrue(createdEmployee.getId() > 0, "createEmployee did not return a employee with id set.");
        assertEquals(1, newEmployee.getDepartmentId(), "createEmployee did not return an employee with the correct departmentId.");
        assertEquals("Test", newEmployee.getFirstName(), "createEmployee did not return an employee with the correct firstName.");
        assertEquals("Testerson", newEmployee.getLastName(), "createEmployee did not return an employee with the correct lastName.");
        assertEquals(LocalDate.parse("2021-02-21"), newEmployee.getBirthDate(), "createEmployee did not return an employee with the correct birthDate.");
        assertEquals(LocalDate.parse("2022-02-21"), newEmployee.getHireDate(), "createEmployee did not return an employee with the correct hireDate.");

        // verify value was saved to database, retrieve it and compare values
        Employee retrievedEmployee = getEmployeeByIdForTestVerification(createdEmployee.getId());
        assertNotNull(retrievedEmployee, "createEmployee does not appear to have correctly persisted the newly created employee. It could not be found by id.");
        assertEmployeesMatch(createdEmployee, retrievedEmployee, "createEmployee does does not appear to have fully persisted the newly created employee. The retrieved employee is incorrect/incomplete.");
    }

    @Test
    public void createEmployee_throws_dao_exception_for_data_integrity_violation() {
        Employee newEmployee = new Employee();
        newEmployee.setDepartmentId(999); // non-existent department_id
        newEmployee.setFirstName("Test");
        newEmployee.setLastName("Testerson");
        newEmployee.setBirthDate(LocalDate.parse("2021-02-21"));
        newEmployee.setHireDate(LocalDate.parse("2022-02-21"));

        String methodName = "createEmployee";
        try {
            sut.createEmployee(newEmployee);
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
    public void updateEmployee_updates_employee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId(EMPLOYEE_2.getId());
        existingEmployee.setDepartmentId(1);
        existingEmployee.setFirstName("TestUpdate");
        existingEmployee.setLastName("UpdateTesterson");
        existingEmployee.setBirthDate(LocalDate.parse("2003-02-21"));
        existingEmployee.setHireDate(LocalDate.parse("2023-02-21"));

        Employee updatedEmployee = sut.updateEmployee(existingEmployee);
        assertNotNull(updatedEmployee, "updateEmployee returned a null employee.");
        assertEmployeesMatch(updatedEmployee, existingEmployee, "updateEmployee returned an incorrect/incomplete employee.");

        // verify value was saved to database, retrieve it and compare values
        Employee retrievedEmployee = getEmployeeByIdForTestVerification(EMPLOYEE_2.getId());
        assertEmployeesMatch(updatedEmployee, retrievedEmployee, "updateEmployee does not appear to have fully persisted the updated employee. The retrieved employee is incorrect/incomplete.");
    }

    @Test
    public void updateEmployee_throws_dao_exception_for_data_integrity_violation() {
        Employee newEmployee = new Employee();
        newEmployee.setDepartmentId(999); // non-existent department_id
        newEmployee.setFirstName("Test");
        newEmployee.setLastName("Testerson");
        newEmployee.setBirthDate(LocalDate.parse("2021-02-21"));
        newEmployee.setHireDate(LocalDate.parse("2022-02-21"));

        String methodName = "updateEmployee";
        try {
            sut.updateEmployee(newEmployee);
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
    public void deleteEmployeeById_deletes_employee() {
        int recordsAffected = sut.deleteEmployeeById(EMPLOYEE_1.getId());
        assertEquals(1, recordsAffected, "deleteEmployeeById did not return the correct number of rows affected.");
        Employee retrievedEmployee = getEmployeeByIdForTestVerification(EMPLOYEE_1.getId());
        assertNull(retrievedEmployee, "deleteEmployeeById did not remove employee from database.");
    }

    @Test
    public void deleteEmployeesByDepartmentId_deletes_employees() {
        int recordsAffected = sut.deleteEmployeesByDepartmentId(EMPLOYEE_1.getDepartmentId());
        assertEquals(3, recordsAffected, "deleteEmployeesByDepartmentId did not return the correct number of rows affected.");
        Employee retrievedEmployee = getEmployeeByIdForTestVerification(EMPLOYEE_1.getId());
        assertNull(retrievedEmployee, "deleteEmployeesByDepartmentId did not remove employees from database.");
    }

    @Test
    public void deleteEmployeeById_with_invalid_id_returns_zero_rows_affected() {
        int recordsAffected = sut.deleteEmployeeById(999); // non-existent employee_id
        assertEquals(0, recordsAffected, "deleteEmployeeById with invalid id did not return the correct number of rows affected.");
    }

    @Test
    public void deleteEmployeesByDepartmentId_with_invalid_id_returns_zero_rows_affected() {
        int recordsAffected = sut.deleteEmployeesByDepartmentId(999); // non-existent department_id
        assertEquals(0, recordsAffected, "deleteEmployeesByDepartmentId with invalid id did not return the correct number of rows affected.");
    }

    @Test
    public void employee_dao_get_methods_throw_dao_exception_for_invalid_connection() {
        String methodName = "";

        methodName = "getEmployeeById";
        try {
            invalidConnectionDao.getEmployeeById(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "getEmployees";
        try {
            invalidConnectionDao.getEmployees();
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "getEmployeesByFirstNameLastName";
        try {
            invalidConnectionDao.getEmployeesByFirstNameLastName("First1", "Last1");
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "getEmployeesByProjectId";
        try {
            invalidConnectionDao.getEmployeesByProjectId(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "getEmployeesWithoutProjects";
        try {
            invalidConnectionDao.getEmployeesWithoutProjects();
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
    public void employee_dao_insert_update_delete_methods_throw_dao_exception_for_invalid_connection() {
        String methodName = "";

        methodName = "createEmployee";
        try {
            invalidConnectionDao.createEmployee(EMPLOYEE_1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "updateEmployee";
        try {
            invalidConnectionDao.updateEmployee(EMPLOYEE_1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "deleteEmployeeById";
        try {
            invalidConnectionDao.deleteEmployeeById(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "deleteEmployeesByDepartmentId";
        try {
            invalidConnectionDao.deleteEmployeesByDepartmentId(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }
    }

    public static void assertEmployeesMatch(Employee expected, Employee actual, String message) {
        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getDepartmentId(), actual.getDepartmentId(), message);
        assertEquals(expected.getFirstName(), actual.getFirstName(), message);
        assertEquals(expected.getLastName(), actual.getLastName(), message);
        assertEquals(expected.getBirthDate(), actual.getBirthDate(), message);
        assertEquals(expected.getHireDate(), actual.getHireDate(), message);
    }

    // test-specific implementation of getEmployeeById to be independent of DAO class
    private Employee getEmployeeByIdForTestVerification(int id) {
        Employee employee = null;
        String sql = "SELECT employee_id, department_id, first_name, last_name, birth_date, hire_date " +
                "FROM employee WHERE employee_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            Employee mappedEmployee = new Employee();
            mappedEmployee.setId(results.getInt("employee_id"));
            mappedEmployee.setDepartmentId(results.getInt("department_id"));
            mappedEmployee.setFirstName(results.getString("first_name"));
            mappedEmployee.setLastName(results.getString("last_name"));
            mappedEmployee.setBirthDate(results.getDate("birth_date").toLocalDate());
            mappedEmployee.setHireDate(results.getDate("hire_date").toLocalDate());
            employee = mappedEmployee;
        }
        return employee;
    }
}
