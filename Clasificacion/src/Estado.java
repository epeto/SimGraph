
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<Integer[]> estVert; //Estado de los vértices.
    LinkedList<Integer[]> tree; // Arcos del árbol
    LinkedList<Integer[]> back; // Arcos de regreso
    LinkedList<Integer[]> forward; // Arcos hacia adelante
    LinkedList<Integer[]> cross; // Arcos de cruce

    /**
     * @param g gráfica de la cual se guarda el estado.
     * @param particion partición de aristas.
     */
    public Estado(Grafica g, LinkedList<LinkedList<Arista>> particion){
        estVert = new LinkedList<>();
        tree = new LinkedList<>();
        back = new LinkedList<>();
        forward = new LinkedList<>();
        cross = new LinkedList<>();

        for(Vertice v : g.vertices){
            Integer[] est = new Integer[3];
            est[0] = v.id;
            est[1] = v.visitado;
            est[2] = v.dfi;
            estVert.add(est);
        }

        LinkedList<Arista> ariTree = particion.get(0);
        for(Arista ari : ariTree){
            Integer[] nueva = new Integer[2];
            nueva[0] = ari.vi.id;
            nueva[1] = ari.vf.id;
            tree.add(nueva);
        }

        LinkedList<Arista> ariBack = particion.get(1);
        for(Arista ari : ariBack){
            Integer[] nueva = new Integer[2];
            nueva[0] = ari.vi.id;
            nueva[1] = ari.vf.id;
            back.add(nueva);
        }

        LinkedList<Arista> ariForward = particion.get(2);
        for(Arista ari : ariForward){
            Integer[] nueva = new Integer[2];
            nueva[0] = ari.vi.id;
            nueva[1] = ari.vf.id;
            forward.add(nueva);
        }

        LinkedList<Arista> ariCross = particion.get(3);
        for(Arista ari : ariCross){
            Integer[] nueva = new Integer[2];
            nueva[0] = ari.vi.id;
            nueva[1] = ari.vf.id;
            cross.add(nueva);
        }
    }
}

