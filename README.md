# Simulador de gráficas

Animaciones de algoritmos en teoría de gráficas.

- BFS
- DFS
- Vértices de corte y puentes
- Bloques
- Topological sort
- Hierholzer
- Clasificación de aristas
- Kosaraju
- Kruskal
- Prim
- Dijkstra
- Bellman-Ford
- Floyd-Warshall
- Cerradura transitiva

Para ejecutar los programas requerirá un sistema operativo de Linux. Si usa un sistema operativo diferente, deberá modificar los archivos Makefile según le convenga.

Cada directorio es un proyecto independiente. Las instrucciones para compilar y ejecutar son:
- Colocarse en el directorio donde está el archivo Makefile.
- Compilar: make compile
- Ejecutar: make run

Como notará, los __.jar__ que aparecen en la carpeta __lib__ son de JavaFX y GraphStream. Si salen versiones nuevas o si usa un sistema operativo que no sea de Linux, puede borrar el contenido de esa carpeta y descargar los __.jar__ de las páginas oficiales:
- [OpenJFX](https://openjfx.io/)
- [GraphStream](https://graphstream-project.org/)

Si coloca los archivos __.jar__ en otra dirección deberá modificar los archivos Makefile.
