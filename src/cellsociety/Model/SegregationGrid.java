package cellsociety.Model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SegregationGrid extends Grid {
    public SegregationGrid(HashMap<Point, Integer> gridMap, HashMap<String, Integer> cellValues) {
        super(cellValues);
        for (int r = 0; r < myHeight; r++) {
            for (int c = 0; c < myWidth; c++) {
                Point p = new Point(c, r);
                if (gridMap.containsKey(p)) {
                    pointCellMap.put(p, new SegregationCell(gridMap.get(p)));
                }
            }
        }
        buildSquareNeighbors(pointCellMap);
    }

    @Override
    public void nextFrame() {
        ArrayList<Point> unsatisfiedPoints = new ArrayList<>();
        int index = 0;
        for (Point p : pointCellMap.keySet()) {
            Cell c = pointCellMap.get(p);
            int satisfied = c.calculateNextState();
            if (satisfied == 2) {
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
