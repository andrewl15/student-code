package com.techelevator.projects.dao;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcDepartmentDaoTest extends BaseDaoTest {

    private static final Department DEPARTMENT_1 = new Department(1, "Department 1");
    private static final Department DEPARTMENT_2 = new Department(2, "Department 2");

    private JdbcDepartmentDao sut;
    private JdbcDepartmentDao invalidConnectionDao;

    @BeforeEach
    public void setup() {
        sut = new JdbcDepartmentDao(dataSource);
        invalidConnectionDao = new JdbcDepartmentDao(invalidDataSource);
    }

    @Test
    public void createDepartment_creates_department() {
        Department newDepartment = new Department();
        newDepartment.setName("New Department Test");

        Department createdDepartment = sut.createDepartment(newDepartment);
        assertNotNull(createdDepartment, "createDepartment returned a null department.");
        assertTrue(createdDepartment.getId() > 0, "createDepartment did not return a department with id set.");
        assertEquals(newDepartment.getName(), createdDepartment.getName(),
                "createDepartment did not return a department with the correct name.");

        // verify value was saved to database, retrieve it and compare values
        Department retrievedDepartment = getDepartmentByIdForTestVerification(createdDepartment.getId());
        assertNotNull(retrievedDepartment, "createDepartment does not appear to have correctly persisted the newly created department. It could not be found by id.");
        assertDepartmentsMatch(createdDepartment, retrievedDepartment,
                "createDepartment does not appear to have fully persisted the newly created department. The retrieved department is incorrect/incomplete.");
    }

    @Test
    public void createDepartment_throws_dao_exception_for_data_integrity_violation() {
        Department newDepartment = new Department();
        newDepartment.setName(DEPARTMENT_1.getName()); // non-unique name

        String methodName = "createDepartment";
        try {
            sut.createDepartment(newDepartment);
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
    public void updateDepartment_updates_department() {
        Department existingDepartment = new Department();
        existingDepartment.setId(DEPARTMENT_2.getId());
        existingDepartment.setName("Test Updated Project Name");

        Department updatedDepartment = sut.updateDepartment(existingDepartment);
        assertNotNull(updatedDepartment, "updateDepartment returned a null department.");
        assertDepartmentsMatch(existingDepartment, updatedDepartment, "updateDepartment returned an incorrect/incomplete department.");

        // verify value was saved to database, retrieve it and compare values
        Department retrievedDepartment = getDepartmentByIdForTestVerification(DEPARTMENT_2.getId());
        assertDepartmentsMatch(updatedDepartment, retrievedDepartment, "updateDepartment does not appear to have fully persisted the updated department. The retrieved department is incorrect/incomplete.");
    }

    @Test
    public void updateDepartment_throws_dao_exception_for_data_integrity_violation() {
        Department existingDepartment = new Department();
        existingDepartment.setId(DEPARTMENT_2.getId());
        existingDepartment.setName(DEPARTMENT_1.getName()); // non-unique name

        String methodName = "updateDepartment";
        try {
            sut.updateDepartment(existingDepartment);
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
    public void deleteDepartmentById_deletes_department() {
        int rowsAffected = sut.deleteDepartmentById(DEPARTMENT_1.getId());
        assertEquals(1, rowsAffected, "deleteDepartmentById did not return correct number of rows affected.");
        Department retrievedDepartment = getDepartmentByIdForTestVerification(DEPARTMENT_1.getId());
        assertNull(retrievedDepartment, "deleteDepartmentById did not remove department from database.");
    }

    @Test
    public void deleteDepartmentById_with_invalid_id_returns_zero_rows_affected()
    {
        int rowsAffected = sut.deleteDepartmentById(999); // non-existent department_id
        assertEquals(0, rowsAffected, "deleteDepartmentById with invalid id did not return the correct number of rows affected");
    }

    @Test
    public void department_dao_get_methods_throw_dao_exception_for_invalid_connection() {
        String methodName = "";

        methodName = "getDepartmentById";
        try {
            invalidConnectionDao.getDepartmentById(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "getDepartments";
        try {
            invalidConnectionDao.getDepartments();
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
    public void department_dao_insert_update_delete_methods_throw_dao_exception_for_invalid_connection() {
        String methodName = "";

        methodName = "createDepartment";
        try {
            invalidConnectionDao.createDepartment(DEPARTMENT_1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "updateDepartment";
        try {
            invalidConnectionDao.updateDepartment(DEPARTMENT_1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }

        methodName = "deleteDepartmentById";
        try {
            invalidConnectionDao.deleteDepartmentById(1);
            fail(didNotThrowAnyExceptionForCannotGetConnection(methodName));
        } catch (DaoException e) {
            if (e.getMessage().equalsIgnoreCase(methodName + NOT_IMPLEMENTED_LONG_SUFFIX)) {
                fail(threwNotImplementedException(methodName));
            }
        } catch (Exception e) {
            fail(didNotThrowDaoExceptionForCannotGetConnection(methodName));
        }
    }

    private void assertDepartmentsMatch(Department expected, Department actual, String message) {
        assertEquals(expected.getId(), actual.getId(), message);
        assertEquals(expected.getName(), actual.getName(), message);
    }

    // test-specific implementation of getDepartmentById to be independent of DAO class
    private Department getDepartmentByIdForTestVerification(int id) {
        Department department = null;
        String sql = "SELECT department_id, name FROM department WHERE department_id = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            Department mappedDepartment = new Department();
            mappedDepartment.setId(results.getInt("department_id"));
            mappedDepartment.setName(results.getString("name"));
            department = mappedDepartment;
        }
        return department;
    }
}
