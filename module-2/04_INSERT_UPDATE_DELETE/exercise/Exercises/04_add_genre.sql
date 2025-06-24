-- 4. Write an INSERT query to add a "Sports" genre to the genre table. Add the movie "Coach Carter" to the newly created genre. (1 row each)

insert into genre(genre_name)
values('Sports');

insert into movie_genre(movie_id, genre_id)
select m.movie_id, g.genre_id from movie m
join genre g on g.genre_name = 'Sports'
where m.title = 'Coach Carter';