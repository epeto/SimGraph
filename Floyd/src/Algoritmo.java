
public class Algoritmo{

    //Copia la matriz a en la matriz b.
    public static void copia(int[][] a, int[][] b){
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b.length;j++){
                b[i][j] = a[i][j];
            }
        }
    }

    //Suma sin overflow
    public static int sumaOF(int a, int b){
        if(a == Integer.MAX_VALUE || b == Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else{
            return a+b;
        }
    }

    //Recibe una matriz de pesos, una matriz de distancias y una matriz predecesora.
    //Las dos últimas van a contener los resultados finales.
    public static void floyd_warshall(int[][] pesos, Ejecucion exec){
        int n = pesos.length;
        int[][] d = new int[n][n];
        int[][] p = new int[n][n];
        for(int i = 0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i==j || pesos[i][j]==Integer.MAX_VALUE){
                    p[i][j] = -1;
                }else{
                    p[i][j] = i;
                }
            }
        }
        copia(pesos, d);
        for(int k=0; k<n; k++){
            exec.agrega(new Estado(d, p));
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    if(d[i][j] > sumaOF(d[i][k], d[k][j])){
                        d[i][j] = sumaOF(d[i][k], d[k][j]);
                        p[i][j] = p[k][j];
                    }
                }
            }
        }
        exec.agrega(new Estado(d, p));
    }
}

