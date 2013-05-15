@echo off
cd ..
mvn package -Dmaven.test.skip=true 
cmd
pause

rem telnet 192.168.3.193 8080
rem GET /cvmedhome-datamine/ HTTP/1.1
rem GBK
rem chcp 936
rem UTF-8
rem chcp 65001

rem ssh -T git@github.com

rem ssh-keygen -C "yggsoft@163.com" -f ~/.ssh/yggsoft
rem cat ~/.ssh/yggsoft.pub | pbcopy
rem git clone git@github.com:yggsoft/AppHub.git
rem git add README.md
rem git commit -m "README for this project."
rem git push origin master

rem git remote add origin git@github.com:yggsoft/AppHub.git
rem git push -u origin master