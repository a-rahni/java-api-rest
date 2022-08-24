CREATE USER 'api_user'@'localhost' IDENTIFIED BY 'api_pwd';
GRANT ALL ON `tp_rest` . * TO 'api_user'@'localhost' IDENTIFIED BY 'api_pwd';