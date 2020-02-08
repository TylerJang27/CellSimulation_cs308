package cellsociety.Model;

import cellsociety.Controller.GridParser;
import cellsociety.Controller.XMLParser;
import cellsociety.Main;
import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for Grids of the Predator-Prey Type
 *
 * @author Thomas Quintanilla
 */
public class PredatorPreyGrid extends Grid {

  private static final int DEFAULT_FISH_BREED = 2;
  private static final int DEFAULT_SHARK_BREED = 7;
  private static final int DEFAULT_SHARK_STARVE = 4;

  private ResourceBundle RESOURCES = Main.myResources;
  private int FISH_TURNS_TO_BREED;
  private int SHARK_TURNS_TO_BREED;
  private int TURNS_TO_STARVE;
  private int FISH = 1;
  private int SHARK = 2;
  private int EMPTY = 0;
  private int MAX_VAL = 2;

  /**
   * Uses gridMap to construct Wa-Tor grid and define specific thresholds for breeding and death and
   * cellValues to set cells at points.
   *
   * @param gridMap:    Map with KVP of a coordinate point to an int, which represents the state to
   *                    construct the cell
   * @param cellValues: Map with KVP of a string referencing a parameter to construct a grid to the
   *                    parameter value
   */
  public PredatorPreyGrid(Map<Point, Integer> gridMap, Map<String, Integer> cellValues) {
    super(cellValues);
    FISH_TURNS_TO_BREED = cellValues
        .getOrDefault(RESOURCES.getString("FishBreed"), DEFAULT_FISH_BREED);
    SHARK_TURNS_TO_BREED = cellValues
        .getOrDefault(RESOURCES.getString("SharkBreed"), DEFAULT_SHARK_BREED);
    TURNS_TO_STARVE = cellValues
        .getOrDefault(RESOURCES.getString("SharkStarve"), DEFAULT_SHARK_STARVE);
    for (int y = EMPTY; y < myHeight; y++) {
      for (int x = EMPTY; x < myWidth; x++) {
        Point p = new Point(x, y);
        if (cellValues.get(RESOURCES.getString("GridType")).equals(GridParser.RANDOM)) { //TODO: SEARCH FOR GRIDPARSER AND CHANGE THIS?
          pointCellMap.put(p,
              new PredatorPreyCell(gridMap.getOrDefault(p, (int) (Math.random() * (1 + MAX_VAL))),
                  TURNS_TO_STARVE));
        } else {
          pointCellMap.put(p, new PredatorPreyCell(gridMap.getOrDefault(p, 0), TURNS_TO_STARVE));
        }
      }
    }
    buildNSEWNeighbors();
  }

  /**
   * Calls basicNextFrame(), which updates states of each cell. Then moves cells if there are empty
   * adjacent cells. Finally checks if each cell can breed a new cell of the same state.
   */
  @Override
  public void nextFrame() {
    basicNextFrame();

    resetMovement();

    for (Cell c : pointCellMap.values()) {
      PredatorPreyCell currentCell = (PredatorPreyCell) c;
      int state = currentCell.getState();

      currentCell = handleMovement(currentCell, state);

      handleBreeding(currentCell, state);
    }
  }

  private void resetMovement() {
    for (Cell c : pointCellMap.values()) {
      PredatorPreyCell updateCell = (PredatorPreyCell) c;
      updateCell.didMove = false;
    }
  }

  /**
   * Checks if current cell is capable of moving and calls method to move cell if so
   * @param currentCell Current cell being looked at
   * @param state state of that current cell
   * @return the nwe cell that the fish/shark moved, or the original if it didn't move
   */
  private PredatorPreyCell handleMovement(PredatorPreyCell currentCell, int state) {
    int activeNeighbors = currentCell.countAliveNeighbors();
    if (state != EMPTY && !currentCell.didMove) {
      if (!currentCell.didKill && activeNeighbors < 4) {
        currentCell = moveCell(currentCell);
      }
      currentCell.setStepsAlive(currentCell.getStepsAlive() + 1);
    }
    return currentCell;
  }

  /**
   * Checks if cell should breed or not
   * @param currentCell Current cell being looked at
   * @param state state of that current cell
   */
  private void handleBreeding(PredatorPreyCell currentCell, int state) {
    int breedingTime = 0;
    if (state == FISH) {
      breedingTime = FISH_TURNS_TO_BREED;
    } else if (state == SHARK) {
      breedingTime = SHARK_TURNS_TO_BREED;
    }
    if (currentCell.getStepsAlive() >= breedingTime) {
      for (Cell childCell : currentCell.getNeighbors()) {
        if (childCell.getState() == EMPTY) {
          childCell.updateState(state);
          currentCell.setStepsAlive(0);
          break;
        }
      }
    }
  }

  /**
   * Transfers values to any adjacent empty cell to represent a move
   *
   * @param currentCell Cell being moved
   * @return cell that received transferred data
   */
  private PredatorPreyCell moveCell(PredatorPreyCell currentCell) {
    List<Cell> neighborPoints = currentCell.getNeighbors();

    Collections.shuffle(neighborPoints);

    for (Cell neighbor : neighborPoints) {
      PredatorPreyCell predatorPreyNeighbor = (PredatorPreyCell) neighbor;
      if (predatorPreyNeighbor.getState() == EMPTY) {
        predatorPreyNeighbor.updateState(currentCell.getState());
        predatorPreyNeighbor.setStepsAlive(currentCell.getStepsAlive());
        currentCell.updateState(EMPTY);
        currentCell.setStepsAlive(0);
        currentCell = predatorPreyNeighbor;
        currentCell.didMove = true;
        break;
      }
    }
    return currentCell;
  }
}
