package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Main;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for Grids of the Fire type
 *
 * @author Thomas Quintanilla
 */
public class FireGrid extends Grid {

  private ResourceBundle RESOURCES = Main.myResources;
  public static final int MAX_VAL = 2;
  private static final int TREE_DEFAULT = 50;
  private static final int BURNING_DEFAULT = 15;

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
    double chanceToBurn = (double) gridMap.getOrDefault(RESOURCES.getString("Fire"), 50) / 100;

    for (int y = 0; y < myHeight; y++) {
      for (int x = 0; x < myWidth; x++) {
        Point p = new Point(x, y);
        if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
          pointCellMap.put(p,
                  new FireCell(gridMap.getOrDefault(p, (int) (Math.random() * (1 + MAX_VAL))),
                          chanceToBurn));
        } else if (cellValues.get(RESOURCES.getString("GridType")).compareTo(GridParser.PARAMETRIZED_RANDOM) >= 0) {
          parametrizedRandomGenerator(cellValues, chanceToBurn, p);
        } else {
          pointCellMap.put(p, new FireCell(gridMap.getOrDefault(p, 0), chanceToBurn));
        }
      }
    }
    buildNSEWNeighbors();
  }

  /**
   * Generates a cell based on defined parameters in cellValues
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   * @param chanceToBurn likelihood that a Tree will catch fire
   * @param p xy coordinates of generated cell
   */
  private void parametrizedRandomGenerator(Map<String, Integer> cellValues, double chanceToBurn, Point p) {
    double trees = cellValues.getOrDefault(RESOURCES.getString("Trees"), TREE_DEFAULT) / 100.0;
    double burning = cellValues.getOrDefault(RESOURCES.getString("Burning"), BURNING_DEFAULT) / 100.0;
    double rand = Math.random();
    if (rand < trees) {
      pointCellMap.put(p, new FireCell(FireCell.ALIVE, chanceToBurn));
    } else if (rand - trees < (1-trees) * burning) {
      pointCellMap.put(p, new FireCell(FireCell.BURNING, chanceToBurn));
    } else {
      pointCellMap.put(p, new FireCell(FireCell.EMPTY, chanceToBurn));
    }
  }

  /**
   * Returns the maximum state allowed for a particular simulation
   */
  @Override
  public int getMaxState() {
    return MAX_VAL;
  }
}
