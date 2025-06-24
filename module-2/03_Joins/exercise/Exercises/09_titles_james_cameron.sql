-- 9. The titles of movies directed by James Cameron, sorted alphabetically.
-- (6 rows)
select title from movie
join person on person.person_id = movie.director_id
where person.person_name = 'James Cameron'
order by title;
