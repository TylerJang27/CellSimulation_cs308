package cellsociety.Model;

import java.util.ArrayList;

public class GameOfLifeCell extends Cell {
    private static final int DEAD = 0;
    private static final int ALIVE = 1;

    public GameOfLifeCell(int beginState) {
        state = beginState;
    }

    /* There are three rules to Game Of LIfe:
    Any live cell with two or three neighbors survives.
    Any dead cell with three live neighbors becomes a live cell.
    All other live cells die in the next generation. Similarly, all other dead cells stay dead.

    This method calculates and returns the new state of the cell based on the rules mentioned.
    */
    @Override
    public int calculateNextState() {
        int newState;
        int aliveNeighbors = countAliveNeighbors();
        //Rule where any live cell that has two or three neighbors alive stay alive
        if (state == ALIVE && (aliveNeighbors == 2 || aliveNeighbors == 3)) {
            newState = 1;
        //Rule where if any dead cell has exactly three live neighbors, it becomes a live cell
        } else if (state == DEAD && aliveNeighbors == 3) {
            newState = 1;
        //If the cell does not live from either two rules, it dies/stays dead
        } else {
            newState = 0;
        }
        return newState;
    }
}
