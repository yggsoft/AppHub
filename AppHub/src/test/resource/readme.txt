testDataTemplate��
--src
----1.txt
----1
------2.txt
------2

--dest

--------------------------------------------
expect:
	copy files: 2
	compare tree


===============================
testDataTemplate2��
--src
----1.txt
----1
------2.txt
------2

--dest
----1
------2.txt

expect:
	copy files: 1
	compare tree


===============================
testDataTemplate3��
--src
----1.txt
----1
------2.txt
------2

--dest
----1
------2.txt [���ݲ���]

expect:
	copy files: 2
	compare tree


===============================
testDataTemplate4��
--src
----1.txt

--dest
----1.txt
----1
------2.txt
------2

expect:
	delete files: 1
	compare tree




