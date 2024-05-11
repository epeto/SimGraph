
import java.util.LinkedList;

public class Algoritmo{

    /**
     * Decide si una gráfica es par; es decir, que todos los vértices tengan grado par.
     * @param grafica
     * @return verdadero si y solo si es par
     */
    public static boolean esGraficaPar(Grafica grafica){
        for(Vertice v : grafica.vertices){
            if((v.ady.size()%2) == 1){//Si alguno tiene un número impar de vecinos se devuelve falso.
                return false;
            }
        }
        return true;
    }

    public static LinkedList<Vertice> euler(Grafica grafica, int idRaiz, Ejecucion exec){
        LinkedList<Vertice> cTotal = new LinkedList<Vertice>(); //Circuito total
        LinkedList<Vertice> cAct = new LinkedList<Vertice>(); //Circuito actual.

        for(Vertice v:grafica.vertices){
            v.it = v.ady.listIterator();
        }

        Vertice vRaiz = grafica.getVertice(idRaiz);
        cAct.add(vRaiz);
        
        exec.agrega(new Estado(grafica, cAct, cTotal));

        while(!cAct.isEmpty()){
            Vertice v = cAct.getLast(); //Obtengo el último elemento agregado a la lista (como en una pila).
            if(v.it.hasNext()){ //Si tiene elementos por visitar.
                Vertice u = v.it.next(); //u es el siguiente vecino de v.
                if(!v.aVisitada.get(u.id)){ //Si la arista (v,u) no ha sido visitada
                    v.aVisitada.replace(u.id, true); //Marcar u como visitada en la lista de v.
                    u.aVisitada.replace(v.id, true); //Marcar v como visitada en la lista de u.
                    cAct.addLast(u); //Agregar arista u al final de la lista cAct

                    exec.agrega(new Estado(grafica, cAct, cTotal));
                }
            }else{//Si v ya no tiene elementos por visitar
                int indice = cTotal.indexOf(v); //Obtengo el índice de v en el circuito total.
                if(indice < 0){
                    indice = 0;
                }else{
                    cTotal.remove(indice);
                }
                cTotal.addAll(indice,cAct); //Agregar cAct al circuito total.
                
                vRaiz = null; //Se "limpia" el vértice raíz.
                for(Vertice w:cAct){//Se busca la siguiente raíz que tenga aristas sin visitar.
                    while(w.it.hasNext() && vRaiz == null){
                        Vertice x = w.it.next();
                        if(!w.aVisitada.get(x.id)){ //Si la arista (w,x) no ha sido visitada encontré el vértice correcto (w)
                            w.it.previous();//Regreso una posición el iterador
                            vRaiz = w;
                        }
                    }
                    if(vRaiz != null){ //Si ya encontré la raíz rompo el ciclo for.
                        break;
                    }
                }
                cAct.clear(); //Hay que vaciar el circuito actual
                if(vRaiz != null){
                    cAct.add(vRaiz); //Agregar la nueva raíz al circuito actual siempre que no sea null
                }
                exec.agrega(new Estado(grafica, cAct, cTotal));
            }
        }

        exec.agrega(new Estado(grafica, cAct, cTotal));

        return cTotal; //Devuelvo el circuito total
    }
}
