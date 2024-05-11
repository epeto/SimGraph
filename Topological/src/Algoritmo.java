
import java.util.LinkedList;

public class Algoritmo{

    /**
     * Calcula el ingrado de cada vértice en la gráfica g.
     * @param g gráfica.
     */
    public static void calcIndegree(Grafica g){
        for(Vertice u : g.vertices){
            for(Vertice v : u.ady){
                v.indegree++;
            }
        }
    }

    /**
     * Algoritmo de ordenamiento topológico.
     * Asigna un número a cada elemento en la gráfica g.
     * La gráfica debe ser acíclica y débilmente conexa
     * para que el algoritmo etiquete a todos los vértices.
     * @param g gráfica.
     */
    public static void topologicalSort(Grafica g, Ejecucion exec){
        calcIndegree(g);
        Cola<Vertice> cola = new Cola<>();
        int label = 0;
        LinkedList<Integer[]> ariVis = new LinkedList<>();

        for(Vertice v : g.vertices){
            if(v.indegree == 0){
                cola.enqueue(v);
            }
        }
        exec.agrega(new Estado(g, cola, ariVis));

        while(!cola.isEmpty()){
            Vertice v = cola.dequeue();
            label++;
            v.tsi = label;
            v.visitado = true;
            exec.agrega(new Estado(g, cola, ariVis));

            for(Vertice w : v.ady){
                w.indegree--;
                Integer[] arista = new Integer[2];
                arista[0] = v.id;
                arista[1] = w.id;
                ariVis.add(arista);
                if(w.indegree == 0){
                    cola.enqueue(w);
                }
            }

            if(!v.ady.isEmpty()){
                exec.agrega(new Estado(g, cola, ariVis));
            }
        }
    }
}

