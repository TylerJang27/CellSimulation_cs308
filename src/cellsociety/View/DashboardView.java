package cellsociety.View;

import cellsociety.Controller.SimulationControl;
import cellsociety.Main;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class to handle the behavior of a DashBoard
 *
 * @author Mariusz Derezinski-Choo
 */
public class DashboardView extends Pane {

  private static final double MARGINS = 10;
  private static final Insets PADDING = new Insets(MARGINS, MARGINS, MARGINS, MARGINS);
  private static final double SPACING = 30;

  private static final ResourceBundle RESOURCES = Main.myResources;
  private static final String STEP = RESOURCES.getString("step");
  private static final String PAUSE = RESOURCES.getString("pause");
  private static final String PLAY = RESOURCES.getString("play");
  private static final String SAVE = RESOURCES.getString("save");
  private static final String CHOOSE_CONFIG_FILE = RESOURCES.getString("ChooseConfigFile");
  private static final String STYLE_CLASS = RESOURCES.getString("dashboard-css-class");
  private static final String GRAPH_TITLE = RESOURCES.getString("cell-graph-title");
  private static final String FRAME = RESOURCES.getString("frame");
  private static final String POPULATION = RESOURCES.getString("population");
  private static final double CHART_MAX_WIDTH = 400;
  private static final double MIN_SIM_RATE = 1;

  private Slider mySpeedSlider;
  private ObjectProperty<File> myFileProperty;
  private LineChart<Number, Number> myLineChart;
  private Map<String, XYChart.Series> myCellPlots;

  /**
   * Construct a DashBoardView Object whose elements trigger the EventHandlers and Listeners
   * provided
   *
   * @param playButtonClickedHandler  the EventHandler that is triggered when the play Button is
   *                                  clicked
   * @param pauseButtonClickedHandler the EventHandler that is triggered when the pause button is
   *                                  clicked
   * @param stepButtonClickedHandler  the EventHandler that is triggered when the step button is
   *                                  clicked
   * @param saveButtonClickedHandler  the EventHandler that is triggered when the save button is
   *                                  clicked
   * @param sliderListener            the ChangeListener that is triggered when the slider is
   *                                  toggled
   * @param fileListener              the ChangeListener that is tiggered when a new file is
   *                                  uploaded
   */
  public DashboardView(EventHandler<MouseEvent> playButtonClickedHandler,
      EventHandler<MouseEvent> pauseButtonClickedHandler,
      EventHandler<MouseEvent> stepButtonClickedHandler,
      EventHandler<MouseEvent> saveButtonClickedHandler,
      ChangeListener<? super Number> sliderListener, ChangeListener<? super File> fileListener) {

    super();
    getStyleClass().add(STYLE_CLASS);
    myCellPlots = new HashMap<>();
    setUpFileProperty(fileListener);

    VBox myDashBoard = new VBox(SPACING);
    myDashBoard.prefHeightProperty().bind(this.heightProperty());
    myDashBoard.setPadding(PADDING);

    Pane fileChooserButtonPane = getFileChooserPane();

    mySpeedSlider = new Slider(MIN_SIM_RATE, SimulationControl.RATE_MAX,
        SimulationControl.DEFAULT_RATE);
    mySpeedSlider.valueProperty().addListener(sliderListener);

    Pane playButtonsPane = getPlayButtonsPane(playButtonClickedHandler, pauseButtonClickedHandler,
        stepButtonClickedHandler, saveButtonClickedHandler);

    Region spacerRegion = new Region();
    VBox.setVgrow(spacerRegion, Priority.ALWAYS);

    createGraph();

    myDashBoard.getChildren()
        .addAll(fileChooserButtonPane, mySpeedSlider, myLineChart, spacerRegion, playButtonsPane);

    getChildren().add(myDashBoard);
    setHeight(myDashBoard.getMinHeight());
  }

  /**
   * Plot a time point onto the graph for the given id, specifying the type of cell, and a given
   * time and population
   *
   * @param id         the id that specifies what the cell state is
   * @param time       the time (independent variable) of the time point
   * @param population the population (dependent variable) of the given id at the specified time
   */
  public void plotTimePoint(String id, double time, double population) {
    if (myCellPlots.get(id) == null) {
      XYChart.Series series = new XYChart.Series();
      series.setName(id);
      myLineChart.getData().add(series);
      myCellPlots.put(id, series);
    }
    myCellPlots.get(id).getData().add(new XYChart.Data(time, population));
  }

  private Pane getPlayButtonsPane(EventHandler<MouseEvent> playButtonClickedHandler,
      EventHandler<MouseEvent> pauseButtonClickedHandler,
      EventHandler<MouseEvent> stepButtonClickedHandler,
      EventHandler<MouseEvent> saveButtonClickedHandler) {
    Pane playButtonsPane = new Pane();

    HBox playButtons = new HBox();
    playButtons.prefWidthProperty().bind(playButtonsPane.widthProperty());

    Button playButton = getConsoleButton(PLAY, playButtonClickedHandler);
    Button pauseButton = getConsoleButton(PAUSE, pauseButtonClickedHandler);
    Button stepButton = getConsoleButton(STEP, stepButtonClickedHandler);
    Button saveButton = getConsoleButton(SAVE, saveButtonClickedHandler);

    playButtons.getChildren().addAll(playButton, pauseButton, stepButton, saveButton);

    playButtonsPane.getChildren().add(playButtons);
    return playButtonsPane;
  }

  private Button getConsoleButton(String description, EventHandler<? super MouseEvent> handler) {
    Button button = new Button(description);
    button.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(button, Priority.ALWAYS);
    button.setOnMouseClicked(handler);
    return button;
  }

  private Button getFileChooserButton() {
    Button fileChooserButton = new Button();
    fileChooserButton.setText(CHOOSE_CONFIG_FILE);
    fileChooserButton.setOnMouseClicked(event -> handleFileButtonClicked());
    return fileChooserButton;
  }

  private void setUpFileProperty(ChangeListener<? super File> fileListener) {
    myFileProperty = new SimpleObjectProperty<>();
    myFileProperty.addListener(fileListener);
  }

  private void handleFileButtonClicked() {
    FileChooser myFileChooser = new FileChooser();
    File selectedFile = myFileChooser.showOpenDialog(new Stage());
    if (selectedFile != null) {
      myFileProperty.setValue(selectedFile);
    }
  }

  private void createGraph() {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    xAxis.setForceZeroInRange(false);
    xAxis.setLabel(FRAME);
    yAxis.setLabel(POPULATION);
    myLineChart = new LineChart<>(xAxis, yAxis);
    myLineChart.setTitle(GRAPH_TITLE);
    myLineChart.setMaxWidth(CHART_MAX_WIDTH);
  }

  private Pane getFileChooserPane() {
    Pane fileChooserButtonPane = new Pane();
    Button fileChooserButton = getFileChooserButton();
    fileChooserButtonPane.getChildren().add(fileChooserButton);
    fileChooserButton.prefWidthProperty().bind(fileChooserButtonPane.widthProperty());
    return fileChooserButtonPane;
  }
}
