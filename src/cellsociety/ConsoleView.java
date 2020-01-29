package cellsociety;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ConsoleView extends Pane {

    public ConsoleView(){
        super();
        this.setStyle("-fx-background-color: blue");
        getChildren().add(new Rectangle(300,300));
    }
}
