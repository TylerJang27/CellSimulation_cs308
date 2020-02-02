package cellsociety.View;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collection;

public class GridView extends GridPane {
    private Collection<CellView> myCells;

    public GridView(int numRows, int numColumns,double width, double height){
        super();
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10,10,10,10));
        setConstraints(numRows);
        //TODO: HashMap implementation to make updateCell() O(1) (right now it is O(n^2), doesn't really matter since the size is small enough for it to be fine but still good idea to change it
        myCells = new ArrayList<>();
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){
                CellView adding = new CellView();
                myCells.add(adding);
                // will be deleted later, keeping for now for testing
                /*
                adding.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("clicked");
                        updateCell(2,3,1);
                    }
                });
                */

                add(adding,i,j);
            }
        }

        setPrefHeight(width);
        setPrefWidth(height);
        this.setStyle("-fx-background-color: green");
    }
    public void updateCell(int row, int column, int state){
        for(CellView cell : myCells){
            if(getColumnIndex(cell) == column && getRowIndex(cell) == row){
                cell.changeState(state);
            }
        }
    }
    private void setConstraints(int numRows){
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
