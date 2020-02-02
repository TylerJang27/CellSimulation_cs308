package cellsociety.Controller;

import cellsociety.View.ApplicationView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

/**
 * Core class of the Controller part of the MVC Model
 * Reads in data from the XML file and
 *
 * //FIXME: Add comments and documentation
 * //FIXME: All codestyle
 * //FIXME: Add package organization
 */
public class SimulationControl {

    //private Grid myGrid;
    //private ApplicationView myView;
    private Simulation mySim;
    private ApplicationView myApplicationView;

    public SimulationControl(Stage primaryStage){
        EventHandler<MouseEvent> playButtonClickedHandler = getPlayHandler();
        EventHandler<MouseEvent> pauseButtonClickedHandler = getPauseListener();
        EventHandler<MouseEvent> stepButtonClickedHandler = getStepHandler();
        ChangeListener<? super Number> sliderListener = getSliderListener();
        myApplicationView = new ApplicationView(800, primaryStage,playButtonClickedHandler,pauseButtonClickedHandler,stepButtonClickedHandler,sliderListener);
    }

    public SimulationControl(String fname, Stage primaryStage) throws IOException, SAXException {
        //
        initializeModel(fname, primaryStage);
        //
    }
    //TODO: Implement these methods
    private void pauseSimulation() {
        System.out.println("Pause the Simulation");
    }

    private void stepSimulation() {
        System.out.println("step the simulation");
    }

    private void changeSimulationSpeed(Number value) {
        System.out.println(value);
    }

    private void playSimulation() {
        System.out.println("We are all living in a simulation, and now it is playing");
    }

    public void next() {
        //update grid

        //update view? tell view to update?
    }

    public void initializeModel(String fname, Stage primaryStage) throws IOException, SAXException {
        File dataFile = new File(fname);
        mySim = new XMLParser("type").getSimulation(dataFile); //FIXME: Change "type" format

        //myGrid = new Grid(mySim.getValueSet(), mySim.getGrid());
            //need grid to take these parameters
        //myView = new ApplicationView(myGrid, primaryStage)
            //should we pass myGrid there?

        //anything else?
    }

    //depreciated?
    public void changeModelSetup(String fname) {

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

    private ChangeListener<? super Number> getSliderListener() {
        ChangeListener<? super Number> sliderListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changeSimulationSpeed(observable.getValue());
            }
        };
        return sliderListener;
    }


}
