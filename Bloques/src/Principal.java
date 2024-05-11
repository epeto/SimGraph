
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Optional;
import java.util.Random;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    VBox pilaVista; //Contiene los rectángulos de la pila.
    Group bloques; //Contiene los bloques de la gráfica.
    Group superGrupo; //Contiene la pila y los bloques.
    ScrollPane deslizador;
    String clasesSS;

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

        pilaVista = new VBox();

        bloques = new Group();
        bloques.setLayoutX(fuente*9);

        superGrupo = new Group(pilaVista, bloques);

        deslizador = new ScrollPane(superGrupo);
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
        escenario.setTitle("Bloques");
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
            System.err.println("Archivo no encontrado o mal formado.");
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
            clasesSS = clasesSS + " edge.c"+i+" {fill-color: rgb("
                       +r+","+g+","+b+");}";
        }
    }

    private void setStyle(){
        styleSheet = "edge {size: 3px;}"
        +"edge.rojo {fill-color: red;}"
        +"edge.negro {fill-color: black;}"
        +"node {size: 15px; text-size: "+fuente+"; text-background-mode: plain;}"
        +"node.negro {fill-color: black; text-background-color: black; text-color: white;}"
        +"node.corte {fill-color: purple; text-background-color: purple; text-color: white;}"
        +"node.visitado {fill-color: darkblue; text-background-color: darkblue; text-color: white;}"
        +clasesSS;
    }

    /**
     * Dibuja la gráfica de GraphStream.
     */
    public void dibujaGrafica(){
        coloresRand(grafica.getTamano());
        setStyle();
        graphStream = new SingleGraph("Grafica 1");
        //En esta parte se agregan todos los vértices a "graphStream".
        for(Vertice v : grafica.vertices){
            graphStream.addNode(String.valueOf(v.id));
            Node nodoV = graphStream.getNode(String.valueOf(v.id));
            String dfi = String.valueOf(v.dfi);
            nodoV.setAttribute("ui.label", v.toString()+", "+dfi);
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
     * Dibuja la pila en la ventana de JavaFx.
     */
    private void dibujaPila(LinkedList<String> elements){
        pilaVista.getChildren().clear();
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
            pilaVista.getChildren().add(bloque);
        }
    }

    private void dibujaBloques(LinkedList<LinkedList<Integer>> elements){
        bloques.getChildren().clear();
        for(int i=0; i<elements.size(); i++){
            HBox bloqueVista = new HBox();
            bloqueVista.setLayoutY(i*fuente*2);
            for(Integer elem : elements.get(i)){
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
     * También actualiza la cola en JavaFx.
     */
    public void actualizaGrafica(){
        Estado actual = exec.getEstado(itAct);
        //Pinta las aristas de negro.
        graphStream.edges().forEach(edgeGS -> {
            edgeGS.setAttribute("ui.class", "negro");
        });

        //Pinta los nodos visitados de azul, los no visitados de negro y los de corte de púrpura.
        for(Integer[] tupla : actual.estVert){
            Node nodoGS = graphStream.getNode(String.valueOf(tupla[0]));
            String dfi = tupla[1].toString();
            String low = tupla[2].toString();
            if(tupla[1] == 0){
                nodoGS.setAttribute("ui.class", "negro");
            }else{
                nodoGS.setAttribute("ui.class", "visitado");
            }
            if(tupla[3] == 1){ //Significa que es vértice de corte.
                nodoGS.setAttribute("ui.class", "corte");
            }
            nodoGS.setAttribute("ui.label", "v"+tupla[0]+"  "+dfi+"/"+low);
        }

        //Pinta las aristas del árbol de rojo.
        for(Integer[] ari : actual.rojas){
            if(grafica.dirigida){
                Edge edgeGS = graphStream.getEdge(ari[0]+","+ari[1]);
                edgeGS.setAttribute("ui.class", "rojo");
            }else{
                Edge edgeGS1 = graphStream.getEdge(ari[0]+","+ari[1]);
                if(edgeGS1 != null){
                    edgeGS1.setAttribute("ui.class", "rojo");
                }else{
                    Edge edgeGS2 = graphStream.getEdge(ari[1]+","+ari[0]);
                    edgeGS2.setAttribute("ui.class", "rojo");
                }
            }
        }

        dibujaPila(actual.estPila);
        dibujaBloques(actual.estBloques);

        // Si es la última iteración se pintan las aristas de varios colores.
        if(itAct == exec.estados.size() - 1){
            int indColor = 0; //índice de color.
            for(LinkedList<Integer> bloque : actual.estBloques){
                for(int i=0; i<bloque.size(); i++){
                    for(int j=i+1; j<bloque.size(); j++){
                        int id1 = bloque.get(i);
                        int id2 = bloque.get(j);
                        Edge ari1 = graphStream.getEdge(id1+","+id2);
                        if(ari1 != null){
                            ari1.setAttribute("ui.class", "c"+indColor);
                        }
                        Edge ari2 = graphStream.getEdge(id2+","+id1);
                        if(ari2 != null){
                            ari2.setAttribute("ui.class", "c"+indColor);
                        }
                    }
                }
                indColor++;
            }
        }
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        grupo.getChildren().clear();
        grupo.getChildren().addAll(btnAnt, btnSig, btnFuente, deslizador);
        btnAnt.setDisable(true); //Deshabilita el botón anterior.
        exec = new Ejecucion();
        Random rn = new Random();
        int origen = rn.nextInt(grafica.getOrden());
        Algoritmo.getBloques(grafica, grafica.getVertice(origen), exec);
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
            dibujaPila(exec.getEstado(itAct).estPila);
            setStyle();
            graphStream.setAttribute("ui.stylesheet", styleSheet);
        }

        bloques.setLayoutX(10+fuente*9);
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
