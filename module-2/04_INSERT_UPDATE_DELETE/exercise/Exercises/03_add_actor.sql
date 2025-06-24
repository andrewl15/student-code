-- 3. Did you know Eric Stoltz was originally cast as Marty McFly in "Back to the Future"? Write an INSERT query to add Eric Stoltz
--    to the list of actors for "Back to the Future" (1 row)
insert into movie_actor(movie_id, actor_id)
select m.movie_id, p.person_id from movie m
join person p on p.person_name = 'Eric Stoltz'
where m.title = 'Back to the Future';