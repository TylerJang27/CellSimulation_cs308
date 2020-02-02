package cellsociety.Model;

import java.awt.*;
import java.util.HashMap;

public class PercolationGrid extends Grid {
    public PercolationGrid(HashMap<Point, Integer> gridMap, HashMap<String, Integer> cellValues) {
        super(cellValues);
        for (int y = 0; y < myHeight; y++ ) {
            for (int x = 0; x < myWidth; x++) {
                Point p = new Point (x, y);
                pointCellMap.put(p, new PercolationCell(gridMap.getOrDefault(p, 0)));
            }
        }
        buildSquareNeighbors(pointCellMap);
    }

    @Override
    public void nextFrame() {
        basicNextFrame();
    }
}
