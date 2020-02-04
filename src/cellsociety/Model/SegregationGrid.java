package cellsociety.Model;

import cellsociety.Controller.XMLParser;
import cellsociety.Main;

import java.awt.*;
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

    private ResourceBundle RESOURCES= Main.myResources;
    private static final int MAX_VAL = 2;

    /**
     * Uses gridMap to construct Percolation and gridcell values to set cells at points.
     * @param gridMap: Map with KVP of a coordinate point to an int, which represents the state to construct cell with.
     * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the parameter value
     */
    public SegregationGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
        super(cellValues);
        for (int r = 0; r < myHeight; r++) {
            for (int c = 0; c < myWidth; c++) {
                Point p = new Point(c, r);
                if (cellValues.get(RESOURCES.getString("GridType")).equals(XMLParser.RANDOM)) {
                    pointCellMap.put(p, new SegregationCell(gridMap.getOrDefault(p, (int)(Math.random() * (1 + MAX_VAL))),
                            (double)cellValues.getOrDefault(RESOURCES.getString("Similar"), DEFAULT_THRESHOLD)/100));
                } else {
                    pointCellMap.put(p, new SegregationCell(gridMap.getOrDefault(p, 0),
                            (double)cellValues.getOrDefault(RESOURCES.getString("Similar"), DEFAULT_THRESHOLD)/100));
                }
            }
        }
        buildSquareNeighbors();
    }

    /**
     * Creates list of all unsatisfied cells in the grid then places each of them in a random empty cell
     */
    @Override
    public void nextFrame() {
        myFrame++;
        ArrayList<Point> unsatisfiedPoints = new ArrayList<>();
        int index = 0;
        for (Point p : pointCellMap.keySet()) {
            Cell c = pointCellMap.get(p);
            if (c.calculateNextState() == 2) {
                unsatisfiedPoints.add(p);
            }
        }
        for (Point unsatisfiedP: unsatisfiedPoints) {
            while (pointCellMap.get(unsatisfiedP).getState() != 0) {
                Point newP = new Point((int) (Math.random() * myWidth), (int) (Math.random() * myHeight));
                if (pointCellMap.get(newP).getState() == 0) {
                    pointCellMap.get(newP).updateState(pointCellMap.get(unsatisfiedP).getState());
                    pointCellMap.get(unsatisfiedP).updateState(0);
                }
            }
        }
    }
}
