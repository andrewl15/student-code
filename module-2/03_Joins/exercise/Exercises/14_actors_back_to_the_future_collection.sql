-- 14. The names of actors who've appeared in the movies in the "Back to the Future Collection", sorted alphabetically.
-- (28 rows)
select distinct person_name from person
join movie_actor as ma on ma.actor_id = person_id
join movie on movie.movie_id = ma.movie_id
join collection as co on co.collection_id = movie.collection_id
where collection_name = 'Back to the Future Collection'
order by person_name;
