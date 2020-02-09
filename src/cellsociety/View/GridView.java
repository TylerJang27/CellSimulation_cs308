package cellsociety.View;

import javafx.scene.Node;

public abstract class GridView {

    public abstract void updateCell(int row, int column, int state);

    public abstract Node getNode();
}
