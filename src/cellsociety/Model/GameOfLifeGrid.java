package cellsociety.Model;

import java.awt.*;
import java.util.Map;

public class GameOfLifeGrid extends Grid {
    public GameOfLifeGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
        super(cellValues);
        for (int y = 0; y < myHeight; y++ ) {
            for (int x = 0; x < myWidth; x++) {
                Point p = new Point (x, y);
                pointCellMap.put(p, new GameOfLifeCell(gridMap.getOrDefault(p, 0)));
            }
        }
        buildSquareNeighbors(pointCellMap);
    }

    @Override
    public void nextFrame() {
        basicNextFrame();
    }
}
