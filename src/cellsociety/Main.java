package cellsociety;

import cellsociety.Controller.Simulation;
import cellsociety.Controller.SimulationControl;
import cellsociety.Controller.XMLParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {

    public static final int FRAMES_PER_SECOND = 4;
    public static final int DEFAULT_MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    public static final String LANGUAGE = "English";
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public static ResourceBundle myResources=ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
    //public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";

    private static final String TITLE = myResources.getString("CellSimulator");

    private SimulationControl mySim;
    private KeyFrame frame;
    private Timeline animation;

    //FIXME: publicity of constants
    //FIXME: go through methods for comments and code style


    //FIXME: please help me clean this I'm bad at events
    private ChangeListener<? super Number> getSliderListener() {
        ChangeListener<? super Number> sliderListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changeFrameRate(observable.getValue());
            }
        };
        return sliderListener;
    }

    /**
     * Resets the framerate to the new rate set by the slider
     *
     * @param newMillis
     */
    private void changeFrameRate(Number newMillis) {
        frame = new KeyFrame(Duration.millis((double)newMillis), e -> {
            step((double)newMillis / 10);
        });
        animation.getKeyFrames().set(0, frame);
        animation.play();
    }

    /**
     * Starts the Application by creating SimulationControl and starting the animation
     *
     * @param primaryStage the stage for the Application
     * @throws IOException  failed to read file
     * @throws SAXException failed to read file
     */
    @Override
    public void start(Stage primaryStage) throws IOException, SAXException {
        mySim = new SimulationControl(primaryStage, getSliderListener());

        primaryStage.setTitle(TITLE);
        primaryStage.show();

        //begins game loop to call step
        frame = new KeyFrame(Duration.millis(DEFAULT_MILLISECOND_DELAY), e -> {
            step(DEFAULT_MILLISECOND_DELAY / 10);
        });
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * Steps the simulation for each frame
     *
     * @param elapsedTime amount of time since last step
     */
    private void step(double elapsedTime) {
        mySim.next(elapsedTime, false);
    }

    /**
     * Start of the program, launching the Application
     */
    public static void main (String[] args) throws IOException, SAXException {
        File dataFile = new File("data/Fire1.xml");
        Simulation mySim = new XMLParser("type").getSimulation(dataFile);

        System.out.printf("Simulation type: %s\n", mySim.getType());
        System.out.printf("grid type: \t %s\n", mySim.getGridType());

        for (String s: mySim.getValueSet().keySet()) {
            System.out.printf("%s: \t %s \n", s, mySim.getValue(s).toString());
        }

        System.out.println("\ngrid:");
        for (Point p: mySim.getGrid().keySet()) {
            System.out.printf("x: %f y: %f \t %d\n", p.getX(), p.getY(), mySim.getGrid().get(p));
        }

        launch(args);

    }
}
