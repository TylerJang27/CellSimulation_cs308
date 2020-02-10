package cellsociety.Controller;

import cellsociety.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An enumerated type for the Simulation Type, specifying all of the Simulation Types and the
 * necessary fields for each type
 * <p>
 * To add a new SimulationType, simply add a new core SimType with its name and required fields
 * Ensure XML Files are formatted correctly
 * <p>
 * Portions taken from ESRBRating.java from spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/enumerated/ESRBRating.java
 *
 * @author Tyler Jang
 */
public enum SimType {
  GAME_OF_LIFE(Main.myResources.getString("GameOfLife"), new String[]{},
      new String[]{Main.myResources.getString("Coverage")}, 1),
  PERCOLATION(Main.myResources.getString("Percolation"), new String[]{},
      new String[]{Main.myResources.getString("Blocked"), Main.myResources.getString("Filled")}, 2),
  SEGREGATION(Main.myResources.getString("Segregation"), new String[]{},
      new String[]{Main.myResources.getString("Similar"), Main.myResources.getString("Red"),
          Main.myResources.getString("Empty")}, 2),
  PREDATOR_PREY(Main.myResources.getString("PredatorPrey"), new String[]{},
      new String[]{Main.myResources.getString("FishBreed"),
          Main.myResources.getString("SharkStarve"), Main.myResources.getString("SharkBreed"),
          Main.myResources.getString("Shark"), Main.myResources.getString("Empty")}, 2),
  FIRE(Main.myResources.getString("Fire"), new String[]{},
      new String[]{Main.myResources.getString("Catch"), Main.myResources.getString("Trees")}, 2),
  ROCK_PAPER_SCISSORS(Main.myResources.getString("RockPaperScissors"), new String[]{},
      new String[]{Main.myResources.getString("Rock"), Main.myResources.getString("Paper"),
          Main.myResources.getString("RPSThreshold")}, 3);

  private String myName;
  private final List<String> myMandatoryFields;
  private final List<String> myOptionalFields;
  private final Integer maxVal;

  /**
   * Constructor for SimType, setting its name and fields
   *
   * @param name            the type of Simulation
   * @param mandatoryFields the different Strings denoting acceptable Integer mandatoryFields for
   *                        that simulation
   */
  private SimType(String name, String[] mandatoryFields, String[] optionalFields, Integer val) {
    myName = name;
    myMandatoryFields = new ArrayList<>(Arrays.asList(mandatoryFields));
    myOptionalFields = new ArrayList<>(Arrays.asList(optionalFields));
    maxVal = val;
  }

  /**
   * Returns the SimType's mandatory field names
   */
  public List<String> getMandatoryFields() {
    return myMandatoryFields;
  }

  /**
   * Returns the SimType's optional field names
   */
  public List<String> getOptionalFields() {
    return List.copyOf(myOptionalFields);
  }

  /**
   * Returns the SimType's max cell value
   */
  public Integer getMaxVal() {
    return maxVal;
  }

  /**
   * Returns the appropriate SimType based off of its String name
   */
  public static SimType of(String code) {
    for (SimType r : SimType.values()) {
      if (code.contains(r.myName)) {
        return r;
      }
    }
    throw new IllegalArgumentException(
        String.format(Main.myResources.getString("INVALID_ARGUMENT"), code));
  }

  /**
   * Returns the name of the type
   */
  @Override
  public String toString() {
    return myName;
  }
}
