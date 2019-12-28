/***************************************************************************
* Create the database named stock_search, its tables, and a user
*****************************************************************************/

DROP DATABASE IF EXISTS stock_search;
CREATE DATABASE stock_search;
USE stock_search;

/* Stock table hold the symbol and name */
DROP TABLE IF EXISTS Stock;
CREATE TABLE IF NOT EXISTS Stock(
    symbol		VARCHAR(5) NOT NULL PRIMARY KEY,
    name		VARCHAR(100) NOT NULL
);

/* StockPriceSearch represents a search event and contains the results and time of the search */
DROP TABLE IF EXISTS StockPriceSearch;
CREATE TABLE IF NOT EXISTS StockPriceSearch(
    stock_price_id       BIGINT NOT NULL AUTO_INCREMENT,
    symbol               VARCHAR(5) NOT NULL,
    currency             VARCHAR(3),
    price                NUMERIC(7,2) NOT NULL,
    price_open           NUMERIC(7,2),
    day_high             NUMERIC(7,2),
    day_low              NUMERIC(7,2),
    52_week_high         NUMERIC(7,2),
    52_week_low          NUMERIC(7,2),
    day_change           NUMERIC(7,2),
    change_pct           NUMERIC(7,2),
    close_yesterday      NUMERIC(7,2),
    market_cap           BIGINT,
    volume               BIGINT,
    volume_avg           DOUBLE(12,2),
    shares               BIGINT,
    stock_exchange_long  VARCHAR(100),
    stock_exchange_short VARCHAR(20),
    timezone             VARCHAR(5),
    timezone_name        VARCHAR(50),
    gmt_offset           BIGINT,
    last_trade_time      VARCHAR(20),
    pe                   VARCHAR(20),
    eps                  NUMERIC(20,2),
    search_date_time     DATETIME NOT NULL,
    time_elapsed		 BIGINT NOT NULL,
    CONSTRAINT fk_price_search_symbol FOREIGN KEY (symbol) 
    REFERENCES Stock (symbol),
    CONSTRAINT pk_price_search PRIMARY KEY (stock_price_id)
);

/* StockHistorySearch contains the ID and time of a stock history search */
DROP TABLE IF EXISTS StockHistorySearch;
CREATE TABLE IF NOT EXISTS StockHistorySearch(
    stock_history_search_id     BIGINT NOT NULL AUTO_INCREMENT,
    search_date_time            DATETIME NOT NULL,     
    CONSTRAINT pk_history_search PRIMARY KEY (stock_history_search_id)
);

/* StockHistoryResult holds a single dated result for a stock history search */
DROP TABLE IF EXISTS StockHistoryResult;
CREATE TABLE IF NOT EXISTS StockHistoryResult(
    symbol          VARCHAR(5) NOT NULL,
    history_date    DATE NOT NULL,
    open            NUMERIC(7,2),
    close           NUMERIC(7,2),
    high            NUMERIC(7,2),
    low             NUMERIC(7,2),
    volume          BIGINT,
    CONSTRAINT fk_history_result_symbol FOREIGN KEY (symbol) 
    REFERENCES Stock (symbol),
    CONSTRAINT pk_history_result PRIMARY KEY (symbol, history_date)
);

/* StockHistorySearchResult links stock history searches with dated result elements */
DROP TABLE IF EXISTS StockHistorySearchResult;
CREATE TABLE IF NOT EXISTS StockHistorySearchResult(
    stock_history_search_id     BIGINT NOT NULL,
    symbol                      VARCHAR(5) NOT NULL,
    history_date                DATE NOT NULL,     
    CONSTRAINT fk_history_search_result_search FOREIGN KEY (stock_history_search_id) 
    REFERENCES StockHistorySearch (stock_history_search_id),    
    CONSTRAINT fk_history_search_result_result FOREIGN KEY (symbol, history_date) 
    REFERENCES StockHistoryResult (symbol, history_date),
    CONSTRAINT pk_history_search_result PRIMARY KEY (stock_history_search_id, symbol, history_date)
);

