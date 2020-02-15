package cellsociety.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for parsing XML files to determine simulation styling. Extends the abstract class XMLParser.
 * <p>
 * Class based mainly on ConfigParser.java from spike_simulation by Rhondu Smithwick and Robert C.
 * Duvall https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/XMLParser.java
 *
 * @author Tyler Jang
 */
public class StyleParser extends XMLParser {

  /**
   * Creates an XML Parser instance given a type to read from. This type represents a root node of the XML file with
   * the format data *type* = style.
   *
   * @param type root node attribute from which to determine if the XML is valid for this use case
   */
  public StyleParser(String type) {
    super(type);
  }

  /**
   * Get data contained in this XML file as a Map of Strings to Styles for each SimStyle.
   *
   * @param dataFile file from which to read configuration
   * @return Returns a Map of Strings for SimStyle to Styles holding styling information
   * @throws XMLException if the file is not of the right format or cannot be found.
   */
  public Map<String, Style> getStyle(File dataFile) {
    if (!isXML(dataFile)) {
      throw new XMLException(ERROR_MESSAGE, Style.DATA_TYPE);
    }

    Element root = getRootElement(dataFile);

    if (!isValidFile(root, Style.DATA_TYPE)) {
      throw new XMLException(ERROR_MESSAGE, Style.DATA_TYPE);
    }

    try {
      myDoc = getDocumentBuilder().parse(dataFile);
    } catch (SAXException e) {
      throw new XMLException(String.format(RESOURCES.getString("BadFormat"), dataFile.getName()));
    } catch (IOException e) {
      throw new XMLException(String.format(RESOURCES.getString("FileMissing"), dataFile.getName()));
    }

    Map<String, Style> styleMap = new HashMap<>();
    for (Style s : readStyles()) {
      styleMap.put(s.getType().toString(), s);
    }
    return styleMap;
  }

  /**
   * Generates a List of Styles of styling settings to be later mapped to their SimStyle.
   *
   * @return Returns a List of Styles holding styling information
   */
  private List<Style> readStyles() {
    List<Style> styles = new ArrayList<>();
    for (SimStyle s : SimStyle.values()) {
      NodeList nodeList = myDoc.getElementsByTagName(s.toString());
      Map<String, String> styleFields = new HashMap<>();
      extractOneStyle(s, nodeList, styleFields);
      styles.add(new Style(s.toString(), styleFields));
    }
    return styles;
  }

  /**
   * Extracts information about one style and adds it to styleFields.
   *
   * @param s           the Simulation type for this style
   * @param nodeList    a List of Nodes referring to the children of the Element
   * @param styleFields a Map to which the style information should be added
   */
  private void extractOneStyle(SimStyle s, NodeList nodeList, Map<String, String> styleFields) {
    for (int k = 0; k < nodeList.getLength(); k++) {
      Node node = nodeList.item(k);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element e = (Element) node;
        for (String field : s.getStyleFields()) {
          styleFields.put(field, e.getElementsByTagName(field).item(0).getTextContent());
        }
      }
    }
  }
}
