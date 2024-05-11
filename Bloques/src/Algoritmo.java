
import java.util.LinkedList;

public class Algoritmo{

    /**
     * Función que calcula los bloques de una gráfica.
     * @param g gráfica.
     * @param r Vértice raíz.
     * @param exec conjunto de estados.
     * @return Lista de bloques.
     */
    public static LinkedList<LinkedList<Arista>> getBloques(Grafica g, Vertice r, Ejecucion exec){
        LinkedList<LinkedList<Arista>> bloques = new LinkedList<LinkedList<Arista>>(); //Lista de bloques
        Pila<Arista> pila = new Pila<Arista>(); //Pila auxiliar para explorar los bloques.
        int[] time = new int[1]; //El tiempo se guarda en una caja, que es un arreglo de una casilla.
        time[0] = 1;

        getBloquesAux(g, r, time, pila, bloques, exec);

        //Si al final aún hay vértices en la pila, hay que vaciarla.
        LinkedList<Arista> bFinal = new LinkedList<Arista>();
        while(!pila.isEmpty()){
            bFinal.add(pila.pop());
        }

        if(bFinal.size() != 0){
            bloques.add(bFinal);
            exec.agrega(new Estado(g, bloques, pila));
        }

        return bloques;
    }

    /**
     * Función que obtiene los bloques para vértices que no son la raíz.
     * @param g gráfica que contiene a v.
     * @param v Vértice que se está visitando.
     * @param time Variable de tiempo, que indica el momento en el que fue visitado un vértice.
     * @param pila Pila que lleva el orden en el que son visitadas las aristas del árbol DFS.
     * @param bloques Lista de bloques de la gráfica.
     * @param exec lista de estados.
     */
    public static void getBloquesAux(Grafica g, Vertice v, int[] time, Pila<Arista> pila, 
                                     LinkedList<LinkedList<Arista>> bloques, Ejecucion exec){
        v.visitado = true;
        v.low = v.dfi = time[0]; //Regla 1
        time[0]++;

        exec.agrega(new Estado(g, bloques, pila));

        for(Vertice w : v.ady){
            if(!w.visitado){ //tree edge
                v.nHijos++;
                w.p = v;
                pila.push(new Arista(v,w));
                getBloquesAux(g, w, time, pila, bloques, exec); //Se visita a w
                if((v.dfi>1 && w.low >= v.dfi) ||
                   (v.dfi == 1 && v.nHijos>1)){ // v es un vértice de corte
                    v.deCorte = true;
                    LinkedList<Arista> b = new LinkedList<Arista>();
                    while(!pila.isEmpty() &&
                          !(v.equals(pila.peek().vi) && w.equals(pila.peek().vf))){
                        b.add(pila.pop());
                    }
                    if(!pila.isEmpty()){
                        b.add(pila.pop());
                    }
                    bloques.add(b);
                }
                v.low = Math.min(v.low, w.low); //Regla 3
            }else{
                if(v.p != w){ //Back edge
                    v.low = Math.min(v.low, w.dfi); //Regla 2
                }
            }
        }

	    if(v.ady.size()>1){
            exec.agrega(new Estado(g, bloques, pila));
	    }
    }
}



