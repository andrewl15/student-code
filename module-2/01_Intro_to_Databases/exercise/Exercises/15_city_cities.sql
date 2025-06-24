-- 15. The name, state abbreviation, and population for cities that end with the word "City".
-- Order the results by population, largest first.
-- (11 rows)
select city_name, state_abbreviation, population from city
	where city_name like '%City'
	order by population desc;
