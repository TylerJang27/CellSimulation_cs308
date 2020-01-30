package cellsociety;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class DashboardView extends Pane {
    public DashboardView(Stage primaryStage){
        super();

        VBox myDashBoard = new VBox(30);
        myDashBoard.prefHeightProperty().bind(this.heightProperty());
        myDashBoard.setPadding(new Insets(10,10,10,10));

        Button fileChooserButton = new Button();
        fileChooserButton.setText("Choose Configuration File");
        fileChooserButton.setOnMouseClicked(event -> handleFileButtonClicked(primaryStage));

        Slider speedSlider = new Slider(0,1,.5);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
        Pane comboBoxPane = new Pane();
        ComboBox comboBox = new ComboBox(options);
        comboBox.prefWidthProperty().bind(comboBoxPane.widthProperty());
        comboBoxPane.getChildren().add(comboBox);

        HBox playButtons = new HBox();
        Button playButton = new Button("Play");
        playButton.setOnMouseClicked(event -> handlePlayButtonClicked());
        Button pauseButton = new Button("Pause");
        pauseButton.setOnMouseClicked(event -> handlePauseButtonClicked());
        Button stepButton = new Button("Step");
        stepButton.setOnMouseClicked(event -> handleStepButtonClicked());

        playButtons.getChildren().addAll(playButton,pauseButton,stepButton);


        Region spacerRegion = new Region();
        VBox.setVgrow(spacerRegion, Priority.ALWAYS);
        myDashBoard.getChildren().addAll(fileChooserButton,speedSlider,comboBoxPane,spacerRegion,playButtons);
        getChildren().add(myDashBoard);
        this.setStyle("-fx-background-color: red");
    }

    private void handleFileButtonClicked(Stage primaryStage) {
        FileChooser myFileChooser = new FileChooser();
        File selectedFile = myFileChooser.showOpenDialog(primaryStage);
        System.out.println("File Chosen: " + selectedFile.getName());

    }

    private void handleStepButtonClicked(){
        System.out.println("Step Button Clicked!");
    }

    private void handlePauseButtonClicked() {
        System.out.println("Pause Button Clicked!");
    }

    private void handlePlayButtonClicked() {
        System.out.println("Play Button Clicked!");
    }
}
