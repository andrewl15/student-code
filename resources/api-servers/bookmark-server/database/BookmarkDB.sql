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
	CONSTRAINT PK_tag PRIMARY KEY (tag_id),
	CONSTRAINT UQ_name UNIQUE (name)
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
INSERT INTO app_user (username, password_hash, role, display_name, img_url, short_bio)
	VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', null, null, null);
INSERT INTO app_user (username, password_hash, role, display_name, img_url, short_bio)
	VALUES ('job_coach','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Imani',
		'https://hostedboringavatars.vercel.app/api/bauhaus?size=80&name=job_coach',
		'Career coach, specializing in working with students fresh out of college and career changers, with a special interest in technology roles.');
INSERT INTO app_user (username, password_hash, role, display_name, img_url, short_bio)
	VALUES ('newbie_coder','$2a$10$We8.y4IV/uQOPT1crppxR.aASgeKFr24ISrkHcqWWSYlxRu4oeqE6','ROLE_USER', null,
		'https://hostedboringavatars.vercel.app/api/beam?size=80&name=newbie_coder',
		'New parent turned coder, with a passion for basketball, stats, and a good laugh.');
INSERT INTO app_user (username, password_hash, role, display_name, img_url, short_bio)
	VALUES ('troublemaker','$2a$10$K/XxMq03OaJM4AhLU7YE3eQh1KAd8/gzWIOWLgBqVrb5AoSy.pmSK','ROLE_USER', null, null, null);

-- Tags --
INSERT INTO tag (name) VALUES
	('Technology'),   -- 1
	('Jobs'),         -- 2
	('Sports'),       -- 3
	('Humor'),        -- 4
	('Productivity'), -- 5
	('Education'),    -- 6
	('Animals'),      -- 7
	('Travel');       -- 8 Keep unused!

-- Bookmarks --
-- For job_coach (has some public links):
INSERT INTO bookmark (user_id, title, url, description, is_public, is_flagged, create_date) VALUES
	(2, 'Create a Good LinkedIn Profile', 'https://www.linkedin.com/help/linkedin/answer/a554351/how-do-i-create-a-good-linkedin-profile', 'Tips for creating a solid LinkedIn profile for job hunting.', true, false, (SELECT now() - INTERVAL '340 DAYS')),
	(2, 'Step by Step Resume Guide', 'https://www.themuse.com/advice/how-to-make-a-resume-examples', 'Nice step by step resume guide with examples.', true, false, (SELECT now() - INTERVAL '340 DAYS')),
	(2, 'huntr', 'https://huntr.co/', 'Awesome website for tracking job applications.', true, false, (SELECT now() - INTERVAL '340 DAYS')),
	(2, 'Developer Networking Site', 'https://www.invisiblenetwork.com/', 'Networking with double opt-in introductions.', true, false, (SELECT now() - INTERVAL '30 DAYS')),
	(2, 'Good Imposter Syndrome Article', 'https://www.themuse.com/advice/5-different-types-of-imposter-syndrome-and-5-ways-to-battle-each-one', NULL, true, false, (SELECT now() - INTERVAL '120 DAYS')),
	(2, 'Tips for Software Engineer Resume', 'https://www.themuse.com/advice/how-to-write-software-engineer-resume-example', 'Tips and examples for writing software engineer resumes.', false, false, (SELECT now() - INTERVAL '132 DAYS')),
	(2, 'Trello', 'https://trello.com/', 'Steph recommended this for project planning & time management.', false, false, (SELECT now() - INTERVAL '8 DAYS'));
