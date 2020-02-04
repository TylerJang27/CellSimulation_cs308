package cellsociety.View;

import java.util.ArrayList;
import java.util.Collection;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


public class GridView extends GridPane {

  private static double GRID_PADDING = 10;
  private static double SIZE = 800;

  private Collection<CellView> myCells;

  /**
   * Construct a GridView object
   *
   * @param numRows    the number of rows in the grid
   * @param numColumns the number of columns in the grid
   * @param width      the width of the grid
   * @param height     the height of the grid
   */
  public GridView(int numRows, int numColumns, double width, double height) {

    super();

    setId("grid");

    setHgap(GRID_PADDING);
    setVgap(GRID_PADDING);
    setPadding(new Insets(GRID_PADDING, GRID_PADDING, GRID_PADDING, GRID_PADDING));
    setConstraints(numRows, numColumns);

    myCells = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        CellView adding = new CellView();
        myCells.add(adding);
        add(adding, i, j);
      }
    }

    setPrefHeight(SIZE);
    setPrefWidth(SIZE);

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
      if (getColumnIndex(cell) == column && getRowIndex(cell) == row) {
        cell.changeState(state);
      }
    }
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
      getColumnConstraints().add(cc);
    }
    for (int i = 0; i < numRows; i++) {
      RowConstraints rc = new RowConstraints();
      rc.setVgrow(Priority.ALWAYS);
      getRowConstraints().add(rc);
    }
  }
}
