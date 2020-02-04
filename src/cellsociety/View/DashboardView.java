package cellsociety.View;

import cellsociety.Controller.SimulationControl;
import cellsociety.Main;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

/**
 * Class to handle the behavior of a DashBoard
 *
 * @author Mariusz Derezinski-Choo
 */
public class DashboardView extends Pane {
    private static final double MARGINS = 10;
    private static final Insets PADDING = new Insets(MARGINS,MARGINS,MARGINS,MARGINS);
    private static final double SPACING = 30;

    private static final ResourceBundle RESOURCES = Main.myResources;
    private static final String STEP = RESOURCES.getString("step");
    private static final String PAUSE = RESOURCES.getString("pause");
    private static final String PLAY = RESOURCES.getString("play");
    private static final String CHOOSE_CONFIG_FILE = RESOURCES.getString("ChooseConfigFile");


    private Slider mySpeedSlider;
    private ObjectProperty<File> myFileProperty;

    /**
     * Construct a DashBoardView Object whose elements trigger the EventHandlers and Listeners provided
     * @param playButtonClickedHandler the EventHandler that is triggered when the play Button is clicked
     * @param pauseButtonClickedHandler the EventHandler that is triggered when the pause button is clicked
     * @param stepButtonClickedHandler the EventHandler that is triggered when the step button is clicked
     * @param sliderListener the ChangeListener that is triggered when the slider is toggled
     * @param fileListener the ChangeListener that is tiggered when a new file is uploaded
     */
    public DashboardView(EventHandler<MouseEvent> playButtonClickedHandler, EventHandler<MouseEvent> pauseButtonClickedHandler, EventHandler<MouseEvent> stepButtonClickedHandler, ChangeListener<? super Number> sliderListener, ChangeListener<? super File> fileListener) {
        super();
        setId("dashboard");
        VBox myDashBoard = new VBox(SPACING);

        myDashBoard.prefHeightProperty().bind(this.heightProperty());
        myDashBoard.setPadding(PADDING);

        setUpFileProperty(fileListener);
        Button fileChooserButton = getFileChooserButton();

        mySpeedSlider = new Slider(1,10, SimulationControl.DEFAULT_RATE);
        mySpeedSlider.valueProperty().addListener(sliderListener);

        Pane playButtonsPane = getPlayButtonsPane(playButtonClickedHandler, pauseButtonClickedHandler, stepButtonClickedHandler);

        Region spacerRegion = new Region();
        VBox.setVgrow(spacerRegion, Priority.ALWAYS);

        myDashBoard.getChildren().addAll(fileChooserButton,mySpeedSlider,spacerRegion,playButtonsPane);
        getChildren().add(myDashBoard);

        setHeight(myDashBoard.getMinHeight());
    }

    private Pane getPlayButtonsPane(EventHandler<MouseEvent> playButtonClickedHandler, EventHandler<MouseEvent> pauseButtonClickedHandler, EventHandler<MouseEvent> stepButtonClickedHandler) {
        Pane playButtonsPane = new Pane();

        HBox playButtons = new HBox();
        playButtons.prefWidthProperty().bind(playButtonsPane.widthProperty());

        Button playButton = getConsoleButton(PLAY,playButtonClickedHandler);
        Button pauseButton = getConsoleButton(PAUSE,pauseButtonClickedHandler);
        Button stepButton = getConsoleButton(STEP,stepButtonClickedHandler);

        playButtons.getChildren().addAll(playButton,pauseButton,stepButton);

        playButtonsPane.getChildren().add(playButtons);
        return playButtonsPane;
    }

    private Button getConsoleButton(String description, EventHandler<? super MouseEvent> handler) {
        Button button = new Button(description);
        button.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(button,Priority.ALWAYS);
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
        if(selectedFile != null){
            myFileProperty.setValue(selectedFile);
        }

    }

}
