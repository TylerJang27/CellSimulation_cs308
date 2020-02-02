package cellsociety.Model;

import java.util.ArrayList;

public class SegregationCell extends Cell {
    private static final int SATISFIED = 1;
    private static final int UNSATISFIED = 2;

    private int isSatisfied;

    public SegregationCell(int beginState) {
        isSatisfied = 1;
        state = beginState;
        neighbors = new ArrayList<>();
    }

    /* There are three rules to Game Of LIfe:
    Any live cell with two or three neighbors survives.
    Any dead cell with three live neighbors becomes a live cell.
    All other live cells die in the next generation. Similarly, all other dead cells stay dead.

    This method calculates and returns the new state of the cell based on the rules mentioned.

    */
    @Override
    public int calculateNextState() {
        double threshold = 0.30;
        double  activeNeighbors = countAliveNeighbors();
        double sameNeighbors = countSameNeighbors();
        //Rule where any live cell that has two or three neighbors alive stay alive
        if (!(sameNeighbors / (activeNeighbors + sameNeighbors) > threshold)) {
            isSatisfied = UNSATISFIED;
        } else {
            isSatisfied = SATISFIED;
        }
            //Rule where if any dead cell has exactly three live neighbors, it becomes a live cell
        return isSatisfied;
    }

    protected int countSameNeighbors() {
        int count = 0;
        for (Cell neighbor: neighbors) {
            if (neighbor.getState() == state)
                count++;
        }
        return count;
    }
}
