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

    public static final int SQUARE = 0;
    public static final int HEXAGON = 1;

    private Document myDoc;
    private int myWidth;
    private int myHeight;
    private int myShape;

    /**
     * Constructor for GridParser objects
     * @param docBuilder DocumentBuilder for all XML Parsing
     * @param dataFile File from which to read
     * @param simulationSettings Map of Strings to Strings for specific configuration parameters
     * @throws XMLException File missing or incorrect format or non-Integer width or height
     */
    public GridParser(DocumentBuilder docBuilder, File dataFile, Map<String, String> simulationSettings) {
        try {
            myWidth = Integer.parseInt(simulationSettings.get(RESOURCES.getString("Width")));
            myHeight = Integer.parseInt(simulationSettings.get(RESOURCES.getString("Height")));
            String shape = simulationSettings.get(RESOURCES.getString("Shape"));
            if (shape != null && shape.length() > 0 && Integer.parseInt(shape) <= HEXAGON) {
                myShape = Integer.parseInt(shape);
            } else {
                myShape = SQUARE;
            }
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
    }

    /**
     * Returns a grid based off of the type of grid stored in the XML file
     *
     * @param gridType the type of grid (all, some, or random)
     * @return a Map representing points and values in the grid
     */
    public Map<Point, Integer> getGrid(int gridType) {
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
        if (myShape == SQUARE) {
            return getAllSquares();
        } else {
            return getAllHex();
        }
    }

    /**
     * Returns a grid of the type where all points are specified for square cells
     *
     * @return a Map representing points an values in the grid
     * @throws XMLException non-Integer value stored in grid
     */
    private Map<Point, Integer> getAllSquares() {
        NodeList nodeList = myDoc.getElementsByTagName(RESOURCES.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            for (int k = 0; k < vals.length; k++) {
                try {
                    grid.put(new Point(j, k), Integer.parseInt(vals[k]));
                } catch (NumberFormatException e) {
                    throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"), RESOURCES.getString("Grid"));
                }
            }
        }
        return grid;
    }

    /**
     * Returns a grid of the type where all points are specified for hexagon cells
     *
     * @return a Map representing points an values in the grid
     * @throws XMLException non-Integer value stored in grid or bad shape
     */
    private Map<Point, Integer> getAllHex() {
        NodeList nodeList = myDoc.getElementsByTagName(RESOURCES.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        if (wholeGrid.length <= 1) {
            throw new XMLException(RESOURCES.getString("BadShape"));
        }

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            for (int k = 0; k < vals.length && k < myWidth / 2 + 1; k++) {
                try {
                    Integer val = Integer.parseInt(vals[k]);
                    if (j % 2 == 0) {
                        grid.put(new Point(j *2, k), val);
                    } else {
                        grid.put(new Point(j*2 - 1, k), val);
                    }
                } catch (NumberFormatException e) {
                    throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"), RESOURCES.getString("Grid"));
                }
            }
        }
        return grid;
    }

    /**
     * Returns a grid of the type where all points are specified in the XML file NOTE: Assumes all
     * points have been correctly specified as x y val
     *
     * @return a Map representing points and values in the grid
     * @throws XMLException non-Integer value stored in grid
     */
    private Map<Point, Integer> getSomeGrid() {
        NodeList nodeList = myDoc.getElementsByTagName(RESOURCES.getString("Grid"));
        Map<Point, Integer> grid = new HashMap<>();
        String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");

        for (int j = 0; j < wholeGrid.length; j++) {
            String row = wholeGrid[j].trim();
            String[] vals = row.split(" ");
            try {
                Integer[] intVals = new Integer[vals.length];
                for (int k = 0; k < intVals.length; k ++) {
                    intVals[k] = Integer.parseInt(vals[k]);
                }
                grid.put(new Point(Math.min(intVals[0], myHeight), Math.min(intVals[1], myWidth)),
                        intVals[2]);
            } catch (NumberFormatException e) {
                throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"), RESOURCES.getString("Grid"));
            }
        }
        return grid;
    }
}
