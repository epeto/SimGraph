
import java.util.LinkedList;

public class Algoritmo{

    // Búsqueda en profundidad 1
    public static int dfs_visitPO1(Vertice u, int time, LinkedList<Integer> postorden,
    Grafica g, Grafica gt, Ejecucion exec){
        u.visitado = true;
        exec.agrega(new Estado(g, gt, postorden, 1, null));
        for(Vertice v : u.ady){
            if(!v.visitado){
                v.p = u;
                time = dfs_visitPO1(v, time, postorden, g, gt, exec);
            }
        }
        time++;
        u.f = time;
        postorden.addFirst(u.id);

        return time;
    }

    // Búsqueda en profundidad 2
    public static void dfs_visitPO2(Vertice u, LinkedList<Vertice> componente){
        u.visitado = true;
        componente.add(u);

        for(Vertice v : u.ady){
            if(!v.visitado){
                v.p = u;
                dfs_visitPO2(v, componente);
            }
        }
    }

    /**
     * Algoritmo para encontrar las componentes fuertemente conexas de una digráfica.
     * @param g gráfica de la cual se van a encontrar las componentes.
     * @param gt transpuesta de g
     * @param exec lista de estados
     * @return lista de componentes.
     */
    public static LinkedList<LinkedList<Vertice>> scc(Grafica g, Grafica gt, Ejecucion exec){
        int time = 0;
        //DFS en la primera gráfica
        LinkedList<Integer> postorden = new LinkedList<Integer>();
        for(Vertice v:g.vertices){
            if(!v.visitado){
                time = dfs_visitPO1(v, time, postorden, g, gt, exec);
            }
        }
        exec.agrega(new Estado(g, gt, postorden, 1, null));
        int n = g.getOrden();
        for(int i=0; i<n; i++){
            gt.vertices[i].f = g.vertices[i].f;
        }
        LinkedList<LinkedList<Vertice>> componentes = new LinkedList<LinkedList<Vertice>>();
        //DFS en la segunda gráfica
        for(Integer idv : postorden){
            Vertice v = gt.getVertice(idv);
            if(!v.visitado){
                LinkedList<Vertice> nuevaComp = new LinkedList<Vertice>();
                dfs_visitPO2(v, nuevaComp);
                componentes.add(nuevaComp);
                exec.agrega(new Estado(g, gt, postorden, 2, componentes));
            }
        }
        return componentes;
    }
}

