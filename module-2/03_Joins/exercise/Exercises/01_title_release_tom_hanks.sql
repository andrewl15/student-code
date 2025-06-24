-- 1. The titles and release dates of movies that Tom Hanks has appeared in.
-- Order the results by release date, newest to oldest.
-- (47 rows)
SELECT title,release_date from movie
JOIN movie_actor as ma on ma.movie_id = movie.movie_id
JOIN person on ma.actor_id = person_id
WHERE person_name = 'Tom Hanks'
ORDER BY release_date desc;
