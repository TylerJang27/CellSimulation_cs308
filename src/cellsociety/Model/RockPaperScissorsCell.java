package cellsociety.Model;

import java.util.ArrayList;

public class RockPaperScissorsCell extends Cell {
  private static final int ROCK = 1;
  private static final int SCISSORS = 2;
  private static final int PAPER = 3;
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
      newState = state < 3 ? state+1 : 1;
    }
    return newState;
  }

  private boolean checkRPSLost(int targetCell, int neighborCell) {
    return targetCell - neighborCell == 1 || targetCell - neighborCell == -2;
  }
}
