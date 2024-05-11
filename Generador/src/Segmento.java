

public class Segmento implements Comparable<Segmento>{
    public Punto vi;
    public Punto vf;
    public double distancia;
    public boolean dirigida; //verdadero si y sólo si la arista es dirigida.

    public Segmento(Punto vInicial, Punto vFinal, boolean dir){
        vi = vInicial;
        vf = vFinal;
        dirigida = dir;
        distancia = Math.sqrt((vi.x-vf.x)*(vi.x-vf.x) + (vi.y-vf.y)*(vi.y-vf.y));
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Segmento){
            Segmento a = (Segmento) o;
            if(dirigida){
                return (vi.equals(a.vi) && vf.equals(a.vf));
            }else{
                boolean s1 = (vi.equals(a.vi) && vf.equals(a.vf));
                boolean s2 = (vi.equals(a.vf) && vf.equals(a.vi));
                return (s1 || s2);
            }
        }else{
            return false;
        }
    }

    /**
     * Devuelve el reflejo de esta arista.
     * Sólo tiene sentido cuando la arista es dirigida.
     * @return (vf, vi)
     */
    public Segmento espejo(){
        return new Segmento(vf, vi, dirigida);
    }

    public Arista toAristaNormal(){
        return new Arista(vi.id, vf.id);
    }

    private double direccion(Punto pi, Punto pj, Punto pk){
        Punto r1 = pk.resta(pi);
        Punto r2 = pj.resta(pi);
        return r1.productoCruz(r2);
    }

    /**
     * Determina si otra arista intersecta con esta.
     * @param otra arista
     * @return verdadero si y sólo si otra intersecta con esta.
     */
    public boolean intersecta(Segmento otra){
        if(equals(otra.espejo())){
            return false;
        }
        Punto p1 = vi;
        Punto p2 = vf;
        Punto p3 = otra.vi;
        Punto p4 = otra.vf;
        double d1 = direccion(p3, p4, p1);
        double d2 = direccion(p3, p4, p2);
        double d3 = direccion(p1, p2, p3);
        double d4 = direccion(p1, p2, p4);
        boolean stm;
        if(((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
           ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))){
            stm = true;
        }else{
            stm = false;
        }
        return stm;
    }

    @Override
    public int compareTo(Segmento a) {
        return ((int)(this.distancia - a.distancia));
    }

}
