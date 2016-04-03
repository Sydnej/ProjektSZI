package main.java.gui;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainStage extends Stage {

    private Scene scene;
    private GridPane gridPane;

    public MainStage() {
        gridPane = new GridPane();
        gridPane.setMaxWidth(300);
        gridPane.setMaxHeight(200);
        gridPane.setStyle("-fx-background-color: rgb(28, 119, 44)");

        scene = new Scene(gridPane);

        setScene(scene);
        setTitle("Traktor");
        setResizable(false);
    }

}
