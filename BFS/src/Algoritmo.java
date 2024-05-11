
/**
 * Contiene al algoritmo de búsqueda en amplitud.
 */
public class Algoritmo {
    /**
     * Algoritmo BFS que calcula la distancia entre el vértice s y el resto de vértices
     * alcanzables por s.
     * @param g Gráfica a la cual se le aplica BFS.
     * @param s Vértice de origen.
     * @param exe Guarda los estados de la gráfica durante la ejecución.
     */
    public static void bfs(Grafica g, Vertice s, Ejecucion exe){
        exe.setInit(s.id);
        
        for(Vertice v : g.vertices){
            v.visitado = false;
            v.d = Integer.MAX_VALUE;
        }
        Cola<Vertice> q = new Cola<Vertice>();
        s.d = 0;
        s.visitado = true;
        q.enqueue(s);

        while(!q.isEmpty()){
            exe.agrega(new Estado(g, q));
            Vertice u = q.dequeue();
            for(Vertice v:u.ady){
                if(!v.visitado){
                    v.visitado = true;
                    v.d = u.d+1;
                    v.p = u;
                    q.enqueue(v);
                }
            }
        }
        exe.agrega(new Estado(g, q));
    }

    /**
     * Determina si una gráfica es bipartita, después de haberle aplicado BFS.
     * @param g gráfica a la que se le aplicó BFS.
     * @return true si la gráfica es bipartita, false en otro caso.
     */
    public static boolean esBipartita(Grafica g){
        // Se tiene que comprobar, para cada vértice, que todos sus vecinos tengan la misma paridad.
        // La paridad está determinada por el atributo d.
        // Si algún vértice tiene al menos un vecino con la misma paridad, entonces no es bipartita.
        for(Vertice v : g.vertices){
            for(Vertice u : v.ady){
                int paridadV = v.d % 2;
                int paridadU = u.d % 2;
                if(paridadV == paridadU){
                    return false;
                }
            }
        }
        return true;
    }
}
