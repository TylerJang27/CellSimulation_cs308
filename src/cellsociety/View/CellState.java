package cellsociety.View;

import javafx.scene.Node;

/**
 * An abstract class representing the State of a single cell. Each cell can change between several CellStates according to the simulation
 * Each CellState has an ID which specifies what state it represents ("predator" vs "prey", etc). this id is displayed in the
 * line graph of the different cells
 *
 * @author Mariusz Derezinski-Choo
 */
public abstract class CellState{
    protected String myID;

    /**
     * get the description of the CellState, used to differentiate between different states in a human-readable manner
     * @return the id of the CellState
     */
    public String getStateDescription(){
        return myID;
    }

    /**
     * return the Node representing this state so that it can be rendered on the Grid
     * @return the Node representing the appearance of this state
     */
    public abstract Node getNode();
}
