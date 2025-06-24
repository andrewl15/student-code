-- 2. The name and area of all cities in Pennsylvania (PA).
-- Order the results in reverse alphabetical order (Z-A) by city name.
-- (4 rows)

Select city_name, area from city
	where state_abbreviation = 'PA'
	order by city_name desc;