start transaction;
--ROLLBACK;

-- (I) Drop Section
drop table if exists purchases;
drop table if exists customers;
drop table if exists art;
drop table if exists artists;


--(II) Create Section
create table customers(
	customer_id serial,
	name varchar not null,
	address varchar not null,
	phone varchar not null,
	
	constraint pk_customers primary key(customer_id)
);

create table artists(
	artist_id serial,
	name varchar not null,
	
	constraint pk_artists primary key(artist_id)
);

create table art(
	art_id serial,
	title varchar not null,
	artist_id int not null,
	
	constraint pk_art primary key(art_id),
	constraint fk_artists_art foreign key(artist_id) references artists(artist_id)
);

create table purchases(
	customer_id int not null,
	art_id int not null,
	purchase_date timestamp not null,
	price money not null,
	
	constraint pk_purchases primary key(customer_id, art_id, purchase_date),
	constraint fk_purchases_customers foreign key(customer_id) references customers(customer_id),
	constraint fk_purchase_art foreign key(art_id) references art(art_id)
);
--(III) Data Section
INSERT INTO customers(name, address, phone) VALUES
('Alice', '123 Deerhaven Lane', '313-281-2343'),
('Bob', 'Flying Cow Circle', '248-932-2123'),
('Carly', '456 Raccoon Street', '810-232-8832');

INSERT INTO artists(name) VALUES
('Robert Sponge'),
('Samwise Jackson');

insert into art(title, artist_id) values
('Seafood Burger in Watercooler', (select artist_id from artists where name = 'Robert Sponge') ),
('Frustrated Programmer', (select artist_id from artists where name='Samwise Jackson') );

INSERT INTO purchases(customer_id, art_id, purchase_date, price) VALUES
((SELECT customer_id FROM customers WHERE name='Alice'), (SELECT art_id FROM art WHERE title='Seafood Burger in Watercolor'), '2024-12-15 09:00:00', 1000);
commit;