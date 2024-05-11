
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<String> estCola; //Estado de la cola.
    LinkedList<Integer[]> estVert; //Estado de los vértices. (id, tsi)
    LinkedList<Integer[]> punteadas; //Aristas punteadas.

    public Estado(Grafica g, Cola<Vertice> c, LinkedList<Integer[]> arisP){
        estCola = new LinkedList<>();
        estVert = new LinkedList<>();
        punteadas = new LinkedList<>();

        for(Vertice v : c.lista){
            estCola.addLast(v.toString());
        }

        for(Vertice v : g.vertices){
            Integer[] nuevo = new Integer[2];
            nuevo[0] = v.id;
            nuevo[1] = v.tsi;
            estVert.add(nuevo);
        }

        punteadas.addAll(arisP);
    }
}

