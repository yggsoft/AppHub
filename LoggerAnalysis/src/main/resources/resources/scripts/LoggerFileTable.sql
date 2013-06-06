DROP TABLE IF EXISTS LoggerFile;
create table LoggerFile(
	id INT PRIMARY KEY AUTO_INCREMENT,
	fileName VARCHAR(100),
	whichDay TIMESTAMP,
	importDay TIMESTAMP
);

--2147483647 Integer.MAX_VALUE