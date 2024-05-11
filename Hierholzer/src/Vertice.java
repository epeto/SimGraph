
//Clase vértice

import java.util.HashMap;
import java.util.ListIterator;
import java.util.LinkedList;

public class Vertice{
    LinkedList<Vertice> ady; //Lista de adyacencias.
    int id; //Identificador.
    HashMap<Integer,Boolean> aVisitada; //tabla hash de aristas visitadas.
    ListIterator<Vertice> it; //Itera sobre la lista de adyacencias.

    /**
     * Constructor de la clase vértice.
     * @param ident identificador del vértice, el cual es un número.
     */
    public Vertice(int ident){
        id = ident;
        ady = new LinkedList<>();
        aVisitada = new HashMap<>();
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
     * @param vec: nuevo vecino.
     */
    public void agregaVecino(Vertice vec){
        if(!ady.contains(vec)){
            ady.add(vec);
        }
        aVisitada.put(vec.id, false);
    }

    @Override
    public String toString(){
        return "v"+id;
    }
}

