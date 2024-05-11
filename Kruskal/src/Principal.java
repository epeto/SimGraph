
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
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
    Label labSA; //Etiqueta de la siguiente arista.
    Group arbolVista; //Contiene los nodos del bosque de conjuntos ajenos.
    HBox arrayVista1; //Contendrá los rectángulos del arreglo de conjuntos ajenos.
    HBox arrayVista2; //Contiene números de 0 a n-1.
    double anchoNodo;
    Group superGrupo; //Contiene los arreglos, la etiqueta de la arista y el árbol.
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

        labSA = new Label();
        labSA.setFont(Font.font(fuente));

        arrayVista1 = new HBox();
        arrayVista1.setLayoutX(fuente*9);

        arrayVista2 = new HBox();
        arrayVista2.setLayoutX(fuente*9);
        arrayVista2.setLayoutY(fuente*2);

        arbolVista = new Group();
        arbolVista.setLayoutY(fuente*4);

        superGrupo = new Group(labSA, arrayVista1, arrayVista2, arbolVista);

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
        escenario.setTitle("Kruskal-MST");
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
        styleSheet = "edge {size: 5px; text-size: "+fuente+";}"
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
        //Se agregan todos los vértices a "graphStream".
        for(Vertice v : grafica.vertices){
            graphStream.addNode(String.valueOf(v.id));
            Node nodoV = graphStream.getNode(String.valueOf(v.id));
            nodoV.setAttribute("ui.label", v.toString());
        }

        //Se agregan las aristas.
        for(Arista ari : grafica.aristas){
            String nombreAri = ari.vi.id+","+ari.vf.id;
            graphStream.addEdge(nombreAri, String.valueOf(ari.vi.id), String.valueOf(ari.vf.id));
            Edge ariGS = graphStream.getEdge(nombreAri);
            ariGS.setAttribute("ui.label", String.valueOf(ari.peso));
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
     * Calcula el número de dígitos en el número n.
     * @param n número del cual se cuentan los dígitos.
     * @return número de dígitos en n.
     */
    private int digitos(int n){
        if(n == 0)
            return 1;
        int d = 0;
        if(n < 0){
            d++;
            n = Math.abs(n);
        }
        while(n > 0){
            n /= 10;
            d++;
        }
        return d;
    }

    private void dibujaArreglo(int[] disjS){
        arrayVista1.getChildren().clear();
        arrayVista2.getChildren().clear();
        int max_digit = digitos(disjS.length-1);
        for(int i=0; i<disjS.length; i++){
            String numero = String.valueOf(disjS[i]);
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(max_digit*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setFill(Color.WHITE);
            Text texto = new Text(numero);
            texto.setFont(Font.font(fuente));
            StackPane casilla = new StackPane();
            casilla.getChildren().addAll(rectangulo, texto);
            arrayVista1.getChildren().add(casilla);

            String numero2 = String.valueOf(i);
            Rectangle rectangulo2 = new Rectangle();
            rectangulo2.setWidth(max_digit*fuente);
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
     * Dibuja las líneas del bosque.
     */
    public void dibujaLineas(DSArbol bosque){
        for(int i=0; i<bosque.arreglo.length; i++){
            DSArbol.NodoDS nodoHijo = bosque.arreglo[i];
            DSArbol.NodoDS nodoPadre = nodoHijo.parent;
            if(nodoPadre != null){
                Line linea = new Line(nodoHijo.x + anchoNodo/2,
                                      nodoHijo.y + fuente,
                                      nodoPadre.x + anchoNodo/2,
                                      nodoPadre.y + fuente);
                linea.setStrokeWidth(4);
                arbolVista.getChildren().add(linea);
            }
        }
    }

    /**
     * Dibuja los elipses del bosque.
     */
    public void dibujaElipses(DSArbol bosque){
        for(int i=0; i<bosque.arreglo.length; i++){
            DSArbol.NodoDS nodoHijo = bosque.arreglo[i];
            Ellipse elipse = new Ellipse();
            elipse.setRadiusX(anchoNodo/2);
            elipse.setRadiusY(fuente);
            elipse.setStroke(Color.BLACK);
            elipse.setFill(Color.AQUAMARINE);
            Text texto = new Text(String.valueOf(nodoHijo.element));
            texto.setFont(Font.font(fuente));
            StackPane sp = new StackPane();
            sp.getChildren().addAll(elipse, texto);
            sp.setLayoutX(nodoHijo.x);
            sp.setLayoutY(nodoHijo.y);
            arbolVista.getChildren().add(sp);
        }
    }

    /**
     * Dibuja los conjuntos ajenos.
     */
    public void dibujaConjuntos(DSArbol bosque){
        arbolVista.getChildren().clear();
        dibujaLineas(bosque);
        dibujaElipses(bosque);
    }

    /**
     * Calcula las coordenadas de cada nodo en un árbol de manera recursiva.
     * @param lsup límite superior.
     * @param lizq límite izquierdo.
     */
    private void calculaCooNodo(DSArbol.NodoDS nodo, int lsup, int lizq){
        nodo.x = lizq*anchoNodo + nodo.ancho*anchoNodo/2;
        nodo.y = lsup*fuente*3;
        if(!nodo.hijos.isEmpty()){
            int sumaLi = lizq;
            for(DSArbol.NodoDS h : nodo.hijos){
                calculaCooNodo(h, lsup+1, sumaLi);
                sumaLi += h.ancho;
            }
        }
    }

    /**
     * Calcula las coordenadas de cada nodo en el bosque.
     */
    public void calculaCoordenadas(DSArbol bosque){
        bosque.alturaBosque();
        bosque.anchoBosque();
        int sumaAlt = 0;
        for(DSArbol.NodoDS raiz : bosque.raices){
            calculaCooNodo(raiz, sumaAlt, 0);
            sumaAlt = sumaAlt+raiz.alto+1;
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

        //Coloca la siguiente arista en la etiqueta labSA.
        String saTexto = "";
        saTexto = saTexto + "(v"+actual.nextAri[0];
        saTexto = saTexto + ", v"+actual.nextAri[1];
        saTexto = saTexto + ", "+actual.nextAri[2]+")";
        labSA.setText(saTexto);
        //Pinta las aristas visitadas de rojo.
        for(Integer[] ari : actual.ariBosque){
            Edge ariGS = graphStream.getEdge(ari[0] + "," + ari[1]);
            ariGS.setAttribute("ui.class", "arbol");
        }
        //Dibuja las estructuras
        dibujaArreglo(actual.disjsets);
        dibujaConjuntos(actual.arbol);
    }

    /**
     * Ejecuta el algoritmo y guarda la ejecución.
     */
    public void inicia(){
        LinkedList<String> opciones = new LinkedList<>(); //Las opciones del choice dialog.
        opciones.add("Ninguna");
        opciones.add("Compresión de trayectoria");
        opciones.add("Unión por tamaño");
        opciones.add("Compresión y unión");

        ChoiceDialog<String> cdialog = new ChoiceDialog<>("Ninguna", opciones);
        cdialog.setTitle("Optimizaciones");
        cdialog.setHeaderText("Elija las optimizaciones de los conjuntos ajenos");
        Optional<String> result = cdialog.showAndWait();
        if(result.isPresent()){
            boolean ct = false; //Opción de compresión de trayectoria.
            boolean ut = false; //Opción de unión por tamaño.
            switch(result.get()){
                case "Compresión de trayectoria":
                ct = true;
                break;
                case "Unión por tamaño":
                ut = true;
                break;
                case "Compresión y unión":
                ct = true;
                ut = true;
                break;
            }
            grupo.getChildren().clear();
            grupo.getChildren().addAll(btnAnt, btnSig, btnFuente, deslizador);
            btnAnt.setDisable(true); //Deshabilita el botón anterior.
            anchoNodo = digitos(grafica.getOrden())*fuente;
            exec = new Ejecucion();
            Algoritmo.kruskal(grafica, ct, ut, exec);
            //Se calculan las coordenadas de cada nodo en el bosque.
            for(Estado est : exec.estados){
                calculaCoordenadas(est.arbol);
            }
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
            anchoNodo = digitos(grafica.getOrden())*fuente;
            setStyle();
            graphStream.setAttribute("ui.stylesheet", styleSheet);

            labSA.setFont(Font.font(fuente));

            arrayVista1.setLayoutX(fuente*9);

            arrayVista2.setLayoutX(fuente*9);
            arrayVista2.setLayoutY(fuente*2);

            arbolVista.setLayoutY(fuente*4);
            for(Estado est : exec.estados){
                calculaCoordenadas(est.arbol);
            }
            dibujaArreglo(exec.getEstado(itAct).disjsets);
            dibujaConjuntos(exec.getEstado(itAct).arbol);
        }
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


