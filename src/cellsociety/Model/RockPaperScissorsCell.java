package cellsociety.Model;

import java.util.ArrayList;

/**
 * Class for Cells of the Percolation type
 *
 * @author Thomas Quintanilla
 */

public class RockPaperScissorsCell extends Cell {
  public static final int ROCK = 0;
  public static final int SCISSORS = 1;
  public static final int PAPER = 2;
  private int loseThreshold;
  /**
   * Constructs cell with initial state and values to be evaluated per step
   *
   * @param beginState Initial state to construct cell
   */
  public RockPaperScissorsCell(int beginState, int threshold) {
    neighbors = new ArrayList<>();
    state = beginState;
    loseThreshold = threshold;
  }

  /**
   * Updates cell's weapon if it lost too many times to surrounding neighbors
   * @return new state
   */
  @Override
  public int calculateNextState() {
    int newState = state;
    int loseCount = 0;
    for (Cell n: neighbors) {
      if (checkRPSLost(state, n.getState())) {
        loseCount++;
      }
    }
    if (loseCount > loseThreshold) {
      newState = state < PAPER ? state+1 : ROCK;
    }
    return newState;
  }

  /**
   * returns true if target cell lost against neighbor cell and false if it won
   * @param targetCell cell being battled against
   * @param neighborCell neighbor battling cell
   * @return
   */
  private boolean checkRPSLost(int targetCell, int neighborCell) {
    return targetCell - neighborCell == 1 || targetCell - neighborCell == -2;
  }
}
