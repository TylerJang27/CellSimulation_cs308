package cellsociety.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HexagonGridView extends GridView {


  public static final int DEFAULT_SIDE_LENGTH = 40;
  private Pane myGrid;
  private double mySideLength;
  private double myGridLineWidth;
  private Map<Point2D, CellView> myCells;
  int numItems;


  public HexagonGridView(int numRows, int numColumns, double width, double length,
      String outlineWidth, EventHandler<CellClickedEvent> cellClickedHandler,
      List<CellStateConfiguration> cellStateConfigs) {
      super();
      myGrid = new Pane();
      myGridLineWidth = Double.parseDouble(outlineWidth);
      myCells = new HashMap<>();
      try {
          mySideLength = Double.parseDouble(cellStateConfigs.get(0).getParameters().get("sideLength"));
      } catch (NullPointerException e) {
          mySideLength = DEFAULT_SIDE_LENGTH;
      }
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
        return cellCounts;
    }
}
