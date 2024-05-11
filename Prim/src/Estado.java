
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    String[] estHeap; //Estado del heap.
    LinkedList<Integer[]> estVert; //Estado de los vértices. (id, key, enCola)
    LinkedList<Integer[]> ariArbol; //Aristas del árbol.
    LinkedList<Integer[]> ariPred; //Aristas predecesoras.

    public Estado(Grafica g, BinaryHeap<Vertice> heap){
        estHeap = new String[heap.size+1];
        estVert = new LinkedList<>();
        ariArbol = new LinkedList<>();
        ariPred = new LinkedList<>();

        //Se guardan los vértices del heap.
        for(int i=1; i <= heap.size; i++){
            Vertice va = heap.get(i);
            estHeap[i] = va.toString();
        }
        
        //Se guarda el estado de los vértices y las aristas del árbol.
        for(Vertice v : g.vertices){
            Integer[] ev = new Integer[3];
            ev[0] = v.id;
            ev[1] = v.key;
            ev[2] = (v.enCola)? 1 : 0;
            estVert.add(ev);
            if(v.p != null){
                if(v.enCola){
                    Integer[] ariPi = new Integer[2];
                    ariPi[0] = v.p.id;
                    ariPi[1] = v.id;
                    ariPred.add(ariPi);
                }else{
                    Integer[] ariTree = new Integer[2];
                    ariTree[0] = v.p.id;
                    ariTree[1] = v.id;
                    ariArbol.add(ariTree);
                }
            }
        }
    }
}

