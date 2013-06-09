--2147483647 Integer.MAX_VALUE
DROP TABLE IF EXISTS LoggerFile;
create table LoggerFile(
	id VARCHAR(100) PRIMARY KEY,
	fileName VARCHAR(100),
	whichDay TIMESTAMP,
	importDay TIMESTAMP
);

DROP TABLE IF EXISTS ExceptionFragment;
create table ExceptionFragment(
	id VARCHAR(100) PRIMARY KEY,
	context VARCHAR(100000),
	detailMessages VARCHAR(2147483647),
	date TIMESTAMP,
	isMatched boolean default false,
	analysisCompleted boolean default false,
	logfileId VARCHAR(100)
);

DROP TABLE IF EXISTS JobStatus;
create table JobStatus(
	fragmentId VARCHAR(100),
	jobId VARCHAR(100),
	times INT default 0,
	status INT default 0
);
--status 1:completed

DROP TABLE IF EXISTS Job;
create table Job(
	id VARCHAR(100),
	jobName VARCHAR(100),
	currentTimes INT
);

DROP TABLE IF EXISTS TemplateExceptionFragment;
create table TemplateExceptionFragment(
	tempId VARCHAR(100),
	fragmentId VARCHAR(100)
);

DROP TABLE IF EXISTS Templete;
create table Templete(
	id VARCHAR(100) PRIMARY KEY,
	name VARCHAR(5000),
	category VARCHAR(5000),
	title VARCHAR(10000),
	RCA VARCHAR(100000),
	reProduceSteps VARCHAR(100000),
	priority INT,
	timestamp TIMESTAMP,
	ignore BOOLEAN default false
);

DROP TABLE IF EXISTS StringRule;
create table StringRule(
	id VARCHAR(100) PRIMARY KEY,
	templeteId VARCHAR(100),
	feature VARCHAR(5000)
);

DROP TABLE IF EXISTS AnalysisStatus;
create table TempleteMatchStatus(
	id VARCHAR(100) PRIMARY KEY AUTO_INCREMENT,
	fragmentId VARCHAR(100),
	newestTempleteDate TIMESTAMP
);
-- this is for improving performance.