package cellsociety.View;

import cellsociety.Main;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;

/**
 * An abstract class representing a grid of CellViews. different implementations will render different looking Grids. All grids must be able to update a cell at a given row and column.
 * All grids must be able to count how many of each type of cell is currently being displayed on the grid
 *
 * @author Mariusz Derezinski-Choo
 */
public abstract class GridView {
    private static final ResourceBundle RESOURCES = Main.myResources;
    public static final String GRID_CSS_CLASS = RESOURCES.getString("grid-css-class");
    /**
     * update a cell at the specified index
     * @param row the row of the cell
     * @param column the column of the cell
     * @param state the state that the cell should be updated to
     */
    public abstract void updateCell(int row, int column, int state);

    /**
     * return a Node representation of the GridView so that the GridView can be added to a GUI
     * @return a node that can render the appearance of the GridView
     */
    public abstract Node getNode();

    /**
     * count how many of each type of cell is in the grid
     * @return a Map that maps Cell type id's to integer values representing the frequency of each cell type.
     */
    public abstract Map<String, Integer> getCellCounts();

}
