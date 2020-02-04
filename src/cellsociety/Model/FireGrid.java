package cellsociety.Model;

import cellsociety.Controller.XMLParser;
import cellsociety.Main;

import java.awt.*;

import java.util.Map;
import java.util.ResourceBundle;

public class FireGrid extends Grid {

    private ResourceBundle RESOURCES= Main.myResources;
    private static final int MAX_VAL = 2;

    public FireGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
        super(cellValues);
        double chanceToBurn = (double) gridMap.getOrDefault(RESOURCES.getString("Fire"), 50)/100;

        for (int y = 0; y < myHeight; y++ ) {
            for (int x = 0; x < myWidth; x++) {
                Point p = new Point (x, y);
                if (cellValues.get(RESOURCES.getString("GridType")).equals(XMLParser.RANDOM)) {
                    pointCellMap.put(p, new FireCell(gridMap.getOrDefault(p, (int)(Math.random() * (1 + MAX_VAL))), chanceToBurn));
                } else {
                    pointCellMap.put(p, new FireCell(gridMap.getOrDefault(p, 0), chanceToBurn));
                }
            }
        }
        buildNSEWNeighbors(pointCellMap);
    }

    @Override
    public void nextFrame() {
        basicNextFrame();
    }
}
