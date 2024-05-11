
//Algoritmo de Prim usando un heap binario como cola de prioridades.

import javafx.util.Pair;

public class Algoritmo{
    /**
     * Ejecuta el algoritmo de Prim.
     * @param g gráfica sobre la cual se ejecuta el algoritmo.
     * @param r raíz sobre la cual se aplica el algoritmo.
     */
    public static void prim(Grafica g, Vertice r, Ejecucion exec){
        BinaryHeap<Vertice> q; //Cola de prioridades.
        //Inicializar la gráfica.
        for(Vertice u : g.vertices){
            u.key = Integer.MAX_VALUE;
            u.enCola = true;
        }
        
        r.key = 0;
        q = new BinaryHeap<Vertice>(g.vertices); //Agregar todos los vértices a q.

        while(!q.isEmpty()){
            exec.agrega(new Estado(g, q));
            Vertice u = q.deleteMin();
            u.enCola = false;
            for(Pair<Vertice, Integer> par : u.ady){
                Vertice v = par.getKey();
                int weight = par.getValue();
                if(v.enCola && weight < v.key){
                    v.p = u;
                    v.key = weight;
                    q.decreaseKey(v);
                }
            }
        }
        exec.agrega(new Estado(g, q));
    }
}

