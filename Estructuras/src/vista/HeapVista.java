
package vista;

import java.util.Optional;
import estructuras.Heap;
import javafx.scene.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ScrollPane;

public class HeapVista {

    Group grupo; //Grupo en el cual se agregan los elementos.
    Label cabecera;
    Button btnInserta; //Botón para insertar.
    Button btnFM; //Botón para encontrar mínimo.
    Button btnDM; //Botón para eliminar mínimo.
    Button btnDel; //Botón para eliminar.
    Button btnDK; //Botón para decrementar llave.
    Button btnME; //Botón para vaciar el heap.
    Button btnFuente; //Botón para cambiar fuente.

    TextField inInserta; //Entrada de datos para la operación inserta.
    Label outFM; //Salida para la operación encontrar mínimo.
    Label outDM; //Salida para la operación eliminar mínimo.
    TextField inDel; //Entrada para operación eliminar.
    Group nodos; //Va a contener los nodos del heap.
    Heap heap;
    double fuente; //Tamaño de letra.
    double alturaBtn; //altura de un botón.
    double anchoBtn; //ancho de un botón.
    double anchoNodo; //El ancho de un nodo en pixeles.
    int max_digit; //Máximo número de dígitos en cualquier número.
    HBox arrayVista; //Contendrá los rectángulos del arreglo ligado al heap.
    ScrollPane desArray; //desplazador del array.
    ScrollPane desNodos; //desplazador de los nodos.

    //Constructor
    public HeapVista(Group g){
        grupo = g;
        fuente = 12;
        alturaBtn = fuente*2;
        anchoBtn = fuente*10;
        heap = new Heap();
    }

