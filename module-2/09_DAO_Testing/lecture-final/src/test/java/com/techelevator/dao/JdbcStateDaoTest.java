package com.techelevator.dao;

import com.techelevator.model.State;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class JdbcStateDaoTest extends BaseDaoTest {

    private static final State STATE_A = new State("AA", "State A");
    private static final State STATE_B = new State("BB", "State B");
    private static final State STATE_C = new State("CC", "State C");

    private JdbcStateDao sut;

    @BeforeEach
    public void setup() {
        sut = new JdbcStateDao(dataSource);
    }

    @Test
    public void getStateByAbbreviation_with_valid_abbreviation_returns_correct_state() {
        State state = sut.getStateByAbbreviation("AA");
        assertStatesMatch(STATE_A, state);

        state = sut.getStateByAbbreviation("BB");
        assertStatesMatch(STATE_B, state);
    }

    @Test
    public void getStateByAbbreviation_with_invalid_abbreviation_returns_null_state() {
        State state = sut.getStateByAbbreviation("XX");
        assertNull(state);
    }

    @Test
    public void getStateByCapital_with_valid_city_returns_correct_state() {
        State state = sut.getStateByCapital(1);
        assertStatesMatch(STATE_A, state);

        state = sut.getStateByCapital(2);
        assertStatesMatch(STATE_B, state);
    }

    @Test
    public void getStateByCapital_with_invalid_city_returns_null_state() {
        State state = sut.getStateByCapital(99); // not in table
        assertNull(state);

        state = sut.getStateByCapital(4); // in table, but not capital
        assertNull(state);
    }

    @Test
    public void getStates_returns_correct_states() {
        List<State> states = sut.getStates();

        assertEquals(3, states.size());

        assertStatesMatch(STATE_A, states.get(0));
        assertStatesMatch(STATE_B, states.get(1));
        assertStatesMatch(STATE_C, states.get(2));
    }

    private void assertStatesMatch(State expected, State actual) {
        assertEquals(expected.getStateAbbreviation(), actual.getStateAbbreviation());
        assertEquals(expected.getStateName(), actual.getStateName());
    }
}
