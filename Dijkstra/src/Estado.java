
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<LinkedList<NodoEst>> estCB; //Estado de la cola binomial.
    LinkedList<Integer[]> estVert; //Estado de los vértices. (id, d)
    LinkedList<Integer[]> ariArbol; //Aristas del árbol.

    public Estado(Grafica g, BinomialQueue<Vertice> cola){
        estVert = new LinkedList<>();
        ariArbol = new LinkedList<>();
        estCB = new LinkedList<>();
        
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
        //Se guarda el estado de la cola binomial.
        estadosCB(estCB, cola);
    }

    private void estadosCB(LinkedList<LinkedList<NodoEst>> lista, BinomialQueue<Vertice> cola){
        for(int i=0; i<cola.theTrees.length; i++){
            if(cola.theTrees[i] != null){
                cola.theTrees[i].posiciones();
                LinkedList<NodoEst> otraLista = new LinkedList<>();
                estadosNodo(otraLista, cola.theTrees[i]);
                lista.add(otraLista);
            }
        }
    }

    private void estadosNodo(LinkedList<NodoEst> lista, BinomialQueue.BinNode<Vertice> nodo){
        NodoEst nuevo = new NodoEst(nodo);
        lista.add(nuevo);
        if(nodo.leftChild != null){
            estadosNodo(lista, nodo.leftChild);
        }
        if(nodo.nextSibling != null){
            estadosNodo(lista, nodo.nextSibling);
        }
    }
}