    /**
     * Inicializa los componentes de la ventana y los
     * coloca en su posición inicial.
     */
    public void inicia(){
        double margenSup = 10;
        int dy = 0;
        cabecera = new Label("Heap binario");
        cabecera.setFont(Font.font(25));
        cabecera.setLayoutX(0);
        cabecera.setLayoutY(0);

        dy++;
        btnInserta = new Button("Inserta");
        btnInserta.setPrefSize(anchoBtn, alturaBtn);
        btnInserta.setLayoutX(0);
        btnInserta.setLayoutY(alturaBtn*dy+margenSup);
        btnInserta.setFont(Font.font(fuente));

        inInserta = new TextField();
        inInserta.setPrefSize(anchoBtn, alturaBtn);
        inInserta.setLayoutX(anchoBtn);
        inInserta.setLayoutY(alturaBtn*dy+margenSup);
        inInserta.setFont(Font.font(fuente));

        dy++;
        btnFM = new Button("Encuentra\nmínimo");
        btnFM.setPrefSize(anchoBtn, alturaBtn*2);
        btnFM.setLayoutX(0);
        btnFM.setLayoutY(alturaBtn*dy + margenSup);
        btnFM.setFont(Font.font(fuente));

        outFM = new Label();
        outFM.setLayoutX(anchoBtn);
        outFM.setLayoutY(alturaBtn*dy + margenSup);
        outFM.setFont(Font.font(fuente));

        dy+=2;
        btnDM = new Button("Elimina\nmínimo");
        btnDM.setPrefSize(anchoBtn, alturaBtn*2);
        btnDM.setLayoutX(0);
        btnDM.setLayoutY(alturaBtn*dy + margenSup);
        btnDM.setFont(Font.font(fuente));

        outDM = new Label();
        outDM.setLayoutX(anchoBtn);
        outDM.setLayoutY(alturaBtn*dy + margenSup);
        outDM.setFont(Font.font(fuente));

        dy+=2;
        btnDel = new Button("Eliminar");
        btnDel.setPrefSize(anchoBtn, alturaBtn);
        btnDel.setLayoutX(0);
        btnDel.setLayoutY(alturaBtn*dy + margenSup);
        btnDel.setFont(Font.font(fuente));

        inDel = new TextField();
        inDel.setPrefSize(anchoBtn, alturaBtn);
        inDel.setLayoutX(anchoBtn);
        inDel.setLayoutY(alturaBtn*dy + margenSup);
        inDel.setFont(Font.font(fuente));

        dy++;
        btnDK = new Button("Decrementa\nllave");
        btnDK.setPrefSize(anchoBtn, alturaBtn*2);
        btnDK.setLayoutX(0);
        btnDK.setLayoutY(alturaBtn*dy + margenSup);
        btnDK.setFont(Font.font(fuente));

        dy+=2;
        btnME = new Button("Vaciar");
        btnME.setPrefSize(anchoBtn, alturaBtn);
        btnME.setLayoutX(0);
        btnME.setLayoutY(alturaBtn*dy + margenSup);
        btnME.setFont(Font.font(fuente));

        dy++;
        btnFuente = new Button("Fuente");
        btnFuente.setPrefSize(anchoBtn, alturaBtn);
        btnFuente.setLayoutX(0);
        btnFuente.setLayoutY(alturaBtn*dy + margenSup);
        btnFuente.setFont(Font.font(fuente));

        nodos = new Group();

        desNodos = new ScrollPane(nodos);
        desNodos.setLayoutX(anchoBtn*2+10);
        desNodos.setLayoutY(alturaBtn*2+margenSup);
        desNodos.setPrefSize(500, 500);

        arrayVista = new HBox();

        desArray = new ScrollPane(arrayVista);
        desArray.setLayoutX(anchoBtn*2+10);
        desArray.setLayoutY(margenSup);
        desArray.setPrefSize(500, alturaBtn);

        grupo.getChildren().add(cabecera);
        grupo.getChildren().add(btnInserta);
        grupo.getChildren().add(inInserta);
        grupo.getChildren().add(btnFM);
        grupo.getChildren().add(outFM);
        grupo.getChildren().add(btnDM);
        grupo.getChildren().add(outDM);
        grupo.getChildren().add(btnDel);
        grupo.getChildren().add(inDel);
        grupo.getChildren().add(btnDK);
        grupo.getChildren().add(btnME);
        grupo.getChildren().add(desNodos);
        grupo.getChildren().add(desArray);
        grupo.getChildren().add(btnFuente);

        btnInserta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insert();
            }
        });

        btnFM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                findMin();
            }
        });

        btnDM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteMin();
            }
        });

        btnDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                delete();
            }
        });

        btnME.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeEmpty();
            }
        });

        btnDK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                decreaseKey();
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
     * Dibuja el arreglo interno del heap.
     */
    private void dibujaArreglo(){
        arrayVista.getChildren().clear();
        for(int i=1; i<=heap.size; i++){
            String numero = String.valueOf(heap.array[i]);
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(numero.length()*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setFill(Color.WHITE);
            Text texto = new Text(numero);
            texto.setFont(Font.font(fuente));
            StackPane casilla = new StackPane();
            casilla.getChildren().addAll(rectangulo, texto);
            arrayVista.getChildren().add(casilla);
        }
    }

    /**
     * Dibuja el árbol que representa al heap.
     */
    private void dibujaHeap(){
        dibujaArreglo();
        nodos.getChildren().clear();
        if(!heap.isEmpty()){
            //Se encuentra la máxima cantidad de dígitos que hay en cualquier número en el heap.
            max_digit = 0;
            for(int i=1; i<=heap.size; i++){
                int digAct = digitos(heap.array[i]);
                if(digAct > max_digit){
                    max_digit = digAct;
                }
            }
            anchoNodo = max_digit*fuente; //El ancho de un nodo en pixeles.
            int max_hojas = pot2(heap.size); //El máximo número de nodos que habrá a lo ancho del árbol.
            double anchoArbol = anchoNodo*max_hojas; //El ancho del árbol.
            dibujaNodo(1, anchoArbol/2, 0, anchoArbol);
        }
    }

    /**
     * Dibuja los nodos del heap de manera recursiva a partir de cierto nodo.
     * @param indice posición en el arreglo en el heap de este nodo.
     * @param cx coordenada x donde se dibujará este nodo.
     * @param cy coordenada y donde se dibujará este nodo.
     * @param anchoArbol ancho de este subárbol en pixeles.
     */
    private void dibujaNodo(int indice, double cx, double cy, double anchoArbol){
        int indiceHI = indice*2; //Índice del hijo izquierdo.
        int indiceHD = indice*2+1; //Índice del hijo derecho.
        double xHI = cx - anchoArbol/4.0; //Posición en x del hijo izquierdo.
        double xHD = cx + anchoArbol/4.0; //Posición en x del hijo derecho.
        double yH = cy + fuente*3; //Posición en y de ambos hijos.
        if(indiceHI <= heap.size){
            //Línea que va hacia el hijo izquierdo.
            Line lineaHI = new Line(cx+anchoNodo/2, cy+fuente, xHI+anchoNodo/2, yH+fuente);
            lineaHI.setStrokeWidth(2);
            nodos.getChildren().add(lineaHI);
        }
        if(indiceHD <= heap.size){
            //Línea que va hacia el hijo derecho.
            Line lineaHD = new Line(cx+anchoNodo/2, cy+fuente, xHD+anchoNodo/2, yH+fuente);
            lineaHD.setStrokeWidth(2);
            nodos.getChildren().add(lineaHD);
        }
        Ellipse elipse = new Ellipse();
        elipse.setRadiusX(anchoNodo/2);
        elipse.setRadiusY(fuente);
        elipse.setStroke(Color.BLACK);
        elipse.setFill(Color.AQUAMARINE);
        Text texto = new Text(String.valueOf(heap.array[indice]));
        texto.setFont(Font.font(fuente));
        StackPane nodo = new StackPane();
        nodo.getChildren().addAll(elipse, texto);
        nodo.setLayoutX(cx);
        nodo.setLayoutY(cy);
        nodos.getChildren().add(nodo);
        if(indiceHI <= heap.size){
            dibujaNodo(indiceHI, xHI, yH, anchoArbol/2);
        }
        if(indiceHD <= heap.size){
            dibujaNodo(indiceHD, xHD, yH, anchoArbol/2);
        }
    }

    /**
     * Inserta un elemento en el heap.
     */
    public void insert(){
        String texto = inInserta.getText();
        if(!texto.isEmpty()){
            //Servirá para comprobar que todos los caracteres de la entrada sean números.
            boolean allDig = true;
            for(int i=0; i<texto.length(); i++){
                if(!Character.isDigit(texto.charAt(i)) && texto.charAt(i) != '-'){
                    allDig = false;
                    break;
                }
            }
            if(allDig){
                int nuevo = Integer.parseInt(texto);
                if(heap.tabla.get(nuevo) == null){
                    heap.insert(nuevo);
                    dibujaHeap();
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("El número ya está en el heap.");
                    alert.setContentText("El heap no puede tener repeticiones.");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Entrada de datos incorrecta.");
                alert.setContentText("Al menos un caracter no es numérico.");
                alert.showAndWait();
            }
            inInserta.setText("");
            outFM.setText("");
            outDM.setText("");
        }
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
            desArray.setPrefHeight(fuente*3);
            desNodos.setLayoutY(fuente*3);
            dibujaHeap();
        }
    }

    /**
     * Encuentra el elemento más pequeño en el heap (la raíz).
     */
    public void findMin(){
        if(!heap.isEmpty()){
            int minimo = heap.findMin();
            outFM.setText(String.valueOf(minimo));
        }
    }

    /**
     * Elimina al elemento más pequeño del heap.
     */
    public void deleteMin(){
        if(!heap.isEmpty()){
            int minimo = heap.deleteMin();
            outDM.setText(String.valueOf(minimo));
            outFM.setText("");
            dibujaHeap();
        }
    }

    /**
     * Elimina al elemento especificado del heap.
     */
    public void delete(){
        if(!heap.isEmpty() && !inDel.getText().isEmpty()){
            String texto = inDel.getText();
            //Servirá para comprobar que todos los caracteres de la entrada sean números.
            boolean allDig = true;
            for(int i=0; i<texto.length(); i++){
                if(!Character.isDigit(texto.charAt(i)) && texto.charAt(i) != '-'){
                    allDig = false;
                    break;
                }
            }
            if(allDig){
                int nae = Integer.parseInt(texto); //Número a eliminar
                if(heap.tabla.get(nae) != null){
                    heap.delete(nae);
                    dibujaHeap();
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("El número no se encuentra en el heap.");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Entrada de datos incorrecta.");
                alert.setContentText("Al menos un caracter no es numérico.");
                alert.showAndWait();
            }
        }
        inDel.setText("");
    }

    /**
     * Decrementa un valor en el heap.
     */
    public void decreaseKey(){
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Decrementar llave");
        dialog.setHeaderText("Decremente el valor de algún elemento del heap.");
        //Agrega los tipos de botones.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        // Agrega una cuadrícula con los elementos.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField viejaLlave = new TextField();
        TextField nuevaLlave = new TextField();
        grid.add(new Label("Valor actual:"), 0, 0);
        grid.add(viejaLlave, 1, 0);
        grid.add(new Label("Valor nuevo:"), 0, 1);
        grid.add(nuevaLlave, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(viejaLlave.getText(), nuevaLlave.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();

        //Si eligió el botón OK.
        if(result.isPresent()){
            Pair<String,String> par = result.get();
            String oktext = par.getKey();
            String nktext = par.getValue();
            if(!oktext.isEmpty() && !nktext.isEmpty()){
                //Servirá para comprobar que todos los caracteres de la entrada sean números.
                boolean allDig = true;
                for(int i=0; i<oktext.length(); i++){
                    if(!Character.isDigit(oktext.charAt(i)) && oktext.charAt(i) != '-'){
                        allDig = false;
                        break;
                    }
                }
                for(int i=0; i<nktext.length(); i++){
                    if(!Character.isDigit(nktext.charAt(i)) && nktext.charAt(i) != '-'){
                        allDig = false;
                        break;
                    }
                }
                if(allDig){
                    int oldKey = Integer.parseInt(oktext);
                    int newKey = Integer.parseInt(nktext);
                    if(heap.tabla.get(oldKey) != null){
                        if(heap.tabla.get(newKey) == null){
                            if(newKey <= oldKey){
                                heap.decreaseKey(oldKey, newKey);
                                outDM.setText("");
                                outFM.setText("");
                                dibujaHeap();
                            }else{
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("El nuevo valor debe ser menor que el actual.");
                                alert.showAndWait();
                            }
                        }else{
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Valor repetido.");
                            alert.setContentText("El nuevo valor ya está en el heap y no admite repeticiones.");
                            alert.showAndWait();
                        }
                    }else{
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Ese valor no está en el heap.");
                        alert.showAndWait();
                    }
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Entrada de datos incorrecta.");
                    alert.setContentText("Al menos un caracter no es numérico.");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Vacía el heap.
     */
    public void makeEmpty(){
        heap.makeEmpty();
        outDM.setText("");
        outFM.setText("");
        dibujaHeap();
    }
}
