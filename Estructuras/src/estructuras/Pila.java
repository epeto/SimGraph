/**
 * Estructura de datos pila. El último elemento en entrar es el primero en salir.
 */

package estructuras;

import java.util.LinkedList;

/**
 *
 * @author emmanuel
 */
public class Pila<T> {
    public LinkedList<T> lista;
    
    public Pila(){
        lista = new LinkedList<>();
    }
    
    /**
     * Agrega un elemento al tope de la pila.
     * @param elem: elemento a agregar.
     */
    public void push(T elem){
        lista.addFirst(elem);
    }
    
    /**
     * Elimina el elemento al tope de la pila.
     * @return elemento eliminado.
     */
    public T pop(){
        return lista.removeFirst();
    }
    
    /**
     * Devuelve el elemento al tope de la pila sin eliminarlo.
     * @return elemento al tope de la pila.
     */
    public T peek(){
        return lista.getFirst();
    }
    
    /**
     * Decide si la pila es vacía.
     * @return verdadero si es vacía y falso en otro caso.
     */
    public boolean isEmpty(){
        return lista.isEmpty();
    }
    
    @Override
    public String toString(){
        return lista.toString();
    }
}
