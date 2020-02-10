package cellsociety.View;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexagonGridView extends GridView{


    public static final int DEFAULT_SIDE_LENGTH = 20;
    private Pane myGrid;
    private double mySideLength;
    private double myGridLineWidth;
    private Map<Point2D, CellView> myCells;
    int numItems;


    public HexagonGridView(int numRows, int numColumns, double width, double length, String outlineWidth, EventHandler<CellClickedEvent> myCellClickedHandler, List<CellStateConfiguration> cellStateConfigs) {
        super();
        myGrid = new Pane();
        myGridLineWidth = Double.parseDouble(outlineWidth);
        myCells = new HashMap<>();
        try {
            mySideLength = Double.parseDouble(cellStateConfigs.get(0).getParameters().get("sideLength"));
        } catch (NullPointerException e) {
            mySideLength = DEFAULT_SIDE_LENGTH;
        }

        VBox evenElements = new VBox(0);

        //TODO: Fix repeated code
        Region topSpacer = new Region();
        topSpacer.setPrefHeight((mySideLength + myGridLineWidth) * Math.sqrt(3) / 2.0);
        evenElements.getChildren().add(topSpacer);
        for(int i = 0; i < numRows; i += 2) {
            HBox firstRow = new HBox();
            Region leftSpacer = new Region();
            leftSpacer.setPrefWidth(mySideLength + .5 * myGridLineWidth);
            firstRow.getChildren().add(leftSpacer);
            for (int j = 0; j < numColumns; j+=2) {
                int finalI = i;
                int finalJ = j;

                CellView addingCell = new CellView(cellStateConfigs);
                myCells.put(new Point2D(i,j), addingCell);
                addingCell.setOnMouseClicked(e -> {
                    addingCell.fireEvent(new CellClickedEvent(addingCell, finalI, finalJ));
                });
                Pane addingPane = new Pane();
                addingPane.getChildren().add(addingCell);
                firstRow.getChildren().add(addingPane);
                Region spacer = new Region();
                spacer.setPrefWidth(mySideLength);
                firstRow.getChildren().add(spacer);
                System.out.println(i + " " + j);
            }
            evenElements.getChildren().add(firstRow);
        }

        VBox oddElements = new VBox(0);
        Region topSpacerOdd = new Region();
        topSpacerOdd.setPrefHeight((2* (mySideLength + myGridLineWidth) )* Math.sqrt(3) / 2.0);
        oddElements.getChildren().add(topSpacerOdd);
        for(int i = 1; i < numRows; i+=2) {
            HBox firstRow = new HBox();
            Region leftSpacer = new Region();
            leftSpacer.setPrefWidth(2.5 * mySideLength + 2 * myGridLineWidth);
            firstRow.getChildren().add(leftSpacer);
            for (int j = 1; j < numColumns; j+=2) {
                int finalI = i;
                int finalJ = j;

                CellView addingCell = new CellView(cellStateConfigs);
                myCells.put(new Point2D(i,j), addingCell);
                addingCell.setOnMouseClicked(e -> {
                    addingCell.fireEvent(new CellClickedEvent(addingCell, finalI, finalJ));
                });
                Pane addingPane = new Pane();
                addingPane.getChildren().add(addingCell);
                firstRow.getChildren().add(addingPane);

                Region spacer = new Region();
                spacer.setPrefWidth(mySideLength);
                firstRow.getChildren().add(spacer);
            }
            oddElements.getChildren().add(firstRow);
        }
        myGrid.getChildren().addAll(evenElements, oddElements);
        myGrid.setPrefSize(700,700);
    }

    @Override
    public void updateCell(int row, int column, int state) {
        myCells.get(new Point2D(row, column)).changeState(state);
    }

    @Override
    public Node getNode() {
        return myGrid;
    }

    @Override
    public Map<String, Integer> getCellCounts() {
        Map<String, Integer> cellCounts = new HashMap<>();
        for(Point2D location : myCells.keySet()){
            CellView tempCell = myCells.get(location);
            cellCounts.putIfAbsent(tempCell.getCellState(),0);
            cellCounts.put(tempCell.getCellState(), cellCounts.get(tempCell.getCellState()) + 1);
        }
        System.out.println(cellCounts);
        return cellCounts;
    }


}
