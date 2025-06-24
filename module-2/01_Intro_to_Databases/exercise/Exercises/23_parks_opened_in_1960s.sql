-- 23. The name and date established of parks opened in the 1960s.
-- Order the results by date established, oldest first.
-- (6 rows)
select park_name, date_established from park
	where date_established >= '1/1/1960' and date_established < '12/31/1969'
	order by date_established;
