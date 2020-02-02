package cellsociety;

import cellsociety.Controller.Simulation;
import cellsociety.Controller.SimulationControl;
import cellsociety.Controller.XMLParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    public static final String LANGUAGE = "English";
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public static ResourceBundle myResources=ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
    //public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";

    private static final String TITLE = myResources.getString("CellSimulator");

    private SimulationControl mySim;

    //FIXME: publicity of constants
    //FIXME: go through methods for comments

    @Override
    public void start(Stage primaryStage) {
        mySim = new SimulationControl(primaryStage);

        primaryStage.setTitle(TITLE);
        primaryStage.show();

        //begins game loop to call step
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
            step(MILLISECOND_DELAY / 10);
        });
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step(double elapsedTime) {
        mySim.next();
    }

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        File dataFile = new File("data/GameOfLife1.xml");
        Simulation mySim = new XMLParser("type").getSimulation(dataFile);

        System.out.printf("Simulation type: %s\n", mySim.getType());
        System.out.printf("rate: \t\t %s\n", mySim.getValue("rate"));
        System.out.printf("width: \t\t %s\n", mySim.getValue("width"));
        System.out.printf("height: \t %s\n", mySim.getValue("height"));
        for (String s: mySim.getType().getFields()) {
            System.out.printf("%s \t\t %s\n", s, mySim.getValue(s));
        }
        System.out.println("grid:");
        for (Point p: mySim.getGrid().keySet()) {
            System.out.printf("x: %f y: %f \t %d\n", p.getX(), p.getY(), mySim.getGrid().get(p));
        }

        launch(args);

    }
}
