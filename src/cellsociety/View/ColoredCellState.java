package cellsociety.View;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Map;

public class ColoredCellState extends CellState {
    private Shape myDisplay;


    public ColoredCellState(Map<String, String> parameters, Shape template){
        super();
        myDisplay = template;
        try{
            myDisplay.setFill(Color.web(parameters.get("hex-fill")));
        } catch (Exception e){
            myDisplay.setFill(Color.web("#FF0000"));
        }

    }

    @Override
    public Node getNode() {
        return myDisplay;
    }
}
