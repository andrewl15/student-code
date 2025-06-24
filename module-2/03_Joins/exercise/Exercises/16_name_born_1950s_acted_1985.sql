-- 16. The names and birthdays of actors born in the 1950s who acted in movies that were released in 1985.
-- Order the results by actor from oldest to youngest.
-- (20 rows)
select distinct person_name, birthday from person
join movie_actor as ma on ma.actor_id = person_id
join movie on movie.movie_id = ma.movie_id
where birthday >= '1/1/1950' and birthday <= '12/31/1959' and movie.release_date >= '1/1/1985' and movie.release_date <= '12/31/1985'
order by birthday;