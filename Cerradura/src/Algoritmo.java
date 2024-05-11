
public class Algoritmo{

    public static void cerradura(boolean[][] a, Ejecucion exec){
        int n = a.length;
        for(int k=0; k<n; k++){
            exec.agrega(new Estado(a));
            for(int i=0; i<n; i++){
                if(a[i][k]){
                    for(int j=0; j<n; j++){
                        if(a[k][j]){
                            a[i][j] = true;
                        }
                    }
                }
            }
        }
        exec.agrega(new Estado(a));
    }
}

