package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainStage extends Stage {

    private Scene scene;
    private GridPane gridPane;

    public MainStage() {
        gridPane = new GridPane();
        gridPane.setMaxWidth(300);
        gridPane.setMaxHeight(200);
        gridPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        scene = new Scene(gridPane);

        setScene(scene);
        setTitle("Traktor");
        setResizable(false);
    }

}
