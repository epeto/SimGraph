
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    Group heapVista; //Contiene los nodos del heap.
    HBox arrayVista1; //Contendrá los rectángulos del arreglo del heap.
    HBox arrayVista2; //Contiene números de 1 a n.
    double anchoNodo; //Ancho de un nodo del heap.
    Group superGrupo; //Contendrá los arreglos y el heapVista.
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

        arrayVista1 = new HBox();

        arrayVista2 = new HBox();
        arrayVista2.setLayoutY(fuente*2);

        heapVista = new Group();
        heapVista.setLayoutY(fuente*4);

        superGrupo = new Group(arrayVista1, arrayVista2, heapVista);

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
        escenario.setTitle("Prim-MST");
        escenario.setScene(scene);
        escenario.setY(0);
        escenario.setX(anchoVentana);
        escenario.show();
    }

    /**
     * Lee un archivo de y crea una gráfica.
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
        styleSheet = "edge {size: 3px; text-size: "+fuente+";}"
        +"edge.arbol {fill-color: red;}"
        +"edge.pred {fill-color: pink;}"
        +"edge.negro {fill-color: black;}"
        +"node {size: 15px; text-size: "+fuente+"; text-background-mode: plain;}"
        +"node.negro {fill-color: black; text-background-color: black; text-color: white;}"
        +"node.visit1 {fill-color: blue; text-background-color: blue; text-color: white;}"
        +"node.visit2 {fill-color: darkblue; text-background-color: darkblue; text-color: white;}";
    }

    /**
     * Dibuja los nodos del heap de manera recursiva a partir de cierto nodo.
     * @param array arreglo del heap.
     * @param indice posición en el arreglo en el heap de este nodo.
     * @param cx coordenada x donde se dibujará este nodo.
     * @param cy coordenada y donde se dibujará este nodo.
     * @param anchoArbol ancho de este subárbol en pixeles.
     */
    private void dibujaNodo(String[] array, int indice, double cx, double cy, double anchoArbol){
        int indiceHI = indice*2; //Índice del hijo izquierdo.
        int indiceHD = indice*2+1; //Índice del hijo derecho.
        double xHI = cx - anchoArbol/4.0; //Posición en x del hijo izquierdo.
        double xHD = cx + anchoArbol/4.0; //Posición en x del hijo derecho.
        double yH = cy + fuente*3; //Posición en y de ambos hijos.
        if(indiceHI < array.length){
            //Línea que va hacia el hijo izquierdo.
            Line lineaHI = new Line(cx+anchoNodo/2, cy+fuente, xHI+anchoNodo/2, yH+fuente);
            lineaHI.setStrokeWidth(2);
            heapVista.getChildren().add(lineaHI);
        }
        if(indiceHD < array.length){
            //Línea que va hacia el hijo derecho.
            Line lineaHD = new Line(cx+anchoNodo/2, cy+fuente, xHD+anchoNodo/2, yH+fuente);
            lineaHD.setStrokeWidth(2);
            heapVista.getChildren().add(lineaHD);
        }
        Ellipse elipse = new Ellipse();
        elipse.setRadiusX(anchoNodo/2);
        elipse.setRadiusY(fuente);
        elipse.setStroke(Color.BLACK);
        elipse.setFill(Color.AQUAMARINE);
        Text texto = new Text(array[indice]);
        texto.setFont(Font.font(fuente));
        StackPane nodo = new StackPane();
        nodo.getChildren().addAll(elipse, texto);
        nodo.setLayoutX(cx);
        nodo.setLayoutY(cy);
        heapVista.getChildren().add(nodo);
        if(indiceHI < array.length){
            dibujaNodo(array, indiceHI, xHI, yH, anchoArbol/2);
        }
        if(indiceHD < array.length){
            dibujaNodo(array, indiceHD, xHD, yH, anchoArbol/2);
        }
    }

    /**
     * Encuentra la potencia de 2 más grande tal que
     * pot <= n
     * @param n cota superior para la potencia.
     * @return potencia de 2 menor o igual a n.
     */
    private int pot2(int n){
        int p1 = 1;
        int p2 = 1;
        while(p1 <= n){
            p2 = p1;
            p1 *= 2;
        }
        return p2;
    }

    /**
     * Dibuja el árbol que representa al heap.
     */
    private void dibujaHeap(String[] array){
        heapVista.getChildren().clear();
        anchoNodo = 0;
        if(array.length > 1){
            //Se encuentra la longitud máxima de cualquier elemento en el heap.
            int maxLen = 0;
            for(int i=1; i<array.length; i++){
                int lenAct = array[i].length();
                if(lenAct > maxLen){
                    maxLen = lenAct;
                }
            }
            anchoNodo = maxLen*fuente; //El ancho de un nodo en pixeles.
            int max_hojas = pot2(array.length-1); //El máximo número de nodos que habrá a lo ancho del árbol.
            double anchoArbol = anchoNodo*max_hojas; //El ancho del árbol.
            dibujaNodo(array, 1, anchoArbol/2, 0, anchoArbol);
        }
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

    /**
     * Dibuja el arreglo que representa al heap.
     */
    private void dibujaArreglo(String[] arreglo){
        arrayVista1.getChildren().clear();
        arrayVista2.getChildren().clear();
        for(int i=1; i<arreglo.length; i++){
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(anchoNodo);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setFill(Color.WHITE);
            Text texto = new Text(arreglo[i]);
            texto.setFont(Font.font(fuente));
            StackPane casilla = new StackPane();
            casilla.getChildren().addAll(rectangulo, texto);
            arrayVista1.getChildren().add(casilla);

            String numero2 = String.valueOf(i);
            Rectangle rectangulo2 = new Rectangle();
            rectangulo2.setWidth(anchoNodo);
            rectangulo2.setHeight(fuente*2);
            rectangulo2.setStroke(Color.BLACK);
            rectangulo2.setFill(Color.WHITE);
            Text texto2 = new Text(numero2);
            texto2.setFont(Font.font(fuente));
            StackPane casilla2 = new StackPane();
            casilla2.getChildren().addAll(rectangulo2, texto2);
            arrayVista2.getChildren().add(casilla2);
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
                if(ev[2] == 1){
                    nodoGS.setAttribute("ui.class", "visit1");
                }else{
                    nodoGS.setAttribute("ui.class", "visit2");
                }
            }else{
                strKey = "Ꝏ";
                nodoGS.setAttribute("ui.class", "negro");
            }
            nodoGS.setAttribute("ui.label", "v"+ev[0]+","+strKey);
        }
        //Pinta las aristas del árbol de rojo.
        for(Integer[] ari : actual.ariArbol){
            Edge ariGS1 = graphStream.getEdge(ari[0] + "," + ari[1]);
            Edge ariGS2 = graphStream.getEdge(ari[1] + "," + ari[0]);
            if(ariGS1 != null){
                ariGS1.setAttribute("ui.class", "arbol");
            }else{
                ariGS2.setAttribute("ui.class", "arbol");
            }
        }

        //Pinta las aristas predecesoras de rosa.
        for(Integer[] ari : actual.ariPred){
            Edge ariGS1 = graphStream.getEdge(ari[0] + "," + ari[1]);
            Edge ariGS2 = graphStream.getEdge(ari[1] + "," + ari[0]);
            if(ariGS1 != null){
                ariGS1.setAttribute("ui.class", "pred");
            }else{
                ariGS2.setAttribute("ui.class", "pred");
            }
        }
        //Dibuja las estructuras
        dibujaHeap(actual.estHeap);
        dibujaArreglo(actual.estHeap);
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
            Algoritmo.prim(grafica, vOrigen, exec);
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
            arrayVista2.setLayoutY(fuente*2);
            heapVista.setLayoutY(fuente*4);
            dibujaHeap(exec.getEstado(itAct).estHeap);
            dibujaArreglo(exec.getEstado(itAct).estHeap);
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


