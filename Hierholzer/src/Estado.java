
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<Integer> cAct; //Estado del circuito actual.
    LinkedList<Integer> cTotal; //Estado del circuito total.

    /**
     * @param g gráfica de la cual se guarda el estado.
     * @param listaA lista de vértices en el último circuito visitado.
     * @param listaT lista de vértices en el circuito total.
     */
    public Estado(Grafica g, LinkedList<Vertice> listaA, LinkedList<Vertice> listaT){
        cAct = new LinkedList<>();
        cTotal = new LinkedList<>();

        for(Vertice v : listaA){
            cAct.add(v.id);
        }

        for(Vertice v : listaT){
            cTotal.add(v.id);
        }
    }
}

