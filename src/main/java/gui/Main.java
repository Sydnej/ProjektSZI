package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.area.Area;
import model.area.Field;
import model.area.GraphVertex;
import model.weather.Season;
import model.weather.Weather;
import model.FuzzyLogic;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
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


        System.out.println("Priorytet: " + flogic.CountPriority(40,30,70)); // (wilgotnosc, temperatura, jakosc)
    }
    //GA


}
