

public class Grafica {

    Vertice[] vertices; //Arreglo de vértices.
    boolean dirigida; //decide si la gráfica es dirigida.
  
    /**
     * Constructor de la clase gráfica.
     * @param dir decide si la gráfica es dirigida o no.
     * @param orden número de vértices de la gráfica.
     **/
    public Grafica(boolean dir, int orden){
        dirigida = dir;
        vertices = new Vertice[orden];
        for(int i=0; i<orden; i++){
            Vertice nuevo = new Vertice(i);
            vertices[i] = nuevo;
        }
    }

    /**
    * Función que recibe un id y devuelve el vértice que tiene dicho id. Si no existe devuelve null.
    * @param ident: identificador del vértice.
    * @return vertice con ese id.
    */
    public Vertice getVertice(int ident){
        Vertice ret = null;
        if(ident >= 0 && ident < vertices.length){
            ret = vertices[ident];
        }
        return ret;
    }
 
    /**
    * Función que agrega una arista cuando recibe los objetos tipo Vertice.
    * @param vi: vértice de origen de la arista.
    * @param vj: id del vértice de destino de la arista.
    */
    public void agregaArista(Vertice vi, Vertice vj){
        vi.agregaVecino(vj);    
        if(!dirigida){
            vj.agregaVecino(vi);
        }
    }

    /**
    * Función que agrega una arista, recibiendo los id's de los vértices.
    * @param i id del vértice de origen de la arista.
    * @param j id del vértice de destino de la arista.
    */
    public void agregaArista(int i, int j){
        Vertice vert_i = getVertice(i);
        Vertice vert_j = getVertice(j);
        vert_i.agregaVecino(vert_j);
    
        if(!dirigida){
            vert_j.agregaVecino(vert_i);
        }
    }

    /**
    * Obtiene el orden(número de vértices) de la gráfica.
    */
    public int getOrden(){
        return vertices.length;
    }

    /**
     * Obtiene el tamaño (número de aristas) de la gráfica.
     */
    public int getTamano(){
        int tam = 0;
        for(Vertice v : vertices){
            tam += v.ady.size();
        }
        return tam/2;
    }

    @Override
    public String toString(){
        String salida = "";
        for(Vertice v:vertices){
            salida += v.toString()+"->"+v.ady.toString()+"\n";
        }
        return salida;
    }
}
