
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
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
    Grafica grafica; //Gráfica local
    Grafica transpuesta;
    Graph graphStream; //Gráfica de GraphStream
    Graph gst; //graphstream de la gráfica transpuesta
    double fuente; //tamaño de letra
    Stage escenario;
    String styleSheet; //Hoja de estilo para la gráfica.
    String clasesSS; //Para definir diferentes colores de vértices. Es parte del styleSheet.
    HBox listaSup; //Lista que mostrará la exploración en postorden de los vértices.
    Ejecucion exec;
    Group bloques; //Contiene los cfc de la gráfica.

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

        listaSup = new HBox();
        listaSup.setLayoutX(10);
        listaSup.setLayoutY(fuente*2 + 10);

        bloques = new Group();
        bloques.setLayoutX(10);
        bloques.setLayoutY(fuente*5);

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
        escenario.setTitle("Componentes fuertemente conexas");
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
     * Genera colores aleatorios y los mete a una clase de CSS.
     */
    public void coloresRand(int n){
        int r, g, b;
        Random rn = new Random();
        clasesSS = "";
        for(int i=0; i<n; i++){
            r = rn.nextInt(256);
            g = rn.nextInt(256);
            b = rn.nextInt(256);
            clasesSS = clasesSS + " node.c"+i+" {fill-color: rgb("
                       +r+","+g+","+b+"); text-background-color: rgb("
                       +r+","+g+","+b+"); text-color: white;}";
        }
    }

    /**
     * Fija la hoja de estilo.
     */
    public void setStyle(){
        styleSheet = "edge {size: 3px; arrow-size: 15px, 7px;}"
        +"edge.negro {fill-color: black;}"
        +"node {size: 15px; text-size: "+fuente+"; text-background-mode: plain;}"
        +"node.negro {fill-color: black; text-background-color: black; text-color: white;}"
        +"node.visit1 {fill-color: blue; text-background-color: blue; text-color: white;}"
        +"node.visit2 {fill-color: darkblue; text-background-color: darkblue; text-color: white;}"
        +clasesSS;
    }

    /**
     * Dibuja las gráficas de GraphStream.
     */
    public void dibujaGrafica(){
        coloresRand(grafica.getOrden());
        setStyle();
        graphStream = new SingleGraph("Grafica 1");
        gst = new SingleGraph("Grafica 2");
        //En esta parte se agregan todos los vértices a graphStream.
        for(Vertice v : grafica.vertices){
            graphStream.addNode(String.valueOf(v.id));
            Node nodoV = graphStream.getNode(String.valueOf(v.id));
            nodoV.setAttribute("ui.label", v.toString()+", "+v.f);
        }
        //En esta parte se agregan todos los vértices a gst.
        for(Vertice v : transpuesta.vertices){
            gst.addNode(String.valueOf(v.id));
            Node nodoV = gst.getNode(String.valueOf(v.id));
            nodoV.setAttribute("ui.label", v.toString()+", "+v.f);
        }

        //En esta parte se agregan las aristas a graphStream.
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

        //En esta parte se agregan las aristas a gst.
        if(transpuesta.dirigida){ //Si es dirigida
            for(Vertice v : transpuesta.vertices){
                for(Vertice u : v.ady){
                    String nombreArista = v.id+","+u.id;
                    gst.addEdge(nombreArista, String.valueOf(v.id), String.valueOf(u.id), true);
                }
            }
        }else{ //Si no es dirigida
            for(Vertice v : transpuesta.vertices){
                for(Vertice u : v.ady){
                    String nombreArista = v.id+","+u.id;
                    String nombreArista2 = u.id+","+v.id;
                    if(gst.getEdge(nombreArista2) == null){ //Si no se ha agregado la arista.
                        gst.addEdge(nombreArista, String.valueOf(v.id), String.valueOf(u.id));
                    }
                }
            }
        }
        graphStream.setAttribute("ui.stylesheet", styleSheet);
        gst.setAttribute("ui.stylesheet", styleSheet);
        System.setProperty("org.graphstream.ui", "swing");
        graphStream.display(); //Se pone la gráfica en pantalla.
        gst.display();
    }

    /**
     * Abre el archivo que representa a la gráfica y la dibuja.
     */
    public void abreArchivo(){
        FileChooser fileChooser = new FileChooser();
        File archivo = fileChooser.showOpenDialog(escenario);
        if(archivo != null){
            lee(archivo);
            transpuesta = grafica.transpuesta();
            dibujaGrafica();
            grupo.getChildren().clear();
            grupo.getChildren().add(btnIniciar);
        }
    }

    /**
     * Dibuja la lista que contiene la exploración en postorden.
     * @param est
     */
    public void dibujaListaSup(Estado est){
        listaSup.getChildren().clear();
        for(Integer idv : est.listaPO){
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(fuente*2);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setFill(Color.AQUAMARINE);
            Text texto = new Text("v"+idv);
            texto.setFont(Font.font(fuente));
            StackPane casilla = new StackPane();
            casilla.getChildren().addAll(rectangulo, texto);

            Integer[] vert = est.estTrans.get(idv);
            if(vert[1] == 1){
                rectangulo.setFill(Color.OLIVE);
            }
            listaSup.getChildren().add(casilla);
        }
    }

    /**
     * Dibuja las componentes fuertemente conexas por filas.
     * @param est
     */
    public void dibujaBloques(Estado est){
        bloques.getChildren().clear();
        for(int i=0; i<est.componentes.size(); i++){
            HBox bloqueVista = new HBox();
            bloqueVista.setLayoutY(i*fuente*2);
            for(Integer elem : est.componentes.get(i)){
                Text texto = new Text("v"+elem);
                texto.setFont(Font.font(fuente));
                Rectangle rectangulo = new Rectangle();
                rectangulo.setWidth(texto.getText().length()*fuente);
                rectangulo.setHeight(fuente*2);
                rectangulo.setStroke(Color.BLACK); //Contorno negro.
                rectangulo.setFill(Color.AQUAMARINE); //Relleno aguamarina.
                StackPane cuadro = new StackPane();
                cuadro.getChildren().addAll(rectangulo, texto);
                bloqueVista.getChildren().add(cuadro);
            }
            bloques.getChildren().add(bloqueVista);
        }
    }

    /**
     * Actualiza la gráfica de GraphStream dependiendo del estado de la ejecución.
     * También actualiza las listas en JavaFx.
     */
    public void actualizaGrafica(){
        Estado actual = exec.getEstado(itAct);

        //Pinta los nodos de negro.
        for(Integer[] tupla : actual.estVert){
            Node nodoGS = graphStream.getNode(tupla[0].toString());
            nodoGS.setAttribute("ui.label", "v"+tupla[0]+", "+tupla[2]);
            nodoGS.setAttribute("ui.class", "negro");
        }
        for(Integer[] tupla : actual.estTrans){
            Node nodoGS = gst.getNode(tupla[0].toString());
            nodoGS.setAttribute("ui.label", "v"+tupla[0]+", "+tupla[2]);
            nodoGS.setAttribute("ui.class", "negro");
        }

        if(actual.parte == 1){
            //Pinta los nodos de azul si ya han sido visitados.
            for(Integer[] tupla : actual.estVert){
                Node nodoGS = graphStream.getNode(tupla[0].toString());
                if(tupla[1] == 1){
                    nodoGS.setAttribute("ui.class", "visit1");
                }
                if(tupla[2] > 0){
                    nodoGS.setAttribute("ui.class", "visit2");
                }
            }
        }else{
            int indColor = 0; //índice de color.
            for(LinkedList<Integer> lista : actual.componentes){
                for(Integer idv : lista){
                    Node nodoGS1 = graphStream.getNode(idv.toString());
                    Node nodoGS2 = gst.getNode(idv.toString());
                    nodoGS1.setAttribute("ui.class", "c"+indColor);
                    nodoGS2.setAttribute("ui.class", "c"+indColor);
                }
                indColor++;
            }
        }
        dibujaListaSup(actual);
        dibujaBloques(actual);
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        grupo.getChildren().clear();
        grupo.getChildren().addAll(btnAnt, btnSig, btnFuente, listaSup, bloques);
        btnAnt.setDisable(true); //Deshabilita el botón anterior.
        exec = new Ejecucion();
        Algoritmo.scc(grafica, transpuesta, exec);
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
            dibujaListaSup(exec.getEstado(itAct));
            bloques.setLayoutY(fuente*5);
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

