package com.techelevator.projects.dao;

import com.techelevator.projects.model.Timesheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class JdbcTimesheetDaoTest extends BaseDaoTest {

    private static final Timesheet TIMESHEET_1 = new Timesheet(1, 1, 1,
            LocalDate.parse("2021-01-01"), 1.0, true, "Timesheet 1");
    private static final Timesheet TIMESHEET_2 = new Timesheet(2, 1, 1,
            LocalDate.parse("2021-01-02"), 1.5, true, "Timesheet 2");
    private static final Timesheet TIMESHEET_3 = new Timesheet(3, 2, 1,
            LocalDate.parse("2021-01-01"), 0.25, true, "Timesheet 3");
    private static final Timesheet TIMESHEET_4 = new Timesheet(4, 2, 2,
            LocalDate.parse("2021-02-01"), 2.0, false, "Timesheet 4");

    private JdbcTimesheetDao dao;


    @BeforeEach
    public void setup() {
        dao = new JdbcTimesheetDao(dataSource);
    }

    @Test
    public void getTimesheetById_with_valid_id_returns_correct_timesheet() {
        Timesheet timesheet = dao.getTimesheetById(1);
        assertTimesheetsMatch(TIMESHEET_1, timesheet);

        timesheet = dao.getTimesheetById(2);
        assertTimesheetsMatch(TIMESHEET_2, timesheet);

        timesheet = dao.getTimesheetById(3);
        assertTimesheetsMatch(TIMESHEET_3, timesheet);

        timesheet = dao.getTimesheetById(4);
        assertTimesheetsMatch(TIMESHEET_4, timesheet);
    }

    @Test
    public void getTimesheetById_with_invalid_id_returns_null_timesheet() {
        Timesheet noTimesheet = dao.getTimesheetById(17);
        assertNull(noTimesheet);
    }

    @Test
    public void getTimesheetsByEmployeeId_with_valid_employee_id_returns_list_of_timesheets_for_employee() {
        List<Timesheet> timesheets = dao.getTimesheetsByEmployeeId(1);
        assertEquals(2, timesheets.size());
        assertTimesheetsMatch(TIMESHEET_1, timesheets.get(0));
        assertTimesheetsMatch(TIMESHEET_2, timesheets.get(1));

        timesheets = dao.getTimesheetsByEmployeeId(2);
        assertEquals(2, timesheets.size());
        assertTimesheetsMatch(TIMESHEET_3, timesheets.get(0));
        assertTimesheetsMatch(TIMESHEET_4, timesheets.get(1));
    }

    @Test
    public void getTimesheetsByEmployeeId_with_invalid_employee_id_returns_empty_list_of_timesheets() {
        List<Timesheet> timesheets = dao.getTimesheetsByEmployeeId(3);
        assertEquals(0, timesheets.size());
    }

    @Test
    public void getTimesheetsByProjectId_with_valid_project_id_returns__list_of_timesheets() {
        List<Timesheet> timesheets = dao.getTimesheetsByProjectId(1);
        assertEquals(3, timesheets.size());
        assertTimesheetsMatch(TIMESHEET_1, timesheets.get(0));
        assertTimesheetsMatch(TIMESHEET_2, timesheets.get(1));
        assertTimesheetsMatch(TIMESHEET_3, timesheets.get(2));
    }
    @Test
    public void getTimesheetsByProjectId_with_invalid_project_id_returns_empty_list_of_timesheets() {
        List<Timesheet> timesheets = dao.getTimesheetsByProjectId(3);
        assertEquals(0, timesheets.size());
    }

    @Test
    public void createTimesheet_creates_timesheet() {
        Timesheet timesheetToAdd = new Timesheet(0,2,2, LocalDate.now(), 1.0, false, "Timesheet 5");
        Timesheet returnedTimesheet = dao.createTimesheet(timesheetToAdd);

        assertNotNull(returnedTimesheet);

        assertTrue(returnedTimesheet.getTimesheetId() > 0);

        Timesheet inDB = dao.getTimesheetById(returnedTimesheet.getTimesheetId());
        assertTimesheetsMatch(inDB, returnedTimesheet);
    }
    @Test
    public void updateTimesheet_updates_timesheet(){
        Timesheet timesheetToUpdate = dao.getTimesheetById(1);

        timesheetToUpdate.setEmployeeId(2);
        timesheetToUpdate.setProjectId(2);
        timesheetToUpdate.setDateWorked(LocalDate.now());
        timesheetToUpdate.setHoursWorked(2.0);
        timesheetToUpdate.setBillable(false);
        timesheetToUpdate.setDescription("Timesheet 2");

        Timesheet updatedTimesheet = dao.updateTimesheet(timesheetToUpdate);
        // make sure I get back a park
        assertNotNull(updatedTimesheet);
        // get the update park from the database
        Timesheet updateDB = dao.getTimesheetById(timesheetToUpdate.getTimesheetId());
        // make sure it matches what I sent in
        assertTimesheetsMatch(timesheetToUpdate,updateDB);
        assertTimesheetsMatch(updatedTimesheet,updateDB);
    }
    @Test
    public void deleteTimesheetById_deletes_timesheet() {
        int rowsAffected = dao.deleteTimesheetById(1);
        assertEquals(1,rowsAffected);

        // validate the park is actually gone from db
        Timesheet noTimesheet = dao.getTimesheetById(1);
        assertNull(noTimesheet);
    }

    @Test
    public void getBillableHours_returns_correct_total() {
        double expected = 2.5;
        double actual = dao.getBillableHours(1, 1);
        assertEquals(expected, actual);
    }

    private void assertTimesheetsMatch(Timesheet expected, Timesheet actual) {
        assertEquals(expected.getTimesheetId(), actual.getTimesheetId());
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getProjectId(), actual.getProjectId());
        assertEquals(expected.getDateWorked(), actual.getDateWorked());
        assertEquals(expected.getHoursWorked(), actual.getHoursWorked(), 0.001);
        assertEquals(expected.isBillable(), actual.isBillable());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

}
