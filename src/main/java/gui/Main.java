package main.java.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.model.weather.Season;
import main.java.model.weather.Weather;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //new MainStage().show();

        Weather weather = new Weather();
        weather.setSeason(Season.WINTER);
        System.out.println("humidity:   " + weather.generateHumidity() + "%");
        System.out.println("rain:   " + weather.generateRain() + " mm/m2");
        System.out.println("temperature:   " + weather.generateTemperature() + " st. C");
    }

}
