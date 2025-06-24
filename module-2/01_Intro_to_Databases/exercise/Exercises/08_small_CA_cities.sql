-- 8. The name and population of cities in California (CA) with a population less than 150,000 people.
-- Order the results by population, smallest first.
-- (37 rows)
select city_name, population from city
	where state_abbreviation = 'CA' and population < 150000
	order by population;
