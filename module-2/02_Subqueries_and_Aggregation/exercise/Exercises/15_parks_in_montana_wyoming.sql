-- 15. The park name, date established, and area for parks in Montana and Wyoming.
-- Order the results by park name alphabetically.
-- (3 rows)
select park_name, date_established, area from park
	where park_id in(select park_id from park_state where state_abbreviation in ('MT', 'WY'))
	order by park_name ;
