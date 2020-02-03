package cellsociety.Model;

import cellsociety.Controller.XMLParser;
import cellsociety.Main;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PredatorPreyGrid extends Grid {
    private static final int TURNS_TO_BREED = 4;
    private static final int EMPTY = 0;
    private static ResourceBundle RESOURCES= Main.myResources;
    private static int MAX_VAL = 2;

    public PredatorPreyGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
        super(cellValues);
        for (int y = EMPTY; y < myHeight; y++ ) {
            for (int x = EMPTY; x < myWidth; x++) {
                Point p = new Point (x, y);
                if (cellValues.get(RESOURCES.getString("GridType")).equals(XMLParser.RANDOM)) {
                    pointCellMap.put(p, new PredatorPreyCell(gridMap.getOrDefault(p, (int)(Math.random() * (1 + MAX_VAL)))));
                } else {
                    pointCellMap.put(p, new PredatorPreyCell(gridMap.getOrDefault(p, 0)));
                }
            }
        }
        buildNSEWNeighbors(pointCellMap);
    }

    @Override
    public void nextFrame() {
        //Checks to see if a shark eats a fish
        basicNextFrame();

        for (Cell c: pointCellMap.values()) {
            PredatorPreyCell updateCell = (PredatorPreyCell) c;
            updateCell.didMove = false;
        }

        // handle movement of updated states
        for (Cell c: pointCellMap.values()) {
            PredatorPreyCell currentCell = (PredatorPreyCell) c;
            int activeNeighbors = currentCell.countAliveNeighbors();

            if (currentCell.getState() != EMPTY && !currentCell.didMove) {
                if (activeNeighbors < 4 && !currentCell.didKill ) {
                    List<Cell> neighborPoints = currentCell.getNeighbors();
                    Collections.shuffle(neighborPoints);
                    for (Cell neighbor : neighborPoints) {
                        PredatorPreyCell predatorPreyNeighbor = (PredatorPreyCell) neighbor;
                        if (predatorPreyNeighbor.getState() == EMPTY) {
                            predatorPreyNeighbor.updateState(currentCell.getState());
                            predatorPreyNeighbor.setStepsAlive(currentCell.getStepsAlive());
                            currentCell.updateState(EMPTY);
                            currentCell.setStepsAlive(0);
                            currentCell = predatorPreyNeighbor;
                            currentCell.didMove = true;
                            break;
                        }
                    }
                }
                currentCell.setStepsAlive(currentCell.getStepsAlive() + 1);
            }

            //Handles breeding
            if (currentCell.getStepsAlive() >= TURNS_TO_BREED) {
                for (Cell childCell: currentCell.getNeighbors()) {
                    if (childCell.getState() == EMPTY) {
                        childCell.updateState(currentCell.getState());
                        currentCell.setStepsAlive(0);
                        break;
                    }
                }
            }
        }
    }
}
