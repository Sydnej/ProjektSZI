package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GeneticAlg.GA;
import model.GeneticAlg.TourManager;
import model.area.Area;
import model.area.Field;
import model.area.GraphVertex;
import model.weather.Season;
import model.weather.Weather;
import model.FuzzyLogic;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {


        GraphVertex city0 = new GraphVertex(0,716.5,231.5);
        TourManager.addVertex(city0);
        GraphVertex city1 = new GraphVertex(1,102,185.5);
        TourManager.addVertex(city1);
        GraphVertex city2 = new GraphVertex(2,220.5,234);
        TourManager.addVertex(city2);
        GraphVertex city3 = new GraphVertex(3,139.5,380);
        TourManager.addVertex(city3);
        GraphVertex city4 = new GraphVertex(4,26.5,544);
        TourManager.addVertex(city4);
        GraphVertex city5 = new GraphVertex(5,85.5,664);
        TourManager.addVertex(city5);
        GraphVertex city6 = new GraphVertex(6,290.5,139);
        TourManager.addVertex(city6);
        GraphVertex city7 = new GraphVertex(7,393,336.5);
        TourManager.addVertex(city7);
        GraphVertex city8 = new GraphVertex(8,333.5,663);
        TourManager.addVertex(city8);
        GraphVertex city9 = new GraphVertex(9,514.5,133);
        TourManager.addVertex(city9);
        GraphVertex city10 = new GraphVertex(10,514.5,133);
        TourManager.addVertex(city10);
        GraphVertex city11 = new GraphVertex(11,514,271.5);
        TourManager.addVertex(city11);
        GraphVertex city12 = new GraphVertex(12,546,414.5);
        TourManager.addVertex(city12);
        GraphVertex city13 = new GraphVertex(13,543.5,522);
        TourManager.addVertex(city13);
        GraphVertex city14 = new GraphVertex(14,720.5,113);
        TourManager.addVertex(city14);
        GraphVertex city15 = new GraphVertex(15,664.5,330);
        TourManager.addVertex(city15);
        GraphVertex city16 = new GraphVertex(16,673.5,521);
        TourManager.addVertex(city16);
        GraphVertex city17 = new GraphVertex(17,860,183.5);
        TourManager.addVertex(city17);
        GraphVertex city18 = new GraphVertex(18,863,367.5);
        TourManager.addVertex(city18);
        GraphVertex city19 = new GraphVertex(19,842.5,517);
        TourManager.addVertex(city19);

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Weather weather = new Weather();
        FuzzyLogic flogic = new FuzzyLogic();
        weather.setSeason(Season.WINTER);
        System.out.println("humidity:   " + weather.generateHumidity() + "%");
        System.out.println("rain:   " + weather.generateRain() + " mm/m2");
        System.out.println("temperature:   " + weather.generateTemperature() + " st. C");
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
        model.GeneticAlg.Population pop = new model.GeneticAlg.Population(50,true);
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

        System.out.println("Priorytet: " + flogic.CountPriority(40, 30, 70)); // (wilgotnosc, temperatura, jakosc)
    }

}
