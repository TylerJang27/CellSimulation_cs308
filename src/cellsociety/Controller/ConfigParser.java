package cellsociety.Controller;

import cellsociety.Main;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for parsing XML files to determine simulation configuration.
 *
 * Class based mainly on ConfigParser.java from spike_simulation by Rhondu Smithwick and Robert C.
 * Duvall https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/XMLParser.java
 *
 * @author Tyler Jang
 */
public class ConfigParser extends XMLParser{

  /**
   * Create parser for XML files of given type.
   */
  public ConfigParser(String type) {
    super(type);
  }

  /**
   * Get data contained in this XML file as an object
   *
   * @param dataFile file from which to read configuration
   * @return Returns a Simulation with all of its configuration information stored
   */
  public Simulation getSimulation(File dataFile) {
    if (!isXML(dataFile)) {
      throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
    }

    Element root = getRootElement(dataFile);

    if (!isValidFile(root, Simulation.DATA_TYPE)) {
      throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
    }

    Map<String, String> simulationSettings = readSettings(root);
    int gridType = Integer.parseInt(getTextValue(root, RESOURCES.getString("GridType"), true));

    Simulation mySim = new Simulation(simulationSettings);
    GridParser myGridParser = new GridParser(getDocumentBuilder(), dataFile, mySim);
    mySim.setGrid(myGridParser.getGrid(gridType));
    return mySim;
  }

  /**
   * Generates a Map of Strings of configuration settings
   *
   * @param root document root
   * @return a Map of Strings of configuration settings
   */
  private Map<String, String> readSettings(Element root) {
    Map<String, String> simulationSettings = new HashMap<>();
    for (String field : Simulation.MANDATORY_DATA_FIELDS) {
      simulationSettings.put(field, getTextValue(root, field, true));
    }
    for (String field: Simulation.OPTIONAL_DATA_FIELDS) {
      putOptional(root, simulationSettings, field);
    }
    for (String field : SimType.of(simulationSettings.get(Simulation.MANDATORY_DATA_FIELDS.get(0)))
        .getMandatoryFields()) {
      simulationSettings.put(field, getTextValue(root, field, true));
    }
    for (String field : SimType.of(simulationSettings.get(Simulation.MANDATORY_DATA_FIELDS.get(0)))
            .getOptionalFields()) {
      putOptional(root, simulationSettings, field);
    }
    return simulationSettings;
  }

  /**
   * Adds value in field to simulationSettings if its value exists, throws no exception
   * @param root document root
   * @param simulationSettings Map of fields and data for Simulation
   * @param field name of field in XML file
   */
  private void putOptional(Element root, Map<String, String> simulationSettings, String field) {
    String val = getTextValue(root, field, false);
    if (val.length() > 0) {
      simulationSettings.put(field, val);
    }
  }
}
