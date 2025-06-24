-- 7. The genres of movies that Christopher Lloyd has appeared in, sorted alphabetically.
-- (8 rows) Hint: DISTINCT will prevent duplicate values in your query results.
select DISTINCT genre_name from genre
join movie_genre as mg on mg.genre_id = genre.genre_id
join movie on movie.movie_id = mg.movie_id
join movie_actor as ma on ma.movie_id = mg.movie_id
join person on person_id = ma.actor_id
where person_name = 'Christopher Lloyd'
order by genre_name;
