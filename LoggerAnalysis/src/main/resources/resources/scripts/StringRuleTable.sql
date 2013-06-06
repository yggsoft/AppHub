DROP TABLE IF EXISTS StringRule;
create table StringRule(
	id INT PRIMARY KEY AUTO_INCREMENT,
	templeteId INT,
	feature VARCHAR(5000)
);

--2147483647 Integer.MAX_VALUE