@echo off
call mvn-build.bat
pause

rem telnet 192.168.3.193 8080
rem GET /cvmedhome-datamine/ HTTP/1.1
rem GBK
rem chcp 936
rem UTF-8
rem chcp 65001

goto comments
	ssh-keygen -C "yggsoft@163.com" -f ~/.ssh/yggsoft
	ssh -T git@github.com
	ssh -Tv git@github.com
	
	cat ~/.ssh/yggsoft.pub | pbcopy
	git clone git@github.com:yggsoft/AppHub.git
	git clone https://github.com/yggsoft/AppHub.git
	git add README.md
	git commit -m "README for this project."
	git push origin master
:comments

rem git remote add origin git@github.com:yggsoft/AppHub.git
rem git push -u origin master

rem ------------------Home page------------------
rem git clone git@github.com:yggsoft/angelo.github.com.git
rem cd angelo.github.com

goto comments
	Create a new repository on the command line
	touch README.md
	git init
	git add README.md
	git commit -m "first commit"
	git remote add origin https://github.com/yggsoft/angelo.github.com.git
	git push -u origin master
:comments

goto comments
	Create a new repository on the command line
	touch README.md
	git init
	git add README.md
	git commit -m "first commit"
	git remote add origin https://github.com/yggsoft/angelo.github.com.git
	git push -u origin master
:comments

goto comments
	git log --pretty=fuller
	git push
	git push -f
:comments

goto comments
	git checkout -b mybranch1
	touch hello1
	git add hello1
	git commit -m "add hello1 for mark."
:comments
goto comments
	git branch -d mybranch1
	git checkout master
	git branch
	git branch -D mybranch1
	git push origin :mybranch1
:comments
goto comments
	git clone git@github.com:yggsoft/angelo.github.com.git
	cd gotgithub.github.com/
	printf "<h1>Angelo's HomePage</h1>It works.\n" > index.html
	git add index.html
	git commit -m "Homepage test version."
	git push -u origin master
:comments
goto comments
:comments
goto comments
:comments

cmd
