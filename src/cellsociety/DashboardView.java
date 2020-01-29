package cellsociety;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class DashboardView extends Pane {
    public DashboardView(Stage primaryStage){
        super();
        HBox myDashBoard = new HBox();
        Button fileChooserButton = new Button();
        fileChooserButton.setText("Choose Configuration File");
        fileChooserButton.setOnMouseClicked(event -> {
            FileChooser myFileChooser = new FileChooser();
            File selectedFile = myFileChooser.showOpenDialog(primaryStage);
        });
        myDashBoard.getChildren().add(fileChooserButton);
        getChildren().add(myDashBoard);
        this.setStyle("-fx-background-color: red");
    }
}
