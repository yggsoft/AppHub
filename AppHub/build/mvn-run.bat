cd ..
mvn package -Dmaven.test.skip=true
mvn exec:java -Dexec.mainClass="com.app.hub.FileManager" 

goto comments
	mvn exec:help -Ddetail=true -Dgoal=java
	mvn exec:java -Dexec.mainClass="com.app.hub.FileManager" -Dexec.args="arg0 arg1 arg2"
	mvn test -PfileManager
:comments