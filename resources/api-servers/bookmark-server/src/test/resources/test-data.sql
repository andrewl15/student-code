BEGIN TRANSACTION;

DROP TABLE IF EXISTS bookmark_tag, bookmark, app_user, tag;

CREATE TABLE app_user (
	user_id serial,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	display_name varchar(50),
	img_url varchar(500),
	short_bio varchar(500),
	CONSTRAINT PK_app_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE TABLE bookmark (
	bookmark_id serial,
	user_id int NOT NULL,
	title varchar(100) NOT NULL,
	url varchar(500) NOT NULL,
 	description varchar(500),
	create_date date NOT NULL DEFAULT NOW(),
	is_public boolean DEFAULT FALSE,
	is_flagged boolean DEFAULT FALSE,
	CONSTRAINT PK_bookmark PRIMARY KEY (bookmark_id),
	CONSTRAINT FK_bookmark_user FOREIGN KEY (user_id) REFERENCES app_user (user_id)
);

CREATE TABLE tag (
	tag_id serial,
	name varchar(50) NOT NULL,
	CONSTRAINT PK_tag PRIMARY KEY (tag_id)
);

CREATE TABLE bookmark_tag (
    bookmark_id int NOT NULL,
    tag_id int NOT NULL,
	CONSTRAINT PK_bookmark_tag PRIMARY KEY(bookmark_id, tag_id),
    CONSTRAINT FK_bookmark_tag_bookmark FOREIGN KEY(bookmark_id) REFERENCES bookmark(bookmark_id),
    CONSTRAINT FK_bookmark_tag_tag FOREIGN KEY(tag_id) REFERENCES tag(tag_id)
);

------------------------------ Test Data ---------------------------------

-- Users - all have password: 'password'
INSERT INTO app_user (user_id, username, password_hash, role, display_name, img_url, short_bio)
	VALUES (100, 'admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', null, null, null);
INSERT INTO app_user (user_id, username, password_hash, role, display_name, img_url, short_bio)
	VALUES (101, 'user1','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'User 1 - Jo Tagolia', 'Test Image URL', 'Test Bio');
INSERT INTO app_user (user_id, username, password_hash, role, display_name, img_url, short_bio)
	VALUES (102, 'user2','$2a$10$We8.y4IV/uQOPT1crppxR.aASgeKFr24ISrkHcqWWSYlxRu4oeqE6','ROLE_USER', 'User 2', null, null);

-- Tags --
INSERT INTO tag (tag_id, name) VALUES
	(200, 'Tag 3'),
	(201, 'Tag 2'),
	(202, 'Tag 1'),
    (203, 'Tag 4'); -- tag with no associations

-- Bookmarks --
INSERT INTO bookmark (bookmark_id, user_id, title, url, description, is_public, is_flagged, create_date) VALUES
	(300, 101, 'User 1 - A', 'https://www.tester.com', 'Test Home.', false, false, '2020-01-20'),
	(301, 102, 'User 2 - B', 'https://www.tester.com', 'User 2 - Test Home.', false, false, '2020-01-21'),
	(302, 101, 'User 1 - C - public, flagged', 'https://www.test.com/sub1/', NULL, true, true, '2020-01-21'),
	(303, 101, 'User 1 - B - public', 'https://fake.org/', 'Fake test site', true, false, '2020-01-22'),
	(304, 102, 'User 2 - A - public, flagged', 'https://www.test.com/sub2/', NULL, true, true, '2020-01-22');

-- Bookmark Tags
INSERT INTO bookmark_tag (bookmark_id, tag_id) VALUES
	(300, 200), (300, 201), (300, 202),
	(301, 200),
	(304, 200), (304, 202);

COMMIT TRANSACTION;
