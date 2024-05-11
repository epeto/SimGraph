
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Principal{

    /**
     * Devuelve una lista de todas las aristas en una gráfica completa.
     * @param orden número de vértices en la gráfica.
     * @param dirigida decide si es dirigida o no.
     * @return lista de aristas en la gráfica completa.
     */
    public static LinkedList<Arista> listaCompleta(int orden, boolean dirigida){
        LinkedList<Arista> listaRet = new LinkedList<>();
        if(dirigida){
            for(int i=0; i<orden; i++){
                for(int j=0; j<orden; j++){
                    if(i != j){
                        listaRet.add(new Arista(i, j));
                    }
                }
            }
        }else{
            for(int i=0; i<orden; i++){
                for(int j=i+1; j<orden; j++){
                    listaRet.add(new Arista(i, j));
                }
            }
        }
        return listaRet;
    }

    /**
     * Devuelve una lista de todas las aristas en una gráfica completa.
     * Las aristas tienen vértices con coordenadas.
     * @param orden número de vértices en la gráfica.
     * @param dirigida decide si es dirigida o no.
     * @return lista de aristas en la gráfica completa.
     */
    public static LinkedList<Segmento> listaSegmentos(int orden, boolean dirigida){
        LinkedList<Segmento> listaRet = new LinkedList<>();
        ArrayList<Punto> listaVert = new ArrayList<>();
        Random rn = new Random();
        for(int i=0; i<orden; i++){
            double cx = rn.nextDouble()*500;
            double cy = rn.nextDouble()*500;
            listaVert.add(new Punto(i, cx, cy));
        }
        if(dirigida){
            for(int i=0; i<orden; i++){
                for(int j=0; j<orden; j++){
                    if(i != j){
                        Punto vi = listaVert.get(i);
                        Punto vj = listaVert.get(j);
                        listaRet.add(new Segmento(vi, vj, dirigida));
                    }
                }
            }
        }else{
            for(int i=0; i<orden; i++){
                for(int j=i+1; j<orden; j++){
                    Punto vi = listaVert.get(i);
                    Punto vj = listaVert.get(j);
                    listaRet.add(new Segmento(vi, vj, dirigida));
                }
            }
        }
        return listaRet;
    }

    /**
     * Aplica algoritmo de Kruskal para formar el árbol generador de peso mínimo.
     * @param grafica lista de aristas que conforman la gráfica
     * @return mst
     */
    public static LinkedList<Segmento> kruskal(int orden, LinkedList<Segmento> aristas){
        LinkedList<Segmento> arbol = new LinkedList<>(); //Conjunto resultante.
        DisjSets ds = new DisjSets(orden);
        PriorityQueue<Segmento> q = new PriorityQueue<>(aristas);

        while((arbol.size() < orden-1) && !q.isEmpty()){
            Segmento edge = q.poll();
            int root1, root2;
            root1 = ds.findSetPC(edge.vi.id);
            root2 = ds.findSetPC(edge.vf.id);
            
            if(root1 != root2){ //Si están en conjuntos diferentes.
                arbol.add(edge); //Se agrega la arista al conjunto a.
                ds.smartUnion(root1, root2);
            }
        }
        return arbol;
    }

    /**
     * Genera un árbol aleatorio y lo devuelve como una lista de aristas.
     * @param orden número de vértices en la gráfica.
     * @param dirigida decide si es dirigida o no.
     * @param total lista de aristas en la gráfica completa.
     * @return lista de aristas que representan al árbol.
     */
    public static LinkedList<Arista> generaArbol(int orden, boolean dirigida, List<Arista> total){
        int nComps = orden; //Número de componentes.
        LinkedList<Integer> componentes[] = new LinkedList[nComps];
        //Inicializar todas las componentes con 1 elemento cada una.
        for(int i=0; i<nComps; i++){
            LinkedList<Integer> lista = new LinkedList<>();
            lista.add(i);
            componentes[i] = lista;
        }
        LinkedList<Arista> arbol = new LinkedList<>();
        Random rn = new Random();
        while(nComps > 1){
            LinkedList<Integer> disponibles = new LinkedList<>(); //Guarda los índices de las componentes disponibles.
            //Se buscan las componentes disponibles.
            for(int i=0; i<componentes.length; i++){
                if(componentes[i] != null){
                    disponibles.add(i);
                }
            }
            int indC1 = disponibles.remove(rn.nextInt(disponibles.size())); //índice de la primer componente.
            int indC2 = disponibles.remove(rn.nextInt(disponibles.size())); //índice de la segunda componente.
            LinkedList<Integer> comp1 = componentes[indC1]; //Primera componente
            LinkedList<Integer> comp2 = componentes[indC2]; //Segunda componente
            int v1 = comp1.get(rn.nextInt(comp1.size())); //Primer vértice
            int v2 = comp2.get(rn.nextInt(comp2.size())); //Segundo vértice
            componentes[indC1].addAll(comp2);
            componentes[indC2] = null;
            nComps--;
            Arista nueva = new Arista(v1, v2);
            arbol.add(nueva);
            total.remove(nueva);
            if(!dirigida){
                total.remove(new Arista(v2, v1));
            }
        }
        return arbol;
    }

    private static LinkedList<Arista> generaGraficaNormal(int orden, 
                                                          int tamano,
                                                          boolean dirigida, 
                                                          boolean conexa){
        //Se obtienen todas las aristas de la gráfica completa.
        LinkedList<Arista> total = listaCompleta(orden, dirigida);
        LinkedList<Arista> grafica;
        if(conexa){
            grafica = generaArbol(orden, dirigida, total);
        }else{
            grafica = new LinkedList<>();
        }
        Random rn = new Random();
        while(!total.isEmpty() && grafica.size()<tamano){
            Arista agregada = total.remove(rn.nextInt(total.size()));
            grafica.add(agregada);
        }

        return grafica;
    }

    private static LinkedList<Arista> generaGraficaPlana(int orden, 
                                                         int tamano,
                                                         boolean dirigida, 
                                                         boolean conexa){
        LinkedList<Segmento> total = listaSegmentos(orden, dirigida);
        LinkedList<Segmento> retEuclid;
        if(conexa){
            retEuclid = kruskal(orden, total);
            total.removeAll(retEuclid);
        }else{
            retEuclid = new LinkedList<>();
        }

        Random rn = new Random();
        while(retEuclid.size() < tamano && !total.isEmpty()){
            int indRand = rn.nextInt(total.size());
            Segmento removido = total.remove(indRand);
            boolean inter = false;
            for(Segmento seg : retEuclid){
                if(removido.intersecta(seg)){
                    inter = true;
                }
            }
            if(!inter){
                retEuclid.add(removido);
            }
        }

        LinkedList<Arista> retNormal = new LinkedList<>();
        for(Segmento ae : retEuclid){
            retNormal.add(ae.toAristaNormal());
        }

        return retNormal;
    }

    /**
     * Genera una gráfica aleatoria
     * @param orden número de vértices en la gráfica
     * @param tamano número de aristas en la gráfica
     * @param dirigida decide si la gráfica es dirigida o no
     * @param conexa para garantizar conexión
     * @param plana para garantizar que sea plana
     * @return lista de aristas que representan a la gráfica
     */
    public static LinkedList<Arista> generaGrafica(int orden, 
                                                   int tamano,
                                                   boolean dirigida, 
                                                   boolean conexa,
                                                   boolean plana){
        if(plana){
            return generaGraficaPlana(orden, tamano, dirigida, conexa);
        }else{
            return generaGraficaNormal(orden, tamano, dirigida, conexa);
        }
    }

    /**
     * Se le asignan pesos a las aristas de una gráfica de forma aleatoria.
     * @param grafica lista de aristas que representan la gráfica.
     * @param ci cota inferior del peso
     * @param cs cota superior del peso
     */
    public static void asignaPesos(List<Arista> grafica, int ci, int cs){
        Random rn = new Random();
        if(cs < 0)
            cs = 0;

        if(ci < 0)
            ci = 0;

        if(cs < ci)
            cs = ci;

        for(Arista ari : grafica){
            int nuevoPeso = rn.nextInt(cs - ci+1) + ci;
            ari.peso = nuevoPeso;
        }
    }

    /**
     * Dadas las características de una gráfica, se crea un archivo en la carpeta output.
     */
    public static void escribeGrafica(String archivo, boolean dirigida, int orden, 
                                      LinkedList<Arista> grafica){
        try{
            FileWriter fw1 = new FileWriter("output/"+archivo);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            
            if(dirigida){
                bw1.write("d");
            }else{
                bw1.write("n");
            }
            bw1.newLine();
            bw1.write(String.valueOf(orden));
            bw1.newLine();
            for(Arista ari : grafica){
                bw1.write(ari.toString());
                bw1.newLine();
            }

            bw1.close();
            System.out.println("Archivo creado con éxito en la carpeta 'output'");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int orden = 0;
        int tam = 0;
        System.out.println("Indique el número de vértices que contendrá la gráfica");
        String strOrden = sc.nextLine();
        orden = Integer.parseInt(strOrden);
        System.out.println("Indique el número de aristas");
        String strTam = sc.nextLine();
        tam = Integer.parseInt(strTam);
        System.out.println("Indique si la gráfica será dirigida o no dirigida\nd -> dirigida\nn -> no dirigida");
        String direccion = sc.nextLine();
        boolean dirigida = direccion.equals("d");
        System.out.println("¿Garantizar conexión?\n1 -> sí\n2 -> no");
        String gc = sc.nextLine();
        boolean conexa = gc.equals("1");
        System.out.println("¿Garantizar planaridad?\n1 -> sí\n2 -> no");
        String gp = sc.nextLine();
        boolean plana = gp.equals("1");
        System.out.println("Escriba el nombre del archivo de salida:");
        String archivo = sc.nextLine();
        System.out.println("¿La gráfica tendrá pesos?\n1 -> sí\n2 -> no");
        String conPeso = sc.nextLine();
        
        //En esta parte se crea la gráfica.
        LinkedList<Arista> grafica = generaGrafica(orden, tam, dirigida, conexa, plana);

        if(conPeso.equals("1")){
            System.out.println("Indique la cota inferior de los pesos:");
            String strCI = sc.nextLine();
            int ci = Integer.parseInt(strCI);
            System.out.println("Indique la cota superior de los pesos:");
            String strCS = sc.nextLine();
            int cs = Integer.parseInt(strCS);
            asignaPesos(grafica, ci, cs);
        }
        escribeGrafica(archivo, dirigida, orden, grafica);
        
        sc.close();
    }
}



