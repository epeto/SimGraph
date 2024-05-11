
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<Integer[]> estVert; //Estado de los vértices. (id, d)
    LinkedList<Integer[]> ariArbol; //Aristas del árbol.

    public Estado(Grafica g){
        estVert = new LinkedList<>();
        ariArbol = new LinkedList<>();
        
        //Se guarda el estado de los vértices y las aristas del árbol.
        for(Vertice v : g.vertices){
            Integer[] ev = new Integer[2];
            ev[0] = v.id;
            ev[1] = v.d;
            estVert.add(ev);
            if(v.p != null){
                Integer[] ariTree = new Integer[2];
                ariTree[0] = v.p.id;
                ariTree[1] = v.id;
                ariArbol.add(ariTree);
            }
        }
    }
}


