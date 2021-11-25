CREATE TABLE `user` (
	id 			BIGINT auto_increment 	NOT NULL,
	email 		VARCHAR(100) 			NOT NULL,
	username 	VARCHAR(100) 			NOT NULL,
	firstname 	VARCHAR(100) 			NOT NULL,
	lastname 	VARCHAR(100) 			NOT NULL,
	password 	VARCHAR(200) 			NOT NULL,
	gender 		ENUM('MALE', 'FEMALE', 'UNDEFINED') DEFAULT 'UNDEFINED' NOT NULL,
	CONSTRAINT user_pk 			PRIMARY KEY (id),
	CONSTRAINT user_email_un 	UNIQUE KEY (email),
	CONSTRAINT user_username_un UNIQUE KEY (username)
);
