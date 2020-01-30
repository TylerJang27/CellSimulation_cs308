package cellsociety;

import javafx.stage.Stage;

import java.io.File;

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


    public SimulationControl(String fname, Stage primaryStage) {
        //
        initializeModel(fname, primaryStage);
        //
    }

    public void next() {
        //update grid

        //update view? tell view to update?
    }

    public void initializeModel(String fname, Stage primaryStage) {
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

}
