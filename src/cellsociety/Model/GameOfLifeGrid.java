package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Main;
import java.awt.*;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for Grids of the Game of Life type
 *
 * @author Thomas Quintanilla
 */
public class GameOfLifeGrid extends Grid {

  private static ResourceBundle RESOURCES = Main.myResources;
  public static int MAX_VAL = 1;
  private static final int COVERAGE_DEFAULT = 50;

  /**
   * Uses gridMap to construct GameOfLife and gridcell values to set cells at points.
   *
   * @param gridMap:    Map with KVP of a coordinate point to an int, which represents the state to
   *                    construct cell with.
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   */
  public GameOfLifeGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    for (int y = 0; y < myHeight; y++) {
      for (int x = 0; x < myWidth; x++) {
        Point p = new Point(x, y);
        if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
          pointCellMap.put(p,
              new GameOfLifeCell(gridMap.getOrDefault(p, (int) (Math.random() * (1 + MAX_VAL)))));
        } else if (cellValues.get(RESOURCES.getString("GridType")).compareTo(GridParser.PARAMETRIZED_RANDOM) >= 0) {
          parametrizedRandomGenerator(cellValues, p);
        } else {
          pointCellMap.put(p, new GameOfLifeCell(gridMap.getOrDefault(p, 0)));
        }
      }
    }
    buildSquareNeighbors();
  }

  /**
   * Generates a cell based on defined parameters in cellValues
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   * @param p xy coordinates of generated cell
   */
  private void parametrizedRandomGenerator(Map<String, Integer> cellValues, Point p) {
    double coverage = cellValues.getOrDefault(RESOURCES.getString("Coverage"), COVERAGE_DEFAULT) / 100.0;
    double rand = Math.random();
    if (rand < coverage) {
      pointCellMap.put(p, new GameOfLifeCell(GameOfLifeCell.ALIVE));
    } else {
      pointCellMap.put(p, new GameOfLifeCell(GameOfLifeCell.DEAD));
    }
  }
}
