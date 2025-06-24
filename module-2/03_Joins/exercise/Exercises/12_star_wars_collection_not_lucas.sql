-- 12. The titles of the movies in the "Star Wars Collection" that weren't directed by George Lucas, sorted alphabetically.
-- (5 rows)
select title from movie
join collection as co on co.collection_id = movie.collection_id
join person on person_id = director_id
where person_name != 'George Lucas' and collection_name = 'Star Wars Collection'
order by title;
