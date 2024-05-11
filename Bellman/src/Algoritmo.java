// Algoritmo de Bellman-Ford

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

    public static void bellman_ford(Grafica g, Vertice s, Ejecucion exec){
        for(Vertice v : g.vertices){
            v.d = Integer.MAX_VALUE;
            v.p = null;
        }
        s.d = 0;
        int n = g.vertices.length;
        for(int i=1; i<n; i++){
            exec.agrega(new Estado(g));
            for(Vertice u : g.vertices){
                for(Pair<Vertice,Integer> par : u.ady){
                    Vertice v = par.getKey();
                    if(v.d > sumaOF(u.d, par.getValue())){
                        v.d = sumaOF(u.d, par.getValue());
                        v.p = u;
                    }
                }
            }
        }
        exec.agrega(new Estado(g));

        for(Vertice u : g.vertices){
            for(Pair<Vertice,Integer> par : u.ady){
                Vertice v = par.getKey();
                if(v.d > sumaOF(u.d, par.getValue())){
                    System.out.println("La gráfica tiene un ciclo de peso negativo.");
                    return;
                }
            }
        }
    }
}


