
import java.util.LinkedList;

public class Arista{
    Vertice vi; //Vértice inicial
    Vertice vf; //Vértice final

    public Arista(Vertice vi, Vertice vf){
        this.vi = vi;
        this.vf = vf;
    }

    @Override
    public String toString(){
        return "("+vi.toString()+", "+vf.toString()+")";
    }

    /**
     * Recibe una lista de aristas y devuelve la lista de los ids 
     * de los vértices en estas aristas, sin repetición.
     * @param aristas lista de aristas
     * @return ids de los vértices en esas aristas
     */
    public static LinkedList<Integer> compacta(LinkedList<Arista> aristas){
        LinkedList<Integer> verticesRet = new LinkedList<>();
        
        for(Arista a:aristas){
            if(!verticesRet.contains(a.vi.id)){
                verticesRet.add(a.vi.id);
            }

            if(!verticesRet.contains(a.vf.id)){
                verticesRet.add(a.vf.id);
            }
        }

        return verticesRet;
    }
}
