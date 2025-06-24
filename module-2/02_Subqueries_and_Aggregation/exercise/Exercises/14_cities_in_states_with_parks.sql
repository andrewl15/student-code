-- 14. The city name and state abbreviation for all states with a national park.
-- Order the results by state abbreviation, then city name, both in alphabetical order.
-- (261 rows)
select city_name, state_abbreviation from city
	where state_abbreviation in(select state_abbreviation from park_state)
	order by state_abbreviation, city_name;
