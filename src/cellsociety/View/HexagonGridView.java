package cellsociety.View;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Map;

public class HexagonGridView extends GridView{


    private Pane myGrid;
    private double mySideLength;
    private double myGridLineWidth;


    public HexagonGridView(int numRows, int numColumns, double width, double length, String outlineWidth, EventHandler<CellClickedEvent> myCellClickedHandler, List<CellStateConfiguration> cellStateConfigs) {
        super();
        myGrid = new Pane();
        myGridLineWidth = Double.parseDouble(outlineWidth);
        mySideLength = Double.parseDouble(cellStateConfigs.get(0).getParameters().get("sideLength"));

        VBox evenElements = new VBox(0);

        //TODO: Fix repeated code
        for(int i = 0; i < numRows; i += 2) {
            HBox firstRow = new HBox();
            for (int j = 0; j < numColumns; j++) {
                firstRow.getChildren().add(new CellView(cellStateConfigs));
                Region spacer = new Region();
                spacer.setPrefWidth(mySideLength);
                firstRow.getChildren().add(spacer);
            }
            evenElements.getChildren().add(firstRow);
        }

        VBox oddElements = new VBox(0);
        Region topSpacer = new Region();
        topSpacer.setPrefHeight((mySideLength + myGridLineWidth) * Math.sqrt(3) / 2.0);
        oddElements.getChildren().add(topSpacer);
        for(int i = 1; i < numRows; i+=2) {
            HBox firstRow = new HBox();
            Region leftSpacer = new Region();
            leftSpacer.setPrefWidth(1.5 * (mySideLength + myGridLineWidth));
            firstRow.getChildren().add(leftSpacer);
            for (int j = 0; j < 4; j+=2) {
                firstRow.getChildren().add(new CellView(cellStateConfigs));
                Region spacer = new Region();
                spacer.setPrefWidth(mySideLength);
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

    @Override
    public Map<String, Integer> getCellCounts() {
        return null;
    }


}
