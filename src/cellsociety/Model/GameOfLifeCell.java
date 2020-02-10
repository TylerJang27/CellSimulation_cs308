package cellsociety.Model;

import java.util.ArrayList;

/**
 * Class for Cells of the Game of Life type
 *
 * @author Thomas Quintanilla
 */
public class GameOfLifeCell extends Cell {

  public static final int DEAD = 0;
  public static final int ALIVE = 1;
  private static final int ALIVE_REQ_2 = 2;
  private static final int ALIVE_REQ_3 = 3;

  /**
   * Constructs cell with initial state and values to be evaluated per step
   *
   * @param beginState Initial state to construct cell
   */
  public GameOfLifeCell(int beginState) {
    neighbors = new ArrayList<>();
    state = beginState;
  }

  /**
   * There are three rules to Game Of LIfe: Any live cell with two or three neighbors survives. Any
   * dead cell with three live neighbors becomes a live cell. All other live cells die in the next
   * generation. Similarly, all other dead cells stay dead.
   * <p>
   * This method calculates and returns the new state of the cell based on the rules mentioned.
   *
   * @return new state of cell
   */
  @Override
  public int calculateNextState() {
    int newState = state;
    int aliveNeighbors = countAliveNeighbors();
    if (state == ALIVE && (aliveNeighbors == ALIVE_REQ_2 || aliveNeighbors == ALIVE_REQ_3)) {
      newState = 1;
    } else if (state == DEAD && aliveNeighbors == ALIVE_REQ_3) {
      newState = 1;
    } else {
      newState = 0;
    }
    return newState;
  }
}
