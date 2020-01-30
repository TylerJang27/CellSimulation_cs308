package cellsociety.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static javafx.geometry.Pos.BASELINE_RIGHT;

public class ConsoleView extends Pane {
    public static final String CONSOLE_STYLING = "-fx-background-color: #baffe0";
    public static final String FRAME_NUMBER_TEXT = "Frame Number: ";
    private Label myErrorLabel;
    private Label myFrameTextLabel;
    private Label myFrameNumberLabel;
    private int myFrame;

    public ConsoleView(){
        super();
        this.setStyle(CONSOLE_STYLING);
        myFrame = 0;

        HBox myConsoleView = new HBox();
        myConsoleView.setPadding(new Insets(0,10,0,10));

        myConsoleView.prefWidthProperty().bind(this.widthProperty());

        myFrameTextLabel = new Label(FRAME_NUMBER_TEXT);
        myErrorLabel = new Label("Simulation running properly");
        myFrameNumberLabel = new Label(String.valueOf(myFrame));

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        myConsoleView.getChildren().addAll(myErrorLabel,region1,myFrameTextLabel,myFrameNumberLabel);

        getChildren().add(myConsoleView);

        setPrefHeight(30);

    }
    public void logError(String error){
        myErrorLabel.setText(error);
    }
    public void incrementFrame(){
        myFrameNumberLabel.setText(String.valueOf(++myFrame));
    }
}
