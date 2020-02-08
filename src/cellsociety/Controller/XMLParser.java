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
 * Class based mainly on XMLParser.java from spike_simulation by Rhondu Smithwick and Robert C.
 * Duvall https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/XMLParser.java
 *
 * @author Tyler Jang
 */
public class XMLParser {

  private static ResourceBundle RESOURCES = Main.myResources;
  private static final String ERROR_MESSAGE = RESOURCES.getString("XML_ERROR_MESSAGE");
  private static final String ERROR_FIELD_MESSAGE = RESOURCES.getString("XML_ERROR_FIELD_MESSAGE");
  private static final String XML_END = ".xml";
  private final String TYPE_ATTRIBUTE;
  private final DocumentBuilder DOCUMENT_BUILDER;

  /**
   * Create parser for XML files of given type.
   */
  public XMLParser(String type) {
    DOCUMENT_BUILDER = getDocumentBuilder();
    TYPE_ATTRIBUTE = type;
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

  /**
   * Get root element of an XML file
   *
   * @param xmlFile the File from which to read
   * @return the Root Element
   */
  private Element getRootElement(File xmlFile) {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  /**
   * Checks whether or not a file is an XML file based on its name
   */
  private boolean isXML(File dataFile) {
    return -1 != dataFile.getName().indexOf(XML_END);
  }

  /**
   * Checks if file is valid
   *
   * @param root the Root Element
   * @param type Simulation, the type of XML file
   * @return a boolean if file is valid
   */
  private boolean isValidFile(Element root, String type) {
    return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
  }

  /**
   * Gets attribute of document based off of name
   *
   * @param e             the Element to retrieve attribute
   * @param attributeName the attribute's label to retrieve
   * @return the retrieved Attribute
   */
  private String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  /**
   * Retrieves the value in the XML file for a given tagName
   *
   * @param e       the Element from which to retrieve the attribute
   * @param tagName the tag to search for in the XML file
   * @return the text for that tag
   * @throws XMLException if field is mandatory and missing
   */
  private String getTextValue(Element e, String tagName, boolean mandatory) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    } else if (mandatory) {
      throw new XMLException(ERROR_FIELD_MESSAGE, Simulation.DATA_TYPE, tagName);
    }
    return "";
  }

  /**
   * Required boilerplate code needed to make a documentBuilder
   *
   * @return a DocumentBuilder for the entire class
   */
  private DocumentBuilder getDocumentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }
}
