package cellsociety;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static javafx.geometry.Pos.BASELINE_RIGHT;

public class ConsoleView extends Pane {
    public static final String FX_BACKGROUND_COLOR_BAFFE_0 = "-fx-background-color: #baffe0";
    private Label myErrorLabel;
    private Label myFrameTextLabel;
    private Label myFrameNumberLabel

    public ConsoleView(){
        super();
        this.setStyle(FX_BACKGROUND_COLOR_BAFFE_0);
        HBox myConsoleView = new HBox();
        myConsoleView.prefWidthProperty().bind(this.widthProperty());
        Label f = new Label("Frame Number: ");
        
        Region region1 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        myConsoleView.getChildren().addAll(region1,myText);

        getChildren().add(myConsoleView);

        setPrefHeight(30);

    }
}
