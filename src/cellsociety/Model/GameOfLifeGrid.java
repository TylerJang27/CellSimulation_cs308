package cellsociety.Model;

import java.awt.*;
import java.util.HashMap;

public class GameOfLifeGrid extends Grid {
    public GameOfLifeGrid(HashMap<Point, Integer> gridMap, HashMap<String, Integer> cellValues) {
        super(cellValues);
        for (int y = 0; y < myHeight; y++ ) {
            for (int x = 0; x < myWidth; x++) {
                Point p = new Point (x, y);
                pointCellMap.put(p, new GameOfLifeCell(gridMap.getOrDefault(p, 0)));
            }
        }
        buildNSEWNeighbors(pointCellMap);
    }

}
