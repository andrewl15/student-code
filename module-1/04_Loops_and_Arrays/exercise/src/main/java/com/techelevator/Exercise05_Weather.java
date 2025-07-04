package com.techelevator;

public class Exercise05_Weather {

    private final static int FREEZING_TEMPERATURE = 32;

    /*
    GaleForce Meteorologists recently developed a new weather station and need it to perform
    some common measurements for reporting.

    Note: Assume all temperatures are in Fahrenheit (°F) unless otherwise
    noted.
     */

    /*
    GaleForce needs to know the number of days in the upcoming forecast
    where the temperature is at or below freezing.

    Given an array of high temperatures, count the number of days when
    the high temperature is freezing (32° F) or below.

    Examples:
	belowFreezing([33, 30, 32, 37, 44, 31, 41]) → 3
	belowFreezing([-7, -3, 19, 35, 30])  → 4
	belowFreezing([]) → 0
    */
	public int belowFreezing(int[] dailyHighs) {
		int daysFreezing = 0;
        for(int i = 0; i < dailyHighs.length; i++){
            if(dailyHighs[i] <= 32){
                daysFreezing += 1;
            }
        }
        return daysFreezing;
	}

    /*
    GaleForce also needs to determine the hottest day when given an upcoming forecast.

    Given an array of high temperatures, determine the hottest temperature.

    Note: The array of high temperatures is guaranteed to have at least one
    element.

    Examples:
	hottestDay([81, 93, 94, 105, 99, 95, 101, 90, 89, 92]) → 105
	hottestDay([23, 24] → 24
	hottestDay([34, 33] → 34
	hottestDay([55]) → 55
    */
    public int hottestDay(int[] dailyHighs) {
        int tempCadidate = dailyHighs[0];
        for(int i = 1; i < dailyHighs.length; i++){
            if(dailyHighs[i] > tempCadidate){
                tempCadidate = dailyHighs[i];
            }
        }
        return tempCadidate;
    }

    /*
    GaleForce discovered an equipment malfunction. Every other reading, starting with the first,
    was off by 2 degrees Fahrenheit (°F).

    Given an array of Fahrenheit temperatures, fix each of the incorrect readings by adding
    2 degrees to its current value.

    Examples:
	fixTemperatures([33, 30, 32, 37, 44, 31, 41]) → [35, 30, 34, 37, 46, 31, 43]
	fixTemperatures([-7, -33, 19, 35]) → [-5, -33, 21, 35]
	fixTemperatures([-1, 0, 1] → [1, 0, 3]
    fixTemperatures([-1] → [1]
	fixTemperatures([]) → []
     */
    public int[] fixTemperatures(int[] temperatures) {
        int[] fixedTemperatures = new int[temperatures.length];
        for(int i = 0; i < temperatures.length; i++){
            if(i % 2 == 0) {
                fixedTemperatures[i] = temperatures[i] + 2;
            }else{
                fixedTemperatures[i] = temperatures[i];
            }
        }
        return fixedTemperatures;
    }
}
