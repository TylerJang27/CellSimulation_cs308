package cellsociety.View;

import cellsociety.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.ResourceBundle;

/**
 * Class to render a Pane displaying the Console for the Cellula Automata Application
 *
 * @author  Mariusz Derezinski-Choo
 */
public class ConsoleView extends Pane {
    public static final ResourceBundle RESOURCES = Main.myResources;
    public static final String CONSOLE_STYLING = "-fx-background-color: #baffe0";
    public static final String FRAME_NUMBER_TEXT = RESOURCES.getString("FrameNumber");
    public static final String SIMULATION_NO_ERRORS = RESOURCES.getString("ConsoleNoErrors");
    public static final double MARGINS = 10;

    private Label myErrorLabel;
    private Label myFrameTextLabel;
    private Label myFrameNumberLabel;
    private int myFrame;

    /**
     * Construct a ConsoleView instance
     */
    public ConsoleView(){
        super();
        this.setStyle(CONSOLE_STYLING);
        myFrame = 0;

        HBox myConsoleView = new HBox();
        myConsoleView.setPadding(new Insets(MARGINS,MARGINS,MARGINS,MARGINS));
        myConsoleView.prefWidthProperty().bind(this.widthProperty());

        myFrameTextLabel = new Label(FRAME_NUMBER_TEXT);
        myErrorLabel = new Label(SIMULATION_NO_ERRORS);
        myFrameNumberLabel = new Label(String.valueOf(myFrame));

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        myConsoleView.getChildren().addAll(myErrorLabel,region1,myFrameTextLabel,myFrameNumberLabel);

        getChildren().add(myConsoleView);

    }

    /**
     * Log an error to the console
     * @param error A String detailing the error
     */
    public void logError(String error){
        myErrorLabel.setText(error);
    }

    /**
     * Display the frame number passed as a parameter
     * @param frameNumber the frame number
     */
    public void showFrame(int frameNumber){
        myFrameNumberLabel.setText(String.valueOf(frameNumber));
    }
}
