package cellsociety.View;

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

        ApplicationView myApplicationView = new ApplicationView(800, primaryStage);
    }
}
