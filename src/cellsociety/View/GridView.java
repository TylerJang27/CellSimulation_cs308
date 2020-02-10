package cellsociety.View;

import cellsociety.Main;
import javafx.scene.Node;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class GridView {
    private static final ResourceBundle RESOURCES = Main.myResources;
    public static final String GRID_CSS_CLASS = RESOURCES.getString("grid-css-class");

    public abstract void updateCell(int row, int column, int state);

    public abstract Node getNode();

    public abstract Map<String, Integer> getCellCounts();

}
