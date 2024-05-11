
/**
 * Estructura de árbol n-ário con listas de hijos.
 * Servirá para construir el árbol de conjuntos ajenos.
 */

import java.util.LinkedList;

public class DSArbol{
    
    public LinkedList<NodoDS> raices; //Lista de raíces.
    public NodoDS[] arreglo; //Arreglo que contiene todos los nodos del árbol.

    /**
     * Constructor del árbol.
     * @param disjset arreglo interno de la estructura de conjuntos ajenos.
     */
    public DSArbol(int[] disjset){
        raices = new LinkedList<>();
        arreglo = new NodoDS[disjset.length];
        //Se crean todos los nodos.
        for(int i=0; i<arreglo.length; i++){
            arreglo[i] = new NodoDS(i);
        }
        //Luego, se agrega cada nodo a la lista de hijos correspondiente.
        for(int i=0; i<arreglo.length; i++){
            int padre = disjset[i];
            if(padre < 0){
                raices.add(arreglo[i]);
            }else{
                arreglo[padre].hijos.add(arreglo[i]);
                arreglo[i].parent = arreglo[padre];
            }
        }
    }

    /**
     * Calcula la altura de un subárbol.
     */
    public int alturaNodo(NodoDS nodo){
        if(nodo.hijos.isEmpty()){
            nodo.alto = 0;
            return 0;
        }else{
            int max_altura = 0; //Guardará la altura máxima de sus hijos.
            for(NodoDS h : nodo.hijos){
                int alturaH = alturaNodo(h);
                if(alturaH > max_altura){
                    max_altura = alturaH;
                }
            }
            nodo.alto = max_altura + 1;
            return nodo.alto;
        }
    }

    /**
     * Se calcula la altura de todos los árboles
     */
    public void alturaBosque(){
        for(NodoDS raiz : raices){
            alturaNodo(raiz);
        }
    }

    /**
     * Calcula el ancho de un subárbol.
     * @param nodo raíz de un subárbol.
     * @return ancho del subárbol.
     */
    public int anchoNodo(NodoDS nodo){
        if(nodo.hijos.isEmpty()){
            nodo.ancho = 1;
            return 1;
        }else{
            int anchoTotal = 0;
            for(NodoDS h : nodo.hijos){
                anchoTotal += anchoNodo(h);
            }

            nodo.ancho = anchoTotal;
            return nodo.ancho;
        }
    }

    /**
     * Calcula el ancho de todos los árboles.
     */
    public void anchoBosque(){
        for(NodoDS raiz : raices){
            anchoNodo(raiz);
        }
    }

    /**
     * Clase interna que representará un nodo.
     */
    public class NodoDS{
        public int element; //Elemento en este nodo.
        public LinkedList<NodoDS> hijos; //Lista de hijos de este nodo.
        public int alto; //Altura de este subárbol.
        public int ancho; //Ancho de este subárbol.
        public int profundidad; //Profundidad de este nodo.
        public double x; //Coordenada x
        public double y; //Coordenada y
        public NodoDS parent; //padre de este nodo.

        /**
         * Constructor.
         * @param e elemento en este nodo.
         */
        public NodoDS(int e){
            element = e;
            hijos = new LinkedList<>();
        }
    }
}

