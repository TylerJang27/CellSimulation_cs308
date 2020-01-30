package cellsociety;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    GAME_OF_LIFE ("GameOfLife", new String[]{}),
    PERCOLATION ("Percolation", new String[]{}),
    SEGREGATION ("Segregation", new String[]{"similar", "red", "empty"}),
    PREDATOR_PREY ("PredatorPrey", new String[]{"fish", "shark", "fish_breed", "shark_starve", "shark_breed"}),
    FIRE ("Fire", new String[]{"catch"});

    private String myName;
    private final List<String> myFields;

    /**
     * Constructor for SimType, setting its name and fields
     */
    SimType(String name, String[] fields) {
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
        throw new IllegalArgumentException(String.format("ERROR: %s is not a valid rating code.", code));
    }

    /**
     * Returns the name of the type
     */
    @Override
    public String toString() {
        return myName;
    }

}
