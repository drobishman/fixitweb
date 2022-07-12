create table app_user (
   id BIGINT NOT NULL AUTO_INCREMENT,
   sso_id VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
   logged_in BOOLEAN,
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

CREATE TABLE car (
   id BIGINT NOT NULL AUTO_INCREMENT,
   registration_number VARCHAR(10) NOT NULL,
   chasis_number VARCHAR(30) NOT NULL,
   brand VARCHAR(50) NOT NULL,
   model VARCHAR(50) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (registration_number)
);

CREATE TABLE owner_car (
   owner_id BIGINT NOT NULL,
   car_id BIGINT NOT NULL,
   PRIMARY KEY (owner_id, car_id),
   CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES app_user (id),
   CONSTRAINT fk_car FOREIGN KEY (car_id) REFERENCES car (id) ON DELETE CASCADE
);

CREATE TABLE trouble_code (
   id BIGINT NOT NULL AUTO_INCREMENT,
   number VARCHAR(10) NOT NULL,
   fault_location VARCHAR(100) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (number)
);

CREATE TABLE car_trouble_code (
   id BIGINT NOT NULL AUTO_INCREMENT,
   car_id BIGINT NOT NULL,
   trouble_code_id BIGINT NOT NULL,
   job VARCHAR(50),
   PRIMARY KEY (id),
   CONSTRAINT fk_broken_car FOREIGN KEY (car_id) REFERENCES car (id),
   CONSTRAINT fk_trouble_code FOREIGN KEY (trouble_code_id) REFERENCES trouble_code (id) ON DELETE CASCADE
);

-- insert into database some test data

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

INSERT INTO car(registration_number, chasis_number, brand, model)
VALUES ('NT04DAL','numar sasiu','caruta','stricata');

INSERT INTO owner_car(owner_id, car_id)
VALUES (1,1);
  
INSERT INTO `trouble_code`(number, fault_location)
VALUES ('P1000','OBDII Monitor Testing Not Complete (Ford, Jaguar, Lincoln) Secondary AIR Delivery (BMW, MB, MINI)');  

-- test data

INSERT into car_trouble_code (car_id, trouble_code_id, job)
Values (2,1,'treaba rezolvata');
  
INSERT into car_trouble_code (car_id, trouble_code_id, job)
Values (2,2,'treaba rezolvata 1');

INSERT into car_trouble_code (car_id, trouble_code_id, job)
Values (2,3,'treaba rezolvata 2');

-- create the table for persistent logins
CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);
