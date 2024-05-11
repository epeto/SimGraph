
package vista;

import estructuras.DisjSets;
import estructuras.DSArbol;
import javafx.scene.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.ScrollPane;

public class DisjSetsVista{
    Group grupo; //Grupo en el cual se agregan los elementos.
    Label cabecera;
    Button btnMakeSet; //botón de make set.
    Button btnFind; //botón find set.
    Button btnUnion; //botón unión.
    Button btnFuente; //botón para cambiar fuente.
    TextField inMakeSet; //entrada de make set.
    TextField inFind; //entrada de find.
    Label outFind; //Salida de find set.
    TextField inUnion1; //entrada 1 de unión.
    TextField inUnion2; //entrada 2 de unión.
    CheckBox unionTam; //checkbox de unión por tamaño.
    CheckBox pathComp; //checkbox de compresión de trayectorias.
    Group nodos; //Va a contener los nodos de los árboles.
    DisjSets conjuntos; //Estructura de conjuntos ajenos con un arreglo.
    DSArbol bosque; //Estructura de conjuntos ajenos con árboles.
    double fuente; //Tamaño de letra.
    double alturaBtn; //altura de un botón.
    double anchoBtn; //ancho de un botón.
    double anchoNodo; //El ancho de un nodo en pixeles.
    int max_digit; //Máximo número de dígitos en cualquier número.
    HBox arrayVista; //Contendrá los rectángulos del arreglo ligado a la estructura.
    HBox arrayVista2; //Contiene números de 0 a n-1.
    VBox arrays; //Contiene ambos arreglos (arrayVista, arrayVista2)
    ScrollPane desArray; //Desplazador de arreglos.
    ScrollPane desNodos; //Desplazador de nodos.

    //Constructor
    public DisjSetsVista(Group g){
        conjuntos = new DisjSets(8); //Se crea un conjunto inicial con 8 elementos.
        grupo = g;
        fuente = 12;
        alturaBtn = fuente*2;
        anchoBtn = fuente*9;
    }

