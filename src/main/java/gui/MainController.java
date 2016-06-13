package gui;

import java.io.PrintWriter;
import weka.classifiers.trees.LMT;
import DecisionTree.C45;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FuzzyLogic;
import model.GeneticAlg.Tour;
import model.Tractor;
import MachineLearning.TestProjectWIP;
import model.area.Field;
import model.area.GraphVertex;
import model.weather.Season;
import model.weather.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucs.State;
import ucs.UnifiedCostSearch;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
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
    @FXML
    private TableColumn<Field, Integer> humidityColumn;
    @FXML
    private TextField speed;
    @FXML
    private Button speedButton;
    @FXML
    private TextField daySpeed;
    @FXML
    private Button daySpeedButton;
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
    private WeatherLoop weatherLoop;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Thread tractorThread;
   // public C45 TreeDecision = new C45();

    public void setSpeedTractor(int newSpeed) {
        tractorSpeed = newSpeed;
    }

    private void slowTractor() throws InterruptedException {
        Thread.sleep(tractorSpeed);
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
    //    LOGGER.info("Moving: " + direction.name() + " " + positionX + " " + positionY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tractor = new Tractor();
        // wczytanie domyślnej mapy
        tractor.getArea().loadData(getClass().getResourceAsStream("/xml/map.xml"));
        tractor.setCurrentPosition(tractor.getArea().getGraphVertices().get(0));

        positionX = tractor.getCurrentPosition().getX();
        positionY = tractor.getCurrentPosition().getY();

        startWeather();
        initWeatherPropertySheet();
        initFieldsTable();
//        startTractor();
//        startGeneticTractor();


        loadImages();
        graphicsContext = canvas.getGraphicsContext2D();

    }

    private void startTractor() {
        stopTractor();
        tractorThread = new Thread(new TractorRunnable());
        tractorThread.setName("Tractor thread");
        tractorThread.setDaemon(true);
        tractorThread.start();
    }

    private void startGeneticTractor() {
        stopTractor();
        tractorThread = new Thread(new GeneticTractorRunnable());
        tractorThread.setName("Tractor thread");
        tractorThread.setDaemon(true);
        tractorThread.start();
    }

    private void startGeneticTractor2() {
        stopTractor();
        tractorThread = new Thread(new GeneticTractorRunnable2());
        tractorThread.setName("Tractor thread");
        tractorThread.setDaemon(true);
        tractorThread.start();
    }

    private void goHarvest(Map<Integer, GraphVertex> graphVertices, Optional<Field> max) throws InterruptedException {
        Field field = max.get();
        goToField(graphVertices, field);
        field.setYields(0);
    }

    private void goCultivation(Map<Integer, GraphVertex> graphVertices, Optional<Field> max) throws
            InterruptedException {
        Field field = max.get();
        goToField(graphVertices, field);
        field.setWeeds(0);
    }

    private void goFertilization(Map<Integer, GraphVertex> graphVertices, Optional<Field> min) throws
            InterruptedException {
        Field field = min.get();
        goToField(graphVertices, field);
        field.setMinerals(100);
    }

    private void goToField(Map<Integer, GraphVertex> graphVertices, Field field) throws InterruptedException {
        Platform.runLater(() -> {
            fieldsTable.getSelectionModel().select(field);
            fieldsTable.scrollTo(field);
        });
        GraphVertex goalVertex = graphVertices.get(field.getId());
        State result = UnifiedCostSearch.calc(tractor.getArea().getGraphVertices(), tractor.getCurrentPosition(),
                goalVertex);
        LinkedList<GraphVertex> path = UnifiedCostSearch.buildPath(result);
        goViaPoints(path);
        tractor.setCurrentPosition(goalVertex);
    }

    private void initWeatherPropertySheet() {
        temperatureLabel.textProperty().bind(Bindings.selectString(weather.temperatureProperty()));
        humidityLabel.textProperty().bind(Bindings.selectString(weather.humidityProperty()));
        rainLabel.textProperty().bind(Bindings.selectString(weather.rainProperty().asString()));
    }

    private void startWeather() {
        weather.setSeason(Season.SPRING);
        weatherLoop = new WeatherLoop(tractor.getArea(), weather);
        Thread thread = new Thread(weatherLoop);
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

    private void goToThePoint(double posX, double posY) throws InterruptedException {
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

    public void goViaPoints(List<GraphVertex> points) throws InterruptedException {
        for (GraphVertex point : points) {
            goToThePoint(point.getX(), point.getY());
        }
    }

    @FXML
    private void handleTractor() {
        startTractor();
    }

    @FXML
        private void handleGeneticTractor() {
            startGeneticTractor();
    }

    @FXML
    private void handleGeneticTractor2() {
        startGeneticTractor2();
    }

    @FXML
    public void handleSpeedButton() {
        setSpeedTractor(Integer.parseInt(speed.getText()));
    }

    @FXML
    public void handleDecisionTreeButton() {
        //weatherLoop.TreeDecision.C45();
        //TreeDecision.C45();
        //System.out.println(weatherLoop.TreeDecision.MakeDecision("niska", "niska", "niskie", "niskie"));
    }

    @FXML
    public void handleDaySpeedButton() {
        weatherLoop.setDaySpeed(Integer.parseInt(daySpeed.getText()));
    }

    @FXML
    public void handleStopTractor() {
        stopTractor();
    }

    private void stopTractor() {
        if (tractorThread != null && tractorThread.isAlive()) {
            tractorThread.interrupt();
        }
    }

    private class TractorRunnable implements Runnable {
        @Override
        public void run() {
            Thread.currentThread().setName("TractorThread");
            FuzzyLogic flogic = new FuzzyLogic();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Map<Integer, Field> fields = tractor.getArea().getFields();
                    Map<Integer, GraphVertex> graphVertices = tractor.getArea().getGraphVertices();
                    Optional<Field> toHarvest = fields.values().stream().max((o1, o2) -> (int) (flogic
                            .calcPriorityForHarvest(o1) - flogic.calcPriorityForHarvest(o2)));


                    Optional<Field> toCultivation = fields.values().stream().max((o1, o2) -> (int) (flogic
                            .calcPriorityForCultivation(o1) - flogic.calcPriorityForCultivation(o2)));
                    Optional<Field> toFertilization = fields.values().stream().max((o1, o2) -> (int) (flogic
                            .calcPriorityForFertilization(o1) - flogic.calcPriorityForFertilization(o2)));
                    if (toHarvest.isPresent() && toHarvest.get().getYields() > 60) {
                        goHarvest(graphVertices, toHarvest);
                    } else if (toCultivation.isPresent() && toCultivation.get().getWeeds() > 60) {
                        goCultivation(graphVertices, toCultivation);
                    } else if (toFertilization.isPresent() && toFertilization.get().getMinerals() < 30) {
                        goFertilization(graphVertices, toFertilization);
                    }
                } catch (InterruptedException e) {
                    LOGGER.info("Interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private class GeneticTractorRunnable implements Runnable {

        @Override
        public void run() {
            Thread.currentThread().setName("GeneticTractorThread");
            List<GraphVertex> tractorPath = new ArrayList<>();
            for (int i = 0; i < Tour.tourSize2(); i++) {
                if (Thread.interrupted()) {
                    return;
                }
                tractorPath.add(Tour.getVertex2(i));
            }
            for (int i = 0; i < model.GeneticAlg.Tour.tourSize2() - 1; i++) {
                if (Thread.interrupted()) {
                    return;
                }
                State calc = UnifiedCostSearch.calc(tractor.getArea().getGraphVertices(), tractorPath.get(i),
                        tractorPath.get(i + 1));

                try {
                    goViaPoints(UnifiedCostSearch.buildPath(calc));
                } catch (InterruptedException e) {
                    LOGGER.info("Interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }



    private class GeneticTractorRunnable2 implements Runnable {

        @Override
        public void run() {
            int OLD_ID = 0;
            Thread.currentThread().setName("GeneticTractorThread2");
            List<GraphVertex> tractorPath = new ArrayList<>();
            for (int i = 0; i < Tour.tourSize2(); i++) {
                if (Thread.interrupted()) {
                    return;
                }
                tractorPath.add(Tour.getVertex2(i));
            }
            for (int i = 0; i < model.GeneticAlg.Tour.tourSize2() - 1; i++) {
                if (Thread.interrupted()) {
                    return;
                }
                State calc = UnifiedCostSearch.calc(tractor.getArea().getGraphVertices(), tractorPath.get(i),
                       tractorPath.get(i + 1));

                // Machine learning - tworzenie modelu i uczenie sie z zestawu treningowego
                Classifier test_model = (Classifier) new LMT();
                try
                {
                    ArffLoader loader = new ArffLoader();
                    loader.setFile(new File("testprojekt.arff"));
                    Instances isTrainingSet = loader.getDataSet();
                    int classIdx = 3;
                    isTrainingSet.setClassIndex(classIdx);

                    test_model.buildClassifier(isTrainingSet);

                }catch(Exception e){
                e.printStackTrace();



            }

                Attribute Attribute1 = new Attribute("yields");
                Attribute Attribute2 = new Attribute("weeds");
                Attribute Attribute3 = new Attribute("minerals");

                FastVector fvNominalVal = new FastVector(3);
                fvNominalVal.addElement("harvest");
                fvNominalVal.addElement("cultivation");
                fvNominalVal.addElement("fertilization");
                Attribute ClassAttribute = new Attribute("priority", fvNominalVal);

                FastVector fvWekaAttributes = new FastVector(4);
                fvWekaAttributes.addElement(Attribute1);
                fvWekaAttributes.addElement(Attribute2);
                fvWekaAttributes.addElement(Attribute3);
                fvWekaAttributes.addElement(ClassAttribute);





                try {




                 //   goViaPoints(UnifiedCostSearch.buildPath(calc));
                    for (GraphVertex point : UnifiedCostSearch.buildPath(calc)) {

                        int IIDD = point.getId();

                        //System.out.println("ZMIERZAM DO " + IIDD);
                        Map<Integer, Field> fields = tractor.getArea().getFields();

                        Field field = fields.get(point.getId());
                        if(IIDD < 20 && IIDD > 0 && IIDD != OLD_ID) {
                            Platform.runLater(() -> {
                                fieldsTable.getSelectionModel().select(field);
                                fieldsTable.scrollTo(field);
                            });
                        }
                        goToThePoint(point.getX(), point.getY());
                        if(IIDD < 20 && IIDD > 0 && IIDD != OLD_ID) {



                            int yields = field.getYields();
                            int weeds = field.getWeeds();
                            int minerals = field.getMinerals();

                            if( IIDD != OLD_ID) {



                                double test_result;
                                test_result = TestProjectWIP.lm(yields, weeds, minerals, fvWekaAttributes, test_model);
                               // System.out.print("Test: " + test_result + " A ID TO " + IIDD + " A OLD ID TO " + OLD_ID);
                                if (test_result == 0) {field.setYields(0); System.out.println("Na polu Nr " + IIDD + " o wartościach " + yields + " " + weeds + " " + minerals + " Wykonałem : Zabranie Plonów");}
                                else if (test_result == 1) {field.setWeeds(0); System.out.println("Na polu Nr " + IIDD + " o wartościach " + yields + " " + weeds + " " + minerals + " Wykonałem : Zerwanie Chwastów");}
                                else if (test_result == 2) {field.setMinerals(100); System.out.println("Na polu Nr " + IIDD + " o wartościach " + yields + " " + weeds + " " + minerals + " Wykonałem : Nawóz Pola");}
                                OLD_ID = IIDD;
                              //  System.out.println("Wykonałem : ")
                            }


                        }


                    }
                } catch (InterruptedException e) {
                    //LOGGER.info("Interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
