
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
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    boolean esBip; //Determina si la gráfica es bipartita.
    Grafica grafica; //Gráfica local
    Graph graphStream; //Gráfica de GraphStream
    double fuente; //tamaño de letra
    Stage escenario;
    String styleSheet; //Hoja de estilo para la gráfica.
    Ejecucion exec;
    HBox colaVista; //Contiene los rectángulos de la cola.
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

        colaVista = new HBox();

        deslizador = new ScrollPane(colaVista);
        deslizador.setLayoutX(10);
        deslizador.setLayoutY(fuente*4);
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
        escenario.setTitle("BFS");
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
            boolean dirigida = lineas.get(0).equals("d"); //Decide si la gráfica es dirigida.
            int orden = Integer.parseInt(lineas.get(1)); //El orden de la gráfica
            grafica = new Grafica(dirigida, orden); //Se crea la gráfica
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
        styleSheet = "edge {size: 3px; arrow-size: 15px, 7px;}"
        +"edge.rojo {fill-color: red;}"
        +"edge.negro {fill-color: black;}"
        +"node {size: 15px; text-size: "+fuente+"; text-background-mode: plain;}"
        +"node.negro {fill-color: black; text-background-color: black; text-color: white;}"
        +"node.azul {fill-color: darkblue; text-background-color: darkblue; text-color: white;}"
        +"node.par {fill-color: darkgreen; text-background-color: darkgreen; text-color: white;}"
        +"node.impar {fill-color: darkred; text-background-color: darkred; text-color: white;}";
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
            String distancia = String.valueOf(v.d);
            if(v.d == Integer.MAX_VALUE){
                distancia = "Ꝏ";
            }
            nodoV.setAttribute("ui.label", v.toString()+", "+distancia);
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

    private void dibujaCola(LinkedList<String> elements){
        //Dibuja la cola en la ventana de JavaFx.
        colaVista.getChildren().clear();
        for(String elem : elements){
            Text texto = new Text(elem);
            texto.setFont(Font.font(fuente));
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(elem.length()*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK); //Contorno negro.
            rectangulo.setFill(Color.AQUAMARINE); //Relleno aguamarina.
            StackPane bloque = new StackPane();
            bloque.getChildren().addAll(rectangulo, texto);
            colaVista.getChildren().add(bloque);
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
        });

        //Pinta los nodos visitados de azul y los no visitados de negro.
        for(Pair<Integer, Integer> par : actual.estVert){
            Node nodoGS = graphStream.getNode(String.valueOf(par.getKey()));
            String distancia = par.getValue().toString();
            if(par.getValue() == Integer.MAX_VALUE){
                distancia = "Ꝏ";
                nodoGS.setAttribute("ui.class", "negro");
            }else{
                nodoGS.setAttribute("ui.class", "azul");
            }
            // Si ya se llegó al final, se pintan los vértices de otro color.
            if((itAct == exec.estados.size()-1) && esBip){
                if((par.getValue()%2) == 0){
                    nodoGS.setAttribute("ui.class", "par");
                }else{
                    nodoGS.setAttribute("ui.class", "impar");
                }
            }
            nodoGS.setAttribute("ui.label", "v"+par.getKey()+", "+distancia);
        }

        //Pinta las aristas del árbol de rojo.
        for(Arista ari : actual.rojas){
            if(grafica.dirigida){
                Edge edgeGS = graphStream.getEdge(ari.vi+","+ari.vf);
                edgeGS.setAttribute("ui.class", "rojo");
            }else{
                Edge edgeGS1 = graphStream.getEdge(ari.vi+","+ari.vf);
                if(edgeGS1 != null){
                    edgeGS1.setAttribute("ui.class", "rojo");
                }else{
                    Edge edgeGS2 = graphStream.getEdge(ari.vf+","+ari.vi);
                    edgeGS2.setAttribute("ui.class", "rojo");
                }
            }
        }

        dibujaCola(actual.estCola);
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Inicia");
        dialog.setHeaderText("Escriba el id del vértice fuente.");
        dialog.setContentText("Escribir solo un número: ");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            grupo.getChildren().clear();
            grupo.getChildren().addAll(btnAnt, btnSig, deslizador, btnFuente);
            btnAnt.setDisable(true); //Deshabilita el botón anterior.
            int idInit = Integer.parseInt(result.get()); //Id del vértice inicial.
            exec = new Ejecucion();
            Vertice s = grafica.getVertice(idInit); //Obtiene el vértice fuente.
            Algoritmo.bfs(grafica, s, exec);
            esBip = Algoritmo.esBipartita(grafica);
            itAct = 0;
            actualizaGrafica();
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
            dibujaCola(exec.getEstado(itAct).estCola);
            setStyle();
            graphStream.setAttribute("ui.stylesheet", styleSheet);
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}