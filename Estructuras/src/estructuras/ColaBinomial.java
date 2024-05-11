
package estructuras;

import java.util.HashMap;

/**
 * Cola de prioridad implementada
 * con una cola binomial.
 */
public class ColaBinomial{

    private static final int DEFAULT_TREES = 1;
    public int size; // # de elementos en la cola de prioridad.
    public NodoBin[] theTrees; // Arreglo que contiene raíces de árboles binomiales.
    public HashMap<Integer, NodoBin> tabla; //Tabla hash que guarda la posición de cada elemento.
    
    /**
     * Hace la cola de prioridades lógicamente vacía.
     */
    public void makeEmpty(){
        size = 0;
        tabla.clear();
        for(int i=0; i<theTrees.length; i++)
            theTrees[i] = null;
    }

    /**
     * Constructor de la cola binomial.
     */
    public ColaBinomial(){
        theTrees = new NodoBin[DEFAULT_TREES];
        tabla = new HashMap<Integer, NodoBin>();
        makeEmpty();
    }

    /**
     * Constructor con un solo elemento.
     */
    public ColaBinomial(int item){
        size = 1;
        theTrees = new NodoBin[1];
        NodoBin nuevo = new NodoBin(item);
        tabla = new HashMap<>();
        tabla.put(item,nuevo);
        theTrees[0] = nuevo;
    }

    /**
     * Expande la capacidad del arreglo de árboles binomiales.
     * @param newNumTrees nuevo número de árboles.
     */
    private void expandTheTrees(int newNumTrees){
        NodoBin[] old = theTrees;
        int oldNumTrees = theTrees.length;
        theTrees = new NodoBin[newNumTrees];
        for(int i=0; i<Math.min(oldNumTrees, newNumTrees); i++)
            theTrees[i] = old[i];
        for(int i=oldNumTrees; i<newNumTrees; i++)
            theTrees[i] = null;
    }

    /**
     * Devuelve la capacidad de la cola.
     * Esto es, el máximo número de nodos que puede haber en esta cola.
     */
    private int capacity(){
        return (1 << theTrees.length) - 1;
    }

    /**
     * Devuelve el resultado de fusionar dos árboles del mismo tamaño: t1 and t2.
     */
    private NodoBin combineTrees(NodoBin t1, NodoBin t2){
        if(t1.element > t2.element)
            return combineTrees( t2, t1 );
        t2.parent = t1; // El padre de t2 es t1.
        t2.nextSibling = t1.leftChild; // El hermano de t2 es el primer hijo de t1.
        t1.leftChild = t2; // El primer hijo de t1 es t2.
        t1.rango++; //Se incrementa el rango del nodo raíz.
        return t1;
    }

    /**
     * Fusiona rhs en esta cola binomial.
     * rhs se vuelve vacío. rhs debe ser diferente a this.
     * @param rhs la otra cola binomial.
     */
    public void merge(ColaBinomial rhs){
        if(this == rhs) // Evita fusionarse consigo misma.
            return;

        size += rhs.size;
        tabla.putAll(rhs.tabla); // Copio los valores de la tabla de rhs a esta tabla.
        
        if(size > capacity()){
            int newNumTrees = Math.max( theTrees.length, rhs.theTrees.length ) + 1;
            expandTheTrees( newNumTrees );
        }

        NodoBin carry = null; //Acarreo.
        for(int i=0, j=1; j<=size; i++, j*=2){
            NodoBin t1 = theTrees[i];
            NodoBin t2 = i < rhs.theTrees.length ? rhs.theTrees[i] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch( whichCase )
            {
              case 0: /* No hay árboles */
              case 1: /* Solo este */
                break;
              case 2: /* Solo rhs */
                theTrees[i] = t2;
                rhs.theTrees[i] = null;
                break;
              case 4: /* Solo carry */
                theTrees[i] = carry;
                carry = null;
                break;
              case 3: /* this y rhs */
                carry = combineTrees(t1, t2);
                theTrees[i] = rhs.theTrees[i] = null;
                break;
              case 5: /* this y carry */
                carry = combineTrees(t1, carry);
                theTrees[i] = null;
                break;
              case 6: /* rhs y carry */
                carry = combineTrees(t2, carry);
                rhs.theTrees[i] = null;
                break;
              case 7: /* Todos los árboles */
                theTrees[i] = carry;
                carry = combineTrees(t1, t2);
                rhs.theTrees[i] = null;
                break;
            }
        }

        for(int k=0; k<rhs.theTrees.length; k++)
            rhs.theTrees[k] = null;
        rhs.size = 0;
        rhs.tabla.clear();
    }

