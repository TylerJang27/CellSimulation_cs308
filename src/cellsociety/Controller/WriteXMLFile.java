package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.Grid;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for creating XML files based off of simulation configuration.
 *
 * Class based mainly on WriteXMLFile.java by mkyong
 * https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
 *
 * @author Tyler Jang
 */
public class WriteXMLFile {
    private static final int ALL_GRID = 0;
    private static final String YES = "yes";
    private Simulation mySim;
    private Grid myGrid;
    private int myRate;
    private static final ResourceBundle RESOURCES = Main.myResources;

    /**
     * Constructor for WriteXMLFile, passing simulation information and the current Grid
     * @param sim Simulation holding all configuration information
     * @param grid Grid sub-class with all current grid states
     * @param rate Current rate
     */
    public WriteXMLFile(Simulation sim, Grid grid, int rate) {
            mySim = sim;
            myGrid = grid;
            myRate = rate;
    }

    /**
     * Write current configuration into an XML file that can be reloaded at a later time
     * @return file name to which data was saved
     * @throws XMLException for cases where file generators cannot be created
     */
    public String writeSimulationXML() {
        String fileAddress = generateFileName();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(RESOURCES.getString("XML_WRITE_CREATE"));
        }

        Document doc = docBuilder.newDocument();
        Element rootElement = createRoot(doc);
        doc.appendChild(rootElement);

        extractFields(doc, rootElement);
        extractGrid(doc, rootElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new XMLException(RESOURCES.getString("XML_WRITE_CREATE"));
        }
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(fileAddress));
        transformer.setOutputProperty(OutputKeys.INDENT, YES);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new XMLException(RESOURCES.getString("XML_WRITE_CREATE"));
        }

        return fileAddress;
    }

    /**
     * Generates the root element, holding the Simulation characteristic
     * @param doc Document with which to build XML hierarchy
     * @return the rootElement of the doc
     */
    private Element createRoot(Document doc) {
        Element rootElement = doc.createElement(RESOURCES.getString("Data"));
        Attr attr = doc.createAttribute(RESOURCES.getString("Type"));
        attr.setValue(RESOURCES.getString("Simulation"));
        rootElement.setAttributeNode(attr);
        Element title = doc.createElement(RESOURCES.getString("Title"));
        title.appendChild(doc.createTextNode(mySim.getType().toString()));
        rootElement.appendChild(title);
        return rootElement;
    }

    /**
     * Generates the file name to which the file shall be stored
     * @return String representing the path to the file
     */
    private String generateFileName() {
        DateFormat dateFormat = new SimpleDateFormat("MMdd_HHmm");
        Date date = new Date();
        return String.format(RESOURCES.getString("FileAddress"), mySim.getType().toString(), dateFormat.format(date));
    }

    /**
     * Extracts current grid information from mySim to save to doc
     * @param doc Document with which to build XML hierarchy
     * @param rootElement Root element, consisting of the Simulation type, to nest information under
     */
    private void extractGrid(Document doc, Element rootElement) {
        Element gridElement = doc.createElement(RESOURCES.getString("Grid"));
        int width = mySim.getValue(RESOURCES.getString("Width"));
        int height = mySim.getValue(RESOURCES.getString("Height"));

        for (int j = 0; j < height; j++) {
            Element row = doc.createElement(RESOURCES.getString("Row"));
            StringBuilder rowString = new StringBuilder("");
            for (int k = 0; k < width; k++) {
                rowString.append(String.format("%d ", myGrid.getState(j, k)));
            }
            row.appendChild(doc.createTextNode(rowString.toString().trim()));
            gridElement.appendChild(row);
        }

        rootElement.appendChild(gridElement);
    }

    /**
     * Extracts fields and configuration information from mySim to add them to doc
     * @param doc Document with which to build XML hierarchy
     * @param rootElement Root element, consisting of the Simulation type, to nest information under
     */
    private void extractFields(Document doc, Element rootElement) {
        Map<String, Integer> valueMap = mySim.getValueMap();
        for (String field: valueMap.keySet()) {
            Element e = doc.createElement(field);
            if (field.equals(RESOURCES.getString("Rate"))) {
                e.appendChild(doc.createTextNode(String.format("%d", myRate)));
            } else if (field.equals(RESOURCES.getString("GridType"))) {
                e.appendChild(doc.createTextNode(String.format("%d", ALL_GRID)));
            } else {
                e.appendChild(doc.createTextNode(valueMap.get(field).toString()));
            }
            rootElement.appendChild(e);
        }
    }
}
