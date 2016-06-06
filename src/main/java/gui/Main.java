package gui;

import DecisionTree.C45;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GeneticAlg.TourManager;
import model.area.Area;
import model.area.Field;
import model.area.GraphVertex;
import model.weather.Season;
import model.weather.Weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        List<GraphVertex> tractorPath = new ArrayList<GraphVertex>();

        Area area = new Area();
        area.loadData(Main.class.getResourceAsStream("../xml/map.xml"));
        Collection<GraphVertex> values = area.getGraphVertices().values();
        new ArrayList<>(values).subList(0, 19).forEach(TourManager::addVertex);


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Weather weather = new Weather();
        weather.setSeason(Season.WINTER);
        System.out.println("humidity:   " + weather.getHumidity() + "%");
        System.out.println("rain:   " + weather.getRain() + " mm/m2");
        System.out.println("temperature:   " + weather.getTemperature() + " st. C");
        System.out.println();

        Area area = new Area();
        area.loadData(getClass().getResourceAsStream("/xml/map.xml"));
        for (Field field : area.getFields().values()) {
            System.out.println();
            for (int i = 0; i < 4; ++i) {
                System.out.println("id: " + field.getId());
                System.out.println("x: " + field.getCorners()[i].getX());
                System.out.println("y: " + field.getCorners()[i].getY());
                System.out.println("pos: " + field.getCorners()[i].getPosition());
            }
        }

        System.out.println();
        System.out.println();
        for (GraphVertex vertex : area.getGraphVertices().values()) {
            System.out.println("id: " + vertex.getId());
            System.out.println("x: " + vertex.getX());
            System.out.println("y: " + vertex.getY());
            for (int linkedVertex : vertex.getLinkedVertices()) {
                System.out.print(linkedVertex + " ");
            }

            System.out.println();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Pane page = loader.load();
            primaryStage.setTitle("Traktor");
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            Scene primaryScene = new Scene(page);
            primaryStage.setScene(primaryScene);
            MainController controller = loader.getController();
            controller.setupListenersAndStartAnimation(primaryStage);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ALGORYTM GENETYCZNY
        //inicjalizowanie populacji
        model.GeneticAlg.Population pop = new model.GeneticAlg.Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        //ewolucja populacji do 100 generacji
        pop = model.GeneticAlg.GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = model.GeneticAlg.GA.evolvePopulation(pop);
        }
        //Rezultat
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
        //
        //koniec GA
    }

}
