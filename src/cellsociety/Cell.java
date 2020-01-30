package cellsociety;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Cell {
    private ArrayList<Cell> neighbors;
    private int state;

    public Cell() {

    }
    public void setNeighbor(Cell c) {
        neighbors.add(c);
    }
    public abstract int calculateNextState();

    public void updateState(int newState) {
        state = newState;
    }

    public int getState() {
        return state;
    }
}
