
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<Integer[]> estVert; //Estado de los vértices. (id, dfi, low, corte)
    LinkedList<Integer[]> rojas; //Aristas rojas. (vi.id, vf.id)
    LinkedList<String> estPila; //Estado de la pila de aristas
    LinkedList<LinkedList<Integer>> estBloques; //Estado de la lista de bloques.

    /**
     * @param g gráfica de la cual se guarda el estado.
     * @param bloques lista de bloques de la gráfica.
     */
    public Estado(Grafica g, LinkedList<LinkedList<Arista>> bloques, Pila<Arista> pila){
        estVert = new LinkedList<>();
        rojas = new LinkedList<>();
        estBloques = new LinkedList<>();
        estPila = new LinkedList<>();

        for(Vertice v : g.vertices){
            Integer[] arreglo = new Integer[4];
            arreglo[0] = v.id;
            arreglo[1] = v.dfi;
            arreglo[2] = v.low;
            arreglo[3] = (v.deCorte)? 1:0;
            estVert.add(arreglo);
            if(v.p != null){
                Integer[] ari = new Integer[2];
                ari[0] = v.p.id;
                ari[1] = v.id;
                rojas.add(ari);
            }
        }

        for(LinkedList<Arista> b : bloques){
            estBloques.add(Arista.compacta(b));
        }

        for(Arista ari : pila.lista){
            estPila.add(ari.toString());
        }
    }
}
