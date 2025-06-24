-- 10. For all people born before 1900 whose profile_path does NOT end in ".jpg", write an UPDATE query to set their
--     home_page to "No image" and their profile_path to NULL (64 rows)
update person 
	set home_page = 'No image', profile_path = NULL
	where profile_path not like('%.jpg') and birthday <= '12/31/1899';

