package cellsociety.Model;

import java.util.ArrayList;

public class RockPaperScissorsCell extends Cell {
  public static final int ROCK = 1;
  public static final int SCISSORS = 2;
  public static final int PAPER = 3;
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
    if (loseCount < loseThreshold) {
      newState = state < PAPER ? state++ : ROCK;
    }
    return newState;
  }

  private boolean checkRPSLost(int targetCell, int neighborCell) {
    return targetCell - neighborCell == 1 || targetCell - neighborCell == -2;
  }
}
