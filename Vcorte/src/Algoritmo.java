
public class Algoritmo {

    /**
     * Función que calcula los puntos bajos cada vértice de forma recursiva
     * y encuentra los vértices de corte que no son la raíz.
     * @param g gráfica que contiene al vértice v.
     * @param v Vértice al cual se le calcula el lowpoint.
     * @param t marcador del tiempo.
     * @param exec guarda la ejecución del algoritmo.
     */
    public static void lowpoint(Grafica g, Vertice v, int[] t, Ejecucion exec){
        v.visitado = true;
        t[0]++;
        v.dfi = t[0];
        v.low = v.dfi; //Regla 1
        exec.agrega(new Estado(g, false));

        for(Vertice w : v.ady){
            if(!w.visitado){ //Tree edge (v,w)
                w.p = v;
                lowpoint(g, w, t, exec);
                if(w.low >= v.dfi && v.p != null){ //v es un vértice de corte (si no es la raíz).
                    v.deCorte = true;
                }
                v.low = Math.min(v.low, w.low); //Regla 3
            }else{
                if(v.p != w){ //Back edge (v,w)
                    v.low = Math.min(v.low, w.dfi); //Regla 2
                }
            }
        }
        if(v.ady.size() > 1){
            exec.agrega(new Estado(g, false));
        }
    }

    /**
     * Encuentra los vértices de corte de una gráfica conexa.
     * @param s id del vértice fuente.
     * @param g gráfica en la cual se encuentran los vértices de corte.
     * @param exec secuencia de estados de la ejecución del algoritmo.
     */
    public static void vertCorte(int s, Grafica g, Ejecucion exec){
        for(Vertice v : g.vertices){
            v.deCorte = false;
            v.visitado = false;
        }
        int[] t = new int[1]; //marcador de tiempo
        t[0] = 0;
        Vertice source = g.getVertice(s);
        lowpoint(g, source, t, exec);
        //Si el vértice origen tiene más de un hijo significa que es de corte.
        int hijosSource = 0;
        for(Vertice v : g.vertices){
            if(v.p != null){
                if(v.p.equals(source)){
                    hijosSource++;
                }
            }
        }
        if(hijosSource > 1){
            source.deCorte = true;
            exec.agrega(new Estado(g, false));
        }
        exec.agrega(new Estado(g, true));
    }
}
