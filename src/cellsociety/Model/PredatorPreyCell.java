package cellsociety.Model;

import java.util.ArrayList;
import java.util.Collections;

public class PredatorPreyCell extends Cell {
    private static final int EMPTY = 0;
    private static final int FISH = 1;
    private static final int SHARK = 2;
    private static int timeToStarve;

    protected Boolean didKill;
    protected Boolean didMove;
    protected int timeSinceEaten;
    private int stepsAlive;

    /**
     * Constructs cell with initial state and values to be evaluated per step
     * @param beginState Initial state to construct cell
     * @param sharkStarve Threshold before shark dies, passed from PredatorPreyGrid
     */
    public PredatorPreyCell(int beginState, int sharkStarve) {
        neighbors = new ArrayList<>();
        state = beginState;
        stepsAlive = 0;
        didMove = false;
        timeSinceEaten = 0;
        timeToStarve = sharkStarve;
    }

    /**
     * Checks to see if a shark eats a fish (updates fish cell to 0) or if shark starves to death.
     * @return new state of cell
     */
    @Override
    public int calculateNextState() {
        int newState = state;
        didKill = false;
        if (state == SHARK) {
            ArrayList<Cell> fishNeighbors = neighbors;
            Collections.shuffle(fishNeighbors);
            for (Cell neighbor: fishNeighbors) {
                if (neighbor.getState() == FISH) {
                    neighbor.updateState(EMPTY);
                    didKill = true;
                    timeSinceEaten = 0;
                    break;
                }
            }
            newState = checkIfStarves();
        }
        return newState;
    }

    /**
     * Checks if shark should die from malnourishment
     * @return 0 if shark dies, 2 if shark still lives on
     */
    private int checkIfStarves() {
        if (!didKill) {
            timeSinceEaten++;
        }
        if (timeSinceEaten >= timeToStarve){
            return EMPTY;
        } else {
            return SHARK;
        }
    }
    public int getStepsAlive() {
        return stepsAlive;
    }
    public void setStepsAlive(int n) {
        stepsAlive = n;
    }

    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }
}
