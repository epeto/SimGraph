
public class Arista {
    public int vi;
    public int vf;
    public int peso;

    public Arista(int v1, int v2){
        vi = v1;
        vf = v2;
        peso = 1;
    }

    public Arista(){
        vi = -1;
        vf = -1;
        peso = 1;
    }

    @Override
    public boolean equals(Object ari){
        if(ari instanceof Arista){
            Arista ari2 = (Arista) ari;
            return ((ari2.vi == this.vi) && (ari2.vf == this.vf));
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return vi+","+vf+","+peso;
    }
}

