package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.*;
import cellsociety.Model.Grid;
import cellsociety.View.ApplicationView;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private boolean paused;
    private int numCols, numRows;
    private static ResourceBundle RESOURCES= Main.myResources;

    private static final double SIZE = 800;

    //private static final File DEFAULT_STARTING_FILE = new File("data/Fire1.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/Fire2.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/Fire3.xml"); //problem?

    //private static final File DEFAULT_STARTING_FILE = new File("data/GameOfLife1.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/GameOfLife2.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/GameOfLife3.xml");

    private static final File DEFAULT_STARTING_FILE = new File("data/Percolation1.xml"); //problem?
    //private static final File DEFAULT_STARTING_FILE = new File("data/Percolation2.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/Percolation3.xml"); //problem?

    //private static final File DEFAULT_STARTING_FILE = new File("data/PredatorPrey1.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/PredatorPrey2.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/PredatorPrey3.xml");

    //private static final File DEFAULT_STARTING_FILE = new File("data/Segregation1.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/Segregation2.xml");
    //private static final File DEFAULT_STARTING_FILE = new File("data/Segregation3.xml");

    /**
     * Constructor for creating a SimulationControl instance
     *
     * @param primaryStage
     * @param sliderListener
     * @throws IOException
     * @throws SAXException
     */
    public SimulationControl(Stage primaryStage, ChangeListener<? super Number> sliderListener) throws IOException, SAXException {
        paused = true;
        initializeView(primaryStage, sliderListener);
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
            myApplicationView.displayFrameNumber(myGrid.getFrame());
            myApplicationView.updateCell(2, 3, (int)Math.random()*3);
        } else{
            myApplicationView.logError("Choose a configuration file to start a simulation!");
        }
    }
    private void initializeView(Stage primaryStage, ChangeListener<? super Number> sliderListener){
        myApplicationView = new ApplicationView(SIZE, primaryStage,getPlayHandler(),getPauseListener(),getStepHandler(),sliderListener,getFileHandler());
    }


    /**
     * Sets the initial settings for SimulationControl
     *
     * @param dataFile the File from which to read configuration instructions
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    public void initializeModel(File dataFile) throws IOException, SAXException {
        //FIXME: ADD A THING FOR ERROR LOGGING IN MYAPPLICATION VIEW? I.E. DIRECT THE EXCEPTIONS THERE?
        //FIXME: ADD A LISTERNER/HANDLER FOR CHANGING THE FILE (CALL INITIALIZE MODEL)

        mySim = new XMLParser("type").getSimulation(dataFile);

        numCols = mySim.getValue(RESOURCES.getString("Width"));
        numRows = mySim.getValue(RESOURCES.getString("Height"));

        //FIXME: Determine accurate size parameter to pass in
        myApplicationView.initializeGrid(numRows, numCols, SIZE, SIZE);
        //FIXME: Fix width, height parameters if necessary
        myGrid = createGrid();
        //FIXME: need to pass in other thresholds and initial setups

        for (Point p: mySim.getGrid().keySet()) {
            myApplicationView.updateCell((int)p.getX(), (int)p.getY(), mySim.getGrid().get(p));
        }
    }

    /**
     * Creates grid based off of the type of Simulation stored in mySim
     *
     * @return a subclass of Grid
     */
    private Grid createGrid() {
        String simType = mySim.getType().toString();
        if (simType.equals(RESOURCES.getString("GameOfLife"))) {
            return new GameOfLifeGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("Percolation"))) {
            return new PercolationGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("Segregation"))) {
            return new SegregationGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("PredatorPrey"))) {
            return new PredatorPreyGrid(mySim.getGrid(), mySim.getValueSet());
        } else if (simType.equals(RESOURCES.getString("Fire"))) {
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
    private ChangeListener<File> getFileHandler(){
        return new ChangeListener<File>() {
            @Override
            public void changed(ObservableValue<? extends File> observable, File oldValue, File newValue) {
                //uploadSimulationFile(newValue);
                System.out.println("This worked! " + newValue.getName());
                try{
                    initializeModel(newValue);
                } catch(Exception e){
                    myApplicationView.logError("Invalid Configuration File!");

                    //FIXME: This doesn't actually work but idk how to fix it
                }
            }
        };
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