    /**
     * Inserta un elemento en la cola binomial, manteniendo la propiedad heap-ordenado
     * de cada árbol binomial.
     * @param x elemento a insertar.
     */
    public void insert(int x){
        size++;
        if(size > capacity())
            expandTheTrees(theTrees.length+1);

        NodoBin t2 = new NodoBin(x);
        tabla.put(x, t2);
        NodoBin carry = null;

        if(theTrees[0] == null){
            theTrees[0] = t2;
            return;
        }else{
            carry = combineTrees(theTrees[0], t2);
            theTrees[0] = null;
        }

        for( int i = 1; i<theTrees.length; i++)
        {
            NodoBin t1 = theTrees[ i ];

            if(t1 == null){
                theTrees[i] = carry;
                return;
            }else{
                carry = combineTrees(t1, carry);
                theTrees[i] = null;
            } 
        }
    }

    /**
     * Encuentra el índice del árbol que contiene al elemento más pequeño.
     * La cola no debe ser vacía.
     * @return el índice del árbol que contiene al elemento más pequeño.
     */
    private int findMinIndex(){
        int i=0;
        int minIndex;

        // Se encuentra al primer elemento no nulo.
        while(theTrees[i] == null){
            i++;
        }

        for(minIndex = i; i<theTrees.length; i++)
            if( theTrees[i] != null &&
                theTrees[i].element  < theTrees[minIndex].element)
                minIndex = i;

        return minIndex;
    }

    /**
     * Comprueba si la cola de prioridades es vacía.
     * @return verdadero si es vacía, falso en otro caso.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Encuentra al elemento más pequeño en la cola de prioridades.
     * @return el elemento más pequeño.
     */
    public int findMin(){
        if(isEmpty())
            throw new RuntimeException("Cola vacía");

        return theTrees[findMinIndex()].element;
    }

    /**
     * Elimina al elemento más pequeño de la cola de prioridades.
     * @return the smallest item, or throw UnderflowException if empty.
     */
    public int deleteMin(){
        if(isEmpty())
            throw new RuntimeException("Cola vacía.");

        int minIndex = findMinIndex();
        int minItem = theTrees[minIndex].element;
        tabla.remove(minItem);
        NodoBin deletedTree = theTrees[minIndex].leftChild;

        ColaBinomial deletedQueue = new ColaBinomial();
        deletedQueue.expandTheTrees(minIndex);
        
        deletedQueue.size = (1 << minIndex) - 1;
        for(int j=minIndex-1; j>=0; j--){
            deletedQueue.theTrees[j] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[j].nextSibling = null;
            deletedQueue.theTrees[j].parent = null;
        }

        theTrees[ minIndex ] = null;
        size -= deletedQueue.size + 1;

        merge(deletedQueue);
        
        return minItem;
    }

    //Clase del nodo binomial.
    public static class NodoBin{
        public int element; // El elemento en el nodo.
        public NodoBin parent; // Padre del nodo.
        public NodoBin leftChild; // Primer hijo.
        public NodoBin nextSibling; // Hermano derecho.
        int rango;

        //Estos atributos servirán para acomodar un nodo dentro de una cuadrícula.
        public int fila;
        public int columna;

        // Constructors
        NodoBin(int theElement){
            this( theElement, null, null, null, 0);
        }

