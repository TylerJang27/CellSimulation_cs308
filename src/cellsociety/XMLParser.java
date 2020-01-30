package cellsociety;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for parsing XML files to determine simulation configuration.
 *
 * Class based mainly on XMLParser.java from spike_simulation by Rhondu Smithwick and Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/XMLParser.java
 *
 * FIXME: Comment style
 */
public class XMLParser {

    // Readable error message that can be displayed by the GUI
    public static final String ERROR_MESSAGE = "XML file does not correctly represent %s";
    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;


    /**
     * Create parser for XML files of given type.
     */
    public XMLParser (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /**
     * Get data contained in this XML file as an object
     */
    public Simulation getSimulation (File dataFile) {
        //resets file reading to root of XML File
        Element root = getRootElement(dataFile);

        if (! isValidFile(root, Simulation.DATA_TYPE)) {
            throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
        }
        // read data associated with the fields given by the object
        Map<String, String> simulationSettings = new HashMap<>();
        for (String field : Simulation.DATA_FIELDS) {
            simulationSettings.put(field, getTextValue(root, field));
        }
        for (String field: SimType.of(simulationSettings.get(Simulation.DATA_FIELDS.get(0))).getFields()) {
            simulationSettings.put(field, getTextValue(root, field));
        }
        return new Simulation(simulationSettings);
    }

    // get root element of an XML file
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // returns if this is a valid XML file for the specified object type
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    // get value of Element's attribute
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    // get value of Element's text
    private String getTextValue (Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: Determine if should be return String or Exception
            throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
            //return "";
        }
    }

    // required boilerplate code needed to make a documentBuilder
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
