package cellsociety.View;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * A class that wraps a MouseEvent, but with additional instance variables that specify which Cell the Mouse clicked. This allows
 * for all Cells to be attached to the same EventHandler, which will delegate tasks accordingly with respect to the row and
 * column of the cell clicked
 *
 * @author Mariusz Derezinski-Choo
 */
public class CellClickedEvent extends Event {
    public static final EventType<CellClickedEvent> CUSTOM_EVENT_TYPE = new EventType(ANY);

    private CellView myCell;
    private int myRow;
    private int myColumn;

    /**
     * Construct a CellClickedEvent
     * @param cell the CellView that was clicked
     * @param row the row of the cell that was clicked with respect to the grid it is in
     * @param column the column of the cell that was clicked with respect to the grid it is in
     */
    public CellClickedEvent(CellView cell, int row, int column){
        super(CUSTOM_EVENT_TYPE);
        myRow = row;
        myColumn = column;
        myCell = cell;
    }

    /**
     * getter method for the Cell that was clicked
     * @return the cell that was clicked
     */
    public CellView getCell(){
        return myCell;
    }

    /**
     * getter method for the row of the cell
     * @return the row of the cell
     */
    public int getRow(){
        return myRow;
    }

    /**
     * getter method for the column of the cell
     * @return the column of the cell
     */
    public int getColumn(){
        return myColumn;
    }
}
