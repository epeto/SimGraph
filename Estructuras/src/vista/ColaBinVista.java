
package vista;

import estructuras.ColaBinomial;
import java.util.Optional;
import javafx.scene.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;

public class ColaBinVista{
    Group grupo; //Grupo en el cual se agregan los elementos.
    Label cabecera;
    Button btnInserta; //Botón para insertar.
    Button btnFM; //Botón para encontrar mínimo.
    Button btnDM; //Botón para eliminar mínimo.
    Button btnDel; //Botón para eliminar.
    Button btnDK; //Botón para decrementar llave.
    Button btnME; //Botón para vaciar el heap.
    Button btnMerge; //Botón para fusionar.
    Button btnFuente; //Botón para cambiar fuente.

    TextField inInserta; //Entrada de datos para la operación inserta.
    Label outFM; //Salida para la operación encontrar mínimo.
    Label outDM; //Salida para la operación eliminar mínimo.
    TextField inDel; //Entrada para operación eliminar.
    Group nodos; //Va a contener los nodos de la cola binomial.
    ColaBinomial cola;
    double fuente; //Tamaño de letra.
    double alturaBtn; //altura de un botón.
    double anchoBtn; //ancho de un botón.
    double anchoNodo; //El ancho de un nodo en pixeles.
    int max_digit; //Máximo número de dígitos en cualquier número.
    ScrollPane desNodos; //Desplazador de nodos.

    //Constructor
    public ColaBinVista(Group g){
        grupo = g;
        fuente = 12;
        alturaBtn = fuente*2;
        anchoBtn = fuente*10;
        cola = new ColaBinomial();
    }

