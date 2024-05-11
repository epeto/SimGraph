
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<Integer[]> estVert; //Estado de los vértices. (id, dfi, low, corte)
    LinkedList<Arista> rojas; //Aristas rojas.
    LinkedList<Arista> puentes;

    /**
     * @param g gráfica de la cual se guarda el estado.
     * @param fin verdadero si el algoritmo ya terminó.
     */
    public Estado(Grafica g, boolean fin){
        estVert = new LinkedList<>();
        rojas = new LinkedList<>();
        puentes = new LinkedList<>();

        for(Vertice v : g.vertices){
            Integer[] arreglo = new Integer[4];
            arreglo[0] = v.id;
            arreglo[1] = v.dfi;
            arreglo[2] = v.low;
            arreglo[3] = (v.deCorte)? 1:0;
            estVert.add(arreglo);
            if(v.p != null){
                rojas.add(new Arista(v.p.id, v.id));
            }
        }

        if(fin){
            for(Vertice v : g.vertices){
                if(v.p != null && v.low > v.p.dfi){
                    puentes.add(new Arista(v.p.id, v.id));
                }
            }
        }
    }
}
