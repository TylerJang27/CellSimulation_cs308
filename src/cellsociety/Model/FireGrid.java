package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Main;
import java.awt.Point;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for Grids of the Fire type
 *
 * @author Thomas Quintanilla
 */
public class FireGrid extends Grid {

  private ResourceBundle RESOURCES = Main.myResources;
  private static final int MAX_VAL = 2;

  /**
   * Uses gridMap to construct Fire grid and define fire chance percentage
   *
   * @param gridMap:    Map with KVP of a coordinate point to an int, which represents the state to
   *                    construct cell with.
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   */
  public FireGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    double chanceToBurn = (double) cellValues.getOrDefault(RESOURCES.getString("Fire"), 50) / 100;
    String shape = RESOURCES.getString("CellShape");
    for (int y = 0; y < myHeight; y++) {
      for (int x = 0; x < myWidth; x++) {
        Point p = new Point(x, y);
        if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
          pointCellMap.put(p,
              new FireCell(gridMap.getOrDefault(p, (int) (Math.random() * (1 + MAX_VAL))),
                  chanceToBurn));
        } else {
          pointCellMap.put(p, new FireCell(gridMap.getOrDefault(p, 0), chanceToBurn));
        }
      }
    }
    if (cellValues.get(shape).equals("hexagon"))
    buildNSEWNeighbors();
  }
}
