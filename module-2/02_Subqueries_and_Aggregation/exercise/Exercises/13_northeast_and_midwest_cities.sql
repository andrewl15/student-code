-- 13. The city name, state abbreviation, and population for all cities in the Northeast and Midwest census regions.
-- Order the results by state abbreviation first (alphabetical), then by population (largest first).
-- (84 rows)
select city_name, state_abbreviation, population from city
	where state_abbreviation in(select state_abbreviation from state where census_region in('Northeast', 'Midwest'))
	order by state_abbreviation, population desc;