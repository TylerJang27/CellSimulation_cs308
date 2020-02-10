package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Controller.SimType;
import cellsociety.Main;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for Grids of the Percolation type
 *
 * @author Thomas Quintanilla
 */
public class PercolationGrid extends Grid {

  private static final int BLOCKED_DEFAULT = 30;
  private static final int FILLED_DEFAULT = 20;
  private static ResourceBundle RESOURCES = Main.myResources;
  private static int MAX_VAL = SimType.of(RESOURCES.getString("Percolation")).getMaxVal();;
  private static final int HEXAGONAL = 1;

  /**
   * Uses gridMap to construct Percolation and gridcell values to set cells at points.
   *
   * @param gridMap:    Map with KVP of a coordinate point to an int, which represents the state to
   *                    construct cell with.
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   */
  public PercolationGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    for (Point p: getPointList()) {
      if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
        pointCellMap.put(p,
                new PercolationCell(gridMap.getOrDefault(p, (int) (Math.random() * (1 + MAX_VAL)))));
      } else if (cellValues.get(RESOURCES.getString("GridType")).compareTo(GridParser.PARAMETRIZED_RANDOM) >= 0) {
        parametrizedRandomGenerator(cellValues, p);
      } else {
        pointCellMap.put(p, new PercolationCell(gridMap.getOrDefault(p, 0)));
      }
    }
    if (getCellShape() == HEXAGONAL) {
      bottomHexNeighborGenerator();
    } else {
      bottomSquareNeighborGenerator();
    }
  }

  /**
   * Generates a cell based on defined parameters in cellValues
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   * @param p xy coordinates of generated cell
   */
  private void parametrizedRandomGenerator(Map<String, Integer> cellValues, Point p) {
    double blocked = cellValues.getOrDefault(RESOURCES.getString("Blocked"), BLOCKED_DEFAULT) / 100.0;
    double filled = cellValues.getOrDefault(RESOURCES.getString("Filled"), FILLED_DEFAULT) / 100.0;
    double rand = Math.random();
    if (rand < blocked) {
      pointCellMap.put(p, new PercolationCell(PercolationCell.CLOSED));
    } else if (rand - blocked < (1-blocked) * filled) {
      pointCellMap.put(p, new PercolationCell(PercolationCell.FILLED));
    } else {
      pointCellMap.put(p, new PercolationCell(PercolationCell.OPENED));
    }
  }

  /**
   * Returns the maximum state allowed for a particular simulation
   */
  public int getMaxState() {
    return MAX_VAL;
  }
}
