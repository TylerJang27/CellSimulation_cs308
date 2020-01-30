package cellsociety.Model;

import java.util.ArrayList;

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
