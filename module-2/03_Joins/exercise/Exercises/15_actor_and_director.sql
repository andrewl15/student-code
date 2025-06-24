-- 15. The title of the movie and the name of director for movies where the director was also an actor in the same movie.
-- Order the results by movie title (A-Z)
-- (73 rows)
select title, director.person_name from movie
join person as director on director_id = person_id
join movie_actor as ma on movie.movie_id = ma.movie_id
join person as actor on actor.person_id = ma.actor_id
where director.person_name = actor.person_name
order by title;
