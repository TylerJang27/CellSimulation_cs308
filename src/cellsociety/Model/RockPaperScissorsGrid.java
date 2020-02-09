package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Main;
import jdk.jfr.Threshold;

import java.awt.Point;
import java.util.*;

public class RockPaperScissorsGrid extends Grid{

  private static final int DEFAULT_VALUE = 1;
  private static final int DEFAULT_STREAK = 3;
  private static final int ROCK_DEFAULT = 33;
  private static final int PAPER_DEFAULT = 50;
  private ResourceBundle RESOURCES = Main.myResources;
  public static final int MAX_VAL = 3;

  public RockPaperScissorsGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    int threshold = cellValues.getOrDefault(RESOURCES.getString("RPSThreshold"), DEFAULT_STREAK);
    for (int y = 0; y < myHeight; y++) {
      for (int x = 0; x < myWidth; x++) {
        Point p = new Point(x, y);
        if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
          pointCellMap.put(p,
                  new RockPaperScissorsCell(gridMap.getOrDefault(p, (int) (Math.random() * MAX_VAL) + 1), threshold));
        } else if (cellValues.get(RESOURCES.getString("GridType")).compareTo(GridParser.PARAMETRIZED_RANDOM) >= 0) {
          parametrizedRandomGenerator(cellValues, threshold, p);
        } else {
          pointCellMap.put(p, new RockPaperScissorsCell(gridMap.getOrDefault(p, DEFAULT_VALUE), threshold));
        }
      }
    }
    buildSquareNeighbors();
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
  @Override
  public int getMaxState() {
    return MAX_VAL;
  }
}
