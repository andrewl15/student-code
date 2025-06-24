BEGIN TRANSACTION;

DROP TABLE IF EXISTS movie_user_like, users, movie;

CREATE TABLE users (
    user_id serial,
    username varchar(50) NOT NULL UNIQUE,
    password_hash varchar(200) NOT NULL,
    role varchar(50) NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE TABLE movie (
    movie_id serial,
    title varchar(255) NOT NULL,
    release_date date NOT NULL,
    rating varchar(10) NOT NULL,
    director varchar(255) NOT NULL,
    CONSTRAINT PK_movie PRIMARY KEY (movie_id)
);

CREATE TABLE movie_user_like (
    movie_id integer NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT FK_movie_movie_id FOREIGN KEY(movie_id) REFERENCES movie(movie_id),
    CONSTRAINT FK_user_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

-- Users - all have password: 'password'
INSERT INTO users (username, password_hash, role) VALUES
    ('movie_fan', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER'),
    ('cameron', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER');

INSERT INTO movie (title, release_date, rating, director) VALUES
    ('Finding Nemo', '2003-05-30', 'G', 'Andrew Stanton'), -- 1
    ('The Incredibles', '2004-11-05', 'PG', 'Brad Bird'), -- 2
    ('Moana', '2016-11-23', 'PG', 'Ron Clements, John Musker'), -- 3
    ('Spider-Man: Into the Spider-Verse', '2018-12-14', 'PG', 'Bob Persichetti, Peter Ramsey, Rodney Rothman'), -- 4
    ('Frozen', '2013-11-27', 'PG', 'Chris Buck, Jennifer Lee'), -- 5
    ('The Lego Movie', '2014-02-07', 'PG', 'Phil Lord, Christopher Miller'), -- 6
    ('Big Hero 6', '2014-11-07', 'PG', 'Don Hall, Chris Williams'), -- 7
    ('Zootopia', '2016-03-04', 'PG', 'Byron Howard, Rich Moore, Jared Bush'), -- 8
    ('WALL-E', '2008-06-27', 'G', 'Andrew Stanton'), -- 9
    ('Up', '2009-05-29', 'PG', 'Pete Docter, Bob Peterson'); -- 10

INSERT INTO movie_user_like (movie_id, user_id) VALUES
    (3, 1),
    (4, 1),
    (7, 1),
    (9, 1),
    (1, 2),
    (5, 2),
    (10, 2);

COMMIT TRANSACTION;
