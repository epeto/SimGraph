
/**
 * Estructura de datos cola. El primero en entrar es el primero en salir.
 */

package estructuras;

import java.util.LinkedList;

/**
 *
 * @author emmanuel
 */
public class Cola<T> {
    public LinkedList<T> lista;

    public Cola(){
        lista = new LinkedList<>();
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elem
     */
    public void enqueue(T elem){
        lista.addLast(elem);
    }

    /**
     * Elimina un elemento del inicio de la cola.
     * @return elemento al inicio de la cola.
     */
    public T dequeue(){
        return lista.removeFirst();
    }

    /**
     * Verifica si la cola es vacía.
     */
    public boolean isEmpty(){
        return lista.isEmpty();
    }

    /**
     * Devuelve la representación en String de la cola.
     */
    public String toString(){
        return lista.toString();
    }
}