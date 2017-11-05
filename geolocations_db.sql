/***************************************************************************
* Create the database named chat_room, its tables, and a user
*****************************************************************************/

DROP DATABASE IF EXISTS geolocations;
CREATE DATABASE geolocations;
USE geolocations;

/*Tables*/

CREATE TABLE Country (
	country_code VARCHAR(3) NOT NULL, 
	country_name VARCHAR(50) NOT NULL,
	CONSTRAINT PK_country_code PRIMARY KEY(country_code)
);

CREATE TABLE Region (
	region_code VARCHAR(3) NOT NULL, 
	region_name VARCHAR(50) NOT NULL,
	CONSTRAINT PK_region_code PRIMARY KEY(region_code)
);

CREATE TABLE Geolocation (
	location_id BIGINT NOT NULL AUTO_INCREMENT, 
	ip_address VARCHAR(50) NOT NULL, 
	latitude DECIMAL(6,4) NOT NULL,
	longitude DECIMAL(7,4) NOT NULL, 
    city VARCHAR(50),
    zip_code VARCHAR(12), 
    country VARCHAR(3), 
    region VARCHAR(3), 
    time_zone VARCHAR(50), 
    metro_code INT, 
	FOREIGN KEY (country) 
	REFERENCES Country (country_code), 
	FOREIGN KEY (region) 
	REFERENCES Region (region_code), 
    CONSTRAINT UK_ip_lat_long 
    UNIQUE KEY(ip_address, latitude, longitude), 
	CONSTRAINT PK_location_id PRIMARY KEY(location_id)
);

CREATE TABLE GeolocationSearchEvent (
	search_id BIGINT NOT NULL AUTO_INCREMENT, 
    time_searched DATETIME NOT NULL, 
    time_elapsed BIGINT NOT NULL, 
    location BIGINT NOT NULL, 
    FOREIGN KEY (location) 
	REFERENCES Geolocation (location_id), 
    CONSTRAINT PK_search_id PRIMARY KEY(search_id)
);

CREATE TABLE GeotracerEvent (
	tracer_id BIGINT NOT NULL AUTO_INCREMENT, 
    time_executed DATETIME NOT NULL, 
    time_elapsed BIGINT NOT NULL,  
    CONSTRAINT PK_tracer_id PRIMARY KEY(tracer_id)
);

CREATE TABLE TracerHop (
	hop_id BIGINT NOT NULL AUTO_INCREMENT, 
    tracer BIGINT NOT NULL, 
    hop_order BIGINT NOT NULL, 
    FOREIGN KEY (tracer) 
	REFERENCES GeotracerEvent (tracer_id), 
    CONSTRAINT UK_tracer_hop_order 
    UNIQUE KEY(tracer, hop_order),  
    CONSTRAINT PK_hop_id PRIMARY KEY(hop_id)
);

CREATE TABLE HopSearch (
	hop BIGINT NOT NULL, 
    search BIGINT NOT NULL, 
    FOREIGN KEY (hop) 
	REFERENCES TracerHop (hop_id), 
	FOREIGN KEY (search) 
	REFERENCES GeolocationSearchEvent (search_id), 
    CONSTRAINT UK_hop_search 
    UNIQUE KEY(search),  
    CONSTRAINT PK_hop_search PRIMARY KEY(hop)
);

CREATE TABLE GeolocationsUser (
	username VARCHAR(50) NOT NULL, 
	first_name VARCHAR(50) NOT NULL, 
	last_name VARCHAR(50) NOT NULL, 
	password VARCHAR(60) NOT NULL,
    CONSTRAINT PK_username PRIMARY KEY(username)
);

CREATE TABLE UserRole (
	username VARCHAR(50) NOT NULL, 
	role VARCHAR(50) NOT NULL, 
    FOREIGN KEY (username) 
	REFERENCES GeolocationsUser (username), 
    CONSTRAINT PK_username_role PRIMARY KEY(username, role)
);

CREATE TABLE UserContact (
	username VARCHAR(50) NOT NULL, 
	street VARCHAR(50) NOT NULL, 
	city VARCHAR(50) NOT NULL, 
	zip_code VARCHAR(12) NOT NULL, 
	region VARCHAR(3) NOT NULL, 
	country VARCHAR(3) NOT NULL, 
	FOREIGN KEY (username) 
	REFERENCES GeolocationsUser (username), 
    FOREIGN KEY (region) 
	REFERENCES Region (region_code), 
    FOREIGN KEY (country) 
	REFERENCES Country (country_code), 
	CONSTRAINT PK_user_contact_id PRIMARY KEY(username)
);

-- Create geolocations_db_user and grant privileges

DELIMITER //
CREATE PROCEDURE drop_user_if_exists()
	BEGIN
	DECLARE userCount BIGINT DEFAULT 0 ;
	SELECT COUNT(*) INTO userCount FROM mysql.user
		WHERE User = 'geolocations_db_user' and  Host = 'localhost';

	IF userCount > 0 THEN
        DROP USER geolocations_db_user@localhost;
	END IF;
	END ; //
DELIMITER ;
CALL drop_user_if_exists() ;
CREATE USER geolocations_db_user@localhost IDENTIFIED BY '9vW00q24CTy@(zl}';
GRANT EXECUTE, SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON geolocations.*
TO geolocations_db_user@localhost;
GRANT SELECT ON mysql.proc TO geolocations_db_user@localhost;

INSERT INTO Country (country_code, country_name) 
VALUES ('US','United States');

INSERT INTO Region (region_code, region_name) 
VALUES ('CA','California'), 
('WA','Washington');

-- passwords: adminpass, userpass

INSERT INTO GeolocationsUser (username, first_name, last_name, password) 
VALUES ('admin1','Joe','Admin','$2a$12$HGAq7Vq7omQd6tT80.HD1evheGsnauy09Ixwm.QyzQeXsTt2IhHBy'), 
('user1','Joe','User','$2a$12$9xwUSzPp0dlis0pSs.gRN.J8SB/LjvLNEK5aUKrqO7RVvGzH79tNO');

INSERT INTO UserRole (username, role) 
VALUES ('admin1','administrator'), 
('user1','user');

INSERT INTO UserContact (username, street, city, zip_code, region, country) 
VALUES ('admin1', '123 Nice Ave.', 'Seattle', '98109', 'WA', 'US'), 
('user1', '321 Shiny Blvd.', 'Los Angeles', '90096', 'CA', 'US');