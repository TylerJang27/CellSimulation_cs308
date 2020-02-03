package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.*;
import cellsociety.Model.Grid;
import cellsociety.View.ApplicationView;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Core class of the Controller part of the MVC Model
 * Reads in data from the XML file and
 *
 * //FIXME: Add comments and documentation
 * //FIXME: All codestyle
 */
public class SimulationControl {

    private Grid myGrid;
    private Simulation mySim;
    private ApplicationView myApplicationView;
    private boolean paused = false;
    private int numCols, numRows;
    private static ResourceBundle RESOURCES= Main.myResources;

    private static final double SIZE = 800;
    private static final File DEFAULT_STARTING_FILE = new File("data/Fire1.xml");

    /**
     * Constructor for creating a SimulationControl instance
     *
     * @param primaryStage
     * @param sliderListener
     * @throws IOException
     * @throws SAXException
     */
    public SimulationControl(Stage primaryStage, ChangeListener<? super Number> sliderListener) throws IOException, SAXException {
        initializeModel(DEFAULT_STARTING_FILE, primaryStage, sliderListener);
    }

    /**
     * Pauses the simulation by setting paused to true, halting the progress of next()
     */
    private void pauseSimulation() {
        paused = true;
    }

    /**
     * Calls next() a singular time, but stopping the repeated play sequence
     */
    private void stepSimulation() {
        paused = true;
        next(0, true);
    }

    /**
     * Plays the simulation by setting paused to false, resuming the progress of next()
     */
    private void playSimulation() {
        paused = false;
    }

    /**
     * Steps the simulation by updating the Grid and updating ApplicationView
     *
     * @param elapsedTime amount of time since last step
     * @param singleStep true if only one step is supposed to occur
     */
    public void next(double elapsedTime, boolean singleStep) {
        //FIXME: OPTIMIZE BASED ON OTHER CONSIDERATIONS
        if (!paused || singleStep) {
            myGrid.nextFrame();
            for (int j = 0; j < numCols; j++) {
                for (int k = 0; k < numRows; k++) {
                    myApplicationView.updateCell(j, k, myGrid.getState(j, k));
                }
            }
        }
    }

    /**
     * Sets the initial settings for SimulationControl
     *
     * @param dataFile the File from which to read configuration instructions
     * @param primaryStage the Stage on which to display the grid
     * @param sliderListener Listener to change the frame rate
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    public void initializeModel(File dataFile, Stage primaryStage, ChangeListener<? super Number> sliderListener) throws IOException, SAXException {
        //FIXME: ADD A THING FOR ERROR LOGGING IN MYAPPLICATION VIEW? I.E. DIRECT THE EXCEPTIONS THERE?
        //FIXME: ADD A LISTERNER/HANDLER FOR CHANGING THE FILE (CALL INITIALIZE MODEL)
        myApplicationView = new ApplicationView(SIZE, primaryStage,getPlayHandler(),getPauseListener(),getStepHandler(),sliderListener);
        mySim = new XMLParser("type").getSimulation(dataFile);

        numCols = mySim.getValue(RESOURCES.getString("Width"));
        numRows = mySim.getValue(RESOURCES.getString("Height"));

        //FIXME: Determine accurate size parameter to pass in
        myApplicationView.initializeGrid(numRows, numCols, SIZE, SIZE);
        //FIXME: Fix width, height parameters if necessary
        myGrid = createGrid(numCols, numRows);
        //FIXME: need to pass in other thresholds and initial setups

        for (Point p: mySim.getGrid().keySet()) {
            myApplicationView.updateCell((int)p.getY(), (int)p.getX(), mySim.getGrid().get(p));
        }
    }

    /**
     * Creates grid based off of the type of Simulation stored in mySim
     *
     * @param cols the width of the grid in Cell units
     * @param rows the height of the grid in Cell units
     * @return a subclass of Grid
     */
    private Grid createGrid(int cols, int rows) {
        String simType = mySim.getType().toString();
        if (simType.equals(RESOURCES.getString("GameOfLife"))) {
            return new GameOfLifeGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("Percolation"))) {
            return new PercolationGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("Segregation"))) {
            return new SegregationGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("PredatorPrey"))) {
            return new PredatorPreyGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("Catch"))) {
            return new FireGrid(mySim.getGrid(), mySim.getValueSet());
        }
        return null;
    }

    /**
     * Returns a handler for playSimulation() to be sent to ApplicationView
     */
    private EventHandler<MouseEvent> getPlayHandler() {
        EventHandler<MouseEvent> playButtonClickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playSimulation();
            }
        };
        return playButtonClickedHandler;
    }

    /**
     * Returns a handler for pauseSimulation() to be sent to ApplicationView
     */
    private EventHandler<MouseEvent> getPauseListener() {
        EventHandler<MouseEvent> pauseButtonClickedHandler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                pauseSimulation();
            }
        };
        return pauseButtonClickedHandler;
    }

    /**
     * Returns a handler for stepSimulation() to be sent to ApplicationView
     */
    private EventHandler<MouseEvent> getStepHandler() {
        EventHandler<MouseEvent> stepButtonClickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stepSimulation();
            }
        };
        return stepButtonClickedHandler;
    }

    /*private ChangeListener<? super Number> getSliderListener() {
        ChangeListener<? super Number> sliderListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changeSimulationSpeed(observable.getValue());
            }
        };
        return sliderListener;
    }*/
}
