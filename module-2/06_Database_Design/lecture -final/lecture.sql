START TRANSACTION;
-- ROLLBACK;

-- (I) DROP SECTION
DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS art;
DROP TABLE IF EXISTS artists;

-- (II) CREATE SECTION
CREATE TABLE customers(
	customer_id serial,
	name varchar not null,
	address varchar not null,
	phone varchar not null,
	CONSTRAINT pk_customers PRIMARY KEY(customer_id)
);

CREATE TABLE artists(
	artist_id serial,
	name varchar not null,
	CONSTRAINT pk_artists PRIMARY KEY(artist_id)
);

CREATE TABLE art(
	art_id serial,
	title varchar not null,
	artist_id int not null, --FOREIGN KEY!
	CONSTRAINT pk_art PRIMARY KEY(art_id),
	CONSTRAINT fk_artists_art FOREIGN KEY(artist_id) REFERENCES artists(artist_id)
);

CREATE TABLE purchases(
	customer_id int not null,
	art_id int not null,
	purchase_date timestamp not null,
	price money not null,
	CONSTRAINT pk_purchases PRIMARY KEY(customer_id, art_id, purchase_date),
	CONSTRAINT fk_purchases_customers FOREIGN KEY(customer_id) REFERENCES customers(customer_id),
	CONSTRAINT fk_purchases_art  FOREIGN KEY(art_id) REFERENCES art(art_id)
);

-- (III) DATA SECTION
INSERT INTO customers(name, address, phone) VALUES
('Alice', '123 Deerhaven Lane', '313-281-2343'),
('Bob', 'Flying Cow Circle', '248-932-2123'),
('Carly', '456 Raccoon Street', '810-232-8832');

INSERT INTO artists(name) VALUES
('Robert Sponge'),
('Samwise Jackson');

INSERT INTO art(title,artist_id) VALUES
('Seafood Burger in Watercolor', (SELECT artist_id FROM artists WHERE name='Robert Sponge') ),
('Frustrated Programmer', (SELECT artist_id FROM artists WHERE name='Samwise Jackson') );

-- Alice bought Seafood Burger in Watercolor for 1000
INSERT INTO purchases(customer_id, art_id, purchase_date, price) VALUES
((SELECT customer_id FROM customers WHERE name='Alice'), (SELECT art_id FROM art WHERE title='Seafood Burger in Watercolor'), '2024-12-15 09:00:00', 1000);


COMMIT;

