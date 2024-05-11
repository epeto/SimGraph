

package vista;

import java.util.Optional;
import estructuras.Pila;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author emmanuel
 */
public class PilaVista {
    
    Group grupoLocal; //Grupo en el cual se agregan los elementos.
    Label cabecera;
    Pila<String> pila;
    Button btnPush;
    Button btnPop;
    Button btnPeek;
    Button btnFuente;
    TextField inPush; //Entrada de datos para la operación push.
    Label outPop; //Salida para la operación pop.
    Label outPeek; //Salida para la operación peek.
    VBox bloques; //Va a contener los rectángulos de la pila.
    int maxLen = 0; //Longitud máxima de un elemento en la pila.
    double fuente; //Tamaño de fuente del texto.
    double alturaBtn;
    double anchoBtn;
    ScrollPane desplazador;
    
    //Constructor
    public PilaVista(Group grupo){
        fuente = 12;
        grupoLocal = grupo;
        alturaBtn = fuente*2;
        anchoBtn = fuente*4;
        pila = new Pila<>();
    }
    
    /**
     * Inicializa las variables y coloca los elementos en su posición inicial.
     */
    public void inicia(){
        double margenSup = 10;
        int dy = 0;

        bloques = new VBox();

        desplazador = new ScrollPane();
        desplazador.setLayoutX(anchoBtn*3);
        desplazador.setLayoutY(alturaBtn);
        desplazador.setContent(bloques);
        desplazador.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        desplazador.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        desplazador.setPannable(true);
        desplazador.setPrefSize(500, 500);
        
        cabecera = new Label("Pila");
        cabecera.setFont(Font.font(25));
        cabecera.setPrefSize(70, 40);
        cabecera.setLayoutX(0);
        cabecera.setLayoutY(0);
        
        dy++;
        btnPush = new Button("Push");
        btnPush.setPrefSize(anchoBtn, alturaBtn);
        btnPush.setLayoutX(0);
        btnPush.setLayoutY(alturaBtn*dy + margenSup);
        btnPush.setFont(Font.font(fuente));
        
        inPush = new TextField();
        inPush.setPrefSize(anchoBtn, alturaBtn);
        inPush.setLayoutX(anchoBtn);
        inPush.setLayoutY(alturaBtn*dy + margenSup);
        inPush.setFont(Font.font(fuente));
        
        dy++;
        btnPop = new Button("Pop");
        btnPop.setPrefSize(anchoBtn, alturaBtn);
        btnPop.setLayoutX(0);
        btnPop.setLayoutY(alturaBtn*dy + margenSup);
        btnPop.setFont(Font.font(fuente));
        
        outPop = new Label();
        outPop.setPrefHeight(alturaBtn);
        outPop.setLayoutX(anchoBtn);
        outPop.setLayoutY(alturaBtn*dy + margenSup);
        outPop.setFont(Font.font(fuente));
        
        dy++;
        btnPeek = new Button("Peek");
        btnPeek.setPrefSize(anchoBtn, alturaBtn);
        btnPeek.setLayoutX(0);
        btnPeek.setLayoutY(alturaBtn*dy + margenSup);
        btnPeek.setFont(Font.font(fuente));
        
        outPeek = new Label();
        outPeek.setPrefHeight(alturaBtn);
        outPeek.setLayoutX(anchoBtn);
        outPeek.setLayoutY(alturaBtn*dy + margenSup);
        outPeek.setFont(Font.font(fuente));
        
        dy++;
        btnFuente = new Button("Fuente");
        btnFuente.setLayoutX(0);
        btnFuente.setLayoutY(alturaBtn*dy + margenSup);
        btnFuente.setFont(Font.font(fuente));

        grupoLocal.getChildren().add(cabecera);
        grupoLocal.getChildren().add(btnPush);
        grupoLocal.getChildren().add(inPush);
        grupoLocal.getChildren().add(btnPop);
        grupoLocal.getChildren().add(outPop);
        grupoLocal.getChildren().add(btnPeek);
        grupoLocal.getChildren().add(outPeek);
        grupoLocal.getChildren().add(desplazador);
        grupoLocal.getChildren().add(btnFuente);
        
        
        btnPush.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                push();
            }
        });
        
        btnPop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pop();
            }
        });
        
        btnPeek.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                peek();
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
            resize();
        }
    }

    /**
     * Inserta un elemento al tope de la pila.
     */
    private void push(){
        String tope = inPush.getText();
        if(!tope.equals("")){
            Text texto = new Text(tope);
            texto.setFont(Font.font(fuente));
            if(tope.length() > maxLen){
                maxLen = tope.length();
                resize();
            }
            pila.push(tope);
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(maxLen*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setFill(Color.AQUAMARINE);
            StackPane bloque = new StackPane();
            bloque.getChildren().addAll(rectangulo, texto);
            bloques.getChildren().add(0,bloque);
        }
        inPush.setText("");
    }
    
    /**
     * Elimina el elemento al tope de la pila.
     */
    private void pop(){
        if(!pila.isEmpty()){
            String valor = pila.pop();
            outPop.setText(valor);
            bloques.getChildren().remove(0);
            outPeek.setText("");
        }
    }
    
    /**
     * Muestra el elemento al tope de la pila.
     */
    private void peek(){
        if(!pila.isEmpty()){
            String valor = pila.peek();
            outPeek.setText(valor);
        }
    }
    
    /**
     * Cambia el tamaño de los rectángulos en la pila.
     * En realidad los borra todos y los vuelve a agregar.
     */
    private void resize(){
        bloques.getChildren().clear();
        for(String elem:pila.lista){
            Rectangle rectangulo = new Rectangle();
            rectangulo.setWidth(maxLen*fuente);
            rectangulo.setHeight(fuente*2);
            rectangulo.setStroke(Color.BLACK);
            rectangulo.setFill(Color.AQUAMARINE);
            Text texto = new Text(elem);
            texto.setFont(Font.font(fuente));
            StackPane bloque = new StackPane();
            bloque.getChildren().addAll(rectangulo, texto);
            bloques.getChildren().add(bloque);
        }
    }
}
