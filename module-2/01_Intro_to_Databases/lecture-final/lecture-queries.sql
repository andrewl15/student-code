-- SELECT
-- Use a SELECT statement to return a literal string
SELECT 'Hello World!';

-- Use a SELECT statement to add two numbers together (and label the result "sum")
SELECT 2 + 2 AS sum;
-- if I need a space, use double quotes for the column name
SELECT 2 + 2 AS "Our Sum";
SELECT 2 + 2 AS our_sum;


-- SELECT ... FROM
-- Write queries to retrieve...

-- The names from all the records in the state table
SELECT state_name FROM state;

-- The names and populations of all cities
SELECT city_name, population FROM city;
SELECT population,city_name FROM city;

-- All columns from the park table
SELECT * FROM park;


-- ORDERING RESULTS

-- Populations of all states from  smallest to largest.
-- Note ASC is default
SELECT state_name,population FROM state
	ORDER BY population ASC;

-- Change sort order to DESC to see largest to smallest
SELECT state_name, population FROM state
	ORDER BY population DESC;

-- States sorted alphabetically (A-Z) within their census region. 
-- The census regions are sorted in reverse alphabetical (Z-A) order.
-- Note the NULL values mean there is no census region assigned. (You'll see how to filter these out later.)
-- Note order of columns in the ORDER BY: census_region is the major sort, state_name the minor sort,
--   meaning it's sorted within the major column, i.e. census_region.
-- Note order of columns in the SELECT only controls order columns are returned (i.e. "displayed"), not sort order.
SELECT state_name, census_region from state
	ORDER BY census_region DESC, state_name;

-- The biggest park by area
-- Note that area isn't in the SELECT, but can be used in the ORDER BY
SELECT park_name from park ORDER BY area desc LIMIT 1;


-- LIMITING RESULTS

-- The 10 largest cities by populations
SELECT city_name FROM city order by population DESC LIMIT 10;

-- The 20 oldest parks from oldest to youngest in years, sorted alphabetically by name.
SELECT CURRENT_DATE;
SELECT (CURRENT_DATE - date_established) / 365,park_name FROM park
	ORDER BY date_established, park_name
	LIMIT 20;
SELECT (date_part('year',CURRENT_DATE));
SELECT (date_part('year',CURRENT_DATE)-date_part('year',date_established)) as age_in_years, park_name
	FROM park
	ORDER BY age_in_years DESC, park_name
	LIMIT 20;

-- SELECT __ FROM __ WHERE
-- Write queries to retrieve...

-- The names of cities in California (CA)
SELECT city_name from city WHERE state_abbreviation = 'CA';

-- The names and state abbreviations of cities NOT in California
SELECT city_name FROM city WHERE state_abbreviation != 'CA';

-- The names and areas of cities smaller than 25 square kilometers
SELECT city_name,area from city WHERE area < 25;

-- The names from all records in the state table that have no assigned census region
SELECT * from state WHERE census_region IS NULL;

-- The names and census regions from all records in the state table that have an assigned census region
SELECT * from state WHERE census_region IS NOT NULL;


-- WHERE with AND/OR
-- Write queries to retrieve...

-- The names, areas, and populations of cities smaller than 25 sq. km. with more than 100,000 people
SELECT city_name,area,population from city
	WHERE area < 25 AND population > 100000;

-- The names and census regions of all states (and territories and districts) 
-- not in the Midwest region (including those that don't have any census region)
SELECT state_name,census_region from state
	WHERE census_region != 'Midwest' OR census_region is NULL;

-- The names, areas, and populations of cities in California (CA) or Florida (FL)
SELECT city_name,area,population from city
	WHERE state_abbreviation = 'CA' OR state_abbreviation = 'FL';
-- rewrite using IN
SELECT city_name,area,population from city
	WHERE state_abbreviation IN('CA','FL');
	
-- The names, areas, and populations of cities in New England 
-- Connecticut (CT), Maine (ME), Massachusetts (MA), New Hampshire (NH), Rhode Island (RI) 
-- and Vermont (VT)
SELECT city_name,area,population from city
	WHERE state_abbreviation IN('CT','ME','MA','NH','RI','VT');


-- SELECT statements involving math
-- Write a query to retrieve the names and areas of all parks in square METERS
-- (the values in the database are stored in square kilometers)
-- Label the second column "area_in_square_meters"
SELECT park_name, (area * 1000000) as area_in_square_meters FROM park;


-- All values vs. distinct values

-- Write a query to retrieve the names of all cities and notice repeats (like Springfield and Columbus)
SELECT city_name from city
	order by city_name;

-- Do it again, but without the repeats (note the difference in row count)
SELECT DISTINCT city_name from city
	ORDER BY city_name;


-- LIKE
-- Write queries to retrieve...

-- The names of all cities that begin with the letter "A"
SELECT city_name FROM city
	WHERE city_name LIKE 'A%';

-- The names of all cities that end with "Falls"
SELECT city_name FROM city
	WHERE city_name LIKE '%Falls';

-- The names of all cities that contain a space
SELECT city_name from city
	WHERE city_name LIKE '% %';


-- BETWEEN
-- Write a query to retrieve the names and areas of parks of at least 100 sq. kilometers but no more than 200 sq. kilometers
SELECT park_name from Park
	WHERE area BETWEEN 100 and 200;


-- DATES
-- Write a query to retrieve the names and dates established of parks established before 1900
SELECT park_name,date_established from park
	WHERE date_established < '11/1/1900';
