package cellsociety.Model;

import javax.swing.*;
import java.util.ArrayList;

public class FireCell extends Cell {
    private static final int EMPTY = 0;
    private static final int ALIVE = 1;
    private static final int BURNING = 2;

    public FireCell(int beginState) {
        neighbors = new ArrayList<>();
        state = beginState;
    }

    @Override
    public int calculateNextState() {
        int newState;
        double probCatch = 0.15;
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
            if (neighborBurning && Math.random() < 0.15) {
                newState = BURNING;
            } else {
                newState = ALIVE;
            }
        }
        return newState;
    }

}
