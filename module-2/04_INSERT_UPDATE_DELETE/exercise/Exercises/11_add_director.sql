-- 11. Hollywood is remaking the classic movie "The Blob" and doesn't have a director yet. Write an INSERT query to add yourself to the person
--     table, and an UPDATE query to assign yourself as the director of "The Blob" (the movie is already in the movie table). (1 record each)
insert into person (person_name, birthday)
values('Andrew Long', '1/15/2000');

update movie
set director_id = (
	select person_id from person where person_name = 'Andrew Long')
	where title = 'The Blob';


