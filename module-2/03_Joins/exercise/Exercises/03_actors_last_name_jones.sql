-- 3. For all actors with the last name of "Jones", display the actor's name and movie titles they appeared in.
-- Order the results by the actor names (A-Z) and then by movie title (A-Z).
-- (48 rows)
select person.person_name, movie.title from person
join movie_actor as ma on person_id = ma.actor_id
join movie on ma.movie_id = movie.movie_id
where person_name like '% Jones'
order by person.person_name, movie.title;
