package gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static gui.Direction.*;

public class MainController implements Initializable {

    private Set<String> currentlyActiveKeys = new HashSet<>();
    private int height;
    private int width;
    private Image map;
    private Image tractorL;
    private Image tractorR;
    private Image tractorUp;
    private Image tractorDown;
    private GraphicsContext graphicsContext;
    //pozycja traktora:
    private double positionX = 718;
    private double positionY = 235;
    //obrót traktora
    private Direction tractorDirection;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Canvas canvas;
    private Stage stage;

    private void prepareActionHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> currentlyActiveKeys.add(event.getCode().toString()));
        scene.setOnKeyReleased(event -> currentlyActiveKeys.remove(event.getCode().toString()));
    }

    private void tickAndRender() {
        //czyszczenie
        graphicsContext.clearRect(0, 0, width, height);
        graphicsContext.drawImage(map, 0, 0);


        if (currentlyActiveKeys.contains("ESCAPE")) {
            stage.close();
        }

        if (currentlyActiveKeys.contains("LEFT")) {
            tractorDirection = LEFT;
            positionX = positionX - 1.0;
            graphicsContext.drawImage(tractorL, positionX, positionY);
        } else if (currentlyActiveKeys.contains("RIGHT")) {
            tractorDirection = RIGHT;
            positionX = positionX + 1.0;
            graphicsContext.drawImage(tractorR, positionX, positionY);
        } else if (currentlyActiveKeys.contains("DOWN")) {
            tractorDirection = DOWN;
            positionY = positionY + 1.0;
            graphicsContext.drawImage(tractorDown, positionX, positionY);
        } else if (currentlyActiveKeys.contains("UP")) {
            tractorDirection = UP;
            positionY = positionY - 1.0;
            graphicsContext.drawImage(tractorUp, positionX, positionY);

        } else {
            switch (tractorDirection) {
                case LEFT:
                    graphicsContext.drawImage(tractorL, positionX, positionY);
                    break;
                case RIGHT:
                    graphicsContext.drawImage(tractorR, positionX, positionY);
                    break;
                case UP:
                    graphicsContext.drawImage(tractorUp, positionX, positionY);
                    break;
                case DOWN:
                    graphicsContext.drawImage(tractorDown, positionX, positionY);
                    break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //pobieranie rozdzielczości ektanu
        Toolkit pack = Toolkit.getDefaultToolkit();
        Dimension size = pack.getScreenSize();
        height = size.height;
        width = size.width;
        // System.out.print("height: " + height + " width: " + width);

        canvas.setWidth(width);
        canvas.setHeight(height);

        //ładowanie obiektów
        loadImages();
        graphicsContext = canvas.getGraphicsContext2D();
        tractorDirection = DOWN;
    }

    public void handleSelectMap(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik mapy");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pliki map", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // TODO obsługa wczytywania mapy
        }
    }

    private void loadImages() {
        map = new Image("img/new-map.png");
        tractorL = new Image("img/tractor-left.png");
        tractorR = new Image("img/tractor-right.png");
        tractorUp = new Image("img/tractor-up.png");
        tractorDown = new Image("img/tractor-down.png");
    }

    public void setupListenersAndStartAnimation(Stage primaryStage) {
        this.stage = primaryStage;
        prepareActionHandlers(primaryStage.getScene());

        //odświeżanie programu
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                tickAndRender();
            }
        }.start();
    }
}
