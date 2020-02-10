package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Controller.SimType;
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
  private static int MAX_VAL = SimType.of(RESOURCES.getString("GameOfLife")).getMaxVal();;
  private static final int COVERAGE_DEFAULT = 50;
  private static final int HEXAGONAL = 1;

  /**
   * Uses gridMap to construct GameOfLife and gridcell values to set cells at points based on
   * shape of cells.
   *
   * @param gridMap:    Map with KVP of a coordinate point to an int, which represents the state to
   *                    construct cell with.
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   */
  public GameOfLifeGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    for (Point p: getPointList()) {
      if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
        pointCellMap.put(p, new GameOfLifeCell((int) (Math.random() * (1 + MAX_VAL))));
      } else if (cellValues.get(RESOURCES.getString("GridType")).compareTo(GridParser.PARAMETRIZED_RANDOM) >= 0) {
        parametrizedRandomGenerator(cellValues, p);
      } else {
        pointCellMap.put(p, new GameOfLifeCell(gridMap.getOrDefault(p, 0)));
      }
    }
    if (getCellShape() == HEXAGONAL) {
      buildHexagonNeighbors();
    } else {
      buildSquareNeighbors();
    }
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

  /**
   * Returns the maximum state allowed for a particular simulation
   */
  public int getMaxState() {
    return MAX_VAL;
  }
}
