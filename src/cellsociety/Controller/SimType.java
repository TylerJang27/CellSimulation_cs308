package cellsociety.Controller;

import cellsociety.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * An enumerated type for the Simulation Type, specifying all of the
 * Simulation Types and the necessary fields for each type
 *
 * To add a new SimulationType, simply add a new core SimType with its name and required fields
 * Ensure XML Files are formatted correctly
 *
 * Portions taken from ESRBRating.java from spike_simulation by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation/blob/master/src/enumerated/ESRBRating.java
 */
public enum SimType {
    GAME_OF_LIFE (Main.myResources.getString("GameOfLife"), new String[]{}),
    PERCOLATION (Main.myResources.getString("Percolation"), new String[]{}),
    SEGREGATION (Main.myResources.getString("Segregation"), new String[]{Main.myResources.getString("Similar"), Main.myResources.getString("Red"), Main.myResources.getString("Empty")}),
    PREDATOR_PREY (Main.myResources.getString("PredatorPrey"), new String[]{Main.myResources.getString("Fish"), Main.myResources.getString("Shark"), Main.myResources.getString("FishBreed"), Main.myResources.getString("SharkStarve"), Main.myResources.getString("SharkBreed")}),
    FIRE ("Fire", new String[]{Main.myResources.getString("Catch")});

    private String myName;
    private final List<String> myFields;

    /**
     * Constructor for SimType, setting its name and fields
     */
    private SimType(String name, String[] fields) {
        myName = name;
        myFields = new ArrayList<>(Arrays.asList(fields));
    }

    /**
     * Returns the SimType's fields
     */
    public List<String> getFields() {
        return myFields;
    }

    /**
     * Returns the appropriate SimType
     */
    public static SimType of (String code) {
        for (SimType r : SimType.values()) {
            if (r.myName.equals(code)) {
                return r;
            }
        }
        throw new IllegalArgumentException(String.format(Main.myResources.getString("INVALID_ARGUMENT"), code));
    }

    /**
     * Returns the name of the type
     */
    @Override
    public String toString() {
        return myName;
    }

}
