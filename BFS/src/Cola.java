
import java.util.LinkedList;

public class Cola<T>{

    public LinkedList<T> lista;

    //Constructor.
    public Cola(){
        lista = new LinkedList<T>();
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param e: elemento a agregar.
     */
    public void enqueue(T e){
        lista.add(e);
    }

    /**
     * Elimina y devuelve elemento al principio de la cola.
     * @return e: elemento que devuelve.
     */
    public T dequeue(){
        return lista.removeFirst();
    }

    /**
     * Ve el elemento al principio de la cola sin eliminarlo.
     * @return e: elemento que devuelve.
     */
    public T peek(){
        return lista.getFirst();
    }

    /**
     * Comprueba si la cola está vacía.
     * @return b: true si y solo si la cola está vacía.
     */
    public boolean isEmpty(){
        return lista.size() == 0;
    }
}
