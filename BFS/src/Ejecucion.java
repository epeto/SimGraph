
/**
 * Una ejecución es una secuencia de estados de la gráfica.
 */

import java.util.LinkedList;

public class Ejecucion {
    public LinkedList<Estado> estados; //Lista de estados.
    public int vinit; //Vértice inicial.

    public Ejecucion(){
        estados = new LinkedList<>();
    }

    /**
     * Fija el vértice inicial.
     * @param inicial id del vértice inicial.
     */
    public void setInit(int inicial){
        vinit = inicial;
    }

    /**
     * Agrega un estado a la ejecución.
     * @param est
     */
    public void agrega(Estado est){
        estados.add(est);
    }

    /**
     * Obtiene un estado dependiendo del índice recibido.
     */
    public Estado getEstado(int indice){
        return estados.get(indice);
    }
}