        NodoBin(int theElement, NodoBin p, NodoBin lt, NodoBin nt, int r){
            element     = theElement;
            parent      = p;
            leftChild   = lt;
            nextSibling = nt;
            rango = r;
        }

        /**
         * Coloca al nodo en la posición que le corresponde en una cuadrícula.
         * @param nodo primer hijo de algún nodo.
         * @param filaPadre fila del padre de nodo.
         * @param columnaPadre columna del padre de nodo.
         */
        private void colocaHijo(NodoBin nodo, int filaPadre, int columnaPadre, int rangoPadre){
            int miFila = filaPadre+1;
            int miColumna = 0;
            int desplazaIzq = 0; //Cuántas posiciones va a estar a la izquierda de su padre.
            if(rangoPadre >= 2){
                desplazaIzq = 1 << (rangoPadre-2);
            }

            miColumna = columnaPadre - desplazaIzq;
            nodo.fila = miFila;
            nodo.columna = miColumna;

            if(nodo.nextSibling != null){
                colocaHermano(nodo.nextSibling, miFila, miColumna, columnaPadre);
            }

            if(nodo.leftChild!= null){
                colocaHijo(nodo.leftChild, miFila, miColumna, nodo.rango);
            }
        }

        /**
         * Coloca al hermano de un nodo en la posición correcta en una cuadrícula.
         */
        private void colocaHermano(NodoBin nodo, int filaHI, int columnaHI, int columnaPadre){
            int miFila = filaHI;
            int miColumna = 0;
            if(nodo.nextSibling == null){
                miColumna = columnaPadre;
            }else{
                miColumna = (columnaHI + columnaPadre)/2;
            }

            nodo.fila = miFila;
            nodo.columna = miColumna;

            if(nodo.nextSibling != null){
                colocaHermano(nodo.nextSibling, miFila, miColumna, columnaPadre);
            }

            if(nodo.leftChild != null){
                colocaHijo(nodo.leftChild, miFila, miColumna, nodo.rango);
            }
        }

        /**
         * Calcula la columna y fila de cada nodo en un árbol binomial
         * suponiendo que se coloca en una cuadrícula.
         */
        public void posiciones(){
            int nc = 0; //Número de columnas.
            if(rango == 0){
                nc = 1;
            }else{
                nc = 1 << (rango-1);
            }

            fila = 0;
            columna = nc-1;

            if(leftChild != null){
                colocaHijo(leftChild, fila, columna, rango);
            }
        }
    }

    /**
     * Realiza un filtrado hacia arriba a partir del nodo que recibe.
     * @param nodo nodo a partir del cual se realiza filtrado hacia arriba.
     */
    private void percolateUp(NodoBin nodo){
        if(nodo.parent != null &&
           nodo.element < nodo.parent.element){
            int t = nodo.element;
            nodo.element = nodo.parent.element;
            nodo.parent.element = t;

            tabla.replace(nodo.element, nodo);
            tabla.replace(nodo.parent.element, nodo.parent);

            percolateUp(nodo.parent);
        }
    }

    /**
     * Decrementa el valor de algún elemento en la cola binomial.
     * @param oldKey valor actual.
     * @param newKey nueva.
     */
    public void decreaseKey(int oldKey, int newKey){
        NodoBin nd = tabla.get(oldKey);
        if(nd != null && newKey < oldKey){
            nd.element = newKey;
            tabla.remove(oldKey);
            tabla.put(newKey, nd);
            percolateUp(nd);
        }
    }

    /**
     * Elimina un elemento específico de la cola binomial.
     * @param elem elemento a eliminar.
     */
    public void delete(int elem){
        decreaseKey(elem, Integer.MIN_VALUE);
        deleteMin();
    }

    /**
     * Calcula la fila y la columna de cada nodo en la cola binomial.
     */
    public void calculaFC(){
        for(int i=0; i<theTrees.length; i++){
            if(theTrees[i] != null){
                theTrees[i].posiciones();
            }
        }
    }
}

