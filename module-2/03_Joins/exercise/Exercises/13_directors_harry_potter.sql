-- 13. The directors of the movies in the "Harry Potter Collection", sorted alphabetically.
-- (4 rows)
select distinct person_name from person
join movie on director_id = person_id
join collection as co on co.collection_id = movie.collection_id
where collection_name = 'Harry Potter Collection'
order by person_name;
