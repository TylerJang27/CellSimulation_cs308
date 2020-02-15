package cellsociety.Controller;

import cellsociety.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An enumerated type for the Simulation Style, specifying all of the Simulation Styles and the
 * necessary fields for each type
 * <p>
 * To add a new style for a Simulation, simply add a new core SimStyle with its name and required
 * fields Ensure XML Files are formatted correctly
 * <p>
 * Portions taken from ESRBRating.java from spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/enumerated/ESRBRating.java
 *
 * @author Tyler Jang
 */
public enum SimStyle {
  GAME_OF_LIFE(Main.myResources.getString("GameOfLife"),
      new String[]{Main.myResources.getString("Empty"), Main.myResources.getString("Alive")}),
  PERCOLATION(Main.myResources.getString("Percolation"),
      new String[]{Main.myResources.getString("Blocked"), Main.myResources.getString("Empty"),
          Main.myResources.getString("Water")}),
  SEGREGATION(Main.myResources.getString("Segregation"),
      new String[]{Main.myResources.getString("Empty"), Main.myResources.getString("A"),
          Main.myResources.getString("B")}),
  PREDATOR_PREY(Main.myResources.getString("PredatorPrey"),
      new String[]{Main.myResources.getString("Empty"), Main.myResources.getString("Fish"),
          Main.myResources.getString("Shark")}),
  FIRE(Main.myResources.getString("Fire"),
      new String[]{Main.myResources.getString("Empty"), Main.myResources.getString("Trees"),
          Main.myResources.getString("Burning")}),
  ROCK_PAPER_SCISSORS(Main.myResources.getString("RockPaperScissors"),
      new String[]{Main.myResources.getString("Rock"), Main.myResources.getString("Paper"),
          Main.myResources.getString("Scissors")});

  private String myName;
  private final List<String> myStyleFields;
  private final List<String> GENERIC_FIELDS = List.of(
      Main.myResources.getString("Outline"),
      Main.myResources.getString("Display"),
      Main.myResources.getString("Width"),
      Main.myResources.getString("Height")
  );

  /**
   * Constructor for SimType, setting its name and fields.
   *
   * @param name        the name for that type of Simulation
   * @param styleFields the different Strings denoting acceptable styles for that simulation
   */
  private SimStyle(String name, String[] styleFields) {
    myName = name;
    myStyleFields = new ArrayList<>(Arrays.asList(styleFields));
    myStyleFields.addAll(GENERIC_FIELDS);
  }

  /**
   * Returns the SimStyle's style field names.
   *
   * @return a copy of the List of style fields
   */
  public List<String> getStyleFields() {
    return List.copyOf(myStyleFields);
  }

  /**
   * Returns the appropriate SimStyle based off of its String name.
   *
   * @param code String representing the name of that SimStyle instance
   * @return    the SimStyle instance for that code
   */
  public static SimStyle of(String code) {
    for (SimStyle r : SimStyle.values()) {
      if (code.contains(r.myName)) {
        return r;
      }
    }
    throw new IllegalArgumentException(
        String.format(Main.myResources.getString("INVALID_ARGUMENT"), code));
  }

  /**
   * Returns strings representing all of the generic fields.
   *
   * @return the Generic fields for a given instance of SimStyle
   */
  public List<String> getGenerics() {
    return GENERIC_FIELDS;
  }

  /**
   * Returns the name of the type.
   *
   * @return    the String name of the SimStyle instance
   */
  @Override
  public String toString() {
    return myName;
  }

}
