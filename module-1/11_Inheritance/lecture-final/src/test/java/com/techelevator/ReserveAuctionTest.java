package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ReserveAuctionTest {

    private static Class clazz;

    @BeforeAll
    public static void classShouldExist() {
        try {
            clazz = Class.forName("com.techelevator.ReserveAuction");
        } catch (Exception e) {
            fail("com.techelevator.ReserveAuction class does not exist");
        }
    }

    @Test
    public void bid_is_ignored_if_less_than_reserve() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor constructor = SafeReflection.getConstructor(clazz, String.class, int.class);
        Object reserveAuction = constructor.newInstance("The Thing", 100);

        Method placeBid = reserveAuction.getClass().getMethod("placeBid", Bid.class);
        placeBid.invoke(reserveAuction, new Bid("Cheapskate", 99));
        Method getAllBids = reserveAuction.getClass().getMethod("getAllBids");
        List<Bid> allBids = (List<Bid>) getAllBids.invoke(reserveAuction);

        assertEquals(0, allBids.size());
    }

    @Test
    public void bid_is_accepted_if_bid_is_equal_to_reserve() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor constructor = SafeReflection.getConstructor(clazz, String.class, int.class);
        Object reserveAuction = constructor.newInstance("The Thing", 100);

        Method placeBid = reserveAuction.getClass().getMethod("placeBid", Bid.class);
        placeBid.invoke(reserveAuction, new Bid("Bidder Bob", 100));
        Method getHighBid = reserveAuction.getClass().getMethod("getHighBid");
        Bid highBid = (Bid) getHighBid.invoke(reserveAuction);

        assertEquals("Bidder Bob", highBid.getBidder());
        assertEquals(100, highBid.getBidAmount());
    }
}
