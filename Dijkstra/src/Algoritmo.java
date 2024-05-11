// Algoritmo de Dijkstra.

import javafx.util.Pair;

public class Algoritmo {
    
    //Suma sin overflow
    public static int sumaOF(int a, int b){
        if(a == Integer.MAX_VALUE || b == Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else{
            return a+b;
        }
    }
    
    public static void dijkstra(Grafica g, Vertice s, Ejecucion exec){
        BinomialQueue<Vertice> q = new BinomialQueue<Vertice>();

        for(Vertice v : g.vertices){
            v.d = Integer.MAX_VALUE;
            v.p = null;
        }
        s.d = 0;
        for(Vertice v : g.vertices)
            q.insert(v);
        
        while(!q.isEmpty()){
            exec.agrega(new Estado(g, q));
            Vertice u = q.deleteMin();
            for(Pair<Vertice, Integer> par : u.ady){
                Vertice v = par.getKey();
                if(v.d > sumaOF(u.d, par.getValue())){
                    v.d = sumaOF(u.d, par.getValue());
                    v.p = u;
                    q.decreaseKey(v);
                }
            }
        }
        exec.agrega(new Estado(g, q));
    }
}

