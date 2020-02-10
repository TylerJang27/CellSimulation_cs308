package cellsociety.View;

import java.util.*;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * An implementation of GridView that utilizes rectangles as the shape for all cells
 *
 * @author Mariusz Derezinski-Choo
 */
public class RectangleGridView extends GridView {

  private static final double GRID_PADDING = 10;

  private GridPane myGrid;
  private Collection<CellView> myCells;

  /**
   * Construct a GridView object
   *
   * @param numRows    the number of rows in the grid
   * @param numColumns the number of columns in the grid
   */
  public RectangleGridView(int numRows, int numColumns, String outlineWidth, EventHandler<CellClickedEvent> cellClickedHandler, List<CellStateConfiguration> stateConfigs) {
    super();
    setUpGrid(outlineWidth);
    setConstraints(numRows, numColumns);

    myCells = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        initializeCellView(cellClickedHandler, stateConfigs, i, j);
      }
    }
  }

  /**
   * update the appearance of a cell to the specified state
   *
   * @param row    the row of the cell to be modified
   * @param column the column of the cell to be modified
   * @param state  the state that the cell should be changed to
   */
  public void updateCell(int row, int column, int state) {
    for (CellView cell : myCells) {
      if (myGrid.getColumnIndex(cell) == column && myGrid.getRowIndex(cell) == row) {
        cell.changeState(state);
      }
    }
  }

  @Override
  public Node getNode() {
    return myGrid;
  }

  @Override
  public Map<String, Integer> getCellCounts() {
    Map<String, Integer> cellCounts = new HashMap<>();

    for(CellView cell : myCells){
      String cellState = cell.getCellState();
      cellCounts.putIfAbsent(cellState, 0);
      cellCounts.put(cellState, cellCounts.get(cellState) + 1);
    }
    return cellCounts;
  }

  /**
   * set constraints on the rows and columns of the GridPane so that the elements scale with the
   * size of the grid
   *
   * @param numRows    the number of rows in the Grid
   * @param numColumns the number of Columns in the Grid
   */
  private void setConstraints(int numRows, int numColumns) {
    for (int i = 0; i < numColumns; i++) {
      ColumnConstraints cc = new ColumnConstraints();
      cc.setHgrow(Priority.ALWAYS);
      myGrid.getColumnConstraints().add(cc);
    }
    for (int i = 0; i < numRows; i++) {
      RowConstraints rc = new RowConstraints();
      rc.setVgrow(Priority.ALWAYS);
      myGrid.getRowConstraints().add(rc);
    }
  }

  private void initializeCellView(EventHandler<CellClickedEvent> cellClickedHandler, List<CellStateConfiguration> stateConfigs, int i, int j) {
    CellView adding = new CellView(stateConfigs);
    int finalI = i;
    int finalJ = j;
    adding.setOnMouseClicked(e -> {
      adding.fireEvent(new CellClickedEvent(adding, finalI, finalJ));
    });
    adding.addEventHandler(CellClickedEvent.CUSTOM_EVENT_TYPE, cellClickedHandler);
    myCells.add(adding);
    myGrid.add(adding, j, i);
  }

  private void setUpGrid(String outlineWidth) {
    myGrid = new GridPane();
    myGrid.getStyleClass().add(GridView.GRID_CSS_CLASS);
    double cellOutlineWidth = Double.parseDouble(outlineWidth);
    myGrid.setHgap(cellOutlineWidth);
    myGrid.setVgap(cellOutlineWidth);
    myGrid.setPadding(new Insets(GRID_PADDING, GRID_PADDING, GRID_PADDING, GRID_PADDING));
  }
}
