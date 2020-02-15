package cellsociety.Controller;

import cellsociety.Main;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Abstract class for parsing XML files, with each sub-class implementation returning an instance of a Data class object.
 * Throws XMLExceptions in the event of a problem reading the XML file. These Exceptions should be caught, with their messages directed to the user.
 * This class and its sub-classes require that Main have a ResourceBundle named myResources from which messages can be drawn.
 * To use this class, create a sub-class and implement methods such as getSimulation(), getGrid(), or getStyle() using the given methods in this abstract class.
 * <p>
 * Class based mainly on ConfigParser.java from spike_simulation by Rhondu Smithwick and Robert C.
 * Duvall https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/XMLParser.java
 *
 * @author Tyler Jang
 */
public abstract class XMLParser {

  protected Document myDoc;
  protected static ResourceBundle RESOURCES = Main.myResources;
  protected static final String XML_END = ".xml";
  protected final String TYPE_ATTRIBUTE;
  protected final DocumentBuilder DOCUMENT_BUILDER;
  protected static final String ERROR_MESSAGE = RESOURCES.getString("XML_ERROR_MESSAGE");
  protected static final String ERROR_FIELD_MESSAGE = RESOURCES.getString("XML_ERROR_FIELD");

  /**
   * Creates an XML Parser instance given a type to read from. This type represents a root node of the XML file with
   * the format data *type* = ____.
   *
   * @param type root node attribute from which to determine if the XML is valid for this use case.
   */
  public XMLParser(String type) {
    DOCUMENT_BUILDER = getDocumentBuilder();
    TYPE_ATTRIBUTE = type;
  }

  /**
   * Gets the root element of an XML file. This will reset the reading process, such that the XML hierarchy can be built.
   *
   * @param xmlFile the File from which to read
   * @return      the Root Element
   */
  protected Element getRootElement(File xmlFile) {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  /**
   * Checks whether or not a file is an XML file based on its name. If the file contains ".xml", as defined in this class's
   * constants, the file will be considered valid for the time being.
   *
   * @param dataFile the File to be analyzed
   * @return      a boolean representing whether the file seems to be an XML file
   */
  protected boolean isXML(File dataFile) {
    return -1 != dataFile.getName().indexOf(XML_END);
  }

  /**
   * Checks if file is valid based on its root element. If this attribute does not match type, this file will be considered invalid.
   *
   * @param root the root Element
   * @param type the type of XML file (e.g. Simulation)
   * @return    a boolean if file is valid based on its root element
   */
  protected boolean isValidFile(Element root, String type) {
    return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
  }

  /**
   * Gets attribute of document based off of name, using the hierarchy of the XML file.
   *
   * @param e             the Element from which to retrieve the attribute
   * @param attributeName the attribute's label to retrieve
   * @return              the retrieved Attribute
   */
  protected String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  /**
   * Retrieves the text value in the XML file for a given tagName. Mandatory can be defined to determine whether or not an exception is thrown if a tag is not found.
   *
   * @param e       the Element from which to retrieve the attribute
   * @param tagName the tag to search for in the XML file
   * @param mandatory a boolean representing whether or not an exception will be thrown if the tag is not found
   * @return        the text for that tag
   * @throws XMLException if the field is considered mandatory and missing
   */
  protected String getTextValue(Element e, String tagName, boolean mandatory) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    } else if (mandatory) {
      throw new XMLException(ERROR_FIELD_MESSAGE, Simulation.DATA_TYPE, tagName);
    }
    return "";
  }

  /**
   * Required boilerplate code needed to make a documentBuilder.
   *
   * @return a DocumentBuilder for the entire class
   */
  protected DocumentBuilder getDocumentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }
}
