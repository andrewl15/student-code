-- 21. For every person in the person table with the first name of "George", list the number of movies they've been in. Name the count column 'num_of_movies'.
-- Include all Georges, even those that have not appeared in any movies. Display the names in alphabetical order.
-- (59 rows)
-- TIP: This one can be a little tricky. If you're off by one, look closer at each clause of your statement. There's something you can change to get a different result set.
select person_name, count(ma.movie_id) as num_of_movies from person
left join movie_actor as ma on ma.actor_id = person_id
where person_name like 'George %'
group by person_name, person_id
order by person_name;

