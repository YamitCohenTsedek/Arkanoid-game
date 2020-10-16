
compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
	rm sources.txt

bin:
	mkdir bin

run:
	java -cp biuoop-1.4.jar:ArkanoidGame.jar:resources Arkanoid

jar:
	jar -cfm ArkanoidGame.jar META-INF/MANIFEST.MF -C bin . -C resources .