
/**
 * Guarda información sobre el estado de la gráfica y la
 * estructura de datos en un punto de la ejecución del algoritmo.
 */

import java.util.LinkedList;

public class Estado {
    int[] nextAri; //Siguiente arista a agregar.
    DSArbol arbol; //árbol de conjuntos ajenos.
    int[] disjsets; //arreglo de conjuntos ajenos.
    LinkedList<Integer[]> ariBosque; //Aristas del bosque.

    /**
     * @param conjunto arreglo de los conjuntos ajenos.
     * @param aristas lista de aristas en el bosque.
     * @param sig siguiente arista a ser agregada.
     */
    public Estado(int[] conjuntos, LinkedList<Arista> aristas, Arista sig){
        //Se copia la siguiente arista.
        nextAri = new int[3];
        if(sig != null){
            nextAri[0] = sig.vi.id;
            nextAri[1] = sig.vf.id;
            nextAri[2] = sig.peso;
        }else{
            nextAri[0] = -1;
            nextAri[1] = -1;
            nextAri[2] = -1;
        }
        //Se copian todos los números del conjunto ajeno.
        disjsets = new int[conjuntos.length];
        for(int i=0; i<conjuntos.length; i++){
            disjsets[i] = conjuntos[i];
        }
        //Se crea un árbol de ese conjunto.
        arbol = new DSArbol(disjsets);
        arbol.anchoBosque();
        arbol.alturaBosque();
        //Se copian las aristas del bosque.
        ariBosque = new LinkedList<>();
        for(Arista ari : aristas){
            Integer[] nueva = new Integer[2];
            nueva[0] = ari.vi.id;
            nueva[1] = ari.vf.id;
            ariBosque.add(nueva);
        }
    }
}

