BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, account, transfer, transfer_status, transfer_type;
DROP SEQUENCE IF EXISTS seq_user_id;


CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE users (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);
CREATE TABLE account (
	account_id serial NOT NULL,
	user_id int NOT NULL REFERENCES users(user_id),
	balance numeric(15,2) NOT NULL,

	CONSTRAINT PK_account PRIMARY KEY (account_id)
);
CREATE TABLE transfer (
    transfer_id serial NOT NULL,
    transferType_id int NOT NULL,
    transferStatus_id int NOT NULL,
    user_to_id int NOT NULL,
    user_from_id int NOT NULL,
    amount numeric(15,2) NOT NULL,

    CONSTRAINT PK_transfer PRIMARY KEY(transfer_id)
);
CREATE TABLE transfer_status (
    transferStatus_id serial NOT NULL,
    description VARCHAR(50) UNIQUE NOT NULL,

    CONSTRAINT PK_transfer_status PRIMARY KEY(transferStatus_id)
);
CREATE TABLE transfer_type (
    transferType_id serial NOT NULL,
    description VARCHAR(50) UNIQUE NOT NULL,

    CONSTRAINT PK_transfer_type PRIMARY KEY(transferType_id)
);
ALTER TABLE account ADD CONSTRAINT FK_Account_User FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE transfer  ADD  CONSTRAINT FK_transfer_user_from FOREIGN KEY(user_from_id) REFERENCES users (user_id);
ALTER TABLE transfer  ADD  CONSTRAINT FK_transfer_user_to FOREIGN KEY(user_to_id) REFERENCES users (user_id);
ALTER TABLE transfer  ADD  CONSTRAINT FK_transfer_transfer_type FOREIGN KEY(transferType_id) REFERENCES transfer_type (transferType_id);
ALTER TABLE transfer  ADD  CONSTRAINT FK_transfer_transfer_status FOREIGN KEY(transferStatus_id) REFERENCES transfer_status (transferStatus_id);
ALTER TABLE account ADD CONSTRAINT CK_Positive_Balance CHECK (balance >= 0);
ALTER TABLE transfer ADD CONSTRAINT CK_Positive_Transfer CHECK (amount >= 0);
ALTER TABLE transfer ADD CONSTRAINT CK_NOT_ME CHECK (user_to_id != user_from_id);
 -- set account table to start at 1000
 ALTER SEQUENCE account_account_id_seq RESTART WITH 1000;
 -- set transfer table to start at 10000
 ALTER SEQUENCE transfer_transfer_id_seq RESTART WITH 10000;

INSERT INTO transfer_status VALUES(default,'Pending');
INSERT INTO transfer_status VALUES(default,'Approved');
INSERT INTO transfer_status VALUES(default,'Rejected');

INSERT INTO transfer_type VALUES(default,'Send');
INSERT INTO transfer_type VALUES(default,'Request');

COMMIT TRANSACTION;
