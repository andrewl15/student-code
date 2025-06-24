-- INNER JOIN

-- Write a query to retrieve the name and state abbreviation for the 2 cities named "Columbus" in the database
SELECT city_name,state_abbreviation from city
WHERE city_name = 'Columbus';

-- Modify the previous query to retrieve the names of the states (rather than their abbreviations).
SELECT city_name,state_name from city
JOIN state as s on city.state_abbreviation = s.state_abbreviation
WHERE city_name = 'Columbus';


-- Write a query to retrieve the names of all the national parks with their state abbreviations.
-- (Some parks will appear more than once in the results, because they cross state boundaries.)
select park_name, state_abbreviation from park
JOIN park_state as ps ON park.park_id = ps.park_id;

-- The park_state table is an associative table that can be used to connect the park and state tables.
-- Modify the previous query to retrieve the names of the states rather than their abbreviations.
select park_name, state_name from park
JOIN park_state as ps ON park.park_id = ps.park_id
JOIN state on state.state_abbreviation = ps.state_abbreviation;

-- Modify the previous query to include the name of the state's capital city.
select park_name, state_name, city_name from park
JOIN park_state as ps ON park.park_id = ps.park_id
JOIN state on state.state_abbreviation = ps.state_abbreviation
JOIN city on state.capital = city.city_id;

-- Modify the previous query to include the area of each park.
select park_name, state_name, city_name, park.area from park
JOIN park_state as ps ON park.park_id = ps.park_id
JOIN state on state.state_abbreviation = ps.state_abbreviation
JOIN city on state.capital = city.city_id;

-- Write a query to retrieve the names and populations of all the cities in the Midwest census region.
select city_name,city.population from city
JOIN state on state.state_abbreviation = city.state_abbreviation
WHERE census_region = 'Midwest';

-- Write a query to retrieve the number of cities in the city table for each state in the Midwest census region.
select count(city_id),state.state_name from city
JOIN state on state.state_abbreviation = city.state_abbreviation
WHERE census_region = 'Midwest'
group by state.state_abbreviation;

-- Modify the previous query to sort the results by the number of cities in descending order.
select count(city_id) as city_count,state.state_name from city
JOIN state on state.state_abbreviation = city.state_abbreviation
WHERE census_region = 'Midwest'
group by state.state_abbreviation
order by city_count desc;

-- LEFT JOIN

-- Write a query to retrieve the state name and the earliest date a park was established 
-- in that state (or territory) for every record in the state table that has park records associated with it.
SELECT state_name,MIN(date_established) as EarliestPark from state
JOIN park_state as ps on ps.state_abbreviation = state.state_abbreviation
JOIN park on park.park_id = ps.park_id
GROUP BY state_name;

-- Modify the previous query so the results include entries for all the records in the state table, 
-- even if they have no park records associated with them.
SELECT state_name,MIN(date_established) as EarliestPark from state
LEFT JOIN park_state as ps on ps.state_abbreviation = state.state_abbreviation
LEFT JOIN park on park.park_id = ps.park_id
GROUP BY state_name;


SELECT state_name,MIN(date_established) as EarliestPark from park_state as ps
RIGHT JOIN state  on ps.state_abbreviation = state.state_abbreviation
LEFT JOIN park on park.park_id = ps.park_id
GROUP BY state_name;

SELECT * from city
LEFT JOIN state on capital = city_id
order by city.state_abbreviation;

-- SELECT *  from state
-- LEFT JOIN park_state as ps on ps.state_abbreviation = state.state_abbreviation
-- JOIN park on park.park_id = ps.park_id
-- GROUP BY state_name;

-- MovieDB
-- After creating the MovieDB database and running the setup script, make sure it is selected in pgAdmin and confirm it is working correctly by writing queries to retrieve...

-- The names of all the movie genres
SELECT * from genre;

-- The titles of all the Comedy movies
SELECT title from movie
JOIN movie_genre as mg on mg.movie_id = movie.movie_id
JOIN genre on mg.genre_id = genre.genre_id
WHERE genre_name = 'Comedy';
