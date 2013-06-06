DROP TABLE IF EXISTS TempleteMatchStatus;
create table TempleteMatchStatus(
	id INT PRIMARY KEY AUTO_INCREMENT,
	fragmentId INT,
	newestTempleteDate TIMESTAMP
);

--2147483647 Integer.MAX_VALUE
