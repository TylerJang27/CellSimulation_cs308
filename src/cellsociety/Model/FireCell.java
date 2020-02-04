package cellsociety.Model;

import javax.swing.*;
import java.util.ArrayList;

public class FireCell extends Cell {
    private static final int EMPTY = 0;
    private static final int ALIVE = 1;
    private static final int BURNING = 2;
    private static double probCatch;

    /**
     * Constructs cell with initial state and values to be evaluated per step
     * @param beginState Initial state to construct cell
     * @param fireProb initial probability of chance to catch on fire
     */
    public FireCell(int beginState, double fireProb) {
        neighbors = new ArrayList<>();
        state = beginState;
        probCatch = fireProb;
    }

    /**
     * If cell was burning last step or already dead, it dies
     * then checks alive trees to see if any neighbor are burning, then applies chance to catch fire per neighbor
     * @return new state of cell
     */
    @Override
    public int calculateNextState() {
        int newState = state;
        if (state == BURNING || state == EMPTY) {
            newState = EMPTY;
        } else {
            for (Cell neighbor : neighbors) {
                if (neighbor.getState() == 2 && Math.random() < probCatch) {
                    newState = BURNING;
                }
            }
        }
        return newState;
    }
}
