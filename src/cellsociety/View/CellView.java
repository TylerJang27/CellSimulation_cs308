package cellsociety.View;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellView extends Pane {
    private Rectangle myDisplay;
    public CellView(){
        super();
        myDisplay = new Rectangle();
        myDisplay.widthProperty().bind(this.widthProperty());
        myDisplay.heightProperty().bind(this.heightProperty());
        this.getChildren().add(myDisplay);
    }
    public void changeState(int state){
        myDisplay.setFill(Color.RED);
    }
}
