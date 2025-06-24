-- 7. Write a DELETE query to remove the actor appearances in "Avengers: Infinity War" (14 rows)
--    Note: Don't remove the actors themselves, just make it so it seems no one appeared in the movie.
delete from movie_actor
where movie_id = (
	select movie_id from movie where title = 'Avengers: Infinity War');
