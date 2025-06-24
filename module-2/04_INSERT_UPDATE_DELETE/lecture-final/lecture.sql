-- INSERT

-- Add Disneyland to the park table. 
-- (It was established on 7/17/1955, has an area of 2.1 square kilometers and does not offer camping.)
INSERT INTO park(park_name,date_established,area,has_camping)
VALUES('Disneyland','7/17/1955',2.1,false);
select * from park;
-- Add Hawkins, IN (with a population of 30,000 and an area of 38.1 square kilometers) 
-- and Cicely, AK (with a popuation of 839 and an area of 11.4 square kilometers) to the city table.
INSERT INTO city(state_abbreviation,city_name,area,population)
VALUES('IN','Hawkins',38.1,30000),('AK','Cicely',11.4,839);
select * from city;
-- Since Disneyland is in California (CA), add a record representing that to the park_state table.
INSERT INTO park_state(park_id,state_abbreviation)
SELECT park_id,'CA' from park where park_name='Disneyland';
select * from park_state;

-- UPDATE

-- Change the state nickname of California to "The Happiest Place on Earth."
UPDATE state
	set state_nickname='The Happiest Place on Earth.'
	WHERE state_abbreviation='CA';

-- Increase the population of California by 1,000,000.
UPDATE state
	set population = population + 1000000
	WHERE state_abbreviation = 'CA';

-- Change the capital of California to Anaheim.
UPDATE state
	set capital = (select city_id from city where city_name='Anaheim' and state_abbreviation='CA' LIMIT 1)
	WHERE state_abbreviation = 'CA';

-- Change California's nickname back to "The Golden State", 
--	reduce the population by 1,000,000, and change the capital back to Sacramento.
UPDATE state
	SET state_nickname = 'The Golden State',
	population = population - 1000000,
	capital = (select city_id from city where city_name='Sacramento' and state_abbreviation='CA' LIMIT 1)
	where state_abbreviation = 'CA';


-- DELETE

-- Delete Hawkins, IN from the city table.
delete from city where city_name = 'Hawkins' and state_abbreviation = 'IN';

-- Delete all cities with a population of less than 1,000 people from the city table.
delete from city where population < 1000;


-- REFERENTIAL INTEGRITY

-- Try adding a city to the city table with "XX" as the state abbreviation.
INSERT INTO city VALUES(default,'Yarrow Point','XX',2500,42);

-- Try deleting California from the state table.
delete from state where state_abbreviation = 'CA';

-- Try deleting Disneyland from the park table. 
delete from park where park_name = 'Disneyland';
-- Try again after deleting its record from the park_state table.
delete from park_state where park_id = (select park_id from park where park_name = 'Disneyland');
delete from park where park_name = 'Disneyland';

-- CONSTRAINTS

-- NOT NULL constraint
-- Try adding Smallville, KS to the city table without specifying its population or area.
INSERT INTO city(city_name,state_abbreviation)
VALUES('Smallville','KS');

-- DEFAULT constraint
-- Try adding Smallville, KS again, specifying an area but not a population.
INSERT INTO city(city_name,state_abbreviation,area)
VALUES('Smallville','KS',159);

-- Retrieve the new record to confirm it has been given a default, non-null value for population.
select * from city where city_name = 'Smallville';

-- UNIQUE constraint
-- Try changing California's nickname to "Vacationland" (which is already the nickname of Maine).
update state set state_nickname = 'Vacationland' where state_abbreviation = 'CA';

-- CHECK constraint
-- Try changing the census region of Florida (FL) to "Southeast" (which is not a valid census region).
UPDATE state set census_region = 'Southeast' where state_abbreviation = 'FL';


-- TRANSACTIONS

-- Delete the record for Smallville, KS within a transaction.
START TRANSACTION;
delete from city where city_name = 'Smallville' and state_abbreviation = 'KS';
commit;
select * from city where city_name = 'Smallville' and state_abbreviation = 'KS';
-- Delete all of the records from the park_state table, 
-- but then "undo" the deletion by rolling back the transaction.
START TRANSACTION;
delete from park_state;
select * from park_state;
ROLLBACK;

-- Update all of the cities to be in the state of Texas (TX), but then roll back the transaction.
START TRANSACTION;
update city set state_abbreviation = 'TX';
rollback;
select * from city;
-- Demonstrate two different SQL connections trying to access the same table 
-- where one is inside of a transaction but hasn't committed yet.
START TRANSACTION;
INSERT INTO city(city_name,state_abbreviation,population,area)
VALUES('Schrodingers City','NJ',1,0);
select * from city;
commit;
INSERT INTO city(city_name,state_abbreviation,population,area)
VALUES('Schrodingers City 2','NJ',1,0);