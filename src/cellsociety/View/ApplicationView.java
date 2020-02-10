package cellsociety.View;

import cellsociety.Main;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Core class for the Simulation's GUI, instantiates all the elements of the GUI (Dashboard, Grid,
 * Console, etc), and places them appropriately in a Border Pane.
 *
 * @author Mariusz Derezinski-Choo
 */
public class ApplicationView {

  private static final ResourceBundle RESOURCES = Main.myResources;
  private static final String STYLESHEET = "style.css";
  private static final String RECTANGLE = RESOURCES.getString("rectangle");
  private static final String HEXAGON = RESOURCES.getString("hexagon");

  private Scene myScene;
  private BorderPane root;
  private GridView myGrid;
  private ScrollPane myGridScroll;
  private ConsoleView myConsoleView;
  private EventHandler<CellClickedEvent> myCellClickedHandler;
  private DashboardView myDashboardView;
  private int myFrameNumber;

  /**
   * Construct an ApplicationView with EventHandlers and Listeners binded to the play/pause/step
   * buttons, speed bar and File Selector
   *
   * @param size                      the size of the Grid in pixels
   * @param primaryStage              The Stage with which the Scene will be rendered
   * @param playButtonClickedHandler  an EventHandler to be triggered when the play button is
   *                                  pressed
   * @param pauseButtonClickedHandler an EventHandler to be triggered when the pause button is
   *                                  clicked
   * @param stepButtonClickedHandler  an EventHandler to be triggered when the step button is
   *                                  clicked
   * @param saveButtonClickedHandler  The EventHandler to be triggered when the "Save" button is
   *                                  clicked
   * @param sliderListener            a ChangeListener to be triggered when the slider is toggled
   * @param fileListener              a ChangeListener to be triggered when the file is chosen
   * @param cellClickedHandler        The EventHandler to be triggered when a cell is clicked
   */
  public ApplicationView(double size, Stage primaryStage,
      EventHandler<MouseEvent> playButtonClickedHandler,
      EventHandler<MouseEvent> pauseButtonClickedHandler,
      EventHandler<MouseEvent> stepButtonClickedHandler,
      EventHandler<MouseEvent> saveButtonClickedHandler,
      ChangeListener<? super Number> sliderListener, ChangeListener<? super File> fileListener,
      EventHandler<CellClickedEvent> cellClickedHandler) {

    myCellClickedHandler = cellClickedHandler;
    myFrameNumber = 0;

    setUpGridScroller(size);

    myConsoleView = new ConsoleView();

    myDashboardView = new DashboardView(playButtonClickedHandler, pauseButtonClickedHandler,
        stepButtonClickedHandler, saveButtonClickedHandler, sliderListener, fileListener);

    root = new BorderPane();
    root.setBottom(myConsoleView);
    root.setCenter(myGridScroll);
    root.setLeft(myDashboardView);

    myScene = new Scene(root);
    myScene.getStylesheets()
        .add(this.getClass().getClassLoader().getResource(STYLESHEET).toExternalForm());

    displayScene(primaryStage);
  }

  /**
   * Display the Frame number in the consnole
   *
   * @param frameNumber the frame number to be displayed
   */
  public void displayFrameNumber(int frameNumber) {
    myFrameNumber = frameNumber;
    myConsoleView.showFrame(myFrameNumber);
  }

  /**
   * Log an error to the console
   *
   * @param errorMessage a String detailing the error message
   */
  public void logError(String errorMessage) {
    myConsoleView.logError(errorMessage);
  }

  /**
   * update the cell at the specified row and column. change the state to the speci
   *
   * @param row    the row to be updated
   * @param column the column to be updated
   * @param state  the state that the cell should be modified to
   */
  public void updateCell(int row, int column, int state) {
    myGrid.updateCell(row, column, state);
  }

  /**
   * Initialize a grid to the Application View to be displayed
   *
   * @param numRows    the number of rows in the grid
   * @param numColumns the number of columns in the grid
   * @param width      the width of the grid in pixels
   * @param length     the length of the grid in pixels
   */
  public void initializeGrid(int numRows, int numColumns, double width, double length,
      String outline, List<CellStateConfiguration> cellStateConfigs) {
    CellStateConfiguration config = cellStateConfigs.get(0);
    if (config.getShape().equals(RECTANGLE)) {
      myGrid = new RectangleGridView(numRows, numColumns, outline, myCellClickedHandler,
          cellStateConfigs);
    } else if (config.getShape().equals(HEXAGON)) {
      myGrid = new HexagonGridView(numRows, numColumns, width, length, outline,
          myCellClickedHandler, cellStateConfigs);
    }
    myGridScroll.setContent(myGrid.getNode());
    root.setCenter((myGridScroll));
  }

  /**
   * Updates the plot of the different cells by adding a single time point for each cell type to the
   * plot in the View
   */
  public void updateCellCounts() {
    Map<String, Integer> temp = myGrid.getCellCounts();
    for (String id : temp.keySet()) {
      myDashboardView.plotTimePoint(id, myFrameNumber, temp.get(id));
    }
  }


  private void displayScene(Stage primaryStage) {
    primaryStage.setScene(myScene);
    primaryStage.show();
    primaryStage.setResizable(false);
  }

  private void setUpGridScroller(double size) {
    myGridScroll = new ScrollPane();
    myGridScroll.setPrefViewportHeight(size);
    myGridScroll.setPrefViewportWidth(size);
    myGridScroll.setContent(getEmptyGrid(size));
  }

  private Pane getEmptyGrid(double size) {
    Pane emptyFillerPane = new Pane();
    emptyFillerPane.getStyleClass().add(GridView.GRID_CSS_CLASS);
    emptyFillerPane.setPrefSize(size, size);
    return emptyFillerPane;
  }
}
