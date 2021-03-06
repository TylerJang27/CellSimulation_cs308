package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Controller.SimType;
import cellsociety.Main;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for Grids of the Segregation type
 *
 * @author Thomas Quintanilla
 */
public class SegregationGrid extends Grid {

  private static final int DEFAULT_THRESHOLD = 30;
  private static final int UNSATISFIED = 2;
  private static final int DEFAULT_RED = 50;
  private static final int DEFAULT_EMPTY = 25;
  private static final int HEXAGONAL = 1;

  private static ResourceBundle RESOURCES = Main.myResources;
  private static final int MAX_VAL = SimType.of(RESOURCES.getString("Segregation")).getMaxVal();

  /**
   * Uses gridMap to construct Segregation and gridcell values to set cells at points.
   *
   * @param gridMap:    Map with KVP of a coordinate point to an int, which represents the state to
   *                    construct cell with.
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   */
  public SegregationGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    for (Point p : getPointList()) {
      if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) {
        pointCellMap.put(p,
            new SegregationCell(gridMap.getOrDefault(p, (int) (Math.random() * (1 + MAX_VAL))),
                (double) cellValues
                    .getOrDefault(RESOURCES.getString("Similar"), DEFAULT_THRESHOLD) / 100.0));
      } else if (
          cellValues.get(RESOURCES.getString("GridType")).compareTo(GridParser.PARAMETRIZED_RANDOM)
              >= 0) {
        parametrizedRandomGenerator(cellValues, p);
      } else {
        pointCellMap.put(p, new SegregationCell(gridMap.getOrDefault(p, 0),
            (double) cellValues.getOrDefault(RESOURCES.getString("Similar"), DEFAULT_THRESHOLD)
                / 100.0));
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
   *
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   * @param p           xy coordinates of generated cell
   */
  private void parametrizedRandomGenerator(Map<String, Integer> cellValues, Point p) {
    double threshold =
        cellValues.getOrDefault(RESOURCES.getString("Similar"), DEFAULT_THRESHOLD) / 100.0;
    double red_portion = cellValues.getOrDefault(RESOURCES.getString("Red"), DEFAULT_RED) / 100.0;
    double empty = cellValues.getOrDefault(RESOURCES.getString("Empty"), DEFAULT_EMPTY) / 100.0;

    double rand = Math.random();
    if (rand < empty) {
      pointCellMap.put(p, new SegregationCell(SegregationCell.EMPTY, threshold));
    } else if (rand - empty < (1 - empty) * red_portion) {
      pointCellMap.put(p, new SegregationCell(SegregationCell.RED, threshold));
    } else {
      pointCellMap.put(p, new SegregationCell(SegregationCell.BLUE, threshold));
    }
  }

  /**
   * Creates list of all unsatisfied cells in the grid then places each of them in a random empty
   * cell
   */
  @Override
  public void nextFrame() {
    addFrame();
    ArrayList<Point> unsatisfiedPoints = new ArrayList<>();
    for (Point p : pointCellMap.keySet()) {
      Cell c = pointCellMap.get(p);
      if (c.calculateNextState() == UNSATISFIED) {
        unsatisfiedPoints.add(p);
      }
    }
    for (Point unsatisfiedP : unsatisfiedPoints) {
      while (pointCellMap.get(unsatisfiedP).getState() != 0) {
        Point newP = new Point((int) (Math.random() * myWidth), (int) (Math.random() * myHeight));
        if (pointCellMap.get(newP).getState() == 0) {
          pointCellMap.get(newP).updateState(pointCellMap.get(unsatisfiedP).getState());
          pointCellMap.get(unsatisfiedP).updateState(0);
        }
      }
    }
  }

  /**
   * Returns the maximum state allowed for a particular simulation
   */
  public int getMaxState() {
    return MAX_VAL;
  }
}
