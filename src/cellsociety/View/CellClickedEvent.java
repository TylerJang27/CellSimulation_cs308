package cellsociety.View;

import javafx.event.Event;
import javafx.event.EventType;

import java.awt.event.MouseEvent;

public class CellClickedEvent extends Event {
    public static final EventType<CellClickedEvent> CUSTOM_EVENT_TYPE = new EventType(ANY);

    private CellView myCell;
    private int myRow;
    private int myColumn;

    public CellClickedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
    public CellClickedEvent(CellView cell, int row, int column){
        super(CUSTOM_EVENT_TYPE);
        myRow = row;
        myColumn = column;
        myCell = cell;
    }

    public CellView getCell(){
        return myCell;
    }

    public int getRow(){
        return myRow;
    }

    public int getColumn(){
        return myColumn;
    }
}
