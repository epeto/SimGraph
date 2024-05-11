
//Clase vértice

import javafx.util.Pair;
import java.util.LinkedList;

public class Vertice implements Comparable<Vertice>{
    LinkedList<Pair<Vertice,Integer>> ady; //Lista de adyacencias (vertice, peso).
    int id; //Identificador.
    int d; //distancia.
    Vertice p; //Padre (o predecesor) de este vértice.

    /**
     * Constructor de la clase vértice.
     * @param ident identificador del vértice, el cual es un número.
     */
    public Vertice(int ident){
        id = ident;
        ady = new LinkedList<>();
        d = 0;
    }
  
    /**
     * Comprueba si 2 vértices son iguales.
     */
    @Override
    public boolean equals(Object o){
        boolean ret = false;
        if(o instanceof Vertice){
            Vertice comp = (Vertice)o;
            if(comp.id == this.id){
                ret = true;
            }
        }
        return ret;
    }
  
    /**
     * Agrega un vecino a este vértice
     * @param vec nuevo vecino.
     * @param peso peso de la arista de este vértice a vec.
     */
    public void agregaVecino(Vertice vec, int peso){
        boolean existe = false;
        for(Pair<Vertice, Integer> par : ady){
            if(par.getKey().equals(vec)){
                existe = true;
            }
        }
        if(!existe){
            Pair<Vertice, Integer> nuevoPar = new Pair<>(vec, peso);
            ady.add(nuevoPar);
        }
    }

    @Override
    public String toString(){
        String strkey = "";
        if(d == Integer.MAX_VALUE){
            strkey = "Ꝏ";
        }else{
            strkey = String.valueOf(d);
        }
        return "v"+id+","+strkey;
    }

    @Override
    public int compareTo(Vertice v) {
        return (this.d - v.d);
    }

    @Override
    public int hashCode(){
        return id;
    }
}

