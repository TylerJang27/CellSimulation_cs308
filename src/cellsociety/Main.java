package cellsociety;

import cellsociety.Controller.SimulationControl;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  public static final int FRAMES_PER_SECOND = 30;
  public static final int DEFAULT_MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

  public static final String LANGUAGE = "English";
  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  public static final ResourceBundle myResources = ResourceBundle
      .getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
  public static final ResourceBundle myImageResources = ResourceBundle
      .getBundle(RESOURCES + "." + "ImageBundle");

  private static final String TITLE = myResources.getString("CellSimulator");

  /**
   * Starts the Application by creating SimulationControl and starting the animation
   *
   * @param primaryStage the stage for the Application
   */
  @Override
  public void start(Stage primaryStage) {
    SimulationControl mySim = new SimulationControl(primaryStage);

    primaryStage.setTitle(TITLE);
    primaryStage.show();

    //begins game loop to call step
    KeyFrame frame = new KeyFrame(Duration.millis(DEFAULT_MILLISECOND_DELAY), e -> {
      step(mySim);
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Steps the simulation for each frame
   */
  private void step(SimulationControl sim) {
    sim.next(false);
  }

  /**
   * Start of the program, launching the Application
   */
  public static void main(String[] args) {
    launch(args);
  }
}
