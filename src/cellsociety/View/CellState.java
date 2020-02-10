package cellsociety.View;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class CellState{
    protected String myID;

    public String getStateDescription(){
        return myID;
    }
    public abstract Node getNode();
}
