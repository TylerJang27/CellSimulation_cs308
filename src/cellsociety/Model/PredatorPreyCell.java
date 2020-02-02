package cellsociety.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PredatorPreyCell extends Cell {
    private static final int EMPTY = 0;
    private static final int FISH = 1;
    private static final int SHARK = 2;
    private static final int TURNS_TO_BREED = 4;

    protected Boolean didKill;
    private int stepsAlive;

    public PredatorPreyCell(int beginState) {
        state = beginState;
        stepsAlive = 1;
        didKill = false;
    }

    @Override
    public int calculateNextState() {
        int newState;
        if (state == SHARK) {
            ArrayList<Cell> fishNeighbors = neighbors;
            Collections.shuffle(fishNeighbors);
            for (Cell neighbor: fishNeighbors) {
                if (neighbor.getState() == FISH) {
                    neighbor.updateState(EMPTY);
                    didKill = true;
                    break;
                }
            }
        }
        stepsAlive++;
        return newState;
    }

    public int getStepsAlive() {
        return stepsAlive;
    }

    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }

    public int
}
