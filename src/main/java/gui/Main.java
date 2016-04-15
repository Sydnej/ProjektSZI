package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.area.Area;
import model.area.Field;
import model.area.GraphVertice;
import model.weather.Season;
import model.weather.Weather;

import java.io.IOException;
import java.util.Map;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Weather weather = new Weather();
        weather.setSeason(Season.WINTER);
        System.out.println("humidity:   " + weather.generateHumidity() + "%");
        System.out.println("rain:   " + weather.generateRain() + " mm/m2");
        System.out.println("temperature:   " + weather.generateTemperature() + " st. C");
        System.out.println();

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

        Area area = new Area();
        for(Map.Entry<Integer, Field> entry : area.getFields().entrySet()) {
            System.out.println("id: " + entry.getKey());
            System.out.println("x: " + entry.getValue().getVertices()[0].getX());
            System.out.println("y: " + entry.getValue().getVertices()[0].getY());
            System.out.println("pos: " + entry.getValue().getVertices()[0].getPosition());
            System.out.println("x: " + entry.getValue().getVertices()[1].getX());
            System.out.println("y: " + entry.getValue().getVertices()[1].getY());
            System.out.println("pos: " + entry.getValue().getVertices()[1].getPosition());
            System.out.println("x: " + entry.getValue().getVertices()[2].getX());
            System.out.println("y: " + entry.getValue().getVertices()[2].getY());
            System.out.println("pos: " + entry.getValue().getVertices()[2].getPosition());
            System.out.println("x: " + entry.getValue().getVertices()[3].getX());
            System.out.println("y: " + entry.getValue().getVertices()[3].getY());
            System.out.println("pos: " + entry.getValue().getVertices()[3].getPosition());
            System.out.println();
        }
        System.out.println();
        System.out.println();
        for(Map.Entry<Integer, GraphVertice> entry : area.getGraphVertices().entrySet()) {
            System.out.println("id: " + entry.getKey());
            System.out.println("x: " + entry.getValue().getX());
            System.out.println("y: " + entry.getValue().getY());
            for(int i=0; i<entry.getValue().getLinkedVertices().size(); i++) {
                System.out.print(entry.getValue().getLinkedVertices().get(i) + " ");
            }
            System.out.println();
        }
    }
}
