CREATE DATABASE Hotel;
SET GLOBAL validate_password.policy=LOW;
CREATE USER 'hoteluser'@'localhost' IDENTIFIED BY 'SuperSecretPassword';
GRANT ALL PRIVILEGES ON Hotel.* TO 'hoteluser'@'localhost';
