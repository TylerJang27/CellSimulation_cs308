package cellsociety.View;

import cellsociety.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class ColoredCellState extends CellState {
    private Rectangle myDisplay;

    /**
     * Default constructor for a CellView. Implemented as a Rectangle
     */
    public ColoredCellState() {
        super();
        myDisplay = new Rectangle();
        myDisplay.widthProperty().bind(this.widthProperty());
        myDisplay.heightProperty().bind(this.heightProperty());

        getChildren().add(myDisplay);
    }

    public ColoredCellState(Map<String, String> parameters){
        this();
        try{
            myDisplay.setFill(Color.web(parameters.get(Main.myResources.getString("Color"))));
        } catch (NullPointerException e){
            myDisplay.setFill(Color.web("#FF0000"));
        } catch (IllegalArgumentException e) {
            myDisplay.setFill(Color.web("#FF0000"));
        }

    }

    public ColoredCellState(Color color){
        this();
        myDisplay.setFill(color);
    }
}
