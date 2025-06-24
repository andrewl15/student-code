-- 6. The name, abbreviation, population, and area of states with an area greater than 200,000 square kilometers.
-- Order the results by area, highest first.
-- (16 rows)
select state_name, state_abbreviation, population, area from state
	where area > 200000
	order by area desc;
