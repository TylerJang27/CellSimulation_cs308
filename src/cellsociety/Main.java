package cellsociety;

import cellsociety.Controller.SimulationControl;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

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

  private static final String TITLE = myResources.getString("CellSimulator");

  private SimulationControl mySim;

  /**
   * Starts the Application by creating SimulationControl and starting the animation
   *
   * @param primaryStage the stage for the Application
   * @throws IOException  failed to read file
   * @throws SAXException failed to read file
   */
  @Override
  public void start(Stage primaryStage) throws IOException, SAXException {
    mySim = new SimulationControl(primaryStage);

    primaryStage.setTitle(TITLE);
    primaryStage.show();

    //begins game loop to call step
    KeyFrame frame = new KeyFrame(Duration.millis(DEFAULT_MILLISECOND_DELAY), e -> {
      step();
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Steps the simulation for each frame
   */
  private void step() {
    mySim.next(false);
  }

  /**
   * Start of the program, launching the Application
   */
  public static void main(String[] args) throws IOException, SAXException {
    launch(args);
  }
}
