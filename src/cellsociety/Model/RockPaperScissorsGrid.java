package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Controller.SimType;
import cellsociety.Main;

import java.awt.Point;
import java.util.*;

/**
 * Class for Cells of the Percolation type
 *
 * @author Thomas Quintanilla
 */

public class RockPaperScissorsGrid extends Grid{

  private static final int DEFAULT_VALUE = 1;
  private static final int DEFAULT_STREAK = 2;
  private static final int ROCK_DEFAULT = 33;
  private static final int PAPER_DEFAULT = 33;
  private static ResourceBundle RESOURCES = Main.myResources;
  private static final int MAX_VAL = SimType.of(RESOURCES.getString("RockPaperScissors")).getMaxVal();;
  private static final int  HEXAGONAL = 1;

  /**
   * Uses gridMap to construct RPS grid and define winning/losing threshold
   *
   * @param gridMap:    Map with KVP of a coordinate point to an int, which represents the state to
   *                    construct cell with.
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   */
  public RockPaperScissorsGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    int threshold = cellValues.getOrDefault(RESOURCES.getString("RPSThreshold"), DEFAULT_STREAK);
    for (Point p: getPointList()) {
      if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
        pointCellMap.put(p,
            new RockPaperScissorsCell(
                gridMap.getOrDefault(p, (int) (Math.random() * MAX_VAL)), threshold));
      } else if (cellValues.get(RESOURCES.getString("GridType"))
          .compareTo(GridParser.PARAMETRIZED_RANDOM) >= 0) {
        parametrizedRandomGenerator(cellValues, threshold, p);
      } else {
        pointCellMap.put(p,
            new RockPaperScissorsCell(gridMap.getOrDefault(p, DEFAULT_VALUE), threshold));
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
   * @param cellValues : Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   * @param p xy coordinates of generated cell
   */
  private void parametrizedRandomGenerator(Map<String, Integer> cellValues, int threshold, Point p) {
    double rock = cellValues.getOrDefault(RESOURCES.getString("Rock"), ROCK_DEFAULT) / 100.0;
    double paper = cellValues.getOrDefault(RESOURCES.getString("Paper"), PAPER_DEFAULT) / 100.0;

    double rand = Math.random();
    if (rand < rock) {
      pointCellMap.put(p, new RockPaperScissorsCell(RockPaperScissorsCell.ROCK, threshold));
    } else if (rand - rock < (1-rock) * paper) {
      pointCellMap.put(p, new RockPaperScissorsCell(RockPaperScissorsCell.PAPER, threshold));
    } else {
      pointCellMap.put(p, new RockPaperScissorsCell(RockPaperScissorsCell.SCISSORS, threshold));
    }
  }
  /**
   * Returns the maximum state allowed for a particular simulation
   */
  public int getMaxState() {
    return MAX_VAL;
  }
}
