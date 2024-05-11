
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;
import javafx.util.Pair;

public class Estado {
    LinkedList<String> estPila; //Estado de la pila.
    LinkedList<Pair<Integer, Integer>> estVert; //Estado de los vértices. (id, dfi)
    LinkedList<Arista> rojas; //Aristas rojas.

    public Estado(Grafica g, Pila<Vertice> p){
        estPila = new LinkedList<>();
        estVert = new LinkedList<>();
        rojas = new LinkedList<>();

        for(Vertice v : p.lista){
            estPila.addLast(v.toString());
        }

        for(Vertice v : g.vertices){
            estVert.add(new Pair<>(v.id, v.dfi));
            if(v.p != null){
                rojas.add(new Arista(v.p.id, v.id));
            }
        }
    }
}
