rem mvn archetype:create -DgroupID=com.angelo -DartifactId=LoggerAnalysis

goto comments
	vi .gitignore
	
	git add .
	git commit -m "--"
	git push
:comments

java -classpath ../lib/*; com.angelo.App init