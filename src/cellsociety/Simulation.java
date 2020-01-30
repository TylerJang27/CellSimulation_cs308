package cellsociety;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class for storing the data needed to configure a Simulation
 * Class stores accessible data in myType, myColors, myDataValues, and myGrid
 *
 * Structure based loosely on setup of Game.java in spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/xml/Game.java
 */
public class Simulation {
    //name for the type of data file necessary to represent a simulation configuration file
    public static final String DATA_TYPE = "simulation";

    private static final String MISSING_MESSAGE = "XML file missing essential configuration setting for SimType %s";

    //valid fields in the data file for all Simulation objects
    public static final List<String> DATA_FIELDS = List.of(
            "mode",
            "rate",
            "width",
            "height",
            "grid"
    );

    private Map<String, Integer> myDataValues;

    //specific data values for this instances
    private SimType myType;
    private Map<Point, Integer> myGrid;

    /**
     * Full constructor to initialize a Simulation from given data.
     */
    public Simulation(String type) {
        myType = SimType.of(type);
        myDataValues = new HashMap<>();
        myGrid = new HashMap<>();
    }

    /**
     * Constructor taking in a data structure of Strings.
     *
     * @param dataValues map of field names to their values
     */
    public Simulation(Map<String, String> dataValues) {
        this(dataValues.get(DATA_FIELDS.get(0)));
        this.setFields(dataValues);
        this.setGrid(dataValues.get(DATA_FIELDS.get(4)));
    }

    /**
     * Function to set myDataValues to map from a field key to a value
     * Sets value to null if dataValues does not contain the value.
     * Note: Implementation of model will need to specify defaults
     *
     * @param dataValues map of field names to their values
     */
    private void setFields(Map<String, String> dataValues) {
        for (int k = 1; k < 4; k ++) {
            String val = dataValues.get(DATA_FIELDS.get(k));
            if (val != null) {
                myDataValues.put(DATA_FIELDS.get(k), Integer.parseInt(val));
            } else  {
                throw new XMLException(MISSING_MESSAGE, myType);
            }
        }
        for (String field: myType.getFields()) {
            if (field.length() > 0) {
                myDataValues.put(field, Integer.parseInt(dataValues.get(field)));
            }
        }
    }

    /**
     * Takes a String specifying initial grid setup and creates the grid
     * Individual Cells should be delimited by ,
     * Point locations and value should be delimited by space
     * Only adds points within the bounds of the grid
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
    public SimType getType() {
        return myType;
    }

    /**
     * Returns Integer value matched to field key.
     */
    public Integer getValue(String field) {
        return myDataValues.get(field);
    }

    /**
     * Returns Grid, with initialized values
     */
    public Map<Point, Integer> getGrid() {
        return myGrid;
    }

}
