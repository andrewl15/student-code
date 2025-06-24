-- 5. The titles and release dates of all the movies that are in the Comedy genre.
-- Order the results by release date, earliest to latest.
-- (220 rows)
select title, release_date from movie
join movie_genre as mg on mg.movie_id = movie.movie_id
join genre on genre.genre_id = mg.genre_id
where genre_name = 'Comedy'
order by release_date;
