package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.weather.Season;
import model.weather.Weather;

import java.io.IOException;

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
    }
}