-- For newbie_coder:
INSERT INTO bookmark (user_id, title, url, description, is_public, is_flagged, create_date) VALUES
	(3, 'Make a GitHub Personal Site', 'https://pages.github.com/', 'Rae says I can host my personal resume/portfolio website here for free.', false, false, (SELECT now() - INTERVAL '2 DAYS')),
	(3, 'Flexbox Guide', 'https://css-tricks.com/snippets/css/a-guide-to-flexbox/', 'Jayson recommended this for flexbox help.', false, false, (SELECT now() - INTERVAL '30 DAYS')),
	(3, 'Interview Prep', 'https://www.freecodecamp.org/learn/coding-interview-prep/', 'Free code camp interview prep path. Juan recommended the take home projects for ideas.', false, false, (SELECT now() - INTERVAL '10 DAYS')),
	(3, 'Game Schedule', 'https://www.nba.com/schedule', NULL, false, false, (SELECT now() - INTERVAL '35 DAYS')),
	(3, 'Too Cute', 'https://youtube.com/playlist?list=PL5075BB69F757047E', NULL, true, false, (SELECT now() - INTERVAL '30 DAYS')),
	(3, 'Reddit Game Tips', 'https://www.reddit.com/r/Basketball/?f=flair_name%3A%22IMPROVING%20MY%20GAME%22', NULL, false, false, (SELECT now() - INTERVAL '35 DAYS')),
	(3, 'For laughs', 'https://www.reddit.com/r/ProgrammerHumor/', NULL, false, false, (SELECT now() - INTERVAL '15 DAYS')),
	(3, 'Learn Spring', 'https://www.linkedin.com/learning/paths/become-a-spring-developer', NULL, true, false, (SELECT now() - INTERVAL '5 DAYS')),
	(3, 'BigO Complexity', 'https://cooervo.github.io/Algorithms-DataStructures-BigONotation/big-O-notation.html', 'Recommended by Seimone last week. Still need to check it out.', false, false, (SELECT now() - INTERVAL '7 DAYS')),
	(3, 'Resume Writing - Software Engineer', 'https://www.themuse.com/advice/how-to-write-software-engineer-resume-example', 'Tips and examples for writing resume. Need to follow up with Marcus next week.', false, false, (SELECT now() - INTERVAL '1 DAYS'));

-- For the troublemaker (has flagged public bookmarks): --
INSERT INTO bookmark (user_id, title, url, description, is_public, is_flagged, create_date) VALUES
	(4, 'Interview Prep', 'https://makeameme.org/meme/everything-under-20', '20 Tips for better interviews.', true, true, (SELECT now() - INTERVAL '2 DAYS')),
	(4, 'Learn to code for free!', 'https://makeameme.org/meme/everything-under-20', 'Why buy that course when you can learn for free!', true, true, (SELECT now() - INTERVAL '2 DAYS')),
	(4, '===>>> Best Link EVER!!! Changed my world!', 'https://makeameme.org/meme/everything-under-20', 'This program will have you living the good life in a month, guaranteed!', true, false, (SELECT now() - INTERVAL '1 DAYS'));


-- Bookmark Tags
INSERT INTO bookmark_tag (bookmark_id, tag_id) VALUES
	(1, 2), (1, 6),          -- LinkedIn, Jobs & Education
	(2, 2),                  -- Resume Guide, Jobs
	(3, 2),                  -- Huntr, Jobs
	(4, 2), (4, 1),          -- Invis Network, Jobs & Technology
    -- No tags for 5
    (6, 1), (6, 2),          -- SE Resume, Technology & Jobs
	(7, 5),                  -- Trello, Productivity
	(8, 1),                  -- GitHub, Technology
	(9, 1),                  -- Flexbox, Technology
	(10, 1), (10, 2),        -- Free Code Camp, Technology & Jobs & Education
	(11, 3),                 -- NBA Schedule, Sports
	(12, 7),                 -- Too Cute, Animals
	(13, 3),                 -- Game Tips, Sports
	(14, 1), (14, 4),        -- Programmer Humor, Technology & Humor
	-- No tags for bookmarks 15 & 16
    (17, 2),                 -- SE Resume, Technology & Jobs
	(18, 2),                 -- Interview Prep Junk, Jobs
	(19, 1), (19, 6),        -- Code Free Junk, Technology & Education
	-- Best Link Ever, all but one tag (Travel)
	(20, 1), (20, 2), (20, 3), (20, 4), (20, 5), (20, 6), (20, 7);

COMMIT TRANSACTION;