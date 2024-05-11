
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
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
    Group colaVista; //Contiene los nodos de la cola de prioridades.
    double anchoNodo; //Ancho de un nodo en la cola.
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

        colaVista = new Group();

        deslizador = new ScrollPane(colaVista);
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
        escenario.setTitle("Dijkstra");
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
            boolean directed = lineas.get(0).equals("d"); //Decide si la gráfica es dirigida.
            int orden = Integer.parseInt(lineas.get(1)); //El orden de la gráfica
            grafica = new Grafica(directed, orden); //Se crea la gráfica
            //Se agregan las aristas.
            for(int i=2; i<lineas.size(); i++){
                String[] ariStr = lineas.get(i).split(",");
                int v0 = Integer.parseInt(ariStr[0]);
                int v1 = Integer.parseInt(ariStr[1]);
                int peso = Integer.parseInt(ariStr[2]);
                grafica.agregaArista(v0, v1, peso);
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
        +"node.negro {fill-color: black; text-background-color: black; text-color: white;}"
        +"node.visit {fill-color: darkblue; text-background-color: darkblue; text-color: white;}";
    }

    /**
     * Dibuja la gráfica de GraphStream.
     */
    public void dibujaGrafica(){
        setStyle();
        graphStream = new SingleGraph("Grafica 1");
        //Se agregan todos los vértices a "graphStream".
        for(Vertice v : grafica.vertices){
            graphStream.addNode(String.valueOf(v.id));
            Node nodoV = graphStream.getNode(String.valueOf(v.id));
            nodoV.setAttribute("ui.label", v.toString());
        }

        //En esta parte se agregan las aristas.
        if(grafica.dirigida){ //Si es dirigida
            for(Vertice v : grafica.vertices){
                for(Pair<Vertice, Integer> par : v.ady){
                    Vertice u = par.getKey();
                    String nombreArista = v.id+","+u.id;
                    graphStream.addEdge(nombreArista, String.valueOf(v.id), String.valueOf(u.id), true);
                    Edge ariGS = graphStream.getEdge(nombreArista);
                    ariGS.setAttribute("ui.label", par.getValue().toString());
                }
            }
        }else{ //Si no es dirigida
            for(Vertice v : grafica.vertices){
                for(Pair<Vertice, Integer> par : v.ady){
                    Vertice u = par.getKey();
                    String nombreArista = v.id+","+u.id;
                    String nombreArista2 = u.id+","+v.id;
                    if(graphStream.getEdge(nombreArista2) == null){ //Si no se ha agregado la arista.
                        graphStream.addEdge(nombreArista, String.valueOf(v.id), String.valueOf(u.id));
                        Edge ariGS = graphStream.getEdge(nombreArista);
                        ariGS.setAttribute("ui.label", par.getValue().toString());
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

    public void dibujaCola(LinkedList<LinkedList<NodoEst>> colaBin){
        colaVista.getChildren().clear();
        int longMax = 0; //Longitud máxima de un elemento en la cola binomial.
        for(LinkedList<NodoEst> arbolBin : colaBin){
            for(NodoEst nodo : arbolBin){
                int longAct = nodo.elemento.length();
                if(longAct > longMax)
                    longMax = longAct;
            }
        }
        anchoNodo = longMax * fuente;
        int limiteIzq = 0;
        // Primero se dibujan las líneas.
        for(LinkedList<NodoEst> arbolBin : colaBin){
            for(NodoEst nodo : arbolBin){
                if(nodo.cpadre >= 0){
                    Line linea = new Line((nodo.columna+limiteIzq)*anchoNodo + anchoNodo/2,
                                  nodo.fila*fuente*3 + fuente,
                                  (nodo.cpadre+limiteIzq)*anchoNodo + anchoNodo/2,
                                  nodo.fpadre*fuente*3 + fuente);
                    linea.setStrokeWidth(2);
                    colaVista.getChildren().add(linea);
                }
            }
            limiteIzq += arbolBin.get(0).columna+1;
        }
        // Luego se dibujan los elipses
        limiteIzq = 0;
        for(LinkedList<NodoEst> arbolBin : colaBin){
            for(NodoEst nodo : arbolBin){
                Ellipse elipse = new Ellipse();
                elipse.setRadiusX(anchoNodo/2);
                elipse.setRadiusY(fuente);
                elipse.setStroke(Color.BLACK);
                elipse.setFill(Color.AQUAMARINE);
                Text texto = new Text(String.valueOf(nodo.elemento));
                texto.setFont(Font.font(fuente));
                StackPane sp = new StackPane();
                sp.getChildren().addAll(elipse, texto);
                sp.setLayoutX((nodo.columna+limiteIzq)*anchoNodo);
                sp.setLayoutY(nodo.fila*fuente*3);
                colaVista.getChildren().add(sp);
            }
            limiteIzq += arbolBin.get(0).columna+1;
        }
    }

    /**
     * Actualiza la gráfica de GraphStream dependiendo del estado de la ejecución.
     * También actualiza la cola en JavaFx.
     */
    public void actualizaGrafica(){
        Estado actual = exec.getEstado(itAct);
        //Pinta todas las aristas de negro.
        graphStream.edges().forEach(edgeGS -> {
            edgeGS.setAttribute("ui.class", "negro");
        });
        //Pinta los nodos y les coloca una etiqueta.
        for(Integer[] ev : actual.estVert){
            String strKey = "";
            Node nodoGS = graphStream.getNode(ev[0].toString());
            if(ev[1] < Integer.MAX_VALUE){
                strKey = String.valueOf(ev[1]);
                nodoGS.setAttribute("ui.class", "visit");
            }else{
                strKey = "Ꝏ";
                nodoGS.setAttribute("ui.class", "negro");
            }
            nodoGS.setAttribute("ui.label", "v"+ev[0]+","+strKey);
        }
        //Pinta las aristas visitadas de rojo.
        if(grafica.dirigida){
            for(Integer[] ari : actual.ariArbol){
                Edge ariGS = graphStream.getEdge(ari[0] + "," + ari[1]);
                if(ariGS != null){
                    ariGS.setAttribute("ui.class", "arbol");
                }
            }
        }else{
            for(Integer[] ari : actual.ariArbol){
                Edge ariGS1 = graphStream.getEdge(ari[0] + "," + ari[1]);
                Edge ariGS2 = graphStream.getEdge(ari[1] + "," + ari[0]);
                if(ariGS1 != null){
                    ariGS1.setAttribute("ui.class", "arbol");
                }else{
                    ariGS2.setAttribute("ui.class", "arbol");
                }
            }
        }
        // Dibuja la estructura
        dibujaCola(actual.estCB);
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
            int idOrigen = Integer.parseInt(result.get());
            Vertice vOrigen = grafica.getVertice(idOrigen);
            grupo.getChildren().clear();
            grupo.getChildren().addAll(btnAnt, btnSig, btnFuente, deslizador);
            btnAnt.setDisable(true); //Deshabilita el botón anterior.
            exec = new Ejecucion();
            Algoritmo.dijkstra(grafica, vOrigen, exec);
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
            setStyle();
            graphStream.setAttribute("ui.stylesheet", styleSheet);
            dibujaCola(exec.getEstado(itAct).estCB);
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


