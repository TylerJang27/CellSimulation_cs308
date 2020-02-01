package cellsociety.View;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApplicationView {
    private Scene myScene;
    private BorderPane root;
    private GridView myGridView;
    public ApplicationView(double size, Stage primaryStage, EventHandler<MouseEvent> playButtonClickedHandler, EventHandler<MouseEvent> pauseButtonClickedHandler, EventHandler<MouseEvent> stepButtonClickedHandler, ChangeListener<? super Number> sliderListener) {
        Node myGridView = new GridView(0,0,800,800);
        Node myConsoleView = new ConsoleView();
        Node myDashboardView = new DashboardView(primaryStage,playButtonClickedHandler,pauseButtonClickedHandler,stepButtonClickedHandler,sliderListener);

        root = new BorderPane();

        root.setBottom(myConsoleView);
        root.setCenter(myGridView);
        root.setLeft(myDashboardView);
        myScene = new Scene(root);

        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public void updateCell(int row, int column, int state){
        myGridView.updateCell(row, column, state);
    }
    public void initializeGrid(int numRows, int numColumns, double width, double length){
        myGridView = new GridView(numRows,numColumns,width, length);
        root.setCenter((myGridView));
    }
}
