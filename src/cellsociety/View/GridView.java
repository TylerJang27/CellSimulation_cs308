package cellsociety.View;

import javafx.scene.Node;

import java.util.Map;

public abstract class GridView {

    public abstract void updateCell(int row, int column, int state);

    public abstract Node getNode();

    public abstract Map<String, Integer> getCellCounts();

}
