create table app_user (
   id BIGINT NOT NULL AUTO_INCREMENT,
   sso_id VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (sso_id)
);

create table user_profile(
   id BIGINT NOT NULL AUTO_INCREMENT,
   type VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
);
   
CREATE TABLE app_user_user_profile (
    user_id BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES app_user (id),
    CONSTRAINT fk_user_profile FOREIGN KEY (user_profile_id) REFERENCES user_profile (id)
);
  
INSERT INTO user_profile(type)
VALUES ('USER');
  
INSERT INTO user_profile(type)
VALUES ('ADMIN');
  
INSERT INTO user_profile(type)
VALUES ('DBA');
  
  
INSERT INTO app_user(sso_id, password, first_name, last_name, email)
VALUES ('sam','$2a$10$.9hCqKyQdKSB.tixU5NUZ.iccZ.Hr8n4Ezv8h43O0oHPKmUDTCwWO', 'Sam','Smith','samy@xyz.com');
  
INSERT INTO app_user_user_profile (user_id, user_profile_id)
  SELECT user.id, profile.id FROM app_user user, user_profile profile
  where user.sso_id='sam' and profile.type='ADMIN';

CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);