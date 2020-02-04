package cellsociety.Model;

import cellsociety.Controller.XMLParser;
import cellsociety.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class SegregationGrid extends Grid {

    private static ResourceBundle RESOURCES= Main.myResources;
    private static int MAX_VAL = 2;

    public SegregationGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
        super(cellValues);
        for (int r = 0; r < myHeight; r++) {
            for (int c = 0; c < myWidth; c++) {
                Point p = new Point(c, r);
                if (cellValues.get(RESOURCES.getString("GridType")).equals(XMLParser.RANDOM)) {
                    pointCellMap.put(p, new SegregationCell(gridMap.getOrDefault(p, (int)(Math.random() * (1 + MAX_VAL)))));
                } else {
                    pointCellMap.put(p, new SegregationCell(gridMap.getOrDefault(p, 0)));
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
