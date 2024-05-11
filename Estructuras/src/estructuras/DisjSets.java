
/**
 * Estructura de datos de conjuntos ajenos.
 */

package estructuras;

public class DisjSets {

    public int[] s;

    /**
     * Constructor.
     * @param numElements el número inicial de elementos en la estructura.
     */
    public DisjSets(int numElements){
        s = new int[numElements];
        for(int i = 0; i < s.length; i++)
            s[i] = -1;
    }

    /**
     * Reinicia el conjunto.
     * @param numElements el número inicial de elementos en la estructura.
     */
    public void makeSet(int numElements){
        s = new int[numElements];
        for(int i = 0; i < s.length; i++)
            s[i] = -1;
    }

    /**
     * Encuentra al representante del conjunto que contiene a x.
     * @param x elemento a buscar.
     * @return conjunto que contiene a x.
     */
    public int findSet(int x){
        if(s[x] < 0)
            return x;
        else
            return findSet(s[x]);
    }

    /**
     * Realiza la unión de dos conjuntos.
     * @param x elemento en el primer conjunto.
     * @param y elemento en el segundo conjunto.
     */
    public void union(int x, int y){
        int root1 = findSet(x);
        int root2 = findSet(y);
        if(root1 != root2){
            //root1 se vuelve padre de root2.
            s[root1] += s[root2];
            s[root2] = root1;
        }
    }

    /**
     * Une dos conjuntos usando la heurística de tamaño.
     * Por simplicidad, se supone que 'x' y 'y' están en conjuntos diferentes.
     * @param x elemento en conjunto 1.
     * @param y elemento en conjunto 2.
     */
    public void smartUnion(int x, int y){
        int root1 = findSet(x);
        int root2 = findSet(y);

        if(root1 != root2){
            //La raíz que tenga el valor absoluto más grande será la nueva raíz.
            if(s[root1] < s[root2]){ //root1 es más grande (porque se guarda el negativo del tamaño).
                s[root1] += s[root2]; //root1 incrementa su tamaño en s[root2]
                s[root2] = root1; //root1 se vuelve raíz de root2.
            }else{
                s[root2] += s[root1];
                s[root1] = root2;
            }
        }
    }

    /**
     * Encuentra al representante del conjunto que contiene a x 
     * usando compresión de trayectoria.
     * @param x el elemento buscado.
     * @return el conjunto que contiene a x.
     */
    public int findSetPC(int x){
        if(s[x] < 0)
            return x;
        else
            return s[x] = findSetPC(s[x]);
    }

}


