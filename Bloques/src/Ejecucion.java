
/**
 * Una ejecución es una secuencia de estados de la gráfica.
 */

import java.util.LinkedList;

public class Ejecucion {
    public LinkedList<Estado> estados; //Lista de estados.

    public Ejecucion(){
        estados = new LinkedList<>();
    }

    /**
     * Agrega un estado a la ejecución.
     * @param est
     */
    public void agrega(Estado est){
        estados.add(est);
    }

    /**
     * Obtiene un estado a partir del índice recibido.
     */
    public Estado getEstado(int indice){
        return estados.get(indice);
    }
}
