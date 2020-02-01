package cellsociety.Model;

import java.awt.*;
import java.util.HashMap;

public class SegregationGrid extends Grid {

    public SegregationGrid(HashMap<String, Integer> gridMap) {
        pointCellHashMap = new HashMap<>();
        myWidth = gridMap.get("width");
        myHeight = gridMap.get("height");
        myRate = gridMap.get("rate");

        for (int r = 0; r < myHeight; r++) {
            for (int c = 0; c < myWidth; c++) {
                Point p = new Point(c, r);
                pointCellHashMap.put(p, new SegregationCell());
            }
        }
        buildNeighbors();
    }
}
