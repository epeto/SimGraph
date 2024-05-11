
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
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
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
    int[][] grafica; //Representación por matriz de adyacencias de una gráfica.
    boolean dirigida; //Decide si la gráfica será dirigida.
    Graph graphStream; //Gráfica de GraphStream
    double fuente; //tamaño de letra
    Stage escenario;
    String styleSheet; //Hoja de estilo para la gráfica.
    Ejecucion exec;
    RadioButton[] filaSel; //Para seleccionar una fila de la matriz.
    ToggleGroup radioGroup; //Para seleccionar a lo sumo una columna a la vez.
    Label[] filaN; //Fila con etiquetas de 0 a n-1
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
                    actualizaGrafica();
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
                    actualizaGrafica();
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
        escenario.setTitle("Floyd-Warshall");
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
            grafica = new int[orden][orden];
            //Se colocan los valores iniciales en la gráfica.
            for(int i=0;i<grafica.length;i++){
                for(int j=0;j<grafica.length;j++){
                    if(i == j){
                        grafica[i][j] = 0;
                    }else{
                        grafica[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
            //Se agregan las aristas.
            for(int i=2; i<lineas.size(); i++){
                String[] ariStr = lineas.get(i).split(",");
                int v0 = Integer.parseInt(ariStr[0]);
                int v1 = Integer.parseInt(ariStr[1]);
                int peso = Integer.parseInt(ariStr[2]);
                grafica[v0][v1] = peso;
                if(!dirigida){
                    grafica[v1][v0] = peso;
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
                if(i!=j && grafica[i][j] < Integer.MAX_VALUE){
                    String nombreArista1 = i + "," + j;
                    String nombreArista2 = j + "," + i;
                    if(!dirigida){
                        Edge arista2 = graphStream.getEdge(nombreArista2);
                        if(arista2 == null){
                            graphStream.addEdge(nombreArista1,
                                                String.valueOf(i),
                                                String.valueOf(j),
                                                dirigida);
                            Edge ariGS = graphStream.getEdge(nombreArista1);
                            ariGS.setAttribute("ui.label", String.valueOf(grafica[i][j]));
                        }
                    }else{
                        graphStream.addEdge(nombreArista1,
                                            String.valueOf(i),
                                            String.valueOf(j),
                                            dirigida);
                        Edge ariGS = graphStream.getEdge(nombreArista1);
                        ariGS.setAttribute("ui.label", String.valueOf(grafica[i][j]));
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
    public void actualizaMatriz(int[][] nueva){
        for(int i=0; i<nueva.length; i++){
            for(int j=0; j<nueva.length; j++){
                String texto = "";
                if(nueva[i][j] == Integer.MAX_VALUE){
                    texto = "Ꝏ";
                }else{
                    texto = String.valueOf(nueva[i][j]);
                }
                matriz[i][j].setText(texto);
            }
        }
    }

    /**
     * Actualiza la gráfica de GraphStream dependiendo del estado de la ejecución.
     * También actualiza la matriz en JavaFx.
     */
    public void actualizaGrafica(){
        Estado actual = exec.getEstado(itAct);
        //Pinta todas las aristas de negro.
        graphStream.edges().forEach(edgeGS -> {
            edgeGS.setAttribute("ui.class", "negro");
        });
        int sel = -1; //Indica la fila seleccionada.
        for(int i=0; i<filaSel.length; i++){
            if(filaSel[i].isSelected()){
                sel = i;
            }
        }
        // Si alguna fila está seleccionada se actualiza la gráfica.
        if(sel > -1){
            for(int j=0; j<actual.estDist.length; j++){
                Node nodoGS = graphStream.getNode(String.valueOf(j));
                String dist = "";
                if(actual.estDist[sel][j] == Integer.MAX_VALUE){
                    dist = "Ꝏ";
                }else{
                    dist = String.valueOf(actual.estDist[sel][j]);
                }
                nodoGS.setAttribute("ui.label", "v"+j+","+dist);
            }
            //En esta parte se agregan las aristas rojas.
            if(dirigida){
                for(int j=0; j<actual.estPred.length; j++){
                    int pred = actual.estPred[sel][j];
                    Edge ariGS = graphStream.getEdge(pred+ "," +j);
                    if(ariGS != null){
                        ariGS.setAttribute("ui.class","arbol");
                    }
                }
            }else{
                for(int j=0; j<actual.estPred.length; j++){
                    int pred = actual.estPred[sel][j];
                    Edge ariGS1 = graphStream.getEdge(pred+","+j);
                    Edge ariGS2 = graphStream.getEdge(j+","+pred);
                    if(ariGS1 != null){
                        ariGS1.setAttribute("ui.class","arbol");
                    }else if(ariGS2 != null){
                        ariGS2.setAttribute("ui.class","arbol");
                    }
                }
            }
        }
        actualizaMatriz(actual.estDist);
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        radioGroup = new ToggleGroup();
        filaN = new Label[grafica.length];
        filaSel = new RadioButton[grafica.length];
        matriz = new Label[grafica.length][grafica.length];
        cuadricula.setPrefColumns(grafica.length+1);
        cuadricula.setPrefRows(grafica.length+1);
        for(int i=0; i<filaN.length; i++){
            filaN[i] = new Label(String.valueOf(i));
            cuadricula.getChildren().add(filaN[i]);
        }
        for(int i=0; i<grafica.length; i++){
            filaSel[i] = new RadioButton(String.valueOf(i));
            filaSel[i].setToggleGroup(radioGroup);
            cuadricula.getChildren().add(filaSel[i]);
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
        Algoritmo.floyd_warshall(grafica, exec);
        actualizaGrafica();
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
            for(RadioButton boton : filaSel){
                boton.setFont(Font.font(fuente));
            }
            for(Label etiq : filaN){
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


