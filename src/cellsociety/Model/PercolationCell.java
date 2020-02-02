package cellsociety.Model;

import java.util.ArrayList;

public class PercolationCell extends Cell {
    private static final int CLOSED = 0;
    private static final int OPENED = 1;
    private static final int FILLED = 2;

    public PercolationCell(int beginState) {
        state = beginState;
    }
    @Override
    public int calculateNextState() {
        int newState = 0;
        if (state == 1) {
            for (Cell neighbor : neighbors) {
                if (neighbor.state == 2) {
                    newState = 2;
                    break;
                }
            }
        } else {
            newState = state;
        }
        return newState;
    }
}
