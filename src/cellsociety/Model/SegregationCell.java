package cellsociety.Model;

import java.util.ArrayList;

/**
 * Class for Cells of the Segregation type
 *
 * @author Thomas Quintanilla
 */
public class SegregationCell extends Cell {

  public static final int EMPTY = 0;
  public static final int RED = 1; //colors used here to denote group A
  public static final int BLUE = 2; //colors used here to denote group B

  public static final int SATISFIED = 1;
  public static final int UNSATISFIED = 2;

  double myThreshold;
  private int isSatisfied;

  /**
   * Constructs cell with initial state and threshold for sameness
   *
   * @param beginState Initial state to construct cell
   * @param threshold  threshold for determining difference required to move
   */
  public SegregationCell(int beginState, double threshold) {
    myThreshold = threshold;
    isSatisfied = SATISFIED;
    state = beginState;
    neighbors = new ArrayList<>();
  }

  /**
   * Checks to see if each cell passes the satisfaction threshold and returns whether cell is
   * satisfied Assumption: if a cell has no neighbors, it is satisfied (this is a reclusive
   * neighborhood)
   *
   * @return satisfied or unsatisfied for cell
   */
  @Override
  public int calculateNextState() {
    double activeNeighbors = countAliveNeighbors();
    double sameNeighbors = countSameNeighbors();
    if ((activeNeighbors + sameNeighbors) != 0 && !(sameNeighbors / activeNeighbors
        > myThreshold)) {
      isSatisfied = UNSATISFIED;
    } else {
      isSatisfied = SATISFIED;
    }
    return isSatisfied;
  }

  /**
   * counts the neighbors sharing the same state
   *
   * @return total same neighbors
   */
  private int countSameNeighbors() {
    int count = 0;
    for (Cell neighbor : neighbors) {
      if (neighbor.getState() == state) {
        count++;
      }
    }
    return count;
  }
}
