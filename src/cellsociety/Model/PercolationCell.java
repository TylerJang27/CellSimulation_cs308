package cellsociety.Model;

import java.util.ArrayList;

/**
 * Class for Cells of the Percolation type
 *
 * @author Thomas Quintanilla
 */
public class PercolationCell extends Cell {

  public static final int CLOSED = 0;
  public static final int OPENED = 1;
  public static final int FILLED = 2;

  /**
   * Constructs cell with initial state and values to be evaluated per step
   *
   * @param beginState Initial state to construct cell
   */
  public PercolationCell(int beginState) {
    neighbors = new ArrayList<>();
    state = beginState;
  }

  /**
   * Fills open cell if any neighbor of cell is filled
   *
   * @return new state
   */
  @Override
  public int calculateNextState() {
    int newState = state;
    if (state == OPENED) {
      for (Cell neighbor : neighbors) {
        if (neighbor.state == FILLED) {
          newState = FILLED;
          break;
        }
      }
    }
    return newState;
  }
}
