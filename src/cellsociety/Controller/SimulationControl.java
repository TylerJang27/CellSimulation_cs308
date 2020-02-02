package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.Grid;
import cellsociety.View.ApplicationView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

/**
 * Core class of the Controller part of the MVC Model
 * Reads in data from the XML file and
 *
 * //FIXME: Add comments and documentation
 * //FIXME: All codestyle
 * //FIXME: Add package organization
 */
public class SimulationControl {

    private Grid myGrid;
    private Simulation mySim;
    private ApplicationView myApplicationView;
    private boolean paused = false;
    private int numCols, numRows;

    private static ResourceBundle RESOURCES= Main.myResources;

    private static final double SIZE = 800;
    private static final File DEFAULT_FILE = new File("data/Fire1.xml");

    public SimulationControl(Stage primaryStage, ChangeListener<? super Number> sliderListener) throws IOException, SAXException {
        initializeModel(DEFAULT_FILE, primaryStage, sliderListener);

    }

    //TODO: Implement these methods
    private void pauseSimulation() {
        paused = true;
    }

    private void stepSimulation() {
        paused = true;
        next();
    }

    private void playSimulation() {
        paused = false;
    }

    public void next() {
        //update grid
        //update view? tell view to update?
        //FIXME: OPTIMIZE BASED ON OTHER CONSIDERATIONS
        myGrid.nextFrame();
        for (int j = 0; j < numCols; j ++) {
            for (int k = 0; k < numRows; k ++) {
                myApplicationView.updateCell(j, k, myGrid.getState(j, k));
            }
        }
    }

    public void initializeModel(File dataFile, Stage primaryStage, ChangeListener<? super Number> sliderListener) throws IOException, SAXException {
        EventHandler<MouseEvent> playButtonClickedHandler = getPlayHandler();
        EventHandler<MouseEvent> pauseButtonClickedHandler = getPauseListener();
        EventHandler<MouseEvent> stepButtonClickedHandler = getStepHandler();
        //FIXME: ADD A THING FOR ERROR LOGGING IN MYAPPLICATION VIEW? I.E. DIRECT THE EXCEPTIONS THERE?
        //FIXME: ADD A LISTERNER/HANDLER FOR CHANGING THE FILE (CALL INITIALIZE MODEL)
        myApplicationView = new ApplicationView(SIZE, primaryStage,playButtonClickedHandler,pauseButtonClickedHandler,stepButtonClickedHandler,sliderListener);
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

    private Grid createGrid(int cols, int rows) {
        String simType = mySim.getType().toString();
        if (simType.equals(RESOURCES.getString("GameOfLife"))) {
            return new GameOfLifeGrid(cols, rows);
        } else if (simType.equals(RESOURCES.getString("Percolation"))) {
            return new PercolationGrid(cols, rows);
        } else if (simType.equals(RESOURCES.getString("Segregation"))) {
            return new SegregationGrid(cols, rows);
        } else if (simType.equals(RESOURCES.getString("PredatorPrey"))) {
            return new PredatorPreyGrid(cols, rows);
        } else if (simType.equals(RESOURCES.getString("Catch"))) {
            return new CatchGrid(cols, rows);
        }
    }

    private EventHandler<MouseEvent> getPlayHandler() {
        EventHandler<MouseEvent> playButtonClickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playSimulation();
            }
        };
        return playButtonClickedHandler;
    }

    private EventHandler<MouseEvent> getPauseListener() {
        EventHandler<MouseEvent> pauseButtonClickedHandler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                pauseSimulation();
            }
        };
        return pauseButtonClickedHandler;
    }

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
