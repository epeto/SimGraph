compile:
	mkdir -p classes
	javac src/Vertice.java -d classes
	javac -cp classes src/Arista.java -d classes
	javac -cp classes src/Grafica.java -d classes
	javac -cp classes src/Estado.java -d classes
	javac -cp classes src/Ejecucion.java -d classes
	javac -cp classes src/Algoritmo.java -d classes
	javac -cp classes -p ../lib --add-modules=ALL-MODULE-PATH src/Principal.java -d classes
run:
	java -p ../lib --add-modules=ALL-MODULE-PATH -cp ./classes Principal
clean:
	rm -r classes
