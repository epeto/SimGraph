
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;
import javafx.util.Pair;

public class Estado {
    LinkedList<String> estCola; //Estado de la cola.
    LinkedList<Pair<Integer, Integer>> estVert; //Estado de los vértices. (id, d)
    LinkedList<Arista> rojas; //Aristas rojas.

    public Estado(Grafica g, Cola<Vertice> c){
        estCola = new LinkedList<>();
        estVert = new LinkedList<>();
        rojas = new LinkedList<>();

        for(Vertice v : c.lista){
            estCola.addLast(v.toString());
        }

        for(Vertice v : g.vertices){
            estVert.add(new Pair<>(v.id, v.d));
            if(v.p != null){
                rojas.add(new Arista(v.p.id, v.id));
            }
        }
    }
}
