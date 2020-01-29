package cellsociety;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class GridView extends GridPane {

    public GridView(int numRows, int numColumns){
        super();
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10,10,10,10));
        setConstraints(numRows);

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                Pane root = new Pane();
                Rectangle adding = new  Rectangle();
                adding.widthProperty().bind(root.widthProperty());
                adding.heightProperty().bind(root.heightProperty());
                root.getChildren().add(adding);
                add(root,i,j);
            }
        }

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
