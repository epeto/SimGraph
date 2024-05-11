
/**
 * Implementación de una cola de prioridad con heap binario.
 */

package estructuras;

import java.util.HashMap;

public class Heap {
    private static final int DEFAULT_CAPACITY = 8; //Capacidad inicial del heap.
    public int size;  // Número de elementos en el heap.
    public int[] array; // El arreglo del heap.
    public HashMap<Integer,Integer> tabla; //Guarda la posición de cada elemento en el arreglo "array".

    /**
     * Constructor del heap binario.
     * @param capacidad capacidad inicial del heap.
     */
    public Heap(int capacidad){
        size = 0;
        array = new int[capacidad + 1];
        tabla = new HashMap<>(capacidad);
    }

    /**
     * Constructor del heap.
     */
    public Heap(){
        this(DEFAULT_CAPACITY);
    }

    /**
     * Mueve el elemento en la posición p hacia arriba.
     * @param p posición del elemento que se va a filtrar.
     */
    private void percolateUp(int p){
        int tmp = array[p];
        array[0] = tmp;
        while(tmp < array[p/2]){
            array[p] = array[p/2];
            tabla.replace(array[p], p);
            p /= 2;
        }
        array[p] = tmp;
        tabla.replace(tmp, p);
    }

    /**
     * Filtra un elemento hacia abajo en el heap.
     * @param p el índice a partir del cual se realiza el filtrado.
     */
    private void percolateDown(int p){
        int hijo;
        int tmp = array[p];

        while(p*2 <= size){
            hijo = p*2;
            if(hijo != size && array[hijo + 1] < array[hijo])
                hijo++;
            if(array[hijo] < tmp){
                array[p] = array[hijo];
                tabla.replace(array[p], p);
            }
            else
                break;
            p = hijo;
        }
        array[p] = tmp;
        tabla.replace(array[p], p);
    }

    /**
     * Agranda el arreglo.
     * @param newSize nuevo tamaño del arreglo.
     */
    private void enlargeArray(int newSize){
        int[] old = array;
        array = new int[newSize];
        for(int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * Inserta un elemento en el heap, manteniendo la propiedad heap-ordenado.
     * No se permiten duplicados (por la tabla hash).
     * @param x elemento a insertar.
     */
    public void insert(int x){
        if(tabla.get(x) == null){
            if(size == array.length - 1)
                enlargeArray(array.length * 2 + 1);

            size++;
            int hole = size;
            array[hole] = x;
            tabla.put(x,hole);
            percolateUp(hole);
        }else{
            throw new RuntimeException("El elemento ya está en el heap.");
        }
    }

    /**
     * Verifica si el heap es vacío.
     * @return true si es vacío, false en otro caso.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Encuentra al elemento más pequeño en el heap.
     * @return el elemento más pequeño en el heap o lanza una excepción si el heap es vacío.
     */
    public int findMin(){
        if(isEmpty())
            throw new RuntimeException("Heap vacío.");
        return array[1];
    }

    /**
     * Elimina el elemento más pequeño del heap.
     * @return el elemento más pequeño o lanza una excepción si el heap es vacío.
     */
    public int deleteMin(){
        if(isEmpty())
            throw new RuntimeException("Heap vacío.");

        int minItem = findMin();
        array[1] = array[size];
        size--;
        percolateDown(1);
        tabla.remove(minItem);

        return minItem;
    }

    /**
     * Hace el heap lógicamente vacío.
     */
    public void makeEmpty(){
        size = 0;
        tabla.clear();
    }

    /**
     * Decrementa el valor de un elemento y lo reubica para que se conserve
     * la propiedad heap-ordenado.
     * @param oldKey llave vieja.
     * @param newKey llave nueva.
     */
    public void decreaseKey(int oldKey, int newKey){
        if(newKey < oldKey){
            int indice = tabla.get(oldKey);
            array[indice] = newKey;
            tabla.remove(oldKey);
            tabla.put(newKey, indice);
            percolateUp(indice);
        }
    }

    /**
     * Elimina un elemento específico del heap.
     * @param x elemento a eliminar.
     */
    public void delete(int x){
        if(tabla.get(x) != null){
            decreaseKey(x, Integer.MIN_VALUE);
            deleteMin();
        }
    }
}
