package cellsociety.Controller;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for parsing XML files to retrieve information about initial grid configuration
 *
 * @author Tyler Jang
 */
public class GridParser extends XMLParser {

  public static final int ALL = 0;
  public static final int SOME = 1;
  public static final int RANDOM = 2;
  public static final int PARAMETRIZED_RANDOM = 3;

  public static final int SQUARE = 0;
  public static final int HEXAGON = 1;

  private int myWidth;
  private int myHeight;
  private int myShape;
  private Integer maxVal;

  /**
   * Constructor for GridParser objects
   *
   * @param docBuilder DocumentBuilder for all XML Parsing
   * @param dataFile   File from which to read
   * @param sim        Simulation storing relevant configuration information
   * @throws XMLException File missing or incorrect format or non-Integer width or height
   */
  public GridParser(DocumentBuilder docBuilder, File dataFile, Simulation sim) {
    super("");
    maxVal = sim.getType().getMaxVal();
    myWidth = sim.getValue(RESOURCES.getString("Width"));
    myHeight = sim.getValue(RESOURCES.getString("Height"));
    try {
      Integer shape = sim.getValue(RESOURCES.getString("Shape"));
      if (shape != null && shape >= HEXAGON) {
        myShape = shape;
      } else {
        myShape = SQUARE;
      }
    } catch (NumberFormatException e) {
      throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"),
          RESOURCES.getString("WidthHeight"));
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
   * @param gridType the type of grid (all, some, random or parametrized random)
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
  private Map<Point, Integer> getAllGrid() {
    NodeList nodeList = myDoc.getElementsByTagName(RESOURCES.getString("Grid"));
    Map<Point, Integer> grid = new HashMap<>();
    String[] wholeGrid = nodeList.item(0).getTextContent().trim().split("\n");
    if (myShape == SQUARE) {
      return getAllSquares(grid, wholeGrid);
    } else {
      return getAllHex(grid, wholeGrid);
    }
  }

  /**
   * Returns a grid of the type where all points are specified for square cells
   *
   * @param grid      Map of Points and Integer to which new KVPs should be added
   * @param wholeGrid an array of Strings representing rows in the grid
   * @return a Map representing points an values in the grid
   * @throws XMLException non-Integer value stored in grid
   */
  private Map<Point, Integer> getAllSquares(Map<Point, Integer> grid, String[] wholeGrid) {
    for (int j = 0; j < wholeGrid.length; j++) {
      String row = wholeGrid[j].trim();
      String[] vals = row.split(" ");
      for (int k = 0; k < vals.length; k++) {
        try {
          grid.put(new Point(j, k), Math.min(Integer.parseInt(vals[k]), maxVal));
        } catch (NumberFormatException e) {
          throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"),
              RESOURCES.getString("Grid"));
        }
      }
    }
    return grid;
  }

  /**
   * Returns a grid of the type where all points are specified for hexagon cells
   *
   * @param grid      Map of Points and Integer to which new KVPs should be added
   * @param wholeGrid an array of Strings representing rows in the grid
   * @return a Map representing points an values in the grid
   * @throws XMLException non-Integer value stored in grid or bad shape
   */
  private Map<Point, Integer> getAllHex(Map<Point, Integer> grid, String[] wholeGrid) {
    if (wholeGrid.length <= 1) {
      throw new XMLException(RESOURCES.getString("BadShape"));
    }

    for (int j = 0; j < wholeGrid.length; j++) {
      String row = wholeGrid[j].trim();
      String[] vals = row.split(" ");
      for (int k = 0; k < vals.length && k < myWidth / 2 + 1; k++) {
        try {
          Integer val = Math.min(Integer.parseInt(vals[k]), maxVal);
          if (j % 2 == 0) {
            grid.put(new Point(j, k * 2), val);
          } else {
            grid.put(new Point(j, k * 2 - 1), val);
          }
        } catch (NumberFormatException e) {
          throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"),
              RESOURCES.getString("Grid"));
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
        for (int k = 0; k < intVals.length; k++) {
          intVals[k] = Integer.parseInt(vals[k]);
        }
        grid.put(new Point(Math.min(intVals[0], myHeight), Math.min(intVals[1], myWidth)),
            Math.min(intVals[2], maxVal));
      } catch (NumberFormatException e) {
        throw new XMLException(RESOURCES.getString("XML_DATA_TYPE_MESSAGE"),
            RESOURCES.getString("Grid"));
      }
    }
    return grid;
  }
}
