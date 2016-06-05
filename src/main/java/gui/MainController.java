package gui;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Tractor;
import model.area.Field;
import model.area.GraphVertex;
import model.weather.Season;
import model.weather.Weather;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

import static gui.Direction.*;

public class MainController implements Initializable {

    @FXML
    private Label temperatureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label rainLabel;
    @FXML
    private TableView<Field> fieldsTable;
    @FXML
    private TableColumn<Field, Integer> fieldNoColumn;
    @FXML
    private TableColumn<Field, Integer> yieldsColumn;
    @FXML
    private TableColumn<Field, Integer> weedsColumn;
    @FXML
    private TableColumn<Field, Integer> mineralsColumn;
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
    private Weather weather = new Weather();

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

        startWeather();
        initWeatherPropertySheet();
        initFieldsTable();


        //pobieranie rozdzielczości ektanu
        height = (int) Screen.getPrimary().getVisualBounds().getHeight();
        width = (int) Screen.getPrimary().getVisualBounds().getWidth();
        System.out.print("height: " + height + " width: " + width);

//        canvas.setWidth(width);
//        canvas.setHeight(height);

        //ładowanie obiektów
        loadImages();
        graphicsContext = canvas.getGraphicsContext2D();
        tractorDirection = DOWN;
    }

    private void initWeatherPropertySheet() {
        temperatureLabel.textProperty().bind(Bindings.selectString(weather.temperatureProperty()));
        humidityLabel.textProperty().bind(Bindings.selectString(weather.humidityProperty()));
        rainLabel.textProperty().bind(Bindings.selectString(weather.rainProperty().asString()));
    }

    private void startWeather() {
        weather.setSeason(Season.SPRING);
        Thread thread = new Thread(new WeatherLoop(tractor.getArea(), weather));
        thread.setDaemon(true);
        thread.start();
    }

    private void initFieldsTable() {
        fieldNoColumn.setCellValueFactory(param -> param.getValue().idProperty().asObject());
        yieldsColumn.setCellValueFactory(param -> param.getValue().yieldsProperty().asObject());
        weedsColumn.setCellValueFactory(param -> param.getValue().weedsProperty().asObject());
        mineralsColumn.setCellValueFactory(param -> param.getValue().mineralsProperty().asObject());
        Collection<Field> fields = tractor.getArea().getFields().values();
        fieldsTable.getItems().addAll(fields);
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

    public void goToThePoint(double posX, double posY) {
        if (posX >= positionX) {
            tractorDirection = Direction.RIGHT;
            while (posX > positionX) {
                positionX = positionX + 0.5;
                graphicsContext.drawImage(tractorRight, positionX, positionY);
            }
        } else {
            tractorDirection = Direction.LEFT;
            while (posX < positionX) {
                positionX = positionX - 0.5;
                graphicsContext.drawImage(tractorLeft, positionX, positionY);
            }
        }
        if (posY >= positionY) {
            tractorDirection = Direction.UP;
            while (posY > positionY) {
                positionY = positionY + 0.5;
                graphicsContext.drawImage(tractorUp, positionX, positionY);
            }
        } else {
            tractorDirection = Direction.DOWN;
            while (posY < positionY) {
                positionY = positionY - 0.5;
                graphicsContext.drawImage(tractorDown, positionX, positionY);
            }
        }
    }

    public void goViaPoints(List<GraphVertex> points) {
        for(int i=0; i<points.size(); i++) {
            goToThePoint(points.get(i).getX(), points.get(i).getY());
        }
    }

}
