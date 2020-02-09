package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Main;
import java.awt.Point;
import java.util.*;

public class RockPaperScissorsGrid extends Grid{

  private ResourceBundle RESOURCES = Main.myResources;
  public static final int MAX_VAL = 3;
  private static final int  HEXAGONAL = 1;

  public RockPaperScissorsGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    int threshold = cellValues.getOrDefault(RESOURCES.getString("RPSThreshold"), 3);
    for (int y = 0; y < myHeight; y++) {
      for (int x = 0; x < myWidth; x++) {
        Point p = new Point(x, y);
        if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
          pointCellMap.put(p,
              new RockPaperScissorsCell(gridMap.getOrDefault(p, (int) (Math.random() *  MAX_VAL)+1), threshold));
        } else {
          pointCellMap.put(p, new RockPaperScissorsCell(gridMap.getOrDefault(p, 1), threshold));
        }
      }
    }
    if (getCellShape() == HEXAGONAL) {
      buildHexagonNeighbors();
    } else {
      buildSquareNeighbors();
    }
  }
}
