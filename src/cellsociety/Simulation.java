package cellsociety;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class for storing the data needed to configure a Simulation
 * Class stores accessible data in myType, myColors, myDataValues, and myGrid
 *
 * Structure based on setup of Game.java in spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/Game.java
 */
public class Simulation {
    //name for the type of data file necessary to represent a simulation configuration file
    public static final String DATA_TYPE = "Simulation";

    //value to use if XML file does not specify grid size
    private static final int GRID_DEFAULT = 20;

    //valid fields in the data file for all Simulation objects
    public static final List<String> DATA_FIELDS = List.of(
            "type",                 //FIXME: Implement enumerated type for Simulation types
            "rate",
            "width",
            "height",
            "tolerance",    //used as a general threshold measurement
            "numGroupA",            //FIXME: Determine if better format of specifying each type's rules
            "numGroupB",             //FIXME: Determine if better format of specifying each type's rules
            "empty",                //FIXME: Determine if better format of specifying each type's rules
            "TTLGroupA",            //FIXME: Determine if better format of specifying each type's rules
            "TTLGroupB",            //FIXME: Determine if better format of specifying each type's rules
            "TTDGroupB",            //FIXME: Determine if better format of specifying each type's rules
            "color1",
            "color2",
            "color3",
            "grid"
    );

    private Map<String, Integer> myDataValues;

    //specific data values for this instances
    private String myType;
    private List<Color> myColors;
    private Map<Point, Integer> myGrid;

    /**
     * Full constructor to initialize a Simulation from given data.
     */
    public Simulation(String type, Color color1, Color color2, Color color3) {
        myType = type;
        myColors =  new ArrayList<>(Arrays.asList(new Color[]{color1, color2, color3}));
        myDataValues = new HashMap<>();
        myGrid = new HashMap<>();
    }

    /**
     * Constructor taking in a data structure of Strings.
     *
     * @param dataValues map of field names to their values
     */
    public Simulation(Map<String, String> dataValues) {
        this(dataValues.get(DATA_FIELDS.get(0)),
                Color.web(dataValues.get(DATA_FIELDS.get(11))), //FIXME: Check for input validation of colors
                Color.web(dataValues.get(DATA_FIELDS.get(12))),
                Color.web(dataValues.get(DATA_FIELDS.get(13))));
        this.setFields(dataValues);
        this.setGrid(dataValues.get(DATA_FIELDS.get(14)));
    }

    /**
     * Function to set myDataValues to map from a field key to a value
     * Sets value to null if dataValues does not contain the value.
     * Note: Implementation of model will need to specify defaults
     *
     * @param dataValues map of field names to their values
     */
    private void setFields(Map<String, String> dataValues) {
        for (int k = 1; k < 11; k ++) {
            String val = dataValues.get(DATA_FIELDS.get(k));
            if (val != null) {
                myDataValues.put(DATA_FIELDS.get(k), Integer.parseInt(val));
            } else {
                myDataValues.put(DATA_FIELDS.get(k), null);
            }
        }
        if (this.getValue("width") == null) { //FIXME: Remove hard-coded Strings
            myDataValues.put("width", GRID_DEFAULT);
        }
        if (this.getValue("height") == null) {
            myDataValues.put("height", GRID_DEFAULT);
        }
    }

    /**
     * Takes a String specifying initial grid setup and creates the grid
     * Individual Cells should be delimited by ,
     * Point locations and value should be delimited by space
     */
    public void setGrid(String gridString) {
        for (String cell: gridString.split(",")) {
            String[] cellVals = cell.split(" "); //FIXME: these next 4 lines can be simplified with better Java
            Integer x = Integer.parseInt(cellVals[0]);
            Integer y = Integer.parseInt(cellVals[1]);
            Integer val = Integer.parseInt(cellVals[2]);
            if (x < this.getValue("width") && y < this.getValue("height")) { //FIXME: Remove hard-coded Strings
                myGrid.put(new Point(x, y), val);
            }
        }
    }

    /**
     * Returns simulation type.
     */
    public String getType() {
        return myType;
    }

    /**
     * Returns Integer value matched to field key.
     */
    public Integer getValue(String field) {
        return myDataValues.get(field);
    }

    /**
     * Returns ColorN value, specified by argument n
     */
    public Color getColor(int n) {
        return myColors.get(n);
    }

    /**
     * Returns Grid, with initialized values
     */
    public Map getGrid() {
        return myGrid;
    }

}
