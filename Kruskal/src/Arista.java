

public class Arista implements Comparable<Arista>{
    Vertice vi; //Vértice inicial
    Vertice vf; //Vértice final
    int peso; //Peso de la arista

    public Arista(Vertice vi, Vertice vf){
        this.vi = vi;
        this.vf = vf;
        peso = 1;
    }

    public Arista(Vertice vi, Vertice vf, int peso){
        this.vi = vi;
        this.vf = vf;
        this.peso = peso;
    }

    @Override
    public String toString(){
        return "("+vi.toString()+", "+vf.toString()+", "+peso+")";
    }

    @Override
    public int compareTo(Arista a) {
        return (this.peso - a.peso);
    }
}

