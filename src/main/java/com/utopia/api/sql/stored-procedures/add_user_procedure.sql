USE utopia;

DROP PROCEDURE IF EXISTS add_user;

DELIMITER $$

CREATE PROCEDURE add_user(
	contact VARCHAR(255), 
    password VARCHAR(255),
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    balance DECIMAL(15, 2),
    country VARCHAR(50),
    province VARCHAR(50),
    city VARCHAR(50),
    address VARCHAR (500)
)
BEGIN
	DECLARE users_count BIGINT;
    DECLARE role BIGINT default 3;
    SELECT COUNT(*) INTO users_count FROM users;
    
    IF users_count = 0 
    THEN
		SELECT 1 INTO role;
	END IF;
    
    IF balance < 0 THEN
		SIGNAL SQLSTATE '22003' SET MESSAGE_TEXT = 'Invalid balance amount';
	END IF;
    
    INSERT INTO users VALUES(DEFAULT, contact, password, role, firstname, lastname, balance, country, province, city, address, DEFAULT);
    
    SELECT * FROM users WHERE users.contact = contact;
END $$

DELIMITER ;