package cellsociety.Controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cellsociety.Main;
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
    public static final String ERROR_MESSAGE = Main.myResources.getString("XML_ERROR_MESSAGE");
    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    public static final int ALL = 0;
    public static final int SOME = 1;
    public static final int RANDOM = 2;

    /**
     * Create parser for XML files of given type.
     */
    public XMLParser (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /**
     * Get data contained in this XML file as an object
     *
     * @param dataFile file from which to read configuration
     * @return Returns a Simulation with all of its configuration information stored
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    public Simulation getSimulation (File dataFile) throws IOException, SAXException {
        //resets file reading to root of XML File
        Element root = getRootElement(dataFile);

        if (! isValidFile(root, Simulation.DATA_TYPE)) {
            throw new XMLException(ERROR_MESSAGE, Simulation.DATA_TYPE);
        }
        // read data associated with the fields given by the object
        Map<String, String> simulationSettings = readSettings(root);
        String gridType = getTextValue(root, Main.myResources.getString("GridType"));
        Map grid = getGrid(dataFile, Integer.parseInt(gridType));
        return new Simulation(simulationSettings, grid);
    }

    /**
     * Generates a Map of Strings of configuration settings
     * @param root document root
     * @return a Map of Strings of configuration settings
     */
    private Map<String, String> readSettings(Element root) {
        Map<String, String> simulationSettings = new HashMap<>();
        for (String field : Simulation.DATA_FIELDS) {
            simulationSettings.put(field, getTextValue(root, field));
        }
        for (String field: SimType.of(simulationSettings.get(Simulation.DATA_FIELDS.get(0))).getFields()) {
            simulationSettings.put(field, getTextValue(root, field));
        }
        return simulationSettings;
    }

    /**
     * Get root element of an XML file
     *
     * @param xmlFile the File from which to read
     * @return the Root Element
     */
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

    /**
     * Checks if file is valid
     *
     * @param root the Root Element
     * @param type Simulation, the type of XML file
     * @return a boolean if file is valid
     */
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    /**
     * Gets attribute of document based off of name
     *
     * @param e the Element to retrieve attribute
     * @param attributeName the attribute's label to retrieve
     * @return the retrieved Attribute
     */
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    /**
     * Retrieves the value in the XML file for a given tagName
     *
     * @param e the Element from which to retrieve the attribute
     * @param tagName the tag to search for in the XML file
     * @return the text for that tag
     */
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

    /**
     * Returns a grid based off of the type of grid stored in the XML file
     *
     * @param dataFile the file from which to read
     * @param gridType the type of grid (all, some, or random)
     * @return a Map representing points and values in the grid
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    private Map<Point, Integer> getGrid(File dataFile, Integer gridType) throws IOException, SAXException {
        if (gridType.equals(ALL)) {
            return getAllGrid(dataFile);
        } else if (gridType.equals(SOME)) {
            return getSomeGrid(dataFile);
        } else {
            return new HashMap<>();
        }
    }

    /**
     * Returns a grid of the type where all points are specified in the XML file
     * NOTE: Assumes all points have been specified
     *
     * @param dataFile the file from which to read
     * @return a Map representing points and values in the grid
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    private Map<Point, Integer> getAllGrid(File dataFile) throws IOException, SAXException {
        Document doc = getDocumentBuilder().parse(dataFile);
        NodeList nodeList = doc.getElementsByTagName(Main.myResources.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            for (int k = 0; k < vals.length; k ++) {
                grid.put(new Point(k, j), Integer.parseInt(vals[k]));
            }
        }
        return grid;
    }

    /**
     * Returns a grid of the type where all points are specified in the XML file
     * NOTE: Assumes all points have been correctly specified as x y val
     *
     * @param dataFile the file from which to read
     * @return a Map representing points and values in the grid
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    private Map<Point, Integer> getSomeGrid(File dataFile) throws IOException, SAXException {
        Document doc = getDocumentBuilder().parse(dataFile);
        NodeList nodeList = doc.getElementsByTagName(Main.myResources.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            grid.put(new Point(Integer.parseInt(vals[0]), Integer.parseInt(vals[1])), Integer.parseInt(vals[2]));
        }
        return grid;
    }

    /**
     * Required boilerplate code needed to make a documentBuilder
     * @return a DocumentBuilder for the entire class
     */
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
