compile:
	mkdir -p classes
	javac -p ../lib --add-modules=ALL-MODULE-PATH src/Vertice.java -d classes
	javac src/BinaryHeap.java -d classes
	javac -cp classes -p ../lib --add-modules=ALL-MODULE-PATH src/Grafica.java -d classes
	javac -cp classes src/Estado.java -d classes
	javac -cp classes src/Ejecucion.java -d classes
	javac -cp classes -p ../lib --add-modules=ALL-MODULE-PATH src/Algoritmo.java -d classes
	javac -cp classes -p ../lib --add-modules=ALL-MODULE-PATH src/Principal.java -d classes
run:
	java -p ../lib --add-modules=ALL-MODULE-PATH -cp ./classes Principal
clean:
	rm -r classes
