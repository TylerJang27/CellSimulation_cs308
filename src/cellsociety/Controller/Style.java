package cellsociety.Controller;

import cellsociety.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for storing the data needed to configure Simulation View Styling. Styles are configured globally for each SimStyle.
 * <p>
 * Structure based loosely on setup of Game.java in spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/Game.java
 *
 * @author Tyler Jang
 */
public class Style {

  private static ResourceBundle RESOURCES = Main.myResources;

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
   * Returns simulation type based off of enum SimStyle.
   *
   * @return    the SimStyle type for this Style
   */
  public SimStyle getType() {
    return myStyle;
  }

  /**
   * Returns Integer value matched to field key.
   *
   * @param field the field from which to retrieve String data for a KVP
   * @return    the String for the requested field, or default "" if field is not in the Map
   */
  public String getValue(String field) {
    return myDataValues.getOrDefault(field, "");
  }

  /**
   * Builds a List of Maps of style parameters used for CellStateConfiguration, with one for each cell state.
   *
   * @return    a List of Maps for information about each state
   */
  public List<Map<String, String>> getConfigParameters() {
    List<Map<String, String>> maps = new ArrayList<>();
    String fill;
    if (getValue(RESOURCES.getString("Display")).equals(RESOURCES.getString("Image"))) {
      fill = RESOURCES.getString("Image");
    } else {
      fill = RESOURCES.getString("Color");
    }
    for (String s : getType().getStyleFields()) {
      if (!getType().getGenerics().contains(s)) {
        buildParameters(maps, fill, s);
      }
    }
    return maps;
  }

  /**
   * Builds parameters for CellStateConfiguration.
   *
   * @param maps a List of maps to add to CellStateConfiguration parameters
   * @param fill image or color
   * @param s    field in Style fields
   */
  private void buildParameters(List<Map<String, String>> maps, String fill, String s) {
    Map<String, String> params = new HashMap<>();
    String val = getValue(s);
    if (val.contains("img") && fill.equals(RESOURCES.getString("Image"))) {
      params.put(fill, val);
    } else {
      params.put(RESOURCES.getString("Color"), val);
    }
    params.put("id", s);
    maps.add(params);
  }
}
