

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import vista.*;

/**
 *
 * @author emmanuel
 */
public class Principal extends Application {
    int anchoScene = 300;
    int altoScene = 250;
    int anchoButton = anchoScene;
    int altoButton = altoScene/5;
    
    @Override
    public void start(Stage primaryStage) {
        //Botón Pila
        Button btnPila = new Button();
        btnPila.setText("Pila");
        btnPila.setLayoutX(0);
        btnPila.setLayoutY(0);
        btnPila.setPrefSize(anchoButton, altoButton);
        
        //Botón Cola
        Button btnCola = new Button();
        btnCola.setText("Cola");
        btnCola.setLayoutX(0);
        btnCola.setLayoutY(altoButton);
        btnCola.setPrefSize(anchoButton, altoButton);
        
        //Botón Heap
        Button btnHeap = new Button();
        btnHeap.setText("Heap binario");
        btnHeap.setLayoutX(0);
        btnHeap.setLayoutY(altoButton*2);
        btnHeap.setPrefSize(anchoButton, altoButton);
        
        //Botón Cola binomial
        Button btnColaBin = new Button();
        btnColaBin.setText("Cola binomial");
        btnColaBin.setLayoutX(0);
        btnColaBin.setLayoutY(altoButton*3);
        btnColaBin.setPrefSize(anchoButton, altoButton);
        
        //Botón conjuntos ajenos
        Button btnConjuntoAjeno = new Button();
        btnConjuntoAjeno.setText("Conjuntos ajenos");
        btnConjuntoAjeno.setLayoutX(0);
        btnConjuntoAjeno.setLayoutY(altoButton*4);
        btnConjuntoAjeno.setPrefSize(anchoButton, altoButton);
        
        Group root = new Group();
        root.getChildren().add(btnPila);
        root.getChildren().add(btnCola);
        root.getChildren().add(btnHeap);
        root.getChildren().add(btnColaBin);
        root.getChildren().add(btnConjuntoAjeno);
        
        Scene scene = new Scene(root, anchoScene, altoScene);
        
        primaryStage.setTitle("Estructuras");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Acción del botón Pila
        btnPila.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.getWindow().setHeight(500);
                scene.getWindow().setWidth(500);
                root.getChildren().clear();
                PilaVista pv = new PilaVista(root);
                pv.inicia();
            }
        });
        
        //Acción del botón Cola
        btnCola.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.getWindow().setHeight(500);
                scene.getWindow().setWidth(500);
                root.getChildren().clear();
                ColaVista cv = new ColaVista(root);
                cv.inicia();
            }
        });
        
        //Acción del botón Heap.
        btnHeap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.getWindow().setHeight(500);
                scene.getWindow().setWidth(500);
                root.getChildren().clear();
                HeapVista hv = new HeapVista(root);
                hv.inicia();
            }
        });
        
        //Acción del botón Cola binomial.
        btnColaBin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.getWindow().setHeight(500);
                scene.getWindow().setWidth(500);
                root.getChildren().clear();
                ColaBinVista cbv = new ColaBinVista(root);
                cbv.inicia();
            }
        });
        
        btnConjuntoAjeno.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.getWindow().setHeight(500);
                scene.getWindow().setWidth(500);
                root.getChildren().clear();
                DisjSetsVista dsv = new DisjSetsVista(root);
                dsv.inicia();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
