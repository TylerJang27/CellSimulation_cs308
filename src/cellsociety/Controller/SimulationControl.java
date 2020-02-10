package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.*;
import cellsociety.View.ApplicationView;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

import cellsociety.View.CellClickedEvent;
import cellsociety.View.CellState;
import cellsociety.View.CellStateConfiguration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Core class of the Controller part of the MVC Model Reads in data from the XML file and
 *
 * @author Tyler Jang
 */
public class SimulationControl {

  public static final int DEFAULT_RATE = 5;
  public static final double SIZE = 700;
  public static final int RATE_MAX = 10;
  private static final int IMAGE = 1;

  private static final String STYLE_ADDRESS = "src/resources/Styling.xml";
  private File styleFile;

  private Grid myGrid;
  private Simulation mySim;
  private ApplicationView myApplicationView;
  private boolean paused;
  private int rate = DEFAULT_RATE;
  private int frameStep;
  private int numCols, numRows;
  private static final ResourceBundle RESOURCES = Main.myResources;

  /**
   * Constructor for creating a SimulationControl instance
   *
   * @param primaryStage the stage for the animation
   */
  public SimulationControl(Stage primaryStage) {
    styleFile = new File(STYLE_ADDRESS);
    paused = true;
    frameStep = 0;
    initializeView(primaryStage);
    myApplicationView.logError("Choose a configuration file to start a simulation!");
  }

  /**
   * Pauses the simulation by setting paused to true, halting the progress of next()
   */
  private void pauseSimulation() {
    paused = true;
  }

  /**
   * Calls next() a singular time, but stops the repeated play sequence
   */
  private void stepSimulation() {
    paused = true;
    next(true);
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
   * @param singleStep true if only one step is supposed to occur
   */
  public void next(boolean singleStep) {
    try {
      int stepper = frameStepNext();
      if ((!paused && stepper == 0) || singleStep) {
        myGrid.nextFrame();
        updateViewGrid();
        myApplicationView.displayFrameNumber(myGrid.getFrame());
      }
    } catch (NullPointerException e) {
      myApplicationView.logError(RESOURCES.getString("BadStep"));
    }
  }

  /**
   * Updates all the Cells in GridView based off of the values in myGrid
   */
  private void updateViewGrid() { //FIXME: HANDLE HEX?
    List<Point> points = myGrid.getPointList();
    for (Point p: points) {
      int state = myGrid.getState((int)p.getX(), (int)p.getY());
      myApplicationView.updateCell((int)p.getX(), (int)p.getY(), state);
    }
  }

  /**
   * Increments frameStep and resets it based on the desired framerate
   *
   * @return frameStep
   */
  private int frameStepNext() {
    frameStep += 1;
    if (frameStep >= rate) {
      frameStep = 0;
    }
    return frameStep;
  }

  /**
   * Creates a new ApplicationView to start the display
   *
   * @param primaryStage the stage for the animation
   */
  private void initializeView(Stage primaryStage) {
    EventHandler<MouseEvent> stepHandler = event -> stepSimulation();
    EventHandler<MouseEvent> pauseHandler = event -> pauseSimulation();
    EventHandler<MouseEvent> playHandler = event -> playSimulation();
    ChangeListener<? super Number> sliderListener = (observable, oldValue, newValue) -> {changeSimulationSpeed(observable.getValue());};
    EventHandler<CellClickedEvent> cellClickedHandler = event -> {
      int state = myGrid.cycleState(event.getRow(), event.getColumn());
      myApplicationView.updateCell(event.getRow(), event.getColumn(), state);
    };
    myApplicationView = new ApplicationView(SIZE, primaryStage, playHandler,
            pauseHandler, stepHandler, sliderListener, getFileListener(), cellClickedHandler);
  }

  /**
   * Sets the initial settings for SimulationControl
   *
   * @param dataFile the File from which to read configuration instructions
   */
  public void initializeModel(File dataFile) {
    //FIXME: TRY TO REFACTOR AND REORDER SO THAT THE VIEW STUFF STAYS IN VIEW
    mySim = new ConfigParser(RESOURCES.getString("Type")).getSimulation(dataFile);

    rate = mySim.getValueMap().getOrDefault(RESOURCES.getString("Rate"), DEFAULT_RATE);

    numCols = mySim.getValue(RESOURCES.getString("Width"));
    numRows = mySim.getValue(RESOURCES.getString("Height"));

    Map<String, Style> styles = new StyleParser(RESOURCES.getString("Type")).getStyle(styleFile);
    Style style = styles.get(mySim.getType().toString());
    String shapeString;
    int shape = mySim.getValue(RESOURCES.getString("Shape"));
    if (shape == GridParser.HEXAGON) {
      shapeString = RESOURCES.getString("Hexagon");
    } else {
      shapeString = RESOURCES.getString("Rectangle");
    }
    List<CellStateConfiguration> cellViewConfiguration = new ArrayList<>();
    for (Map<String, String> params: style.getConfigParameters()) {
      String displayStyle = "color";
      for (String s: params.keySet()) {
        displayStyle = s;
      }
      cellViewConfiguration.add(new CellStateConfiguration(shapeString, displayStyle, params));
    }

    //Alternatively instead of a boolean, you can store a double specifying outlineWidth (0 for not outlined) and then I can adjust the constructor to reflect this. This would make it more flexible
    myApplicationView.initializeGrid(numRows, numCols, SIZE, SIZE, style.getValue(RESOURCES.getString("Outline")), cellViewConfiguration);
    myGrid = createGrid();

    updateViewGrid();
    pauseSimulation();
  }

  /**
   * Creates grid based off of the type of Simulation stored in mySim
   *
   * @return a subclass of Grid
   */
  private Grid createGrid() {
    String simType = mySim.getType().toString();
    if (simType.equals(RESOURCES.getString("GameOfLife"))) {
      return new GameOfLifeGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("Percolation"))) {
      return new PercolationGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("Segregation"))) {
      return new SegregationGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("PredatorPrey"))) {
      return new PredatorPreyGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("Fire"))) {
      return new FireGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("RockPaperScissors"))) {
      return new RockPaperScissorsGrid(mySim.getGrid(), mySim.getValueMap());
    }
    return null;
  }

  /**
   * Writes file to data/ and notes this in the console
   */
  private void saveFile() {
    WriteXMLFile writer = new WriteXMLFile(mySim, myGrid, rate);
    myApplicationView.logError(String.format(RESOURCES.getString("FileSaved"), writer.writeSimulationXML()));
  }

  /**
   * Returns a handler for selecting the file to reset configuration
   */
  private ChangeListener<File> getFileListener() {
    return new ChangeListener<File>() {
      @Override
      public void changed(ObservableValue<? extends File> observable, File oldValue,
                          File newValue) {
        try {
          initializeModel(newValue);
          myApplicationView.logError(RESOURCES.getString("ConsoleReady"));
        } catch (XMLException e) {
          myApplicationView.logError(e.getMessage());
        }
      }
    };
  }

  /**
   * Sets the new simulation rate
   *
   * @param newValue the new simulation rate, usually on a scale of 1-10
   */
  private void changeSimulationSpeed(Number newValue) {
    rate = (RATE_MAX - newValue.intValue());
  }

  /**
   * Returns a handler for changing the new simulation rate
   */
  private ChangeListener<? super Number> getSliderListener() {
    return new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        changeSimulationSpeed(observable.getValue());
      }
    };
  }
}
