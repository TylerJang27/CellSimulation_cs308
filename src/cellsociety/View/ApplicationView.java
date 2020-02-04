package cellsociety.View;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;

/**
 * Core class for the Simulation's GUI, instantiates all the elements of the GUI (Dashboard, Grid, Console, etc), and places them appropriately
 * in a Border Pane.
 *
 * @author Mariusz Derezinski-Choo
 */
public class ApplicationView {

    private Scene myScene;
    private BorderPane root;
    private GridView myGridView;
    private ConsoleView myConsoleView;

    /**
     * Construct an ApplicationView with EventHandlers and Listeners binded to the play/pause/step buttons, speed bar and File Selector
     * @param size the size of the Grid in pixels
     * @param primaryStage The Stage with which the Scene will be rendered
     * @param playButtonClickedHandler an EventHandler to be triggered when the play button is pressed
     * @param pauseButtonClickedHandler an EventHandler to be triggered when the pause button is clicked
     * @param stepButtonClickedHandler an EventHandler to be triggered when the step button is clicked
     * @param sliderListener a ChangeListener to be triggered when the slider is toggled
     * @param fileListener a ChangeListener to be triggered when the file is chosen
     */
    public ApplicationView(double size, Stage primaryStage, EventHandler<MouseEvent> playButtonClickedHandler, EventHandler<MouseEvent> pauseButtonClickedHandler, EventHandler<MouseEvent> stepButtonClickedHandler, ChangeListener<? super Number> sliderListener, ChangeListener<? super File> fileListener) {
        Node myGridView = new GridView(0,0,size,size);
        myConsoleView = new ConsoleView();
        Node myDashboardView = new DashboardView(playButtonClickedHandler,pauseButtonClickedHandler,stepButtonClickedHandler,sliderListener,fileListener);

        root = new BorderPane();

        root.setBottom(myConsoleView);
        root.setCenter(myGridView);
        root.setLeft(myDashboardView);
        myScene = new Scene(root);

        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    /**
     * Display the Frame number in the consnole
     * @param frameNumber the frame number to be displayed
     */
    public void displayFrameNumber(int frameNumber){
        myConsoleView.showFrame(frameNumber);
    }


    /**
     * Log an error to the console
     * @param errorMessage a String detailing the error message
     */
    public void logError(String errorMessage){
        myConsoleView.logError(errorMessage);
    }

    //FIXME: I think in tandem with the way you've set this up, it may be most efficient if when Thomas updates the grids, if a cell's
    //changes it returns its point to grid, allowing grid to make a List<Point> changed that gets pipelined to this method here
    public void updateCell(int row, int column, int state){
        myGridView.updateCell(row, column, state);
    }

    /**
     * Initialize a grid to the Application View to be displayed
     * @param numRows the number of rows in the grid
     * @param numColumns the number of columns in the grid
     * @param width the width of the grid in pixels
     * @param length the length of the grid in pixels
     */
    public void initializeGrid(int numRows, int numColumns, double width, double length){
        myGridView = new GridView(numRows, numColumns, width, length);
        root.setCenter((myGridView));
    }
}
