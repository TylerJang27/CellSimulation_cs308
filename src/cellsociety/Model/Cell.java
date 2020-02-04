package cellsociety.Model;

import java.util.ArrayList;

//Super class for all cell types
public abstract class Cell {
    protected int state;
    protected ArrayList<Cell> neighbors;
    public Cell() {
    }

    public void setNeighbor(Cell c) {
        neighbors.add(c);
    }

    /**
     * Counts number of active neighbors (state not 0) for a cell
     * @return total active neigbors
     */
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
