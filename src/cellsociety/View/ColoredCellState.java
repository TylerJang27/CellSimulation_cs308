package cellsociety.View;

import cellsociety.Main;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * A class to represent a CellState defined by a specific color. The CellState is filled with a single solid color
 * as defined in the Parameters
 */
public class ColoredCellState extends CellState {
    private static final ResourceBundle RESOURCES = Main.myResources;
    private static final String COLOR_KEY = RESOURCES.getString("Color");
    private static final String DEFAULT_COLOR_FILL = RESOURCES.getString("default-color-fill");

    private Shape myDisplay;

    /**
     * Construct a ColoredCellState instance
     * @param parameters the parameters specifying how the CellState should be constructed, such as the color and id tag
     * @param template the Shape that this Coloring will be rendered onto
     */
    public ColoredCellState(Map<String, String> parameters, Shape template){
        super();
        myID = parameters.get(CellState.CELL_STATE_PARAMETER_KEY);
        myDisplay = template;
        try{
            myDisplay.setFill(Color.web(parameters.get(COLOR_KEY)));
        } catch (NullPointerException e){
            myDisplay.setFill(Color.web(DEFAULT_COLOR_FILL));
        } catch (IllegalArgumentException e) {
            myDisplay.setFill(Color.web(DEFAULT_COLOR_FILL));
        }

    }
    @Override
    public Node getNode() {
        return myDisplay;
    }
}
