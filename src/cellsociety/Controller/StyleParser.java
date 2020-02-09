package cellsociety.Controller;

import cellsociety.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class for parsing XML files to determine simulation styling.
 *
 * Class based mainly on XMLParser.java from spike_simulation by Rhondu Smithwick and Robert C.
 * Duvall https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/XMLParser.java
 *
 * @author Tyler Jang
 */
//TODO: CREATE PARENT CLASS HIERARCHY
public class StyleParser {

  private Document myDoc;
  private static ResourceBundle RESOURCES = Main.myResources;
  private static final String XML_END = ".xml";
  private final String TYPE_ATTRIBUTE;
  private final DocumentBuilder DOCUMENT_BUILDER;
  private static final String ERROR_MESSAGE = RESOURCES.getString("XML_ERROR_MESSAGE");
  private static final String ERROR_FIELD_MESSAGE = RESOURCES.getString("XML_ERROR_FIELD_MESSAGE");

  /**
   * Create parser for XML files of given type.
   */
  public StyleParser(String type) {
    DOCUMENT_BUILDER = getDocumentBuilder();
    TYPE_ATTRIBUTE = type;
  }

  /**
   * Get data contained in this XML file as a map
   *
   * @param dataFile file from which to read configuration
   * @return Returns a Map of Strings for SimStyle to Styles holding styling information
   */
  public Map<String, Style> getStyle(File dataFile) {
    if (!isXML(dataFile)) {
      throw new XMLException(ERROR_MESSAGE, Style.DATA_TYPE);
    }

    Element root = getRootElement(dataFile);

    if (!isValidFile(root, Simulation.DATA_TYPE)) {
      throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
    }

    try {
      myDoc = getDocumentBuilder().parse(dataFile);
    } catch (SAXException e) {
      throw new XMLException(String.format(RESOURCES.getString("BadFormat"), dataFile.getName()));
    } catch (IOException e) {
      throw new XMLException(String.format(RESOURCES.getString("FileMissing"), dataFile.getName()));
    }

    Map<String, Style> styleMap = new HashMap<>();
    for (Style s: readStyles(root)) {
      styleMap.put(s.getType().toString(), s);
    }
    return styleMap;
  }

  /**
   * Generates a List of Styles of styling settings
   *
   * @param root document root
   * @return Returns a List of Styles holding styling information
   */
  private List<Style> readStyles(Element root) {
    List<Style> styles = new ArrayList<>();
    for (SimStyle s : SimStyle.values()) {
      NodeList nodeList = myDoc.getElementsByTagName(s.toString());
      Map<String, String> styleFields = new HashMap<>();
      for (int k = 0; k < nodeList.getLength(); k++) {
        Node node = nodeList.item(k);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element e = (Element) node;
          for (String field : s.getStyleFields()) {
            styleFields.put(field, e.getElementsByTagName(field).item(0).getTextContent());
          }
        }
      }
      styles.add(new Style(s.toString(), styleFields));
    }
    return styles;
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
