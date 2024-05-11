
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.LinkedList;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.scene.text.Font;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Principal extends Application{
    
    double anchoVentana;
    double altoVentana;
    Button btnIniciar;
    Button btnOF; //Botón de abrir archivo
    Button btnAnt; //Botón de anterior
    Button btnSig; //Botón de siguiente
    Button btnFuente; //Botón para cambar fuente
    Group grupo; //Grupo que contiene a todos los elementos.
    int itAct; //Iteración actual.
    boolean[][] grafica; //Representación por matriz de adyacencias de una gráfica sin pesos.
    boolean dirigida; //Decide si la gráfica será dirigida.
    Graph graphStream; //Gráfica de GraphStream
    double fuente; //tamaño de letra
    Stage escenario;
    String styleSheet; //Hoja de estilo para la gráfica.
    Ejecucion exec;
    Label[] filaN; //Fila con etiquetas de 0 a n-1
    Label[] columnaN; //Columna con etiquetas de 0 a n-1.
    Label[][] matriz; // Etiquetas para los valores de la matriz.
    TilePane cuadricula; //Cuadrícula donde se colocan las etiquetas.

    @Override
    public void start(Stage primaryStage) {
        fuente = 12;
        grupo = new Group();
        escenario = primaryStage;

        btnOF = new Button();
        btnOF.setText("Abrir archivo");
        btnOF.setLayoutX(10);
        btnOF.setLayoutY(10);
        btnOF.setFont(Font.font(fuente));

        btnIniciar = new Button();
        btnIniciar.setText("Iniciar");
        btnIniciar.setLayoutX(10);
        btnIniciar.setLayoutY(10);
        btnIniciar.setFont(Font.font(fuente));
        
        btnAnt = new Button();
        btnAnt.setText("Anterior");
        btnAnt.setLayoutX(10);
        btnAnt.setLayoutY(10);
        btnAnt.setFont(Font.font(fuente));

        btnSig = new Button();
        btnSig.setText("Siguiente");
        btnSig.setLayoutX(fuente*8 + 10);
        btnSig.setLayoutY(10);
        btnSig.setFont(Font.font(fuente));

        btnFuente = new Button();
        btnFuente.setText("Fuente");
        btnFuente.setLayoutX(fuente*17 + 10);
        btnFuente.setLayoutY(10);
        btnFuente.setFont(Font.font(fuente));

        cuadricula = new TilePane();
        cuadricula.getChildren().add(new Label(""));
        cuadricula.setLayoutX(10);
        cuadricula.setLayoutY(fuente*2 + 10);

        grupo.getChildren().add(btnOF);

        btnOF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                abreArchivo();
            }
        });

        btnIniciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inicia();
            }
        });

        btnAnt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(itAct > 0){
                    itAct--;
                    actualizaMatriz();
                    if(itAct == 0){
                        btnAnt.setDisable(true); //Se deshabilita.
                    }
                    if(itAct == exec.estados.size()-2){
                        btnSig.setDisable(false); //Habilita el botón de siguiente.
                    }
                }
            }
        });

        btnSig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(itAct < exec.estados.size()-1){
                    itAct++;
                    actualizaMatriz();
                    if(itAct == exec.estados.size()-1){
                        btnSig.setDisable(true); //Se deshabilita.
                    }
                    if(itAct == 1){
                        btnAnt.setDisable(false); //Habilita el botón de siguiente.
                    }
                }
            }
        });

        btnFuente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cambiaFuente();
            }
        });

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        anchoVentana = screenBounds.getWidth()/2;
        altoVentana = screenBounds.getHeight();
        Scene scene = new Scene(grupo, anchoVentana, altoVentana);
        escenario.setTitle("Cerradura transitiva");
        escenario.setScene(scene);
        escenario.setY(0);
        escenario.setX(anchoVentana);
        escenario.show();
    }

    /**
     * Lee un archivo y crea una gráfica.
     * @param archivo
     */
    public void lee(File archivo){
        LinkedList<String> lineas = new LinkedList<>();
        try{
            FileReader fr1 = new FileReader(archivo);
            BufferedReader br1 = new BufferedReader(fr1);
            String linea = br1.readLine(); //Lee la primera línea.
            while(linea != null){ //Mientras no llegue al final, lee líneas.
                lineas.add(linea);
                linea = br1.readLine();
            }
            br1.close();
            dirigida = lineas.get(0).equals("d"); //Decide si la gráfica es dirigida.
            int orden = Integer.parseInt(lineas.get(1)); //El orden de la gráfica
            grafica = new boolean[orden][orden];
            //Se colocan los valores iniciales en la gráfica.
            for(int i=0;i<grafica.length;i++){
                for(int j=0;j<grafica.length;j++){
                    if(i == j){
                        grafica[i][j] = true;
                    }else{
                        grafica[i][j] = false;
                    }
                }
            }
            //Se agregan las aristas.
            for(int i=2; i<lineas.size(); i++){
                String[] ariStr = lineas.get(i).split(",");
                int v0 = Integer.parseInt(ariStr[0]);
                int v1 = Integer.parseInt(ariStr[1]);
                grafica[v0][v1] = true;
                if(!dirigida){
                    grafica[v1][v0] = true;
                }
            }
        }catch(Exception e){
            System.err.println("Archivo no encontrado o mal formado");
            e.printStackTrace();
        }
    }

    /**
     * Fija la hoja de estilo.
     */
    public void setStyle(){
        styleSheet = "edge {size: 3px; text-size: "+fuente+"; arrow-size: 15px, 7px;}"
        +"edge.arbol {fill-color: red;}"
        +"edge.negro {fill-color: black;}"
        +"node {size: 15px; text-size: "+fuente+"; text-background-mode: plain;}"
        +"node.negro {fill-color: black; text-background-color: black; text-color: white;}";
    }

    /**
     * Dibuja la gráfica de GraphStream.
     */
    public void dibujaGrafica(){
        setStyle();
        graphStream = new SingleGraph("Grafica 1");
        // Se agregan todos los vértices a "graphStream".
        for(int i=0; i<grafica.length; i++){
            graphStream.addNode(String.valueOf(i));
            Node nodoV = graphStream.getNode(String.valueOf(i));
            nodoV.setAttribute("ui.label", "v"+i);
            nodoV.setAttribute("ui.class", "negro");
        }
        // Luego se agregan las aristas.
        for(int i=0; i<grafica.length; i++){
            for(int j=0; j<grafica.length; j++){
                if(i!=j && grafica[i][j]){
                    String nombreArista1 = i + "," + j;
                    String nombreArista2 = j + "," + i;
                    if(!dirigida){
                        Edge arista2 = graphStream.getEdge(nombreArista2);
                        if(arista2 == null){
                            graphStream.addEdge(nombreArista1,
                                                String.valueOf(i),
                                                String.valueOf(j),
                                                dirigida);
                        }
                    }else{
                        graphStream.addEdge(nombreArista1,
                                            String.valueOf(i),
                                            String.valueOf(j),
                                            dirigida);
                    }
                }
            }
        }

        graphStream.setAttribute("ui.stylesheet", styleSheet);
        System.setProperty("org.graphstream.ui", "swing");
        graphStream.display(); //Se pone la gráfica en pantalla.
    }

    /**
     * Abre el archivo que representa a la gráfica y la dibuja.
     */
    public void abreArchivo(){
        FileChooser fileChooser = new FileChooser();
        File archivo = fileChooser.showOpenDialog(escenario);
        if(archivo != null){
            lee(archivo);
            dibujaGrafica();
            grupo.getChildren().clear();
            grupo.getChildren().add(btnIniciar);
        }
    }

    /**
     * Actualiza los valores de la matriz de etiquetas.
     */
    public void actualizaMatriz(){
        Estado actual = exec.getEstado(itAct);
        for(int i=0; i<actual.est.length; i++){
            for(int j=0; j<actual.est.length; j++){
                String texto = "";
                if(actual.est[i][j]){
                    texto = "1";
                }else{
                    texto = "0";
                }
                matriz[i][j].setText(texto);
            }
        }
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        filaN = new Label[grafica.length];
        columnaN = new Label[grafica.length];
        matriz = new Label[grafica.length][grafica.length];
        cuadricula.setPrefColumns(grafica.length+1);
        cuadricula.setPrefRows(grafica.length+1);
        for(int i=0; i<filaN.length; i++){
            filaN[i] = new Label(String.valueOf(i));
            cuadricula.getChildren().add(filaN[i]);
        }
        for(int i=0; i<grafica.length; i++){
            columnaN[i] = new Label(String.valueOf(i));
            cuadricula.getChildren().add(columnaN[i]);
            for(int j=0; j<grafica.length; j++){
                matriz[i][j] = new Label(String.valueOf(grafica[i][j]));
                cuadricula.getChildren().add(matriz[i][j]);
            }
        }
        cuadricula.setTileAlignment(Pos.TOP_LEFT);
        grupo.getChildren().clear();
        grupo.getChildren().addAll(btnAnt, btnSig, btnFuente, cuadricula);
        btnAnt.setDisable(true); //Deshabilita el botón anterior.
        exec = new Ejecucion();
        Algoritmo.cerradura(grafica, exec);
        actualizaMatriz();
    }

    /**
     * Cambia el tamaño de letra.
     */
    public void cambiaFuente(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Fuente");
        dialog.setHeaderText("Tamaño de letra actual: "+fuente);
        dialog.setContentText("Nuevo tamaño: ");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            fuente = Double.parseDouble(result.get());
            setStyle();
            graphStream.setAttribute("ui.stylesheet", styleSheet);
            for(Label etiq : filaN){
                etiq.setFont(Font.font(fuente));
            }
            for(Label etiq : columnaN){
                etiq.setFont(Font.font(fuente));
            }
            for(int i=0; i<matriz.length; i++){
                for(int j=0; j<matriz.length; j++){
                    matriz[i][j].setFont(Font.font(fuente));
                }
            }
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


