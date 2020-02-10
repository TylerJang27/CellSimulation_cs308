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
  private static final String CHOOSE_CONFIG_FILE = RESOURCES.getString("ChooseConfigFile");


  private Slider mySpeedSlider;
  private ObjectProperty<File> myFileProperty;
  private LineChart<Number,Number> myLineChart;
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
   * @param sliderListener            the ChangeListener that is triggered when the slider is
   *                                  toggled
   * @param fileListener              the ChangeListener that is tiggered when a new file is
   *                                  uploaded
   */
  public DashboardView(EventHandler<MouseEvent> playButtonClickedHandler,
      EventHandler<MouseEvent> pauseButtonClickedHandler,
      EventHandler<MouseEvent> stepButtonClickedHandler,
      ChangeListener<? super Number> sliderListener, ChangeListener<? super File> fileListener) {
    super();
    setId("dashboard");
    myCellPlots = new HashMap<>();

    VBox myDashBoard = new VBox(SPACING);
    myDashBoard.prefHeightProperty().bind(this.heightProperty());
    myDashBoard.setPadding(PADDING);

    setUpFileProperty(fileListener);
    Button fileChooserButton = getFileChooserButton();
    Pane fileChooserButtonPane = new Pane();
    fileChooserButtonPane.getChildren().add(fileChooserButton);
    fileChooserButton.prefWidthProperty().bind(fileChooserButtonPane.widthProperty());

    mySpeedSlider = new Slider(1, SimulationControl.RATE_MAX, SimulationControl.DEFAULT_RATE);
    mySpeedSlider.valueProperty().addListener(sliderListener);

    Pane playButtonsPane = getPlayButtonsPane(playButtonClickedHandler, pauseButtonClickedHandler,
        stepButtonClickedHandler);

    Region spacerRegion = new Region();
    VBox.setVgrow(spacerRegion, Priority.ALWAYS);

    //defining the axes
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setForceZeroInRange(false);
    xAxis.setLabel("Frame");
    yAxis.setLabel("Population");
    //creating the chart
    myLineChart =
            new LineChart<Number,Number>(xAxis,yAxis);

    myLineChart.setTitle("Cell Distribution over Time");
    //defining a series

    myLineChart.setMaxWidth(200);



    myDashBoard.getChildren()
        .addAll(fileChooserButtonPane, mySpeedSlider, myLineChart, spacerRegion, playButtonsPane);
    getChildren().add(myDashBoard);

    setHeight(myDashBoard.getMinHeight());
  }

  public void plotTimePoint(String id, double time, double population){
    if(myCellPlots.get(id) == null){
      XYChart.Series series = new XYChart.Series();
      series.setName(id);
      myLineChart.getData().add(series);
      myCellPlots.put(id,series);
    }
    myCellPlots.get(id).getData().add(new XYChart.Data(time,population));
  }

  private Pane getPlayButtonsPane(EventHandler<MouseEvent> playButtonClickedHandler,
      EventHandler<MouseEvent> pauseButtonClickedHandler,
      EventHandler<MouseEvent> stepButtonClickedHandler) {
    Pane playButtonsPane = new Pane();

    HBox playButtons = new HBox();
    playButtons.prefWidthProperty().bind(playButtonsPane.widthProperty());

    Button playButton = getConsoleButton(PLAY, playButtonClickedHandler);
    Button pauseButton = getConsoleButton(PAUSE, pauseButtonClickedHandler);
    Button stepButton = getConsoleButton(STEP, stepButtonClickedHandler);

    playButtons.getChildren().addAll(playButton, pauseButton, stepButton);

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

}
