package com.techelevator;

public class Exercise01_StoreOrders {

    /*
    Sally's Pizza is bringing its pizza ordering system into the digital age
    to get better customer insights.

    They've encoded each of Sally's pizzas and other dishes as an integer:
	 * 10: small, cheese       20: medium, cheese       30: large, cheese
	 * 11: small, pepperoni    21: medium, pepperoni    31: large, pepperoni
	 * ---
	 * 40: calzone
	 * 41: spaghetti pie
	 * 42: baked ziti
     */

    // You can use these constants in your solutions.
    private final int SMALL_CHEESE = 10;
    private final int SMALL_PEPPERONI = 11;

    private final int MEDIUM_CHEESE = 20;
    private final int MEDIUM_PEPPERONI = 21;

    private final int LARGE_CHEESE = 30;
    private final int LARGE_PEPPERONI = 31;

    private final int CALZONE = 40;
    private final int SPAGHETTI_PIE = 41;
    private final int BAKED_ZITI = 42;

    /*
    Each customer order, consisting of one or more items, is represented as an array
    where each value represents an item in that order.

    Create an "order" that contains the following items:
	 * - small, cheese (SMALL_CHEESE)
	 * - calzone (CALZONE)
	 * - large, pepperoni (LARGE_PEPPERONI)
	 * - spaghetti pie (SPAGHETTI_PIE)

	 Examples:
	 createOrder() → [10, 40, 31, 41]
     */
    public int[] createOrder() {
        // create an array to hold my order
        int[] customerOrder;
        // initialize that array to hold 4 items
        customerOrder = new int[4];
        // add the items in the right order to the array
        customerOrder[0] = SMALL_CHEESE;
        customerOrder[1] = CALZONE;
        customerOrder[2] = LARGE_PEPPERONI;
        customerOrder[3] = SPAGHETTI_PIE;
        // return that array
        return customerOrder;
    }

    /*
    Sally realized that she needed to know how many calzones her shop sells per day.

    Implement the logic to count the number of calzones sold per day when given an
    array representing each item that her customers ordered that day.

    Examples:
    getCalzoneSales([CALZONE, LARGE_CHEESE, LARGE_PEPPERONI, CALZONE, SMALL_CHEESE]) → 2
    which is the same as:
    getCalzoneSales([40, 30, 31, 40, 10]) → 2

    getCalzoneSales([LARGE_CHEESE, LARGE_PEPPERONI, SMALL_CHEESE]) → 0
    getCalzoneSales([]) → 0
     */
    public int getCalzoneSales(int[] orders) {
        int calzoneCount = 0;
        for(int i = 0; i < orders.length; i++){
            if(orders[i] == CALZONE){
                calzoneCount += 1;
            }
        }
        return calzoneCount;
    }

    /*
    Sally also needs to know the total revenue for all cheese pizzas sold on any given day.
        * Each small cheese pizza costs $8.
        * Each medium cheese pizza costs $11.
        * Each large cheese pizza costs $14.

    Implement the logic to return the total revenue of all cheese pizzas when given
    an array representing each item that her customers ordered that day.

    Examples:
    getCheesePizzaRevenue([SMALL_CHEESE]) → 8
    getCheesePizzaRevenue([SMALL_CHEESE, SMALL_PEPPERONI, MEDIUM_CHEESE]) → 19
    getCheesePizzaRevenue([SMALL_PEPPERONI, MEDIUM_PEPPERONI]) → 0
     */
    public int getCheesePizzaRevenue(int[] orders) {
        int totalRevenue = 0;
        for(int i = 0; i < orders.length; i++){
            if(orders[i] == SMALL_CHEESE){
                totalRevenue += 8;
            }
            else if(orders[i] == MEDIUM_CHEESE){
                totalRevenue += 11;
            }else if(orders[i] == LARGE_CHEESE){
                totalRevenue += 14;
            }
        }
        return totalRevenue;
    }
}
