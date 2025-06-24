-- 12. Write an INSERT query to create a "Bill Murray Collection" in the collection table. For the movies that have Bill Murray in them,
--     write an UPDATE query to set their collection ID to the "Bill Murray Collection". (1 row, 6 rows)

insert into collection(collection_name)
values('Bill Murray Collection');

update movie
	set collection_id = (select collection_id from collection where collection_name = 'Bill Murray Collection')
	where movie_id in (
		select ma.movie_id from movie_actor ma
		join person p on ma.actor_id = p.person_id
		where p.person_name = 'Bill Murray'
	);
