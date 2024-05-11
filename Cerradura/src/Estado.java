
/**
 * Guarda información sobre el estado de la gráfica.
 */

public class Estado {
    boolean[][] est; //Estado de la matriz

    public Estado(boolean[][] grafica){
        est = new boolean[grafica.length][grafica.length];
        copia(grafica, est);
    }

    //Copia la matriz a en la matriz b.
    public static void copia(boolean[][] a, boolean[][] b){
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b.length;j++){
                b[i][j] = a[i][j];
            }
        }
    }
}


