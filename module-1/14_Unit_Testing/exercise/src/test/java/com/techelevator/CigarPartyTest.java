package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CigarPartyTest {
    private int cigars;
    private boolean isWeekend;

    CigarParty method = new CigarParty();

    @Test
    public void haveParty_given_cigars_in_normal_range_returns_true(){
        cigars = 50;
        isWeekend = false;

        assertTrue(method.haveParty(cigars, isWeekend));
    }

    @Test
    public void havePart_give_not_enough_cigars_returns_false(){
        cigars = 39;
        isWeekend = false;

        assertFalse(method.haveParty(cigars, isWeekend));
    }
    @Test
    public void havePart_given_more_than_60_cigars_and_isWeekend_returns_true(){
        cigars = 70;
        isWeekend = true;

        assertTrue(method.haveParty(cigars, isWeekend));
    }
    @Test
    public void havePart_given_more_than_60_cigars_and_not_isWeekend_returns_false(){
        cigars = 70;
        isWeekend = false;

        assertFalse(method.haveParty(cigars, isWeekend));
    }
    @Test
    public void havePart_given_less_than_40_cigars_and_isWeekend_returns_false(){
        cigars = 20;
        isWeekend = true;

        assertFalse(method.haveParty(cigars, isWeekend));
    }

}
