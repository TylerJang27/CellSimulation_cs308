package cellsociety;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class DashboardView extends Pane {
    public DashboardView(){
        super();
        HBox myDashBoard = new HBox();
        myDashBoard.getChildren().add(new Rectangle(100,100));
        getChildren().add(myDashBoard);
        this.setStyle("-fx-background-color: red");
    }
}
