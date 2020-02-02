package cellsociety.Model;

import java.awt.*;
import java.util.HashMap;

public class SegregationGrid extends Grid {
    protected HashMap<Point, Cell> segregationPointCellMap;

    public SegregationGrid(HashMap<Point, Integer> gridMap, HashMap<String, Integer> cellValues) {
        super(cellValues);
        segregationPointCellMap = new HashMap<>();

        for (int r = 0; r < myHeight; r++) {
            for (int c = 0; c < myWidth; c++) {
                Point p = new Point(c, r);
                if (gridMap.containsKey(p)) {
                    segregationPointCellMap.put(p, new SegregationCell(gridMap.get(p)));
                }
            }
        }
        buildSquareNeighbors(segregationPointCellMap);
    }

    @Override
    public void nextFrame() {
        for (Point p: pointCellMap.keySet()) {
            Cell c = pointCellMap.get(p);
            int satisfied = c.calculateNextState();
            if (satisfied == 2) {
                for (Point newP: pointCellMap.keySet()) {
                    if (pointCellMap.get(newP).getState() == 0 ) {
                        pointCellMap.get(p).updateState(0);
                        pointCellMap.get(newP).updateState(c.getState());
                        break;
                    }
                }
            }
            buildSquareNeighbors(segregationPointCellMap);
        }
    }

}
