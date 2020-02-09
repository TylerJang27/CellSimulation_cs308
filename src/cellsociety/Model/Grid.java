package cellsociety.Model;

import cellsociety.Main;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for an abstraction of grid types, defining some basic functions
 *
 * @author Thomas Quintanilla
 */
public abstract class Grid {

  protected HashMap<Point, Cell> pointCellMap;
  protected int myWidth;
  protected int myHeight;
  private static ResourceBundle RESOURCES = Main.myResources;
  protected int myFrame;

  /**
   * Creates basic layout of grid as well as initializes map to store cells
   *
   * @param gridMap map that holds all parameters to construct grid, passed from subclass
   *                constructor
   */
  public Grid(Map<String, Integer> gridMap) {
    myWidth = gridMap.get(RESOURCES.getString("Width"));
    myHeight = gridMap.get(RESOURCES.getString("Height"));
    pointCellMap = new HashMap<>();
    myFrame = 0;
  }

  /**
   * adds NSEW adjacent cells in each cell's neighbor list by creating adjacent points and seeing if
   * they exist in Grid
   */
  protected void buildNSEWNeighbors() {
    for (Point p : pointCellMap.keySet()) {
      List<Point> potentialNeighbors = getNeighborPoints(p);
      for (Point neighbor : potentialNeighbors) {
        if (pointCellMap.containsKey(neighbor)) {
          pointCellMap.get(p).setNeighbor(pointCellMap.get(neighbor));
        }
      }
    }
  }

  /**
   * gets list of NSEW neighbors and returns it
   *
   * @param p Point where cell neighbors are checked in grid
   * @return list of neighbor point cells for NSEW
   */
  protected List<Point> getNeighborPoints(Point p) {
    int xPos = (int) p.getX();
    int yPos = (int) p.getY();
    Point left = xPos > 0 ? new Point(xPos - 1, yPos) : new Point(myWidth - 1, yPos);
    Point up = yPos > 0 ? new Point(xPos, yPos - 1) : new Point(xPos, myHeight - 1);
    Point right = xPos < myWidth - 1 ? new Point(xPos + 1, yPos) : new Point(0, yPos);
    Point down = yPos < myHeight - 1 ? new Point(xPos, yPos + 1) : new Point(xPos, 0);

    return Arrays.asList(left, up, right, down);
  }

  /**
   * Checks all surrounding cells (diagonals included) of a specific cell and builds neighbors
   */
  protected void buildSquareNeighbors() {
    for (Point p : pointCellMap.keySet()) {
      buildSquareSingleNeighbor(p);
    }
  }

  private void buildSquareSingleNeighbor(Point p) {
    int xPos = (int) p.getX();
    int yPos = (int) p.getY();

    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        int xOffset = x;
        int yOffset = y;
        if (xPos + xOffset < 0)  {
          xOffset = myWidth - 1;
        } else if (xPos + xOffset > myWidth-1){
          xOffset = -xPos;
        }
        if (yPos + yOffset < 0)  {
          yOffset = myHeight - 1;
        } else if (yPos + yOffset > myHeight - 1){
          yOffset = -yPos;
        }

        Point potentialNeighbor = new Point(xPos + xOffset, yPos + yOffset);
        if (!potentialNeighbor.equals(p)) {
          pointCellMap.get(p).setNeighbor(pointCellMap.get(potentialNeighbor));
        }
      }
    }
  }

  /**
   * Cycle's a cell's state on click, denoted by its row and column
   *
   * @param row row location of the cell
   * @param col column location of the cell
   * @return the cell's new state, in order to update view
   */
  public int cycleState(int row, int col) {
    Cell c = pointCellMap.get(new Point(row, col));
    int currState = c.getState();
    int newState = currState + 1;
    if (currState >= getMaxState()) {
      newState = 0;
    }
    c.setState(newState);
    return newState;
  }

  /**
   * Returns the maximum state allowed for a particular simulation
   */
  public abstract int getMaxState();

  //First calculates and stores new state of each cell
  //Then updates each cell's state

  public void nextFrame() {
    myFrame++;
    int[] states = new int[pointCellMap.values().size()];
    int index = 0;
    for (Cell c : pointCellMap.values()) {
      states[index] = c.calculateNextState();
      index++;
    }

    index = 0;
    for (Cell c : pointCellMap.values()) {
      c.updateState(states[index]);
      index++;
    }
  }

  //returns state of cell at point p
  public int getState(int r, int c) {
    Point p = new Point(r, c);
    return pointCellMap.get(p).getState();
  }

  //FIXME: COMMENT PLEASE
  public int getFrame() {
    return myFrame;
  }

  /**
   * Generates a list of points for a default hexagon setup
   * @return list of points
   */
  protected List<Point> hexPointGenerator() {
    List<Point> hexPoints = new ArrayList<Point>();
    for (int j = 0; j < myHeight; j ++) {
      if (j % 2 == 0) {
        for (int k = 0; k < myWidth; k += 2) {
          hexPoints.add(new Point(j, k));
        }
      } else {
        for (int k = 1; k < myWidth; k += 2) {
          hexPoints.add(new Point(j, k));
        }
      }
    }
    return hexPoints;
  }
}
