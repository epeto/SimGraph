
import java.util.LinkedList;

public class Algoritmo{

    public static LinkedList<LinkedList<Arista>> dfs(Grafica g, Ejecucion exec){
        LinkedList<LinkedList<Arista>> particion = new LinkedList<LinkedList<Arista>>();
        particion.add(new LinkedList<Arista>()); // 0 -> Aristas del árbol (tree edge)
        particion.add(new LinkedList<Arista>()); // 1 -> Aristas de retroceso (back edge)
        particion.add(new LinkedList<Arista>()); // 2 -> Aristas delanteras (forward edge)
        particion.add(new LinkedList<Arista>()); // 3 -> Aristas de cruce (cross edge)
        
        int time = 0;

        for(Vertice v:g.vertices){
            if(v.visitado == 0){
                time = dfs_visit(g, v, time, particion, exec);
            }
        }
        exec.agrega(new Estado(g, particion));
        return particion;
    }

    public static int dfs_visit(Grafica g, Vertice u, int time,
                                LinkedList<LinkedList<Arista>> particion, Ejecucion exec){
        u.visitado = 1;
        time++;
        u.dfi = time;
        exec.agrega(new Estado(g, particion));

        for(Vertice v : u.ady){
            if(v.visitado == 0){
                v.p = u;
                particion.get(0).add(new Arista(u,v)); //tree edge
                time = dfs_visit(g, v, time, particion, exec);
            }else if(v.visitado == 1){
                particion.get(1).add(new Arista(u,v)); //back edge
                exec.agrega(new Estado(g, particion));
            } else if(v.dfi > u.dfi){
                if(g.dirigida){
                    particion.get(2).add(new Arista(u,v)); //forward edge
                }else{
                    particion.get(1).add(new Arista(u,v)); //back edge
                }
                exec.agrega(new Estado(g, particion));
            } else{
                particion.get(3).add(new Arista(u,v)); //cross edge
                exec.agrega(new Estado(g, particion));
            }
        }
        u.visitado = 2;
        return time;
    }
}


