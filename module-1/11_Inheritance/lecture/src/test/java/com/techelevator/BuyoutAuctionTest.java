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
public class BuyoutAuctionTest {

	private static Class clazz;

	@BeforeAll
	public static void classShouldExist() {
		try {
			clazz = Class.forName("com.techelevator.BuyoutAuction");
		} catch (Exception e) {
			fail("com.techelevator.BuyoutAuction class does not exist");
		}
	}

	@Test
	public void bids_made_after_buyout_price_met_are_ignored() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor constructor = SafeReflection.getConstructor(clazz, String.class, int.class);
		Object buyoutAuction = constructor.newInstance("The Thing", 100);

		Method placeBid = buyoutAuction.getClass().getMethod("placeBid", Bid.class);
		placeBid.invoke(buyoutAuction, new Bid("Buyout Bob", 100));
		placeBid.invoke(buyoutAuction, new Bid("Too Late Tom", 101));

		Method getAllBids = buyoutAuction.getClass().getMethod("getAllBids");
		Method getHighBid = buyoutAuction.getClass().getMethod("getHighBid");
		List<Bid> allBids = (List<Bid>) getAllBids.invoke(buyoutAuction);
		Bid highBid = (Bid) getHighBid.invoke(buyoutAuction);

		assertEquals(1, allBids.size());
		assertEquals("Buyout Bob", highBid.getBidder());
		assertEquals(100, highBid.getBidAmount());
	}

	@Test
	public void bids_greater_than_buyout_price_are_reduced_to_buyout_price() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor constructor = SafeReflection.getConstructor(clazz, String.class, int.class);
		Object buyoutAuction = constructor.newInstance("The Thing", 100);

		Method placeBid = buyoutAuction.getClass().getMethod("placeBid", Bid.class);
		placeBid.invoke(buyoutAuction, new Bid("Big Spender", 200));
		Method getHighBid = buyoutAuction.getClass().getMethod("getHighBid");
		Bid highBid = (Bid) getHighBid.invoke(buyoutAuction);

		assertEquals("Big Spender", highBid.getBidder());
		assertEquals(100, highBid.getBidAmount());
	}

}
