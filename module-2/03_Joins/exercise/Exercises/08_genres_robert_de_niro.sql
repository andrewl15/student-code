-- 8. The genres of movies that Robert De Niro has appeared in that were released in 2010 or later, sorted alphabetically.
-- (6 rows)
select distinct genre_name from genre
join movie_genre as mg on mg.genre_id = genre.genre_id
join movie on movie.movie_id = mg.movie_id
join movie_actor as ma on ma.movie_id = mg.movie_id
join person on person_id = ma.actor_id
where person_name = 'Robert De Niro' and movie.release_date >= '1/1/2010'
order by genre_name;
