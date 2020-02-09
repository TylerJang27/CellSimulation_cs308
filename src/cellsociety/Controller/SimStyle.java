package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An enumerated type for the Simulation Style, specifying all of the Simulation Styles and the
 * necessary fields for each type
 *
 * To add a new style for a Simulation, simply add a new core SimStyle with its name and required fields
 * Ensure XML Files are formatted correctly
 *
 * Portions taken from ESRBRating.java from spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/enumerated/ESRBRating.java
 *
 * @author Tyler Jang
 */
public enum SimStyle {
  GAME_OF_LIFE(Main.myResources.getString("GameOfLife"), new String[]{Main.myResources.getString("Alive")}),
  PERCOLATION(Main.myResources.getString("Percolation"), new String[]{Main.myResources.getString("Water"), Main.myResources.getString("Blocked")}),
  SEGREGATION(Main.myResources.getString("Segregation"), new String[]{Main.myResources.getString("A"), Main.myResources.getString("B")}),
  PREDATOR_PREY(Main.myResources.getString("PredatorPrey"), new String[]{Main.myResources.getString("Shark"), Main.myResources.getString("Fish")}),
  FIRE(Main.myResources.getString("Fire"), new String[]{Main.myResources.getString("Burning"), Main.myResources.getString("Trees")}),
  ROCK_PAPER_SCISSORS(Main.myResources.getString("RockPaperScissors"), new String[]{Main.myResources.getString("Rock"), Main.myResources.getString("Paper"), Main.myResources.getString("Scissors")});

  private String myName;
  private final List<String> myStyleFields;
  private final List<String> GENERIC_FIELDS = List.of(
        Main.myResources.getString("Outline"),
        Main.myResources.getString("Display"),
        Main.myResources.getString("Width"),
        Main.myResources.getString("Height")
  );

  /**
   * Constructor for SimType, setting its name and fields
   *
   * @param name   the type of Simulation
   * @param styleFields the different Strings denoting acceptable styles for that simulation
   */
  private SimStyle(String name, String[] styleFields) {
    myName = name;
    myStyleFields = new ArrayList<>(Arrays.asList(styleFields));
    myStyleFields.addAll(GENERIC_FIELDS);
  }

  /**
   * Returns the SimStyle's style field names
   */
  public List<String> getStyleFields() {
    return myStyleFields;
  }

  /**
   * Returns the appropriate SimType based off of its String name
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
   * Returns strings representing all of the SimStyle names
   */
  public static final List<String> getSimulations() {
    List<String> simulations = new ArrayList<>();
    for (SimStyle s: SimStyle.values()) {
      simulations.add(s.toString());
    }
    return simulations;
  }

  /**
   * Returns the name of the type
   */
  @Override
  public String toString() {
    return myName;
  }

}
