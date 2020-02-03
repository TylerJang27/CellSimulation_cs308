package cellsociety.Model;

import java.awt.Point;
import java.util.*;

public abstract class Grid {
    protected HashMap<Point, Cell> pointCellMap;
    protected int myWidth;
    protected int myHeight;

    //Make abstract, and 5 different grids
    public Grid(Map<String, Integer> gridMap) { // change to take in map (simType class)
        myWidth = gridMap.get("width");
        myHeight = gridMap.get("height");
        pointCellMap = new LinkedHashMap<>();
    }

    //adds adjacent cells in each cell's neighbor list by creating adjacent points and seeing if they exist in Grid
    protected void buildNSEWNeighbors(HashMap<Point, Cell> pointCellMap) {
        for (Point p : pointCellMap.keySet()) {
            List<Point> potentialNeighbors = getNeighborPoints(p);

            for (Point neighbor : potentialNeighbors) {
                if (pointCellMap.containsKey(neighbor)) {
                    pointCellMap.get(p).setNeighbor(pointCellMap.get(neighbor));
                }
            }
        }
    }

    protected List<Point> getNeighborPoints(Point p) {
        Point left = new Point((int) p.getX() - 1, (int) p.getY());
        Point up = new Point((int) p.getX(), (int) p.getY() + 1);
        Point right = new Point((int) p.getX() + 1, (int) p.getY());
        Point down = new Point((int) p.getX(), (int) p.getY() - 1);

        return Arrays.asList(left, up, right, down);
    }

    static void buildSquareNeighbors(HashMap<Point, Cell> pointCellMap) {
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
        int[] states = new int [pointCellMap.values().size()];
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
}
