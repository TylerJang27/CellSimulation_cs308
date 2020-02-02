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
import org.w3c.dom.Node;
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


    /**
     * Create parser for XML files of given type.
     */
    public XMLParser (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /**
     * Get data contained in this XML file as an object
     * FIXME: Add additional exception comments
     */
    public Simulation getSimulation (File dataFile) throws IOException, SAXException {
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
        String gridType = getTextValue(root, Main.myResources.getString("GridType"));
        Map grid = getGrid(dataFile, gridType);
        return new Simulation(simulationSettings, grid);
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

    //FIXME: Comments
    private Map<Point, Integer> getGrid(File dataFile, String gridType) throws IOException, SAXException {
        if (gridType.equals(Main.myResources.getString("All"))) {
            return getAllGrid(dataFile);
        } else if (gridType.equals(Main.myResources.getString("Some"))) {
            return getSomeGrid(dataFile);
        } else {
            return new HashMap<>();
        }
    }

    //FIXME: Comments, note assumption of grid size
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

    //FIXME: Comments, assumptions
    private Map<Point, Integer> getSomeGrid(File dataFile) throws IOException, SAXException {
        Document doc = getDocumentBuilder().parse(dataFile);
        NodeList nodeList = doc.getElementsByTagName(Main.myResources.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            System.out.println(row);
            grid.put(new Point(Integer.parseInt(vals[0]), Integer.parseInt(vals[1])), Integer.parseInt(vals[2]));
        }
        return grid;
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
