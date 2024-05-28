# Productions config
CREATE DATABASE Hotel;
SET GLOBAL validate_password.policy=LOW;
CREATE USER 'hoteluser'@'localhost' IDENTIFIED BY 'SuperSecretPassword';
GRANT ALL PRIVILEGES ON Hotel.* TO 'hoteluser'@'localhost' WITH GRANT OPTION;

# Development config
CREATE DATABASE Hotel_Development
SET GLOBAL validate_password.policy=LOW;
CREATE USER 'developer'@'localhost' IDENTIFIED BY 'IAmADeveloper';
GRANT ALL PRIVILEGES ON Hotel_Development.* TO 'developer'@'localhost' WITH GRANT OPTION;

