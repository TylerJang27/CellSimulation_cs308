package cellsociety.Model;

import cellsociety.Main;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for an abstraction of grid types, defining some basic functions
 *
 * @author Thomas Quintanilla
 */
public abstract class Grid {

  private static final int TOROIDAL = 1;
  protected HashMap<Point, Cell> pointCellMap;
  protected int myWidth;
  protected int myHeight;
  protected int myFrame;
  private static ResourceBundle RESOURCES = Main.myResources;
  private int gridShape;
  private int cellShape;

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
    gridShape = gridMap.getOrDefault(RESOURCES.getString("GridShape"), 0);
    cellShape = gridMap.getOrDefault(RESOURCES.getString("Shape"), 0);
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
    Point left = xPos > 0 || gridShape != TOROIDAL ? new Point(xPos - 1, yPos) : new Point(myWidth - 1, yPos);
    Point up = yPos > 0 || gridShape != TOROIDAL ? new Point(xPos, yPos - 1) : new Point(xPos, myHeight - 1);
    Point right = xPos < myWidth - 1 || gridShape != TOROIDAL ? new Point(xPos + 1, yPos) : new Point(0, yPos);
    Point down = yPos < myHeight - 1 || gridShape != TOROIDAL ? new Point(xPos, yPos + 1) : new Point(xPos, 0);

    return Arrays.asList(left, up, right, down);
  }


  protected void buildHexagonNeighbors() {
    for (Point p : pointCellMap.keySet()) {
      int xPos = (int) p.getX();
      int yPos = (int) p.getY();

      for (int x = -1; x <= 1; x++) {
        for (int y = -2; y <= 2; y++) {
          checkAndSetNeighbor(p, xPos, yPos, x, y);
        }
      }
    }
  }

  /**
   * Checks all surrounding cells (diagonals included) of a specific cell and builds neighbors
   */
  protected void buildSquareNeighbors() {
    for (Point p : pointCellMap.keySet()) {
      int xPos = (int) p.getX();
      int yPos = (int) p.getY();

      for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
          checkAndSetNeighbor(p, xPos, yPos, x, y);
        }
      }
    }
  }

  private void checkAndSetNeighbor(Point p, int xPos, int yPos, int x, int y) {
    int xOffset = gridShape == TOROIDAL ? getXOffset(xPos, x) : x;
    int yOffset = gridShape == TOROIDAL ? getYOffset(yPos, y) : y;

    Point potentialNeighbor = new Point(xPos + xOffset, yPos + yOffset);
    if (!potentialNeighbor.equals(p) && pointCellMap.containsKey(potentialNeighbor)) {
      pointCellMap.get(p).setNeighbor(pointCellMap.get(potentialNeighbor));
    }
  }


  private int getYOffset(int yPos, int yOffset) {
    int newOffset = yOffset;
    if (yPos + yOffset < 0)  {
      newOffset = myHeight + yOffset;
    } else if (yPos + yOffset > myHeight - 1){
      newOffset = -myHeight + yOffset;
    }
    return newOffset;
  }

  private int getXOffset(int xPos, int xOffset) {
    int newOffset = xOffset;
    if (xPos + xOffset < 0)  {
      newOffset = myWidth + 1;
    } else if (xPos + xOffset > myWidth-1){
      newOffset = -xPos;
    }
    return newOffset;
  }

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

  public int getCellShape() {
    return cellShape;
  }

  public int getFrame() {
    return myFrame;
  }
}
