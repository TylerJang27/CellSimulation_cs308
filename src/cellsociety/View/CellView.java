package cellsociety.View;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A class to render the Appearance of a cell.
 *
 * @author Mariusz Derezinski-Choo
 */
public class CellView extends Pane {

    private Rectangle myDisplay;

    /**
     * Default constructor for a CellView. Implemented as a Rectangle
     */
    public CellView(){
        super();
        myDisplay = new Rectangle();

        myDisplay.widthProperty().bind(this.widthProperty());
        myDisplay.heightProperty().bind(this.heightProperty());

        getChildren().add(myDisplay);
    }

    /**
     * Change the appearance of the cell based on the state
     * @param state the next state of the cell
     */
    public void changeState(int state){
        switch(state){
            case 0:
                myDisplay.setFill(Color.BLACK);
                break;
            case 1:
                myDisplay.setFill(Color.RED);
                break;
            case 2:
                myDisplay.setFill(Color.BLUE);
                break;
            case 3:
                myDisplay.setFill(Color.YELLOW);
                break;
        }

    }
}
