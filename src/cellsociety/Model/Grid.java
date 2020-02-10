package cellsociety.Model;

import cellsociety.Main;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.ArrayList;

/**
 * Class for an abstraction of grid types, defining some basic functions
 *
 * @author Thomas Quintanilla
 */
public abstract class Grid {

  private static final int TOROIDAL = 1;
  private static final int HEXAGONAL = 1;
  protected HashMap<Point, Cell> pointCellMap;
  protected int myWidth;
  protected int myHeight;
  private ArrayList<Point> pointList;
  private int myFrame;
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
    if (cellShape == HEXAGONAL) {
      pointList = hexPointGenerator();
    } else {
      pointList = squarePointGenerator();
    }
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
      System.out.println(pointCellMap.get(p).neighbors);
    }
  }

  private void checkAndSetNeighbor(Point p, int xPos, int yPos, int x, int y) {
    int xOffset = gridShape == TOROIDAL ? getXOffset(xPos, x) : x;
    int yOffset = gridShape == TOROIDAL ? getYOffset(yPos, y) : y;
    System.out.println("x: " + getXOffset(xPos, x) + " y: " + getYOffset(yPos, y) + "\n");
    Point potentialNeighbor = new Point(xPos + xOffset, yPos + yOffset);
    if (!potentialNeighbor.equals(p) && pointCellMap.containsKey(potentialNeighbor)) {
      pointCellMap.get(p).setNeighbor(pointCellMap.get(potentialNeighbor));
    }
  }

  /**
   * Changes offset for neighbor points so that toroidal neighbors can be implemented
   * @param yPos y coordinate of original cell
   * @param yOffset initial y offset that may be changed
   * @return new y offset
   */
  private int getYOffset(int yPos, int yOffset) {
    int newOffset = yOffset;
    if (yPos + yOffset < 0)  {
      newOffset = myHeight + yOffset;
    } else if (yPos + yOffset > myHeight - 1){
      newOffset = -myHeight + yOffset;
    }
    return newOffset;
  }

  /**
   * Changes offset for neighbor points so that toroidal neighbors can be implemented
   * @param xPos x coordinate of original cell
   * @param xOffset initial x offset that may be changed
   * @return new x offset
   */
  private int getXOffset(int xPos, int xOffset) {
    int newOffset = xOffset;
    if (xPos + xOffset < 0)  {
      newOffset = myWidth + xOffset;
    } else if (xPos + xOffset > myWidth-1){
      newOffset = -myWidth + xOffset;
    }
    return newOffset;
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
  public int getMaxState() {
    return 0;
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

  /**
   * @return cell shape
   */
  public int getCellShape() {
    return cellShape;
  }

  /**
   * @return frame counter
   */
  public int getFrame() {
    return myFrame;
  }

  /**
   * Generates a list of points for a default square setup
   * @return list of points
   */
  public ArrayList<Point> squarePointGenerator() {
    ArrayList<Point> squarePoints = new ArrayList<>();
    for (int y = 0; y < myHeight; y++) {
      for (int x = 0; x < myWidth; x++) {
        squarePoints.add(new Point(x, y));
      }
    }
    return squarePoints;
  }

  /**
   * Generates a list of points for a default hexagon setup
   * @return list of points
   */
  protected ArrayList<Point> hexPointGenerator() {
    ArrayList<Point> hexPoints = new ArrayList<>();
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

  protected ArrayList<Point> getPointList() {
    return pointList;
  }
  public void addFrame() {
    myFrame++;
  }
}
