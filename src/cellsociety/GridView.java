package cellsociety;

import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class GridView extends GridPane {

    public GridView(int numRows){
        super();
        setHgap(10);
        setVgap(10);
        setConstraints(numRows);
        /*
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Rectangle adding = new Rectangle(50,50);
                //adding.widthProperty().bind(this.widthProperty().divide(10));
                add(adding,i,j);
            }
        }
        */
        Pane root = new Pane();
        Rectangle adding = new  Rectangle(50,50);
        adding.widthProperty().bind(root.widthProperty());
        root.getChildren().add(adding);
        Rectangle adding2 = new  Rectangle(50,50);
        add(root,3,3);
        add(adding2,8,8);
        setPrefHeight(800);
        setPrefWidth(800);
        this.setStyle("-fx-background-color: green");
    }
    private void setConstraints(int numRows){
        double percentageColumn = 100.0 / numRows;
        for(int i = 0; i < numRows; i++){
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            getColumnConstraints().add(cc);
        }
        for(int i = 0; i < numRows; i++){
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            getRowConstraints().add(rc);
        }
    }
}
