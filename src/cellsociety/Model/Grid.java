package cellsociety.Model;

import java.awt.Point;
import java.util.*;

public abstract class Grid {
    protected HashMap<Point, Cell> pointCellHashMap;
    protected int myWidth;
    protected int myHeight;
    protected int myRate;
    //Make abstract, and 5 different grids
    public Grid() { // change to take in map (simType class)
    }

    //adds adjacent cells in each cell's neighbor list by creating adjacent points and seeing if they exist in Grid
    protected void buildNeighbors() {
        for (Point p : pointCellHashMap.keySet()) {
            Point left = new Point((int) p.getX() - 1, (int) p.getY());
            Point up = new Point((int) p.getX(), (int) p.getY() + 1);
            Point right = new Point((int) p.getX() + 1, (int) p.getY());
            Point down = new Point((int) p.getX(), (int) p.getY() - 1);

            List<Point> potentialNeighbors = Arrays.asList(left, up, right, down);

            for (Point neighbor : potentialNeighbors) {
                if (pointCellHashMap.containsKey(neighbor)) {
                    pointCellHashMap.get(p).setNeighbor(pointCellHashMap.get(neighbor));
                }
            }
        }
    }
    //First calculates and stores new state of each cell
    //Then updates each cell's state
    public void nextFrame() {
        int[] states = new int [pointCellHashMap.values().size()];
        int index = 0;
        for (Cell c: pointCellHashMap.values()) {
            states[index] = c.calculateNextState();
            index++;
        }
        index = 0;
        for (Cell c: pointCellHashMap.values()) {
            c.updateState(states[index]);
            index++;
        }
    }

    //returns state of cell at point p
    public int getState(int r, int c) {
        Point p = new Point(r, c);
        return pointCellHashMap.get(p).getState();
    }
}
