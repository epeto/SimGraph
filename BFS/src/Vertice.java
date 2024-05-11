
//Clase vértice

import java.util.LinkedList;

public class Vertice{
    LinkedList<Vertice> ady; //Lista de adyacencias.
    int id; //Identificador.
    boolean visitado; //Indica si un vértice ya fue visitado.
    int d; //Atributo de distancia.
    Vertice p; //Vértice padre o predecesor.

    /**
     * Constructor de la clase vértice.
     * @param ident: identificador del vértice, el cual es un número.
     */
    public Vertice(int ident){
        id = ident;
        ady = new LinkedList<Vertice>();
        d = 0;
    }
  
    //Comprueba si 2 vértices son iguales.
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
    }

    @Override
    public String toString(){
        return "v"+id;
    }
}