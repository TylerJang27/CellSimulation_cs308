package cellsociety.Model;

import java.util.ArrayList;

public abstract class Cell {
    protected int state;
    protected ArrayList<Cell> neighbors;
    public Cell() {
    }

    public void setNeighbor(Cell c) {
        neighbors.add(c);
    }


    protected int countAliveNeighbors() {
        int count = 0;
        for (Cell neighbor: neighbors) {
            if (neighbor.getState() != 0) {
                count++;
            }
        }
        return count;
    }

    public abstract int calculateNextState();

    public void updateState(int newState) {
        state = newState;
    }

    public int getState() {
        return state;
    }
}
