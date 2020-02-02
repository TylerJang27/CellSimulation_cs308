package cellsociety.Model;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PredatorPreyGrid extends Grid {
    public PredatorPreyGrid(HashMap<Point, Integer> gridMap, HashMap<String, Integer> cellValues) {
        super(cellValues);
        for (int y = 0; y < myHeight; y++ ) {
            for (int x = 0; x < myWidth; x++) {
                Point p = new Point (x, y);
                pointCellMap.put(p, new PredatorPreyCell(gridMap.getOrDefault(p, 0)));
            }
        }
        buildNSEWNeighbors(pointCellMap);
    }

    @Override
    public void nextFrame() {
        int[] states = new int [pointCellMap.values().size()];
        int index = 0;
        for (Point p: pointCellMap.keySet()) {
            int move = (int) Math.random()*4;
            PredatorPreyCell c = (PredatorPreyCell) pointCellMap.get(p);
            int activeNeighbors = c.countAliveNeighbors();

            states[index] = c.calculateNextState();
            index++;

            if (activeNeighbors < 4) {
                if (c.getStepsAlive() >= 4) {
                    for (Cell neighbor : c.getNeighbors()) {
                        if (neighbor.getState() == 0) {
                            neighbor.updateState(c.getState());
                            buildNSEWNeighbors(pointCellMap);
                            break;
                        }
                    }
                }
                List<Point> neighbors = getNeighborPoints(p);

                double counter = 1;
                if (c.didKill) {
                    for (Point newP : neighbors) {
                        if (move == counter && pointCellMap.get(newP).getState() == 0) {
                            pointCellMap.get(p).updateState(0);
                            pointCellMap.get(newP).updateState(c.getState());
                            break;
                        } else {
                            counter++;
                        }
                    }
                } else {
                    c.didKill = false;
                }
            }

        }
        index = 0;
        for (Cell c: pointCellMap.values()) {
            c.updateState(states[index]);
            index++;
        }
    }
}
