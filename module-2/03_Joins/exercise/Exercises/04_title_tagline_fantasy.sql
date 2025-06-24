-- 4. The titles and taglines of all the movies that are in the Fantasy genre.
-- Order the results by title (A-Z).
-- (81 rows)

select title, tagline from movie
join movie_genre as mg on movie.movie_id = mg.movie_id
join genre on genre.genre_id = mg.genre_id
where genre_name = 'Fantasy'
order by title;
