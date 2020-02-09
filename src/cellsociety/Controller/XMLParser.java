package cellsociety.Controller;

import cellsociety.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Abstract class for parsing XML files.
 *
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
     * Default constructor for XMLParser
     * @param type root node field
     */
    public XMLParser(String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /**
    * Get root element of an XML file
    *
    * @param xmlFile the File from which to read
    * @return the Root Element
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
     * Checks whether or not a file is an XML file based on its name
     */
    protected boolean isXML(File dataFile) {
        return -1 != dataFile.getName().indexOf(XML_END);
    }

    /**
     * Checks if file is valid
     *
     * @param root the Root Element
     * @param type Simulation, the type of XML file
     * @return a boolean if file is valid
     */
    protected boolean isValidFile(Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    /**
     * Gets attribute of document based off of name
     *
     * @param e             the Element to retrieve attribute
     * @param attributeName the attribute's label to retrieve
     * @return the retrieved Attribute
     */
    protected String getAttribute(Element e, String attributeName) {
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
     * Required boilerplate code needed to make a documentBuilder
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
