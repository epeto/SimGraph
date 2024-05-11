
public class Punto {
    int id;
    double x;
    double y;

    public Punto(int id, double x, double y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Punto){
            Punto v = (Punto) o;
            return (v.id == this.id);
        }
        return false;
    }

    public double productoCruz(Punto p){
        return (x*p.y - p.x*y);
    }

    public Punto suma(Punto p){
        return new Punto(-1, x+p.x, y+p.y);
    }

    public Punto resta(Punto p){
        return new Punto(-1, x-p.x, y-p.y);
    }

    @Override
    public String toString(){
        return "("+id+", "+x+", "+y+")";
    }
}
