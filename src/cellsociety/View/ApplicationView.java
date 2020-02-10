package cellsociety.View;

import java.io.File;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
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

  private static final String STYLESHEET = "cellsociety/View/style.css";

  private Scene myScene;
  private BorderPane root;
  private GridView myGrid;
  private ScrollPane myGridScroll;
  private ConsoleView myConsoleView;
  private EventHandler<CellClickedEvent> myCellClickedHandler;
  private double gridViewportHeight;

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
   * @param sliderListener            a ChangeListener to be triggered when the slider is toggled
   * @param fileListener              a ChangeListener to be triggered when the file is chosen
   */
  public ApplicationView(double size, Stage primaryStage,
      EventHandler<MouseEvent> playButtonClickedHandler,
      EventHandler<MouseEvent> pauseButtonClickedHandler,
      EventHandler<MouseEvent> stepButtonClickedHandler,
      ChangeListener<? super Number> sliderListener, ChangeListener<? super File> fileListener, EventHandler<CellClickedEvent> cellClickedHandler) {
    myCellClickedHandler = cellClickedHandler;

    Pane emptyFillerPane = new Pane();
    emptyFillerPane.getStyleClass().add("grid");
    emptyFillerPane.setPrefSize(size,size);


    myGridScroll = new ScrollPane();
    myGridScroll.setPrefViewportHeight(size);
    myGridScroll.setPrefViewportWidth(size);
    myGridScroll.setContent(emptyFillerPane);

    myConsoleView = new ConsoleView();
    Node myDashboardView = new DashboardView(playButtonClickedHandler, pauseButtonClickedHandler,
        stepButtonClickedHandler, sliderListener, fileListener);

    root = new BorderPane();

    root.setBottom(myConsoleView);
    root.setCenter(myGridScroll);
    root.setLeft(myDashboardView);

    myScene = new Scene(root);
    myScene.getStylesheets().add(STYLESHEET);

    primaryStage.setScene(myScene);
    primaryStage.show();
    //primaryStage.setResizable(false);

  }

  /**
   * Display the Frame number in the consnole
   *
   * @param frameNumber the frame number to be displayed
   */
  public void displayFrameNumber(int frameNumber) {
    myConsoleView.showFrame(frameNumber);
  }


  /**
   * Log an error to the console
   *
   * @param errorMessage a String detailing the error message
   */
  public void logError(String errorMessage) {
    myConsoleView.logError(errorMessage);
  }

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
  public void initializeGrid(int numRows, int numColumns, double width, double length, String outline, List<CellStateConfiguration> cellStateConfigs) {
    CellStateConfiguration config = cellStateConfigs.get(0);
    if(config.getShape().equals("rectangle")){
      myGrid = new RectangleGridView(numRows, numColumns, width, length, outline, myCellClickedHandler, cellStateConfigs);
    }else if(config.getShape().equals("hexagon")){
      myGrid = new HexagonGridView(numRows, numColumns, width, length, outline, myCellClickedHandler, cellStateConfigs);
    }
    myGridScroll.setContent(myGrid.getNode());
  //FIXME: outline vs isOutlined and error handling
    root.setCenter((myGridScroll));
  }

  public void updateCellCounts(){
    Map<String, Integer> temp = myGrid.getCellCounts();
  }
}
