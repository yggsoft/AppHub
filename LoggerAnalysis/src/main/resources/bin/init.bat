rem mvn archetype:create -DgroupID=com.angelo -DartifactId=LoggerAnalysis

goto comments
	vi .gitignore
	
	git add .
	git add -u 
	git commit -m "--"
	git push
:comments

java -classpath ../lib/*;  -Xmx512m com.angelo.App init