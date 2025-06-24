-- 20. The name, population, area, and population density (name the column 'population_density') of states, territories, and districts with more than 100 people per square kilometer.
-- Population density is expressed as people per square kilometer. In other words, population divided by area.
-- Order the results by population density, highest first.
-- (12 rows)
select state_name, population, area, population / area as population_density from state
	where population / area > 100
	order by population_density desc;
