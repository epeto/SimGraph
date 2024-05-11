
// Estructura de datos: Heap binario

import java.util.HashMap;

//TODO: cambiar de tipo T a tipo Vertice
public class BinaryHeap<T extends Comparable<? super T>>{

    private static final int DEFAULT_CAPACITY = 10;
    public int size;  // Número de elementos en el heap.
    public T[] array; // El arreglo del heap.
    private HashMap<T,Integer> tabla; //Guarda la posición de cada elemento en el arreglo "array".

    /**
     * Construct the binary heap.
     */
    public BinaryHeap()
    {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor del heap binario
     * @param capacity tamaño inicial del arreglo.
     */
    public BinaryHeap(int capacity){
        size = 0;
        //Como no se puede crear un arreglo de tipo T, se hace un cast de Comparable a T.
        array = (T[]) new Comparable[capacity + 1];
        tabla = new HashMap<T, Integer>(capacity);
    }
    
    /**
     * Construye el heap a partir de un arreglo dado.
     */
    public BinaryHeap(T[] items){
            size = items.length;
            array = (T[]) new Comparable[(size + 2) * 11 / 10];
            tabla = new HashMap<T, Integer>(size);

            int i = 1;
            for( T item : items ){
                array[i] = item;
                tabla.put(item, i);
                i++;
            }
            buildHeap();
    }

    /**
     * Inserta un elemento en el heap, manteniendo la propiedad heap-ordenado.
     * No se permiten duplicados.
     * @param x elemento a insertar.
     */
    public void insert(T x){
        if(size == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        size++;
        int hole = size;
        array[hole] = x;
        tabla.put(x,hole);
        percolateUp(hole);
    }

    /**
     * Si ya se llenó el arreglo, lo hace más grande.
     * @param newSize nuevo tamaño del arreglo.
     */
    private void enlargeArray(int newSize){
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        for(int i = 0; i < old.length; i++)
            array[i] = old[i];
    }
    
    /**
     * Encuentra al elemento más pequeño en el heap.
     * @return elemento más pequeño.
     */
    public T findMin(){
        if(isEmpty())
            throw new RuntimeException("Heap vacío.");
        return array[1];
    }

    /**
     * Devuelve el elemento en la posición i del arreglo.
     * @param i índice del elemento a devolver.
     */
    public T get(int i){
        return array[i];
    }

    /**
     * Elimina el elemento más pequeño del heap.
     * @return elemento más pequeño.
     */
    public T deleteMin(){
        if(isEmpty())
            throw new RuntimeException("Heap vacío.");

        T minItem = findMin();
        array[1] = array[size];
        size--;
        percolateDown(1);
        tabla.remove(minItem);

        return minItem;
    }

    /**
     * Establece la propiedad heap-ordenado a partir de
     * un arreglo arbitrario de elementos.
     */
    private void buildHeap(){
        for(int i = size/2; i > 0; i--)
            percolateDown(i);
    }

    /**
     * Comprueba si el heap es vacío.
     * @return verdadero si es vacío, falso en otro caso.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Hace el heap lógicamente vacío.
     */
    public void makeEmpty(){
        size = 0;
    }

    /**
     * Realiza una filtración hacia arriba a partir del elemento en la posición p.
     * @param p posición del elemento que se va a filtrar.
     */
    private void percolateUp(int p){
        T tmp = array[p];
        array[0] = tmp;
        while(tmp.compareTo(array[p/2]) < 0){
            array[p] = array[p/2];
            tabla.replace(array[p], p);
            p /= 2;
        }
        array[p] = tmp;
        tabla.replace(tmp, p);
    }

    /**
     * Realiza un filtrado hacia abajo en el heap.
     * @param p índice a partir del cual empieza el filtrado.
     */
    private void percolateDown(int p){
        int child;
        T tmp = array[p];

        while(p * 2 <= size){
            child = p * 2;
            if(child != size &&
                    array[child + 1].compareTo(array[child]) < 0)
                child++;
            if(array[child].compareTo(tmp) < 0){
                array[p] = array[child];
                tabla.replace(array[p], p);
            }
            else
                break;

            p = child;
        }
        array[p] = tmp;
        tabla.replace(tmp, p);
    }

    /**
     * Reacomoda un elemento que probablemente decrementó su llave.
     * @param x elemento a reacomodar.
     */
    public void decreaseKey(T x){
        percolateUp(tabla.get(x));
    }

}


