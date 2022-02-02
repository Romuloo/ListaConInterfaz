jar_file = catalogo.jar

jar: benito
	jar -cfm catalogo.jar Manifest.txt -C bin .

benito: camela
	mkdir -p bin
	find . -name *.java | xargs javac -cp "lib/*:bin" -d bin

camela:
	rm -rf *.jar
	rm -rf bin/*
	rm -f $(jar_file)
