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
 * Class based mainly on ConfigParser.java from spike_simulation by Rhondu Smithwick and Robert C.
 * Duvall https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/XMLParser.java
 *
 * @author Tyler Jang
 */
public class StyleParser extends XMLParser {

  /**
   * Create parser for XML files of given type.
   */
  public StyleParser(String type) {
    super(type);
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
}
