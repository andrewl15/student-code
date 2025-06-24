-- 17. The name, population, and area of all records in the state table with no census region (NULL) and area less than 5000 square kilometers.
-- Order the results by area, largest first.
-- (3 rows)
select state_name, population, area from state
	where census_region is NULL and area < 5000
	order by area desc;