/* Holds country code and name */
CREATE TABLE Country (
	country_code VARCHAR(3) NOT NULL, 
	country_name VARCHAR(50) NOT NULL,
	CONSTRAINT PK_country_code PRIMARY KEY(country_code)
);

/* Holds abbreviation and name for state or province */
CREATE TABLE Region (
	region_code VARCHAR(3) NOT NULL, 
	region_name VARCHAR(50) NOT NULL,
	CONSTRAINT PK_region_code PRIMARY KEY(region_code)
);

/* The operation monitor application user */
CREATE TABLE OperationMonitorUser (
	username VARCHAR(50) NOT NULL, 
	first_name VARCHAR(50) NOT NULL, 
	last_name VARCHAR(50) NOT NULL, 
	password VARCHAR(60) NOT NULL,
	CONSTRAINT PK_username PRIMARY KEY(username)
);

/* User role */
CREATE TABLE UserRole (
	username VARCHAR(50) NOT NULL, 
	role VARCHAR(50) NOT NULL, 
	FOREIGN KEY (username) 
	REFERENCES OperationMonitorUser (username), 
	CONSTRAINT PK_username_role PRIMARY KEY(username, role)
);

/* User contact information */
CREATE TABLE UserContact (
	username VARCHAR(50) NOT NULL, 
	street VARCHAR(50) NOT NULL, 
	city VARCHAR(50) NOT NULL, 
	zip_code VARCHAR(12) NOT NULL, 
	region VARCHAR(3) NOT NULL, 
	country VARCHAR(3) NOT NULL, 
	FOREIGN KEY (username) 
	REFERENCES OperationMonitorUser (username), 
	FOREIGN KEY (region) 
	REFERENCES Region (region_code), 
	FOREIGN KEY (country) 
	REFERENCES Country (country_code), 
	CONSTRAINT PK_user_contact_id PRIMARY KEY(username)
);

-- Create stock_search_db_user and grant privileges

DELIMITER //
CREATE PROCEDURE drop_user_if_exists()
	BEGIN
	DECLARE userCount BIGINT DEFAULT 0 ;
	SELECT COUNT(*) INTO userCount FROM mysql.user
		WHERE User = 'stock_search_db_user' and  Host = 'localhost';

	IF userCount > 0 THEN
        DROP USER stock_search_db_user@localhost;
	END IF;
	END ; //
DELIMITER ;
CALL drop_user_if_exists() ;
CREATE USER stock_search_db_user@localhost IDENTIFIED BY '7r6H5fq}dD98!@x';
GRANT EXECUTE, SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON stock_search.*
TO stock_search_db_user@localhost;
GRANT SELECT ON mysql.proc TO stock_search_db_user@localhost;

INSERT INTO Country (country_code, country_name) 
VALUES ('US','United States');

INSERT INTO Region (region_code, region_name) 
VALUES ('CA','California'), 
('WA','Washington');

-- passwords: adminpass, userpass

INSERT INTO OperationMonitorUser (username, first_name, last_name, password) 
VALUES ('admin1','Joe','Admin','$2a$12$HGAq7Vq7omQd6tT80.HD1evheGsnauy09Ixwm.QyzQeXsTt2IhHBy'), 
('user1','Joe','User','$2a$12$9xwUSzPp0dlis0pSs.gRN.J8SB/LjvLNEK5aUKrqO7RVvGzH79tNO');

INSERT INTO UserRole (username, role) 
VALUES ('admin1','administrator'), 
('user1','user');

INSERT INTO UserContact (username, street, city, zip_code, region, country) 
VALUES ('admin1', '123 Nice Ave.', 'Seattle', '98109', 'WA', 'US'), 
('user1', '321 Shiny Blvd.', 'Los Angeles', '90096', 'CA', 'US');