    /**
     * Inicializa los componentes de la ventana y los
     * coloca en su posición inicial.
     */
    public void inicia(){
        double margenSup = 10;
        int dy = 0;
        cabecera = new Label("Conjuntos ajenos");
        cabecera.setFont(Font.font(25));
        cabecera.setLayoutX(0);
        cabecera.setLayoutY(0);

        dy++;
        btnMakeSet = new Button("Crea\nconjuntos");
        btnMakeSet.setPrefSize(anchoBtn, alturaBtn*2);
        btnMakeSet.setLayoutX(0);
        btnMakeSet.setLayoutY(alturaBtn*dy+margenSup);
        btnMakeSet.setFont(Font.font(fuente));

        inMakeSet = new TextField();
        inMakeSet.setPrefSize(anchoBtn, alturaBtn*2);
        inMakeSet.setLayoutX(anchoBtn);
        inMakeSet.setLayoutY(alturaBtn*dy+margenSup);
        inMakeSet.setFont(Font.font(fuente));

        dy+=2;
        btnFind = new Button("Encuentra\nconjunto");
        btnFind.setPrefSize(anchoBtn, alturaBtn*2);
        btnFind.setLayoutX(0);
        btnFind.setLayoutY(alturaBtn*dy+margenSup);
        btnFind.setFont(Font.font(fuente));

        inFind = new TextField();
        inFind.setPrefSize(anchoBtn, alturaBtn);
        inFind.setLayoutX(anchoBtn);
        inFind.setLayoutY(alturaBtn*dy+margenSup);
        inFind.setFont(Font.font(fuente));

        outFind = new Label();
        outFind.setLayoutX(anchoBtn);
        outFind.setLayoutY(alturaBtn*(dy+1) + margenSup);
        outFind.setFont(Font.font(fuente));

        dy+=2;
        btnUnion = new Button("Unión");
        btnUnion.setPrefSize(anchoBtn, alturaBtn);
        btnUnion.setLayoutX(0);
        btnUnion.setLayoutY(alturaBtn*dy+margenSup);
        btnUnion.setFont(Font.font(fuente));

        inUnion1 = new TextField();
        inUnion1.setPrefSize(anchoBtn/2, alturaBtn);
        inUnion1.setLayoutX(anchoBtn);
        inUnion1.setLayoutY(alturaBtn*dy+margenSup);
        inUnion1.setFont(Font.font(fuente));

        inUnion2 = new TextField();
        inUnion2.setPrefSize(anchoBtn/2, alturaBtn);
        inUnion2.setLayoutX(anchoBtn*1.5);
        inUnion2.setLayoutY(alturaBtn*dy+margenSup);
        inUnion2.setFont(Font.font(fuente));

        dy++;
        unionTam = new CheckBox("Unión por\ntamaño");
        unionTam.setLayoutY(alturaBtn*dy+margenSup);
        unionTam.setPrefHeight(alturaBtn*2);

        dy+=2;
        pathComp = new CheckBox("Compresión de\ntrayectoria");
        pathComp.setPrefHeight(alturaBtn*2);
        pathComp.setLayoutY(alturaBtn*dy+margenSup);

        dy+=2;
        btnFuente = new Button("Fuente");
        btnFuente.setPrefSize(anchoBtn, alturaBtn);
        btnFuente.setLayoutX(0);
        btnFuente.setLayoutY(alturaBtn*dy+margenSup);
        btnFuente.setFont(Font.font(fuente));

        nodos = new Group();

        desNodos = new ScrollPane(nodos);
        desNodos.setLayoutX(anchoBtn*2+10);
        desNodos.setLayoutY(alturaBtn*3+margenSup);
        desNodos.setPrefSize(500, 500);

        arrayVista = new HBox();
        arrayVista2 = new HBox();
        arrays = new VBox();
        arrays.getChildren().addAll(arrayVista, arrayVista2);

        desArray = new ScrollPane(arrays);
        desArray.setLayoutX(anchoBtn*2+10);
        desArray.setLayoutY(margenSup);
        desArray.setPrefSize(500, fuente*5);

        grupo.getChildren().add(cabecera);
        grupo.getChildren().add(btnMakeSet);
        grupo.getChildren().add(inMakeSet);
        grupo.getChildren().add(btnFind);
        grupo.getChildren().add(inFind);
        grupo.getChildren().add(outFind);
        grupo.getChildren().add(btnUnion);
        grupo.getChildren().add(inUnion1);
        grupo.getChildren().add(inUnion2);
        grupo.getChildren().add(unionTam);
        grupo.getChildren().add(pathComp);
        grupo.getChildren().add(desNodos);
        grupo.getChildren().add(desArray);
        grupo.getChildren().add(btnFuente);

        btnMakeSet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeSet();
            }
        });

        btnFind.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findSet();
            }
        });

        btnUnion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                union();
            }
        });

        btnFuente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cambiaFuente();
            }
        });
    }

    /**
     * Cambia el tamaño de fuente.
     */
    private void cambiaFuente(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Fuente");
        dialog.setHeaderText("Tamaño de letra actual: "+fuente);
        dialog.setContentText("Nuevo tamaño: ");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            fuente = Double.parseDouble(result.get());
            desArray.setLayoutY(0);
            desArray.setPrefHeight(fuente*5);
            desNodos.setLayoutY(fuente*5);
            dibujaConjuntos();
        }
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
    public void calculaCoordenadas(){
        bosque.alturaBosque();
        bosque.anchoBosque();
        int sumaAlt = 0;
        for(DSArbol.NodoDS raiz : bosque.raices){
            calculaCooNodo(raiz, sumaAlt, 0);
            sumaAlt = sumaAlt+raiz.alto+1;
        }
    }

    /**
     * Dibuja las líneas del bosque.
     */
    public void dibujaLineas(){
        for(int i=0; i<bosque.arreglo.length; i++){
            DSArbol.NodoDS nodoHijo = bosque.arreglo[i];
            DSArbol.NodoDS nodoPadre = nodoHijo.parent;
            if(nodoPadre != null){
                Line linea = new Line(nodoHijo.x + anchoNodo/2,
                                      nodoHijo.y + fuente,
                                      nodoPadre.x + anchoNodo/2,
                                      nodoPadre.y + fuente);
                linea.setStrokeWidth(2);
                nodos.getChildren().add(linea);
            }
        }
    }

    /**
     * Dibuja los elipses del bosque.
     */
    public void dibujaElipses(){
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
            nodos.getChildren().add(sp);
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

    /**
     * Dibuja el arreglo interno de la estructura.
     */
    private void dibujaArreglo(){
        arrayVista.getChildren().clear();
        arrayVista2.getChildren().clear();
        for(int i=0; i<conjuntos.s.length; i++){
            String numero = String.valueOf(conjuntos.s[i]);
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(max_digit*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setFill(Color.WHITE);
            Text texto = new Text(numero);
            texto.setFont(Font.font(fuente));
            StackPane casilla = new StackPane();
            casilla.getChildren().addAll(rectangulo, texto);
            arrayVista.getChildren().add(casilla);

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
     * Dibuja los conjuntos ajenos.
     */
    public void dibujaConjuntos(){
        max_digit = Math.max(digitos(conjuntos.s.length-1), 2);
        anchoNodo = max_digit*fuente;
        dibujaArreglo();
        bosque = new DSArbol(conjuntos.s);
        nodos.getChildren().clear();
        calculaCoordenadas();
        dibujaLineas();
        dibujaElipses();
    }

    /**
     * Crea los conjuntos.
     */
    public void makeSet(){
        String texto = inMakeSet.getText();
        if(!texto.isEmpty()){
            int tam = Integer.parseInt(texto);
            conjuntos.makeSet(tam);
            dibujaConjuntos();
            inMakeSet.setText("");
        }
    }

    /**
     * Encuentra al representante de un conjunto.
     */
    public void findSet(){
        String texto = inFind.getText();
        if(!texto.isEmpty()){
            int numero = Integer.parseInt(texto);
            if(0 <= numero && numero < conjuntos.s.length){
                int raiz = 0;
                if(pathComp.isSelected()){
                    raiz = conjuntos.findSetPC(numero);
                }else{
                    raiz = conjuntos.findSet(numero);
                }
                outFind.setText(String.valueOf(raiz));
                dibujaConjuntos();
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Número fuera de rango.");
                alert.setContentText("El número debe estar entre 0 y "+(conjuntos.s.length-1));
                alert.showAndWait();
            }
            inFind.setText("");
        }
    }

    /**
     * Realiza unión de dos conjuntos.
     */
    public void union(){
        String texto1 = inUnion1.getText();
        String texto2 = inUnion2.getText();
        if(!texto1.isEmpty() && !texto2.isEmpty()){
            int n1 = Integer.parseInt(texto1);
            int n2 = Integer.parseInt(texto2);
            if(0 <= n1 && 0 <= n2 && n1 < conjuntos.s.length && n2 < conjuntos.s.length){
                if(unionTam.isSelected()){
                    conjuntos.smartUnion(n1, n2);
                }else{
                    conjuntos.union(n1, n2);
                }
                dibujaConjuntos();
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Número fuera de rango.");
                alert.setContentText("Los números debe estar entre 0 y "+(conjuntos.s.length-1));
                alert.showAndWait();
            }
            inUnion1.setText("");
            inUnion2.setText("");
        }
    }
}
