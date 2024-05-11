
/**
 * Guarda información sobre el estado de la gráfica.
 */

public class Estado {
    int[][] estDist; //Estado de la matriz de distancias.
    int[][] estPred; //Estado de la matriz predecesora.

    public Estado(int[][] dist, int[][] pred){
        estDist = new int[dist.length][dist.length];
        estPred = new int[pred.length][pred.length];
        copia(dist, estDist);
        copia(pred, estPred);
    }

    //Copia la matriz a en la matriz b.
    public static void copia(int[][] a, int[][] b){
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b.length;j++){
                b[i][j] = a[i][j];
            }
        }
    }
}


