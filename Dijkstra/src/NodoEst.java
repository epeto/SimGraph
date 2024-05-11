
/**
 * Guarda la información importante de un nodo binomial.
 */

public class NodoEst{
    String elemento; // El elemento que contiene el nodo en String.
    int columna;
    int fila;
    int cpadre; // Columna del padre
    int fpadre; // Fila del padre

    public NodoEst(BinomialQueue.BinNode<Vertice> nodo){
        elemento = nodo.element.toString();
        columna = nodo.columna;
        fila = nodo.fila;
        if(nodo.parent != null){
            cpadre = nodo.parent.columna;
            fpadre = nodo.parent.fila;
        }else{
            cpadre = -1;
            fpadre = -1;
        }
    }
}
