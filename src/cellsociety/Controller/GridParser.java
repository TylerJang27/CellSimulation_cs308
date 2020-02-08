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
import java.util.ResourceBundle;

public class GridParser {

    private static final ResourceBundle RESOURCES = Main.myResources;
    public static final int ALL = 0;
    public static final int SOME = 1;
    public static final int RANDOM = 2;
    public static final int PARAMETRIZED_RANDOM = 3;

    private Document myDoc;
    private int myWidth;
    private int myHeight;

    //TODO: add comments
    public GridParser(DocumentBuilder docBuilder, File dataFile, String width, String height) { //TODO: MAKE ENUM IF NECESSARY FOR THE DIFFERENT TYPES
        try {
            myWidth = Integer.parseInt(width);
            myHeight = Integer.parseInt(height);
        } catch (NumberFormatException e) {
            throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"), RESOURCES.getString("WidthHeight"));
        }

        try {
            myDoc = docBuilder.parse(dataFile);
        } catch (SAXException e) {
            throw new XMLException(String.format(RESOURCES.getString("BadFormat"), dataFile.getName()));
        } catch (IOException e) {
            throw new XMLException(String.format(RESOURCES.getString("FileMissing"), dataFile.getName()));
        }
        //TODO: determine type of grid needed to read (both some, all, random) as well as shape
    }

    /**
     * Returns a grid based off of the type of grid stored in the XML file
     *
     * @param gridType the type of grid (all, some, or random)
     * @return a Map representing points and values in the grid
     */
    public Map<Point, Integer> getGrid(int gridType) {
        //FIXME: This should all be in Grid Parser
        if (gridType == ALL) {
            return getAllGrid();
        } else if (gridType == SOME) {
            return getSomeGrid();
        } else {
            return new HashMap<>();
        }
    }

    /**
     * Returns a grid of the type where all points are specified in the XML file NOTE: Assumes all
     * points have been specified
     *
     * @return a Map representing points and values in the grid
     */
    private Map<Point, Integer> getAllGrid()  {
        NodeList nodeList = myDoc.getElementsByTagName(Main.myResources.getString("Grid"));
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
     * @return a Map representing points and values in the grid
     */
    private Map<Point, Integer> getSomeGrid() {
        NodeList nodeList = myDoc.getElementsByTagName(Main.myResources.getString("Grid"));
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
