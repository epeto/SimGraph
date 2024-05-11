
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    LinkedList<Integer[]> estVert; //Estado de los vértices
    LinkedList<Integer[]> estTrans; //Estado de los vértices de la gráfica transpuesta
    LinkedList<Integer> listaPO; //La lista en postorden de la primera visita DFS
    LinkedList<LinkedList<Integer>> componentes; //componentes fuertemente conexas
    int parte; //parte del algoritmo que se está ejecutando (1 o 2)

    /**
     * @param g gráfica de la cual se guarda el estado
     * @param gt transpuesta de g
     * @param postorden lista en postorden
     * @param p parte del algoritmo (1 o 2)
     * @param comps lista de componentes fuertemente conexas
     */
    public Estado(Grafica g, Grafica gt, LinkedList<Integer> postorden, 
    int p, LinkedList<LinkedList<Vertice>> comps){
        estVert = new LinkedList<>();
        estTrans = new LinkedList<>();
        listaPO = new LinkedList<>();
        componentes = new LinkedList<>();

        for(Vertice v : g.vertices){
            Integer[] est = new Integer[3];
            est[0] = v.id;
            est[1] = (v.visitado)? 1 : 0;
            est[2] = v.f;
            estVert.add(est);
        }

        for(Vertice v : gt.vertices){
            Integer[] est = new Integer[3];
            est[0] = v.id;
            est[1] = (v.visitado)? 1 : 0;
            est[2] = v.f;
            estTrans.add(est);
        }

        listaPO.addAll(postorden);
        parte = p;
        if(comps != null){
            for(LinkedList<Vertice> lista : comps){
                LinkedList<Integer> local = new LinkedList<>();
                for(Vertice v : lista){
                    local.add(v.id);
                }
                componentes.add(local);
            }
        }
    }
}

