CREATE TABLE user_forgot_password (
	token 	VARCHAR(244) 	NOT NULL,
	user_id BIGINT 			NOT NULL,
	CONSTRAINT user_forgot_password_pk PRIMARY KEY (token),
	CONSTRAINT user_forgot_password_un UNIQUE KEY (user_id),
	CONSTRAINT user_forgot_password_FK FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE ON UPDATE CASCADE
);