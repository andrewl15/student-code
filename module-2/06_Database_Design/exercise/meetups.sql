start transaction;

--rollback;
drop table if exists group_members, event_members, members, events, interest_groups cascade;

create table members(
	member_id serial,
	last_name varchar not null,
	first_name varchar not null,
	email varchar not null,
	phone varchar,
	birthdate varchar not null,
	wants_reminders boolean,
	
	constraint pk_members primary key(member_id)
);

create table interest_groups(
	group_id serial,
	group_name varchar not null unique,
	
	constraint pk_interest_groups primary key(group_id)
);

create table group_members(
	group_id int not null,
	member_id int not null,
	
	constraint pk_group_members primary key(group_id, member_id),
	CONSTRAINT fk_members_interest_group FOREIGN KEY(member_id) REFERENCES members(member_id),
	CONSTRAINT fk_interest_groups_members FOREIGN KEY(group_id) REFERENCES interest_groups(group_id)
);

create table events(
	event_id serial,
	event_name varchar not null,
	description text not null,
	start_datetime timestamp not null,
	duration_minutes int check(duration_minutes >= 30),
	group_id int not null,
	
	constraint pk_event_id primary key(event_id),
	CONSTRAINT fk_interest_groups FOREIGN KEY(group_id) REFERENCES interest_groups(group_id)
);

create table event_members(
	event_id int not null,
	member_id int not null,
	
	constraint pk_event_members primary key(event_id, member_id),
	CONSTRAINT fk_event_members_member FOREIGN KEY(member_id) REFERENCES members(member_id),
	CONSTRAINT fk_members_events_event FOREIGN KEY(event_id) REFERENCES events(event_id)
);

INSERT INTO members (last_name, first_name, email, phone, birthdate, wants_reminders)
VALUES
    ('Smith', 'John', 'john.smith@example.com', '123-456-7890', '1990-01-01', TRUE),
    ('Doe', 'Jane', 'jane.doe@example.com', NULL, '1988-05-15', FALSE),
    ('Brown', 'Charlie', 'charlie.brown@example.com', '987-654-3210', '1995-12-10', TRUE),
    ('Wilson', 'Emma', 'emma.wilson@example.com', '456-789-1234', '1993-06-20', TRUE),
    ('Taylor', 'Oliver', 'oliver.taylor@example.com', '321-654-9870', '1987-03-05', FALSE),
    ('Johnson', 'Sophia', 'sophia.johnson@example.com', NULL, '1992-10-25', TRUE),
    ('Lee', 'Liam', 'liam.lee@example.com', '789-123-4567', '1991-08-30', FALSE),
    ('Clark', 'Mia', 'mia.clark@example.com', '654-987-3210', '1989-11-11', TRUE);

INSERT INTO interest_groups (group_name)
VALUES
    ('Line Dancing Club'),
    ('Jesus Lovers Club'),
    ('Hiking Enthusiasts');
	
INSERT INTO events (event_name, description, start_datetime, duration_minutes, group_id)
VALUES
    ('Honkey Tonk', 'Line dancing night.', '2025-04-25 10:00:00', 90, 1),
    ('Bible Study', 'Discussing our Matthew 15.', '2025-04-28 18:00:00', 120, 2),
    ('Mountain Hike', 'A challenging hike up the Blue Mountain.', '2025-05-01 08:30:00', 240, 3),
    ('Line Dancing Beginner Night', 'A workshop for beginners in line dancing.', '2025-04-30 14:00:00', 120, 1);

-- Group member inserts
INSERT INTO group_members (group_id, member_id)
SELECT g.group_id, m.member_id
FROM interest_groups g
JOIN members m ON m.last_name IN ('Lee', 'Smith')
WHERE g.group_name = 'Line Dancing Club';

INSERT INTO group_members (group_id, member_id)
SELECT g.group_id, m.member_id
FROM interest_groups g
JOIN members m ON m.last_name IN ('Clark', 'Johnson')
WHERE g.group_name = 'Jesus Lovers Club';

INSERT INTO group_members (group_id, member_id)
SELECT g.group_id, m.member_id
FROM interest_groups g
JOIN members m ON m.last_name IN ('Doe', 'Wilson')
WHERE g.group_name = 'Hiking Enthusiasts';

-- event members inserts

INSERT INTO event_members (event_id, member_id)
SELECT e.event_id, m.member_id
FROM events e
JOIN members m ON m.last_name IN ('Lee', 'Smith')
WHERE e.event_name = 'Honkey Tonk';

INSERT INTO event_members (event_id, member_id)
SELECT e.event_id, m.member_id
FROM events e
JOIN members m ON m.last_name IN ('Clark', 'Johnson')
WHERE e.event_name = 'Bible Study';

INSERT INTO event_members (event_id, member_id)
SELECT e.event_id, m.member_id
FROM events e
JOIN members m ON m.last_name IN ('Lee', 'Smith')
WHERE e.event_name = 'Line Dancing Beginner Night';

INSERT INTO event_members (event_id, member_id)
SELECT e.event_id, m.member_id
FROM events e
JOIN members m ON m.last_name IN ('Doe', 'Wilson')
WHERE e.event_name = 'Mountain Hike';

commit;