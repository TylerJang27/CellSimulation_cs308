package cellsociety.View;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that implements a GridView with hexagonal-shaped Cells.
 *
 * @author Mariusz Derezinski-Choo
 */
public class HexagonGridView extends GridView{
    public static final int DEFAULT_SIDE_LENGTH = 40;
    private Pane myGrid;
    private double mySideLength;
    private double myGridLineWidth;
    private Map<Point2D, CellView> myCells;


    /**
     * Construct a Hexagonal Grid View, which consists of a grid of hexagon shaped Cells
     * @param numRows the number of rows in the Grid
     * @param numColumns the number of columns in the grid
     * @param outlineWidth the width of the outline for the grid
     * @param cellClickedHandler the EventHandler that is triggered when a cell is clicked
     * @param cellStateConfigs A list of configurations with which the CellStates for each cell will be constructed
     */
    public HexagonGridView(int numRows, int numColumns, String outlineWidth, EventHandler<CellClickedEvent> cellClickedHandler, List<CellStateConfiguration> cellStateConfigs) {
        super();
        myGrid = new Pane();
        myGridLineWidth = Double.parseDouble(outlineWidth);
        myCells = new HashMap<>();
        getSideLength(cellStateConfigs);
        VBox evenElements = renderElements(numRows, numColumns, cellClickedHandler, cellStateConfigs, mySideLength + myGridLineWidth, 0, mySideLength, .5);

        VBox oddElements = renderElements(numRows, numColumns, cellClickedHandler, cellStateConfigs, 2 * (mySideLength + myGridLineWidth), 1, 2.5 * mySideLength, 2);
        myGrid.getChildren().addAll(evenElements, oddElements);
    }

    VBox evenElements = new VBox(0);

    //TODO: Fix repeated code
    Region topSpacer = new Region();
    topSpacer.setPrefHeight((mySideLength + myGridLineWidth) * Math.sqrt(3) / 2.0);
    evenElements.getChildren().add(topSpacer);
    for (int i = 0; i < numRows; i += 2) {
      HBox firstRow = new HBox();
      Region leftSpacer = new Region();
      leftSpacer.setPrefWidth(mySideLength + .5 * myGridLineWidth);
      firstRow.getChildren().add(leftSpacer);
      for (int j = 0; j < numColumns; j += 2) {
        int finalI = i;
        int finalJ = j;

        CellView addingCell = new CellView(cellStateConfigs);
        myCells.put(new Point2D(i, j), addingCell);
        addingCell.setOnMouseClicked(e -> {
          addingCell.fireEvent(new CellClickedEvent(addingCell, finalI, finalJ));
        });
        addingCell.addEventHandler(CellClickedEvent.CUSTOM_EVENT_TYPE, cellClickedHandler);
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
    topSpacerOdd.setPrefHeight((2 * (mySideLength + myGridLineWidth)) * Math.sqrt(3) / 2.0);
    oddElements.getChildren().add(topSpacerOdd);
    for (int i = 1; i < numRows; i += 2) {
      HBox firstRow = new HBox();
      Region leftSpacer = new Region();
      leftSpacer.setPrefWidth(2.5 * mySideLength + 2 * myGridLineWidth);
      firstRow.getChildren().add(leftSpacer);
      for (int j = 1; j < numColumns; j += 2) {
        int finalI = i;
        int finalJ = j;

        CellView addingCell = new CellView(cellStateConfigs);
        myCells.put(new Point2D(i, j), addingCell);
        addingCell.setOnMouseClicked(e -> {
          addingCell.fireEvent(new CellClickedEvent(addingCell, finalI, finalJ));
        });
        addingCell.addEventHandler(CellClickedEvent.CUSTOM_EVENT_TYPE, cellClickedHandler);

        Pane addingPane = new Pane();
        addingPane.getChildren().add(addingCell);
        firstRow.getChildren().add(addingPane);

        Region spacer = new Region();
        spacer.setPrefWidth(mySideLength);
        firstRow.getChildren().add(spacer);
      }
      oddElements.getChildren().add(firstRow);
    }

    @Override
    public Map<String, Integer> getCellCounts() {
        Map<String, Integer> cellCounts = new HashMap<>();
        for(Point2D location : myCells.keySet()){
            CellView tempCell = myCells.get(location);
            cellCounts.putIfAbsent(tempCell.getCellState(),0);
            cellCounts.put(tempCell.getCellState(), cellCounts.get(tempCell.getCellState()) + 1);
        }
        return cellCounts;
    }
    System.out.println(cellCounts);
    return cellCounts;
  }



    private VBox renderElements(int numRows, int numColumns, EventHandler<CellClickedEvent> cellClickedHandler, List<CellStateConfiguration> cellStateConfigs, double v, int i2, double v2, double i3) {
        VBox oddElements = getBlankBox(v);
        for (int i = i2; i < numRows; i += 2) {
            HBox rowBox = new HBox();
            addHorizontalRegion(rowBox, v2 + i3 * myGridLineWidth);
            for (int j = i2; j < numColumns; j += 2) {
                CellView addingCell = addCellToGrid(cellClickedHandler, cellStateConfigs, i, j);
                Pane addingPane = new Pane();
                addingPane.getChildren().add(addingCell);
                rowBox.getChildren().add(addingPane);
                addHorizontalRegion(rowBox, mySideLength);
            }
            oddElements.getChildren().add(rowBox);
        }
        return oddElements;
    }

    private void addHorizontalRegion(HBox box, double width){
        Region spacer = new Region();
        spacer.setPrefWidth(width);
        box.getChildren().add(spacer);
    }

    private VBox getBlankBox(double verticalDistance) {
        VBox evenElements = new VBox(0);
        Region topSpacer = new Region();
        topSpacer.setPrefHeight((verticalDistance) * Math.sqrt(3) / 2.0);
        evenElements.getChildren().add(topSpacer);
        return evenElements;
    }

    private CellView addCellToGrid(EventHandler<CellClickedEvent> cellClickedHandler, List<CellStateConfiguration> cellStateConfigs, int i, int j) {
        int finalI = i;
        int finalJ = j;

        CellView addingCell = new CellView(cellStateConfigs);
        myCells.put(new Point2D(i, j), addingCell);
        addingCell.setOnMouseClicked(e -> {
            addingCell.fireEvent(new CellClickedEvent(addingCell, finalI, finalJ));
        });
        addingCell.addEventHandler(CellClickedEvent.CUSTOM_EVENT_TYPE, cellClickedHandler);
        return addingCell;
    }

    private void getSideLength(List<CellStateConfiguration> cellStateConfigs) {
        try {
            mySideLength = Double.parseDouble(cellStateConfigs.get(0).getParameters().get("sideLength"));
        } catch (NullPointerException e) {
            mySideLength = DEFAULT_SIDE_LENGTH;
        }
    }
}
