package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.weather.Season;
import model.weather.Weather;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;

public class Main extends Application {

    static Scene primaryScene;
    static HashSet<String> currentlyActiveKeys = new HashSet<String>();

    static int Height;
    static int Width;

    static  Image map;
    static Image tractorL;
    static Image tractorR;
    static Image tractorUp;
    static Image tractorDown;
    static GraphicsContext graphicsContext;

    //pozycja traktora:
    static double positionX = 718;
    static double positionY = 235;

    //obrót traktora
    static String tractorDirection;

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

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            primaryStage.setTitle("Traktor");
            primaryStage.setFullScreen(true);
            primaryScene = new Scene(page);
            primaryStage.setScene(primaryScene);

            //pobieranie rozdzielczości ektanu
            Toolkit pack = Toolkit.getDefaultToolkit();
            Dimension size = pack.getScreenSize();
            Height = size.height;
            Width = size.width;
           // System.out.print("Height: " + Height + " Width: " + Width);

            Canvas canvas = new Canvas(Width, Height);
            page.getChildren().add(canvas);

            //ładowanie obiektów
            map = new Image("img/new-map.png");
            tractorL = new Image("img/tractor-left.png");
            tractorR = new Image("img/tractor-right.png");
            tractorUp = new Image("img/tractor-up.png");
            tractorDown = new Image("img/tractor-down.png");
            prepareActionHandlers();
            graphicsContext = canvas.getGraphicsContext2D();
            tractorDirection = "DOWN";

            //odświeżanie programu
            new AnimationTimer()
            {
                public void handle(long currentNanoTime) {
                    tickAndRender(primaryStage);

                }
            }.start();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void prepareActionHandlers()
    {
        primaryScene.setOnKeyPressed(event -> currentlyActiveKeys.add(event.getCode().toString()));
        primaryScene.setOnKeyReleased(event -> currentlyActiveKeys.remove(event.getCode().toString()));
    }

    private static void tickAndRender(Stage primaryStage)
    {
        //czyszczenie
        graphicsContext.clearRect(0, 0, Width, Height);
        graphicsContext.drawImage(map, 0, 0);



        if (currentlyActiveKeys.contains("ESCAPE"))
        {
            primaryStage.close();
        }

        if (currentlyActiveKeys.contains("LEFT"))
        {
            tractorDirection = "LEFT";
            positionX = positionX - 1.0;
            graphicsContext.drawImage(tractorL, positionX, positionY);
        }
        else if (currentlyActiveKeys.contains("RIGHT"))
        {
            tractorDirection = "RIGHT";
            positionX = positionX + 1.0;
            graphicsContext.drawImage(tractorR, positionX , positionY);
        }
        else if (currentlyActiveKeys.contains("DOWN")) {
            tractorDirection = "DOWN";
            positionY = positionY + 1.0;
            graphicsContext.drawImage(tractorDown, positionX, positionY);
        }
        else if (currentlyActiveKeys.contains("UP")) {
            tractorDirection = "UP";
            positionY = positionY - 1.0;
            graphicsContext.drawImage(tractorUp, positionX, positionY);

        }
        else {
            if (tractorDirection == "LEFT") {
                graphicsContext.drawImage(tractorL, positionX, positionY);
            }
            else if (tractorDirection == "RIGHT"){
                graphicsContext.drawImage(tractorR, positionX, positionY);
            }
            else if (tractorDirection == "UP"){
                graphicsContext.drawImage(tractorUp, positionX, positionY);
            }
            else if (tractorDirection == "DOWN"){
                graphicsContext.drawImage(tractorDown, positionX, positionY);
            }
        }
    }



}
