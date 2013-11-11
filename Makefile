CP=~/jcs-1.3/jcs-1.3.jar:.:~/commons-logging-1.1.3/commons-logging-1.1.3.jar
all: BenchMark.class test

BenchMark.class: BenchMark.java
	javac BenchMark.java -cp $(CP)

test: BenchMark.class
	java -cp $(CP) BenchMark

clean:
	rm BenchMark.class

