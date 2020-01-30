package cellsociety;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.Console;
public class ApplicationView {
    private Scene myScene;
    private BorderPane root;
    private GridView myGridView;
    public ApplicationView(double size, Stage primaryStage){
        Node myGridView = new GridView(10,10,800,800);
        Node myConsoleView = new ConsoleView();
        Node myDashboardView = new DashboardView(primaryStage);


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
