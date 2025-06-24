-- 6. The genres of "The Wizard of Oz" sorted in alphabetical order (A-Z).
-- (3 rows)
select genre_name from genre
join movie_genre as mg on mg.genre_id = genre.genre_id
join movie on movie.movie_id = mg.movie_id
where movie.title = 'The Wizard of Oz'
order by genre_name ASC;
