package cellsociety.View;

import cellsociety.Controller.Simulation;
import cellsociety.Controller.SimulationControl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely. 
 */

public class Main extends Application {
    /**
     * Start of the program.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Running");

        SimulationControl mySim = new SimulationControl(primaryStage);
    }
}
