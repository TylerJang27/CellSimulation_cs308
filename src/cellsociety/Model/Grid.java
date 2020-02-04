package cellsociety.Model;

import cellsociety.Main;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.*;
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
    protected static ResourceBundle RESOURCES = Main.myResources;
    protected int myFrame;

    /**
     * Creates basic layout of grid as well as initializes map to store cells
     * @param gridMap map that holds all parameters to construct grid, passed from subclass constructor
     */
    public Grid(Map<String, Integer> gridMap) {
        myWidth = gridMap.get(RESOURCES.getString("Width"));
        myHeight = gridMap.get(RESOURCES.getString("Height"));
        pointCellMap = new LinkedHashMap<>();
        myFrame = 0;
    }

    /**
     *adds NSEW adjacent cells in each cell's neighbor list by creating adjacent points and seeing if they exist in Grid
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
     * @param p Point where cell neighbors are checked in grid
     * @return list of neighbor point cells for NSEW
     */
    protected List<Point> getNeighborPoints(Point p) {
        Point left = new Point((int) p.getX() - 1, (int) p.getY());
        Point up = new Point((int) p.getX(), (int) p.getY() + 1);
        Point right = new Point((int) p.getX() + 1, (int) p.getY());
        Point down = new Point((int) p.getX(), (int) p.getY() - 1);

        return Arrays.asList(left, up, right, down);
    }

    /**
     * Checks all surrounding cells (diagonals included) of a specific cell and builds neighbors
     */
    protected void buildSquareNeighbors() {
        for (Point p : pointCellMap.keySet()) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    Point potentialNeighbor = new Point((int) p.getX() + x, (int) p.getY() + y);
                    if (!potentialNeighbor.equals(p) && pointCellMap.containsKey(potentialNeighbor)) {
                        pointCellMap.get(p).setNeighbor(pointCellMap.get(potentialNeighbor));
                    }
                }
            }
        }
    }

    //First calculates and stores new state of each cell
    //Then updates each cell's state
    public abstract void nextFrame();

    public void basicNextFrame() {
        myFrame++;
        int[] states = new int [pointCellMap.values().size()];
        ArrayList<Cell> activeCells = new ArrayList<>();
        int index = 0;
        for (Cell c: pointCellMap.values()) {
            states[index] = c.calculateNextState();
            //FIXME: Tyler's suggestion. Create a List of all the cells that will be changing so that
            //FIXME: (A) The next loop will be more efficient and (B) you can have nextFrame() return this of Points
            //FIXME: and I can use this to pass to ApplicationView/GridView
            index++;
        }

        index = 0;
        for (Cell c: pointCellMap.values()) {
            c.updateState(states[index]);
            index++;
        }
    }

    //returns state of cell at point p
    public int getState(int r, int c) {
        Point p = new Point(r, c);
        return pointCellMap.get(p).getState();
    }
    //Used for debugging
    public void printGrid() {
        for (Point p: pointCellMap.keySet()) {
            System.out.println("Location: " + p + "State: " + pointCellMap.get(p).getState() + "\n");
        }
    }

    public int getFrame(){
        return myFrame;
    }
}
