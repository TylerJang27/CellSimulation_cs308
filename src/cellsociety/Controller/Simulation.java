package cellsociety.Controller;

import cellsociety.Main;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for storing the data needed to configure a Simulation Class stores accessible data in
 * myType, myColors, myDataValues, and myGrid
 * <p>
 * Structure based loosely on setup of Game.java in spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/Game.java
 *
 * @author Tyler Jang
 */
public class Simulation {

  private static ResourceBundle RESOURCES = Main.myResources;

  //name for the type of data file necessary to represent a simulation configuration file
  public static final String DATA_TYPE = RESOURCES.getString("Simulation");
  private static final String MISSING_MESSAGE = RESOURCES.getString("MISSING_MESSAGE");

  //mandatory fields in the data file for all Simulation objects
  public static final List<String> MANDATORY_DATA_FIELDS = List.of(
      RESOURCES.getString("Title"),
      RESOURCES.getString("Width"),
      RESOURCES.getString("Height")
  );

  //valid fields in the data file for all Simulation objects
  public static final List<String> OPTIONAL_DATA_FIELDS = List.of(
      RESOURCES.getString("Rate"),
      RESOURCES.getString("GridType"),
      RESOURCES.getString("Shape"),
      RESOURCES.getString("GridShape")
  );

  private Map<String, Integer> myDataValues;

  private SimType myType;
  private Map<Point, Integer> myGrid;

  /**
   * Full constructor to initialize a Simulation from given data.
   *
   * @param type the type of Simulation based off of enum SimType
   */
  public Simulation(String type) {
    myType = SimType.of(type);
    myDataValues = new HashMap<>();
    myGrid = new HashMap<>();
  }

  /**
   * Constructor taking in a data structure of Strings.
   *
   * @param dataValues map of field names to their values
   */
  public Simulation(Map<String, String> dataValues) {
    this(dataValues.get(MANDATORY_DATA_FIELDS.get(0)));
    this.setFields(dataValues);
  }

  /**
   * Setter for grid
   *
   * @param grid map of grid points to their values
   */
  public void setGrid(Map<Point, Integer> grid) {
    myGrid = grid;
  }

  /**
   * Function to set myDataValues to map from a field key to a value Sets value to null if
   * dataValues does not contain the value Note: Implementation of model will need to specify
   * defaults
   *
   * @param dataValues map of field names to their values
   */
  private void setFields(Map<String, String> dataValues) {
    extractMandatoryValues(dataValues);
    extractOptionalValues(dataValues);
  }

  /**
   * Removes the mandatory values from dataValues and throws error if missing
   *
   * @param dataValues Map of String to String from constructor
   * @throws XMLException if mandatory field is missing or if invalid data type when expecting
   *                      Integer
   */
  private void extractMandatoryValues(Map<String, String> dataValues) {
    for (int k = 1; k < MANDATORY_DATA_FIELDS.size(); k++) {
      String val = dataValues.get(MANDATORY_DATA_FIELDS.get(k));
      if (val != null) {
        try {
          myDataValues.put(MANDATORY_DATA_FIELDS.get(k), Integer.parseInt(val));
        } catch (NumberFormatException e) {
          throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"),
              MANDATORY_DATA_FIELDS.get(k));
        }
      } else {
        throw new XMLException(MISSING_MESSAGE, myType);
      }
    }
    for (String field : myType.getMandatoryFields()) {
      if (field.length() > 0) {
        try {
          Integer val = Integer.parseInt(dataValues.get(field));
          myDataValues.put(field, val);
        } catch (NumberFormatException e) {
          throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"), field);
        }
      }
    }
  }

  /**
   * Removes the optional values from dataValues but throws no error if missing
   *
   * @param dataValues Map of String to String from constructor
   * @throws XMLException if invalid data type when expecting Integer
   */
  private void extractOptionalValues(Map<String, String> dataValues) {
    for (String field : OPTIONAL_DATA_FIELDS) {
      String val = dataValues.get(field);
      if (val != null) {
        try {
          myDataValues.put(field, Integer.parseInt(val));
        } catch (NumberFormatException e) {
          throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"), field);
        }
      }
    }
    for (String field : myType.getOptionalFields()) {
      String val = dataValues.get(field);
      if (field.length() > 0 && val != null) {
        try {
          myDataValues.put(field, Integer.parseInt(val));
        } catch (NumberFormatException e) {
          throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"), field);
        }
      }
    }
  }

  /**
   * Returns simulation type based off of enum SimType
   */
  public SimType getType() {
    return myType;
  }

  /**
   * Returns Map with all String and Integer values for fields
   */
  public Map<String, Integer> getValueMap() {
    return myDataValues;
  }

  /**
   * Returns Integer value matched to field key
   */
  public Integer getValue(String field) {
    return myDataValues.getOrDefault(field, -1);
  }

  /**
   * Returns Grid, with initialized values (others all default to 0)
   */
  public Map<Point, Integer> getGrid() {
    return myGrid;
  }

}
