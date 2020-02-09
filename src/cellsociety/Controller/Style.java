package cellsociety.Controller;

import cellsociety.Main;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class for storing the data needed to configure Simulation View Styling
 *
 * Structure based loosely on setup of Game.java in spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/Game.java
 *
 * @author Tyler Jang
 */
public class Style {

  private static ResourceBundle RESOURCES = Main.myResources;

  //name for the type of data file necessary to represent a simulation configuration file
  public static final String DATA_TYPE = RESOURCES.getString("Style");

  private SimStyle myStyle;
  private Map<String, String> myDataValues;

  /**
   * Full constructor to initialize a Simulation from given data.
   *
   * @param type the type of Simulation based off of enum SimStyle
   */
  public Style(String type) {
    myStyle = SimStyle.of(type);
    myDataValues = new HashMap<>();
  }

  /**
   * Constructor taking in a data structure of Strings.
   *
   * @param dataValues map of field names to their values
   */
  public Style(String style, Map<String, String> dataValues) {
    this(style);
    myDataValues = dataValues;
  }

  /**
   * Returns simulation type based off of enum SimStyle
   */
  public SimStyle getType() {
    return myStyle;
  }

  /**
   * Returns Map with all String and String values for fields
   */
  public Map<String, String> getValueMap() {
    return myDataValues;
  }

  /**
   * Returns Integer value matched to field key
   */
  public String getValue(String field) {
    return myDataValues.getOrDefault(field, "");
  }
}
