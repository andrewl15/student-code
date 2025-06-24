-- 17. The titles and taglines of movies that are in the "Family" genre that Samuel L. Jackson has acted in.
-- Order the results alphabetically by movie title.
-- (4 rows)
select title, tagline from movie
join movie_actor as ma on ma.movie_id = movie.movie_id
join person on person_id = ma.actor_id
join movie_genre as mg on movie.movie_id = mg.movie_id
join genre on genre.genre_id = mg.genre_id
where genre.genre_name = 'Family' and person_name = 'Samuel L. Jackson'
order by title;
