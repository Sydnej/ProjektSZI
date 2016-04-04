package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private Canvas canvas;
    @FXML
    private TilePane rightPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        canvas.getGraphicsContext2D().setFill(Color.GREEN);
//        canvas.getGraphicsContext2D().fillRect(20, 20, 20, 20);
//        canvas.getGraphicsContext2D().strokeRect(20, 20, 20, 20);
    }
}
