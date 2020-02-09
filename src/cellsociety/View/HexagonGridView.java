package cellsociety.View;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class HexagonGridView extends GridView{


    private Pane myGrid;
    private double mySideLength;

    public HexagonGridView(double sideLength){
        super();
        myGrid = new Pane();
        mySideLength = sideLength;

        VBox evenElements = new VBox(0);


        for(int i = 0; i < 5; i++) {
            HBox firstRow = new HBox();
            for (int j = 0; j < 5; j++) {
                firstRow.getChildren().add(makeHexagon(sideLength));
                Region spacer = new Region();
                spacer.setPrefWidth(sideLength);
                firstRow.getChildren().add(spacer);
            }
            evenElements.getChildren().add(firstRow);
        }
        VBox oddElements = new VBox(0);
        Region topSpacer = new Region();
        topSpacer.setPrefHeight((mySideLength + gridLineWidth) * Math.sqrt(3) / 2.0);
        oddElements.getChildren().add(topSpacer);
        for(int i = 0; i < 4; i++) {
            HBox firstRow = new HBox();
            Region leftSpacer = new Region();
            leftSpacer.setPrefWidth(1.5 * (sideLength + gridLineWidth));
            firstRow.getChildren().add(leftSpacer);
            for (int j = 0; j < 4; j++) {
                Shape testing = makeHexagon(sideLength);
                testing.setFill(Color.RED);
                firstRow.getChildren().add(testing);
                Region spacer = new Region();
                spacer.setPrefWidth(sideLength);
                firstRow.getChildren().add(spacer);
            }
            oddElements.getChildren().add(firstRow);
        }
        StackPane.setAlignment(oddElements, Pos.CENTER);
        StackPane.setAlignment(evenElements, Pos.CENTER);
        myGrid.getChildren().addAll(evenElements, oddElements);
    }

    @Override
    public void updateCell(int row, int column, int state) {

    }

    @Override
    public Node getNode() {
        return myGrid;
    }


}
