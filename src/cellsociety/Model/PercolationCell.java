package cellsociety.Model;

import java.util.ArrayList;

public class PercolationCell extends Cell {
    private static final int CLOSED = 0;
    private static final int OPENED = 1;
    private static final int FILLED = 2;

    /**
     * Constructs cell with initial state and values to be evaluated per step
     * @param beginState Initial state to construct cell
     */
    public PercolationCell(int beginState) {
        neighbors = new ArrayList<>();
        state = beginState;
    }

    /**
     * Fills open cell if any neighbor of cell is filled
     * @return new state
     */
    @Override
    public int calculateNextState() {
        int newState = state;
        if (state == 1) {
            for (Cell neighbor : neighbors) {
                if (neighbor.state == 2) {
                    newState = 2;
                    break;
                }
            }
        }
        return newState;
    }
}
