
package vista;

import javafx.scene.Group;
import estructuras.Cola;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;

public class ColaVista {

    Group grupo; //Grupo en el cual se agregan los elementos.
    Label cabecera;
    Button btnEnqueue; //Botón para encolar
    Button btnDequeue; //Botón para desencolar
    Button btnFuente; //Botón para cambiar fuente
    TextField inEnqueue; //Entrada de datos para la operación enqueue.
    Label outDequeue; //Salida para la operación dequeue.
    HBox bloques; //Va a contener los rectángulos de la cola.
    Cola<String> cola;
    double fuente;
    double alturaBtn; //altura de un botón
    double anchoBtn; //ancho de un botón
    ScrollPane desBloques; //desplazador de bloques

    //Constructor
    public ColaVista(Group g){
        fuente = 12;
        grupo = g;
        alturaBtn = fuente*2;
        anchoBtn = fuente*10;
        cola = new Cola<>();
    }

    /**
     * Inicializa todas las variables y las coloca en su posición inicial.
     */
    public void inicia(){
        double margenSup = 10;
        int dy=0;
        cabecera = new Label("Cola");
        cabecera.setFont(Font.font(25));
        cabecera.setPrefSize(70, 40);
        cabecera.setLayoutX(0);
        cabecera.setLayoutY(0);

        dy++;
        btnEnqueue = new Button("Encolar");
        btnEnqueue.setPrefSize(anchoBtn, alturaBtn);
        btnEnqueue.setLayoutX(0);
        btnEnqueue.setLayoutY(alturaBtn*dy+margenSup);
        btnEnqueue.setFont(Font.font(fuente));

        inEnqueue = new TextField();
        inEnqueue.setPrefSize(anchoBtn, alturaBtn);
        inEnqueue.setLayoutX(anchoBtn);
        inEnqueue.setLayoutY(alturaBtn*dy+margenSup);
        inEnqueue.setFont(Font.font(fuente));

        dy++;
        btnDequeue = new Button("Desencolar");
        btnDequeue.setPrefSize(anchoBtn, alturaBtn);
        btnDequeue.setLayoutX(0);
        btnDequeue.setLayoutY(alturaBtn*dy + margenSup);
        btnDequeue.setFont(Font.font(fuente));

        outDequeue = new Label();
        outDequeue.setPrefHeight(alturaBtn);
        outDequeue.setLayoutX(anchoBtn);
        outDequeue.setLayoutY(alturaBtn*dy + margenSup);
        outDequeue.setFont(Font.font(fuente));

        dy++;
        btnFuente = new Button("Fuente");
        btnFuente.setPrefSize(anchoBtn, alturaBtn);
        btnFuente.setLayoutX(0);
        btnFuente.setLayoutY(alturaBtn*dy + margenSup);
        btnFuente.setFont(Font.font(fuente));

        dy+=2;
        bloques = new HBox();

        desBloques = new ScrollPane(bloques);
        desBloques.setLayoutX(0);
        desBloques.setLayoutY(alturaBtn*dy+10);
        desBloques.setPrefSize(500, 500);

        grupo.getChildren().addAll(cabecera, btnEnqueue, inEnqueue, btnDequeue, outDequeue, desBloques, btnFuente);

        btnEnqueue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enqueue();
            }
        });

        btnDequeue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dequeue();
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
     * Agrega un elemento al final de la cola.
     */
    private void enqueue(){
        String entrada = inEnqueue.getText();
        if(!entrada.equals("")){
            Text texto = new Text(entrada);
            texto.setFont(Font.font(fuente));
            cola.enqueue(entrada);
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(entrada.length()*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK); //Contorno negro.
            rectangulo.setFill(Color.AQUAMARINE); //Relleno aguamarina.
            StackPane bloque = new StackPane();
            bloque.getChildren().addAll(rectangulo, texto);
            bloques.getChildren().add(bloque);
            inEnqueue.setText("");
        }
    }

    /**
     * Elimina un elemento al inicio de la cola.
     */
    private void dequeue(){
        if(!cola.isEmpty()){
            String elem = cola.dequeue();
            bloques.getChildren().remove(0);
            outDequeue.setText(elem);
        }
    }

    /**
     * Dibuja la cola binomial.
     */
    private void redibuja(){
        bloques.getChildren().clear();
        for(String elem : cola.lista){
            Text texto = new Text(elem);
            texto.setFont(Font.font(fuente));
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(elem.length()*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK); //Contorno negro.
            rectangulo.setFill(Color.AQUAMARINE); //Relleno aguamarina.
            StackPane bloque = new StackPane();
            bloque.getChildren().addAll(rectangulo, texto);
            bloques.getChildren().add(bloque);
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
            redibuja();
        }
    }
}
