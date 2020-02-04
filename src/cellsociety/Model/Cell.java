package cellsociety.Model;

import java.util.ArrayList;

/**
 * Super class for all Cell types
 *
 * @author Thomas Quintanilla
 */

public abstract class Cell {
    protected int state;
    protected ArrayList<Cell> neighbors;

    /**
     * Default constructor
     */
    public Cell() {
    }

    /**
     * Adds a neighbor to a cell's List of neighbors (up to 8)
     * @param c A neighboring Cell
     */
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

    /**
     * Abstract method to calculate a cell's state in the next update
     * @return new state
     */
    public abstract int calculateNextState();

    /**
     * Updates a cell's state to newState
     * @param newState
     */
    public void updateState(int newState) {
        state = newState;
    }

    /**
     * Returns a cell's current state
     */
    public int getState() {
        return state;
    }
}
