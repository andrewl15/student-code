-- 2. The names and birthdays of actors in "The Fifth Element"
-- Order the results alphabetically (A-Z) by name.
-- (15 rows)
select person_name, birthday from person
join movie_actor as ma on person_id = ma.actor_id
join movie on ma.movie_id = movie.movie_id
where title = 'The Fifth Element'
order by person_name;


