

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
}

