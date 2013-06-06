DROP TABLE IF EXISTS ExceptionFragment;
create table ExceptionFragment(
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(5000),
	RCA VARCHAR(10000),
	reproduceSteps VARCHAR(100000),
	rootException VARCHAR(100000),
	context VARCHAR(100000),
	detailMessages VARCHAR(2147483647),
	date TIMESTAMP,
	analysisCompleted boolean default false,
	isMatched boolean default false,
	ignore boolean default false
);

--2147483647 Integer.MAX_VALUE
ALTER TABLE ExceptionFragment
ADD RETRY_TYPE VARCHAR(50);