
public class Algoritmo {

    /**
     * Aplica el algoritmo DFS sobre todos los vértices alcanzables desde u.
     * @param u Vértice fuente del algoritmo.
     * @param time Tiempo inicial.
     * @return Tiempo final.
     */
    public static int dfs_visit(Vertice u, Grafica g, int time, Ejecucion exec){
        Pila<Vertice> pila = new Pila<Vertice>();
        time++;
        u.visitado = true;
        u.dfi = time;
        pila.push(u);

        exec.agrega(new Estado(g, pila));

        while(!pila.isEmpty()){
            Vertice v = pila.peek(); //El elemento al tope de la pila.
            if(v.it.hasNext()){ //Mientras tenga vecinos.
                Vertice w = v.it.next(); //w es el siguiente vecino de v
                if(!w.visitado){  //Si w no ha sido visitado
                    w.visitado = true; //Se marca como visitado.
                    w.p = v; //El predecesor de w es v.
                    time++; //Se incrementa el contador de tiempo.
                    w.dfi = time; //Se le coloca el índice dfi.
                    pila.push(w); //Se agrega w a la pila.

                    exec.agrega(new Estado(g, pila)); //Se guarda el estado.
                }
            }else{ //Si ya no tiene vecinos por visitar
                pila.pop(); //Se extrae el elemento al tope de la pila

                exec.agrega(new Estado(g, pila)); //Se guarda el estado.
            }
        }

        return time;
    }

    /**
     * Algoritmo para aplicar DFS en todos los vértices.
     * @param g Gráfica sobre la cual se aplica DFS.
     */
    public static void dfs(Grafica g, Ejecucion exec){
        int time = 0; //Lleva el etiquetado de dfs.

        //Configuración inicial de cada vértice.
        for(Vertice u : g.vertices){
            u.it = u.ady.listIterator();
            u.visitado = false;
        }

        for(Vertice u : g.vertices){
            if(!u.visitado){
                time = dfs_visit(u, g, time, exec);
            }
        }
    }
}