    /**
     * Inicializa los componentes de la ventana y los
     * coloca en su posición inicial.
     */
    public void inicia(){
        double margenSup = 10;
        int dy = 0;
        cabecera = new Label("Cola binomial");
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
        btnMerge = new Button("Fusión");
        btnMerge.setPrefSize(anchoBtn, alturaBtn);
        btnMerge.setLayoutX(0);
        btnMerge.setLayoutY(alturaBtn*dy + margenSup);
        btnMerge.setFont(Font.font(fuente));

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
        grupo.getChildren().add(btnMerge);
        grupo.getChildren().add(desNodos);
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

        btnDK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                decreaseKey();
            }
        });

        btnME.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeEmpty();
            }
        });

        btnMerge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                merge();
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
            dibujaCola();
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
     * Encuentra el máximo número de dígitos en un árbol binomial de forma recursiva.
     * @param nodo
     */
    private void maxDigitosNodo(ColaBinomial.NodoBin nodo){
        int digitActual = digitos(nodo.element);
        if(digitActual > max_digit){
            max_digit = digitActual;
        }
        if(nodo.leftChild != null){
            maxDigitosNodo(nodo.leftChild);
        }
        if(nodo.nextSibling != null){
            maxDigitosNodo(nodo.nextSibling);
        }
    }

    /**
     * Encuentra el máximo número de dígitos de cualquier número
     * en la cola binomial.
     */
    public void maxDigitosCola(){
        for(int i=0; i<cola.theTrees.length; i++){
            if(cola.theTrees[i] != null){
                maxDigitosNodo(cola.theTrees[i]);
            }
        }
    }

    /**
     * Dibuja una línea de un nodo hacia su padre.
     */
    public void dibujaLineasNodo(ColaBinomial.NodoBin nodo, int limiteIzq){
        if(nodo.parent != null){
            Line linea = new Line((nodo.columna+limiteIzq)*anchoNodo + anchoNodo/2,
                                  nodo.fila*fuente*3 + fuente,
                                  (nodo.parent.columna+limiteIzq)*anchoNodo + anchoNodo/2,
                                  nodo.parent.fila*fuente*3 + fuente);
            linea.setStrokeWidth(2);
            nodos.getChildren().add(linea);
        }
        if(nodo.leftChild != null){
            dibujaLineasNodo(nodo.leftChild, limiteIzq);
        }
        if(nodo.nextSibling != null){
            dibujaLineasNodo(nodo.nextSibling, limiteIzq);
        }
    }

    /**
     * Dibuja todas las líneas de la cola binomial.
     */
    public void dibujaLineasCola(){
        int li = 0;
        for(int i=0; i<cola.theTrees.length; i++){
            if(cola.theTrees[i] != null){
                dibujaLineasNodo(cola.theTrees[i], li);
                li = li + cola.theTrees[i].columna + 1;
            }
        }
    }

    /**
     * Dibuja un elipse para un nodo en la cola binomial.
     */
    public void dibujaElipsesNodo(ColaBinomial.NodoBin nodo, int limiteIzq){
        Ellipse elipse = new Ellipse();
        elipse.setRadiusX(anchoNodo/2);
        elipse.setRadiusY(fuente);
        elipse.setStroke(Color.BLACK);
        elipse.setFill(Color.AQUAMARINE);
        Text texto = new Text(String.valueOf(nodo.element));
        texto.setFont(Font.font(fuente));
        StackPane sp = new StackPane();
        sp.getChildren().addAll(elipse, texto);
        sp.setLayoutX((nodo.columna+limiteIzq)*anchoNodo);
        sp.setLayoutY(nodo.fila*fuente*3);
        nodos.getChildren().add(sp);

        if(nodo.leftChild != null){
            dibujaElipsesNodo(nodo.leftChild, limiteIzq);
        }

        if(nodo.nextSibling != null){
            dibujaElipsesNodo(nodo.nextSibling, limiteIzq);
        }
    }

    /**
     * Dibuja un elipse por cada nodo en la cola binomial.
     */
    public void dibujaElipsesCola(){
        int li = 0;
        for(int i=0; i<cola.theTrees.length; i++){
            if(cola.theTrees[i] != null){
                dibujaElipsesNodo(cola.theTrees[i], li);
                li = li + cola.theTrees[i].columna + 1;
            }
        }
    }

    /**
     * Dibuja la cola binomial.
     */
    public void dibujaCola(){
        nodos.getChildren().clear();
        if(!cola.isEmpty()){
            max_digit = 0;
            maxDigitosCola();
            anchoNodo = max_digit*fuente;
            cola.calculaFC();
            dibujaLineasCola();
            dibujaElipsesCola();
        }
    }

    /**
     * Inserta un elemento en la cola.
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
                if(cola.tabla.get(nuevo) == null){
                    cola.insert(nuevo);
                    dibujaCola();
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("El número ya está en la cola.");
                    alert.setContentText("La cola binomial no puede tener repeticiones.");
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
     * Encuentra el elemento más pequeño en la cola binomial.
     */
    public void findMin(){
        if(!cola.isEmpty()){
            int minimo = cola.findMin();
            outFM.setText(String.valueOf(minimo));
        }
    }

    /**
     * Elimina al elemento más pequeño del heap.
     */
    public void deleteMin(){
        if(!cola.isEmpty()){
            int minimo = cola.deleteMin();
            outDM.setText(String.valueOf(minimo));
            outFM.setText("");
            dibujaCola();
        }
    }

    /**
     * Elimina al elemento especificado del heap.
     */
    public void delete(){
        if(!cola.isEmpty() && !inDel.getText().isEmpty()){
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
                if(cola.tabla.get(nae) != null){
                    cola.delete(nae);
                    dibujaCola();
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("El número no se encuentra la cola.");
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
        dialog.setHeaderText("Decremente el valor de algún elemento en la cola binomial.");
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
                    if(cola.tabla.get(oldKey) != null){
                        if(cola.tabla.get(newKey) == null){
                            if(newKey <= oldKey){
                                cola.decreaseKey(oldKey, newKey);
                                outDM.setText("");
                                outFM.setText("");
                                dibujaCola();
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
                            alert.setContentText("El nuevo valor ya está en la cola y no admite repeticiones.");
                            alert.showAndWait();
                        }
                    }else{
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Ese valor no está en la cola.");
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
     * Vacía la cola.
     */
    public void makeEmpty(){
        cola.makeEmpty();
        outDM.setText("");
        outFM.setText("");
        dibujaCola();
    }

    /**
     * Crea otra cola binomial y la fusiona con esta cola.
     */
    public void merge(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Fusión");
        dialog.setHeaderText("Ingrese los números a fusionar en la cola binomial.");
        dialog.setContentText("Solo números separados por comas, sin espacio.");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            String[] numStr = result.get().split(",");
            int[] numeros = new int[numStr.length];
            for(int i=0; i<numStr.length; i++){
                numeros[i] = Integer.parseInt(numStr[i]);
            }
            boolean repetido = false;
            int numRep = 0;
            for(int i=0; i<numeros.length; i++){
                if(cola.tabla.get(numeros[i]) != null){
                    repetido = true;
                    numRep = numeros[i];
                    break;
                }
            }

            if(repetido){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("El número "+numRep+" ya está en la cola.");
                alert.setContentText("La cola binomial no puede tener repeticiones.");
                alert.showAndWait();
            }else{
                ColaBinomial cola2 = new ColaBinomial();
                for(int i=0; i<numeros.length; i++){
                    cola2.insert(numeros[i]);
                }
                cola.merge(cola2);
                dibujaCola();
            }
        }
    }
}
