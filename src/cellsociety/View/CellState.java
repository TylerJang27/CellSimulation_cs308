package cellsociety.View;

import javafx.scene.layout.Pane;

public abstract class CellState extends Pane {

    private String myStateDescription;
    // This is not used now, but is going to be once I implement the graph of the cell states over time
    public String getStateDescription(){
        return myStateDescription;
    }
}
