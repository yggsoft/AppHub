rem mvn archetype:create -DgroupID=com.angelo -DartifactId=LoggerAnalysis

goto comments
	vi .gitignore
	
	git add .
	git commit -m "--"
	git push
:comments

java -classpath slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;log4j-1.2.14.jar; com.angelo.App C:/Users/ggyang/Desktop/AllLogger/April C:/Users/ggyang/Desktop/AllLogger/April_Results