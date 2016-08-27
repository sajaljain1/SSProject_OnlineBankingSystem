CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO users(username,password,enabled)
VALUES ('admin','$2a$10$Ifz2pEtkR5pdMpaIig3K8eZa0lWYTLU0b5YVosZWpkmJOj6rJ/nYC', true);
INSERT INTO users(username,password,enabled)
VALUES ('normal_user','$2a$10$Ifz2pEtkR5pdMpaIig3K8eZa0lWYTLU0b5YVosZWpkmJOj6rJ/nYC', true);

INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('normal_user', 'ROLE_NORMAL_USER');

CREATE TABLE USER_PUBLIC_KEY(
key_id int(11) NOT NULL AUTO_INCREMENT,
username varchar(45) NOT NULL,
public_key varchar(500) NOT NULL,
PRIMARY KEY(key_id),
CONSTRAINT FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE govt_info_req(
id int(11) NOT NULL AUTO_INCREMENT,
pii varchar(30) NOT NULL,
`complete` tinyint(4) NOT NULL DEFAULT '0',
PRIMARY KEY(id)
);

--public :MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALiwpa300+eNT0BoWWC8jRVs8eB3u+RhbFnGNo6LlQR+Bp6PNN9gGgt5orKKukzeqtsKrZBzzMSthN9HIhXRisECAwEAAQ==
--private :MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAuLClrfTT541PQGhZYLyNFWzx4He75GFsWcY2jouVBH4Gno8032AaC3misoq6TN6q2wqtkHPMxK2E30ciFdGKwQIDAQABAkBGMu5mdl5s7qsm4gLG4CQH9sbg+OGz5svWO57VA3z6nX49fxEdRlnerQCRzOOtLwXD3fxe+LGKcAatb6mCEBI5AiEA7zmuvPCsIDkqVGfhQ/GXRqtI/WSlFl4Jn3xhL8q/Qv8CIQDFpAFZPecvRZvWCuNzVZlKDaO3zq/bDsF7eiQsm5XyPwIgCUozJ/Esrf+qTibOj6XlGwBx0C1FX+38762T1JhJ/x8CIGyxN3Sm0J90vrFGJ7RkHPtW6PTLHHdydAG+OGq33gztAiA6+NdJvog0+Klm9eC8io2B7BegfSlP08mM9bHimFwlGQ==
insert into USER_PUBLIC_KEY values(1, 'normal_user', 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALiwpa300+eNT0BoWWC8jRVs8eB3u+RhbFnGNo6LlQR+Bp6PNN9gGgt5orKKukzeqtsKrZBzzMSthN9HIhXRisECAwEAAQ==');

--public :MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMRcD8gixpPEqi6itrkZQUZke7vJ423BXeHobSYkKmaT009Li0HiHbpR8xXE34gNSACUPUoNbdLSYuJW6Jad5w8CAwEAAQ==
--private :MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAxFwPyCLGk8SqLqK2uRlBRmR7u8njbcFd4ehtJiQqZpPTT0uLQeIdulHzFcTfiA1IAJQ9Sg1t0tJi4lbolp3nDwIDAQABAkBaEVRX28O0UhgxrgccXj43uK2c1J0SYgjbL46pWRtt0v/YSs7yl5bR+7nFyTVBjBhbt2j1u4/sCIHnAdbb9K5xAiEA+Vu7zPPD6NkpqJIrAsdX62+nCXyz9+y5ELCzhhzwPEkCIQDJlvPoH7fZWl6moKHd9KLZO9P2sKM0JD7CDlDkIieYlwIhAKN2wUv/2MPB6I87ErsFltseHHAZsZirND5+t5EJU2sRAiEApPRzsz65Fw8eomSQ72luFCojoRobAeCixfIm+PaFxhMCIQCdOGoX8fTdZILWzWesO21HFljMIrXVd0fbs/6uCjGBGA==
insert into USER_PUBLIC_KEY values(2, 'user2',  'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMRcD8gixpPEqi6itrkZQUZke7vJ423BXeHobSYkKmaT009Li0HiHbpR8xXE34gNSACUPUoNbdLSYuJW6Jad5w8CAwEAAQ==');


commit