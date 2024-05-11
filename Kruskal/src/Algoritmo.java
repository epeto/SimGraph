
//Se implementa el algoritmo de Kruskal con conjuntos ajenos.

import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Algoritmo{

    /**
     * Algoritmo de Kruskal con conjuntos ajenos.
     * @param g gráfica conexa.
     * @param pathComp decide si se realiza compresión de trayectoria.
     * @param smartUn decide si se realiza unión por tamaño.
     * @param exec Ejecución
     * @return aristas que forman el árbol.
     */
    public static List<Arista> kruskal(Grafica g, boolean pathComp, boolean smartUn, Ejecucion exec){
        LinkedList<Arista> a = new LinkedList<>(); //Conjunto resultante.
        DisjSets ds = new DisjSets(g.getOrden()); //Conjuntos disjuntos de vértices.
        PriorityQueue<Arista> q = new PriorityQueue<>(g.aristas); //Cola de prioridades que contendrá a las aristas.

        while(a.size() < g.getOrden()-1){
            Arista edge = q.poll();
            exec.agrega(new Estado(ds.s, a, edge));
            int root1, root2;
            if(pathComp){
                root1 = ds.findSetPC(edge.vi.id);
                root2 = ds.findSetPC(edge.vf.id);
            }else{
                root1 = ds.findSet(edge.vi.id);
                root2 = ds.findSet(edge.vf.id);
            }
            
            if(root1 != root2){ //Si están en conjuntos diferentes.
                a.add(edge); //Se agrega la arista al conjunto a.
                if(smartUn){
                    ds.smartUnion(root1, root2);
                }else{
                    ds.union(root1, root2);
                }
            }
        }
        exec.agrega(new Estado(ds.s, a, null));
        return a;
    }
}


