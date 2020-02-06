package cellsociety.Controller;

import cellsociety.Main;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GridParser {

    private DocumentBuilder myDocBuilder;
    private File myDataFile;

    public GridParser(DocumentBuilder docBuilder, File dataFile) {
        myDocBuilder = docBuilder;
        myDataFile = dataFile;
    }

    /**
     * Returns a grid of the type where all points are specified in the XML file NOTE: Assumes all
     * points have been specified
     *
     * @param dataFile the file from which to read
     * @return a Map representing points and values in the grid
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    public Map<Point, Integer> getAllGrid(File dataFile) throws IOException, SAXException {
        Document doc = myDocBuilder.parse(dataFile);
        NodeList nodeList = doc.getElementsByTagName(Main.myResources.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            for (int k = 0; k < vals.length; k++) {
                grid.put(new Point(j, k), Integer.parseInt(vals[k]));
            }
        }
        return grid;
    }

    /**
     * Returns a grid of the type where all points are specified in the XML file NOTE: Assumes all
     * points have been correctly specified as x y val
     *
     * @param dataFile the file from which to read
     * @return a Map representing points and values in the grid
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    public Map<Point, Integer> getSomeGrid(File dataFile) throws IOException, SAXException {
        Document doc = myDocBuilder.parse(dataFile);
        NodeList nodeList = doc.getElementsByTagName(Main.myResources.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            grid.put(new Point(Integer.parseInt(vals[1]), Integer.parseInt(vals[0])),
                    Integer.parseInt(vals[2]));
        }
        return grid;
    }

}
