package gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Tractor;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPaneBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static gui.Direction.*;

public class MainController implements Initializable {

    final ToggleButton toggle = new ToggleButton();
    private Set<String> currentlyActiveKeys = new HashSet<>();
    private int height;
    private int width;
    private Image map;
    private Image tractorLeft;
    private Image tractorRight;
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
    private Tractor tractor;

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
            graphicsContext.drawImage(tractorLeft, positionX, positionY);
        } else if (currentlyActiveKeys.contains("RIGHT")) {
            tractorDirection = RIGHT;
            positionX = positionX + 1.0;
            graphicsContext.drawImage(tractorRight, positionX, positionY);
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
                    graphicsContext.drawImage(tractorLeft, positionX, positionY);
                    break;
                case RIGHT:
                    graphicsContext.drawImage(tractorRight, positionX, positionY);
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
        tractor = new Tractor();
        // wczytanie domyślnej mapy
        tractor.getArea().loadData(getClass().getResourceAsStream("/xml/map.xml"));

        //pobieranie rozdzielczości ektanu
        height = (int) Screen.getPrimary().getVisualBounds().getHeight();
        width = (int) Screen.getPrimary().getVisualBounds().getWidth();
        System.out.print("height: " + height + " width: " + width);

        canvas.setWidth(width);
        canvas.setHeight(height);

        //ładowanie obiektów
        loadImages();
        graphicsContext = canvas.getGraphicsContext2D();
        tractorDirection = DOWN;
        ButtonLoad();
    }

    @FXML
    private void handleSelectMap() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik mapy");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pliki map", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            tractor.getArea().loadData(new FileInputStream(selectedFile));
            map = new Image("img/" + tractor.getArea().getAreaImagePath());
        }
    }

    private void loadImages() {
        map = new Image("img/" + tractor.getArea().getAreaImagePath());
        tractorLeft = new Image("img/tractor-left.png");
        tractorRight = new Image("img/tractor-right.png");
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
    public void ButtonLoad(){
        System.out.println("Button load... ");
        toggle.getStylesheets().add(this.getClass().getResource(
                "togglebutton.css"
        ).toExternalForm());
        toggle.setMinSize(148, 148); toggle.setMaxSize(148, 148);
        stage.setScene(new Scene(
                StackPaneBuilder.create()
                        .children(toggle)
                        .style("-fx-padding:10; -fx-background-color: cornsilk;")
                        .build()
        ));
    }
}
