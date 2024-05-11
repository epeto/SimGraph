
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
import javafx.scene.paint.Color;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    Label labTree;
    Label labBack;
    Label labForward;
    Label labCross;
    Group grupo; //Grupo que contiene a todos los elementos.
    int itAct; //Iteración actual.
    Grafica grafica; //Gráfica local
    Graph graphStream; //Gráfica de GraphStream
    double fuente; //tamaño de letra
    Stage escenario;
    String styleSheet; //Hoja de estilo para la gráfica.
    Ejecucion exec;

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

        labTree = new Label("Arco de árbol: rojo.");
        labTree.setLayoutX(10);
        labTree.setLayoutY(fuente*3);
        labTree.setFont(Font.font(fuente));
        labTree.setTextFill(Color.RED);

        labBack = new Label("Arco de regreso: púrpura.");
        labBack.setLayoutX(10);
        labBack.setLayoutY(fuente*5);
        labBack.setFont(Font.font(fuente));
        labBack.setTextFill(Color.PURPLE);

        labForward = new Label("Arco hacia adelante: verde.");
        labForward.setLayoutX(10);
        labForward.setLayoutY(fuente*7);
        labForward.setFont(Font.font(fuente));
        labForward.setTextFill(Color.GREEN);

        labCross = new Label("Arco cruzado: dorado.");
        labCross.setLayoutX(10);
        labCross.setLayoutY(fuente*9);
        labCross.setFont(Font.font(fuente));
        labCross.setTextFill(Color.GOLD);

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
        escenario.setTitle("Clasificación de aristas");
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
            boolean dir = lineas.get(0).equals("d");
            int orden = Integer.parseInt(lineas.get(1)); //El orden de la gráfica
            grafica = new Grafica(dir, orden); //Se crea la gráfica
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
        +"edge.negro {fill-color: black;}"
        +"edge.tree {fill-color: red;}"
        +"edge.back {fill-color: purple;}"
        +"edge.forward {fill-color: green;}"
        +"edge.cruzado {fill-color: gold;}"
        +"node {size: 15px; text-size: "+fuente+"; text-background-mode: plain;}"
        +"node.negro {fill-color: black; text-background-color: black; text-color: white;}"
        +"node.visit1 {fill-color: blue; text-background-color: blue; text-color: white;}"
        +"node.visit2 {fill-color: darkblue; text-background-color: darkblue; text-color: white;}";
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
            nodoV.setAttribute("ui.label", v.toString()+", "+v.dfi);
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
        for(Integer[] tupla : actual.estVert){
            Node nodoGS = graphStream.getNode(tupla[0].toString());
            switch(tupla[1]){
                case 0: nodoGS.setAttribute("ui.class", "negro");
                break;
                case 1: nodoGS.setAttribute("ui.class", "visit1");
                break;
                case 2: nodoGS.setAttribute("ui.class", "visit2");
                break;
                default: break;
            }
            nodoGS.setAttribute("ui.label", "v"+tupla[0]+", "+tupla[2]);
        }

        //Tree edges
        for(Integer[] ari : actual.tree){
            Edge edgeGS = graphStream.getEdge(ari[0]+","+ari[1]);
            if(edgeGS != null){
                edgeGS.setAttribute("ui.class", "tree");
            }
        }

        //Back edges
        for(Integer[] ari : actual.back){
            Edge edgeGS = graphStream.getEdge(ari[0]+","+ari[1]);
            if(edgeGS != null){
                edgeGS.setAttribute("ui.class", "back");
            }
        }

        //Forward edge
        for(Integer[] ari : actual.forward){
            Edge edgeGS = graphStream.getEdge(ari[0]+","+ari[1]);
            if(edgeGS != null){
                edgeGS.setAttribute("ui.class", "forward");
            }
        }

        //Cross edges
        for(Integer[] ari : actual.cross){
            Edge edgeGS = graphStream.getEdge(ari[0]+","+ari[1]);
            if(edgeGS != null){
                edgeGS.setAttribute("ui.class", "cruzado");
            }
        }
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        grupo.getChildren().clear();
        grupo.getChildren().addAll(btnAnt, btnSig, btnFuente, 
                                  labTree, labBack, labForward, labCross);
        btnAnt.setDisable(true); //Deshabilita el botón anterior.
        exec = new Ejecucion();
        Algoritmo.dfs(grafica, exec);
        itAct = 0;
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
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

