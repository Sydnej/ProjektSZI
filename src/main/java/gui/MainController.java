package gui;

import DecisionTree.C45;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FuzzyLogic;
import model.Tractor;
import model.area.Field;
import model.area.GraphVertex;
import model.weather.Season;
import model.weather.Weather;
import ucs.State;
import ucs.UnifiedCostSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class MainController implements Initializable {

    private static final Logger LOGGER = Logger.getGlobal();
    @FXML
    private Label temperatureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label rainLabel;
    @FXML
    private Label day;
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
    @FXML
    private TableColumn<Field, Integer> humidityColumn;
    @FXML
    private TextField speed;
    @FXML
    private Button speedButton;
    @FXML
    private Button decisionTreeButton;
    private Set<String> currentlyActiveKeys = new HashSet<>();
    private int height;
    private int width;
    private Image map;
    private Image tractorLeft;
    private Image tractorRight;
    private Image tractorUp;
    private Image tractorDown;
    private Image tractorImage;
    private GraphicsContext graphicsContext;
    //pozycja traktora:
    private double positionX = 718;
    private double positionY = 235;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Canvas canvas;
    private Stage stage;
    private Tractor tractor;
    private Weather weather = new Weather();
    private int tractorSpeed = 10; //ms

    public void setSpeedTractor(int newSpeed) {
        tractorSpeed = newSpeed;
    }

    private void slowTractor() {
        try {
            Thread.sleep(tractorSpeed);
        } catch (InterruptedException e) {
            LOGGER.info("Interrupted");
        }
    }


    private void prepareActionHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> currentlyActiveKeys.add(event.getCode().toString()));
        scene.setOnKeyReleased(event -> currentlyActiveKeys.remove(event.getCode().toString()));
    }

    private void tickAndRender() {
        //czyszczenie
        graphicsContext.clearRect(0, 0, width, height);
        graphicsContext.drawImage(map, 0, 0);
        graphicsContext.drawImage(tractorImage, positionX, positionY);
    }

    private void moveTractor(Direction direction) {
        switch (direction) {
            case LEFT:
                tractorImage = tractorLeft;
                positionX = positionX - 0.5;
                break;
            case RIGHT:
                tractorImage = tractorRight;
                positionX = positionX + 0.5;
                break;
            case DOWN:
                tractorImage = tractorDown;
                positionY = positionY + 0.5;
                break;
            case UP:
                tractorImage = tractorUp;
                positionY = positionY - 0.5;
                break;
        }
        LOGGER.info("Moving: " + direction.name() + " " + positionX + " " + positionY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tractor = new Tractor();
        // wczytanie domyślnej mapy
        tractor.getArea().loadData(getClass().getResourceAsStream("/xml/map.xml"));
        tractor.setCurrentPosition(tractor.getArea().getGraphVertices().get(0));

        positionX = tractor.getCurrentPosition().getX();
        positionY = tractor.getCurrentPosition().getY();

        day.setText("Day 1");

        decisionTreeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                C45 TreeDecision = new C45();
                TreeDecision.C45();
                System.out.println(TreeDecision.MakeDecision("overcast", "hot", "normal", "weak"));
            }
        });

        speedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSpeedTractor(Integer.parseInt(speed.getText()));
            }
        });

        startWeather();
        initWeatherPropertySheet();
        initFieldsTable();
        startTractor();

        //pobieranie rozdzielczości ektanu
//        height = (int) Screen.getPrimary().getVisualBounds().getHeight();
//        width = (int) Screen.getPrimary().getVisualBounds().getWidth();
//        System.out.print("height: " + height + " width: " + width);

//        canvas.setWidth(width);
//        canvas.setHeight(height);

        //ładowanie obiektów
        loadImages();
        graphicsContext = canvas.getGraphicsContext2D();

    }

    private void startTractor() {
        Thread thread = new Thread(() -> {
            FuzzyLogic flogic = new FuzzyLogic();
            while (true) {
                Map<Integer, Field> fields = tractor.getArea().getFields();
                Map<Integer, GraphVertex> graphVertices = tractor.getArea().getGraphVertices();
                Optional<Field> max = fields.values().stream().max((o1, o2) -> (int) (flogic.calcPriorityForHarvest
                        (o1) - flogic.calcPriorityForHarvest(o2)));
                if (max.isPresent()) {
                    Field field = max.get();
                    fieldsTable.getSelectionModel().select(field);
                    fieldsTable.scrollTo(field);
                    GraphVertex goalVertex = graphVertices.get(field.getId());
                    State result = UnifiedCostSearch.calc(tractor.getArea().getGraphVertices(), tractor
                            .getCurrentPosition(), goalVertex);
                    LinkedList<GraphVertex> path = UnifiedCostSearch.buildPath(result);
                    goViaPoints(path);
                    tractor.setCurrentPosition(goalVertex);
                    field.setYields(0);
                }
            }
        });
        thread.setName("Tractor thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void initWeatherPropertySheet() {
        temperatureLabel.textProperty().bind(Bindings.selectString(weather.temperatureProperty()));
        humidityLabel.textProperty().bind(Bindings.selectString(weather.humidityProperty()));
        rainLabel.textProperty().bind(Bindings.selectString(weather.rainProperty().asString()));
    }

    private void startWeather() {
        weather.setSeason(Season.SPRING);
        Thread thread = new Thread(new WeatherLoop(tractor.getArea(), weather));
        thread.setName("Weather thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void initFieldsTable() {
        fieldNoColumn.setCellValueFactory(param -> param.getValue().idProperty().asObject());
        yieldsColumn.setCellValueFactory(param -> param.getValue().yieldsProperty().asObject());
        yieldsColumn.setCellFactory(new PropertyCellFactory(false));
        weedsColumn.setCellValueFactory(param -> param.getValue().weedsProperty().asObject());
        weedsColumn.setCellFactory(new PropertyCellFactory(false));
        mineralsColumn.setCellValueFactory(param -> param.getValue().mineralsProperty().asObject());
        mineralsColumn.setCellFactory(new PropertyCellFactory(true));
        humidityColumn.setCellValueFactory(param -> param.getValue().humidityProperty().asObject());
        humidityColumn.setCellFactory(new PropertyCellFactory(true));
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
        tractorImage = tractorDown;
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

    private void goToThePoint(double posX, double posY) {
        if (posX >= positionX) {
            while (posX > positionX) {
                moveTractor(Direction.RIGHT);
                slowTractor();
            }
        } else {
            while (posX < positionX) {
                moveTractor(Direction.LEFT);
                slowTractor();
            }
        }
        if (posY >= positionY) {
            while (posY > positionY) {
                moveTractor(Direction.DOWN);
                slowTractor();
            }
        } else {
            while (posY < positionY) {
                moveTractor(Direction.UP);
                slowTractor();
            }
        }
    }

    public void goViaPoints(List<GraphVertex> points) {
        for (GraphVertex point : points) {
            goToThePoint(point.getX(), point.getY());
        }
    }
}
