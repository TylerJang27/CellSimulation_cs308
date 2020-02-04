package cellsociety.Model;

import java.util.ArrayList;

/**
 * Class for Cells of the Segregation type
 *
 * @author Thomas Quintanilla
 */
public class SegregationCell extends Cell {
    private static final int SATISFIED = 1;
    private static final int UNSATISFIED = 2;

    double myThreshold;
    private int isSatisfied;

    /**
     * Constructs cell with initial state and threshold for sameness
     * @param beginState Initial state to construct cell
     * @param threshold threshold for determining difference required to move
     */
    public SegregationCell(int beginState, double threshold) {
        myThreshold = threshold;
        isSatisfied = 1;
        state = beginState;
        neighbors = new ArrayList<>();
    }

    /**
     * Checks to see if each cell passes the satisfaction threshold and returns whether cell is satisfied
     * Assumption: if a cell has no neighbors, it is satisfied (this is a reclusive neighborhood)
     * @return satisfied or unsatisfied for cell
     */
    @Override
    public int calculateNextState() {
        double activeNeighbors = countAliveNeighbors();
        double sameNeighbors = countSameNeighbors();
        if ((activeNeighbors + sameNeighbors) != 0 && !(sameNeighbors / activeNeighbors > myThreshold)) {
            isSatisfied = UNSATISFIED;
        } else {
            isSatisfied = SATISFIED;
        }
        return isSatisfied;
    }

    /**
     * counts the neighbors sharing the same state
     * @return total same neighbors
     */
    protected int countSameNeighbors() {
        int count = 0;
        for (Cell neighbor: neighbors) {
            if (neighbor.getState() == state)
                count++;
        }
        return count;
    }
}
