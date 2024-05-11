
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.LinkedList;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.control.ScrollPane;

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
    Grafica grafica; //Gráfica local
    Graph graphStream; //Gráfica de GraphStream
    double fuente; //tamaño de letra
    Stage escenario;
    String styleSheet; //Hoja de estilo para la gráfica.
    Ejecucion exec;
    Label labAct; //Etiqueta de la lista actual.
    HBox lista1V; //Contiene los cuadros de la lista 1.
    Label labTot; //Etiqueta de la lista total.
    HBox lista2V; //Contiene los cuadros de la lista 2.
    Group superGrupo; //Contiene las 2 listas y sus etiquetas.
    ScrollPane deslizador;

    @Override
    public void start(Stage primaryStage) {
        fuente = 12;
        grupo = new Group();
        escenario = primaryStage;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        anchoVentana = screenBounds.getWidth()/2;
        altoVentana = screenBounds.getHeight();

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

        labAct = new Label("Actual:");
        labAct.setFont(Font.font(fuente));

        lista1V = new HBox();
        lista1V.setLayoutY(fuente*2);

        labTot = new Label("Total:");
        labTot.setLayoutY(fuente*4);
        labTot.setFont(Font.font(fuente));

        lista2V = new HBox();
        lista2V.setLayoutY(fuente*6);

        superGrupo = new Group(labAct, lista1V, labTot, lista2V);

        deslizador = new ScrollPane(superGrupo);
        deslizador.setLayoutX(10);
        deslizador.setLayoutY(fuente*2 + 10);
        deslizador.setPrefSize(anchoVentana - 20, altoVentana - 150);

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

        Scene scene = new Scene(grupo, anchoVentana, altoVentana);
        escenario.setTitle("Circuito Euleriano");
        escenario.setScene(scene);
        escenario.setY(0);
        escenario.setX(anchoVentana);
        escenario.show();
    }

    /**
     * Lee un archivo de la carpeta "input" y crea una gráfica.
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
            int orden = Integer.parseInt(lineas.get(1)); //El orden de la gráfica
            grafica = new Grafica(false, orden); //Se crea la gráfica
            //Se agregan las aristas.
            for(int i=2; i<lineas.size(); i++){
                String[] ariStr = lineas.get(i).split(",");
                int v0 = Integer.parseInt(ariStr[0]);
                int v1 = Integer.parseInt(ariStr[1]);
                grafica.agregaArista(v0, v1);
            }
        }catch(Exception e){
            System.err.println("Archivo no encontrado");
            e.printStackTrace();
        }
    }

    /**
     * Fija la hoja de estilo.
     */
    public void setStyle(){
        styleSheet = "edge {size: 3px; text-size: "+fuente+";}"
        +"edge.visitado {fill-color: red;}"
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
        //En esta parte se agregan todos los vértices a "graph".
        for(Vertice v : grafica.vertices){
            graphStream.addNode(String.valueOf(v.id));
            Node nodoV = graphStream.getNode(String.valueOf(v.id));
            nodoV.setAttribute("ui.label", v.toString());
        }

        //En esta parte se agregan las aristas.
        if(grafica.dirigida){ //Si es dirigida
            for(Vertice v : grafica.vertices){
                for(Vertice u : v.ady){
                    String nombreArista = v.id+","+u.id;
                    graphStream.addEdge(nombreArista, String.valueOf(v.id), String.valueOf(u.id), true);
                }
            }
        }else{ //Si no es dirigida
            for(Vertice v : grafica.vertices){
                for(Vertice u : v.ady){
                    String nombreArista = v.id+","+u.id;
                    String nombreArista2 = u.id+","+v.id;
                    if(graphStream.getEdge(nombreArista2) == null){ //Si no se ha agregado la arista.
                        graphStream.addEdge(nombreArista, String.valueOf(v.id), String.valueOf(u.id));
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

    //Dibuja las listas en la ventana de JavaFx.
    private void dibujaListas(LinkedList<Integer> l1, LinkedList<Integer> l2){
        lista1V.getChildren().clear();
        lista2V.getChildren().clear();
        for(Integer elem : l1){
            Text texto = new Text("v"+elem);
            texto.setFont(Font.font(fuente));
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(texto.getText().length()*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK); //Contorno negro.
            rectangulo.setFill(Color.AQUAMARINE); //Relleno aguamarina.
            StackPane bloque = new StackPane();
            bloque.getChildren().addAll(rectangulo, texto);
            lista1V.getChildren().add(bloque);
        }

        for(Integer elem : l2){
            Text texto = new Text("v"+elem);
            texto.setFont(Font.font(fuente));
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(texto.getText().length()*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK); //Contorno negro.
            rectangulo.setFill(Color.AQUAMARINE); //Relleno aguamarina.
            StackPane bloque = new StackPane();
            bloque.getChildren().addAll(rectangulo, texto);
            lista2V.getChildren().add(bloque);
        }
    }

    /**
     * Actualiza la gráfica de GraphStream dependiendo del estado de la ejecución.
     * También actualiza la cola en JavaFx.
     */
    public void actualizaGrafica(){
        Estado actual = exec.getEstado(itAct);
        //Pinta las aristas de negro.
        graphStream.edges().forEach(edgeGS -> {
            edgeGS.setAttribute("ui.class", "negro");
            edgeGS.setAttribute("ui.label", "");
        });

        //Pinta las aristas visitadas de rojo.
        for(int i=1; i<actual.cAct.size(); i++){
            int v1 = actual.cAct.get(i-1);
            int v2 = actual.cAct.get(i);
            Edge ari1 = graphStream.getEdge(v1+","+v2);
            Edge ari2 = graphStream.getEdge(v2+","+v1);
            if(ari1 != null){
                ari1.setAttribute("ui.class", "visitado");
            }
            if(ari2 != null){
                ari2.setAttribute("ui.class", "visitado");
            }
        }

        for(int i=1; i<actual.cTotal.size(); i++){
            int v1 = actual.cTotal.get(i-1);
            int v2 = actual.cTotal.get(i);
            Edge ari1 = graphStream.getEdge(v1+","+v2);
            Edge ari2 = graphStream.getEdge(v2+","+v1);
            if(ari1 != null){
                ari1.setAttribute("ui.class", "visitado");
                ari1.setAttribute("ui.label", String.valueOf(i));
            }
            if(ari2 != null){
                ari2.setAttribute("ui.class", "visitado");
                ari2.setAttribute("ui.label", String.valueOf(i));
            }
        }

        dibujaListas(actual.cAct, actual.cTotal);
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        if(Algoritmo.esGraficaPar(grafica)){
            grupo.getChildren().clear();
            grupo.getChildren().addAll(btnAnt, btnSig, btnFuente, deslizador);
            btnAnt.setDisable(true); //Deshabilita el botón anterior.
            exec = new Ejecucion();
            Random rn = new Random();
            int origen = rn.nextInt(grafica.getOrden());
            Algoritmo.euler(grafica, origen, exec);
            itAct = 0;
            actualizaGrafica();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gráfica no euleriana");
            alert.setContentText("La gráfica tiene al menos un vértice de grado impar.");
            alert.showAndWait();
        }
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
            dibujaListas(exec.getEstado(itAct).cAct, exec.getEstado(itAct).cTotal);
            setStyle();
            graphStream.setAttribute("ui.stylesheet", styleSheet);
            labAct.setFont(Font.font(fuente));
            lista1V.setLayoutY(fuente*2);
            labTot.setLayoutY(fuente*4);
            labTot.setFont(Font.font(fuente));
            lista2V.setLayoutY(fuente*6);
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

