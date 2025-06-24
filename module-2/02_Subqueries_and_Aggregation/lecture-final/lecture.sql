-- CONCATENATING OUTPUTS

-- All city names and their state abbreviation.
select city_name || ' (' || state_abbreviation || ')' from city;

-- All park names and area.
select 'Name: ' || park_name || ' Area: ' || area as name_area from park;

-- The census region and state name of all states in the West & Midwest 
-- sorted in ascending order.
select state_name || ': ' || census_region from state
	WHERE census_region IN('West','Midwest')
	ORDER BY census_region;

select state_name || ': ' || census_region from state
	WHERE census_region = 'West' OR census_region = 'Midwest'
	ORDER BY census_region;

select state_name || ': ' || census_region from state
	WHERE census_region ILIKE '%west'
	ORDER BY census_region;
	
-- SUBQUERIES

-- List all cities in the western census region
select * from city 
where state_abbreviation in(select state_abbreviation from state where census_region = 'West');

-- List all cities in states with "garden" in their nickname
select * from city 
where state_abbreviation in (select state_abbreviation from state where state_nickname ILIKE '%garden%');

-- Get the state name and census region for all states with a national park
select state_name,census_region from state
WHERE state_abbreviation in (select state_abbreviation from park_state)
	AND census_region is not null
order by census_region;


-- AGGREGATE FUNCTIONS

-- The number of cities with populations greater than 1 million
select count(population) from city where population > 1000000;

-- The number of state nicknames.
select count(state_nickname) from state;
select * from state where state_nickname is null;


-- Average population across all the states. 
-- Note the use of alias, common with aggregated values.
select AVG(population) as average_population from state;

-- Total population in the West and South census regions
select sum(population) as total_west_south from state where census_region in ('West','South');

-- The area of the smallest and largest parks.
select MIN(area), MAX(area) from park;

-- Get the park names with the smallest and largest areas
-- Note you can use a subquery to get the park names with the smallest and largest areas.
select park_name, area from park
where area = (select min(area) from park) OR
	area = (select max(area) from park);

-- GROUP BY

-- Count the number of cities in each state, ordered from most cities to least.
select count(city_name) as city_count,state_abbreviation from city
 group by state_abbreviation
 order by city_count desc;

-- Determine the average park area depending upon whether parks allow camping or not.
select avg(area),has_camping from park
group by has_camping;

-- Sum of the population of cities in each state ordered by state abbreviation.
select sum(population),state_abbreviation from city
group by state_abbreviation
order by state_abbreviation;

-- The smallest city population in each state ordered by city population.
select min(population) as smallest_city,state_abbreviation from city
group by state_abbreviation
order by smallest_city;
