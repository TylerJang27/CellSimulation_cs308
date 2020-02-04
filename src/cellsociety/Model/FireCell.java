package cellsociety.Model;

import javax.swing.*;
import java.util.ArrayList;

public class FireCell extends Cell {
    private static final int EMPTY = 0;
    private static final int ALIVE = 1;
    private static final int BURNING = 2;
    private static double probCatch;

    public FireCell(int beginState, double fireProb) {
        neighbors = new ArrayList<>();
        state = beginState;
        probCatch = fireProb;
    }

    @Override
    public int calculateNextState() {
        int newState;
        boolean neighborBurning = false;
        if (state == BURNING || state == EMPTY) {
            newState = EMPTY;
        } else {
            for (Cell neighbor : neighbors) {
                if (neighbor.getState() == BURNING) {
                    neighborBurning = true;
                    break;
                }
            }
            if (neighborBurning && Math.random() < probCatch) {
                newState = BURNING;
            } else {
                newState = ALIVE;
            }
        }
        return newState;
    }

}
