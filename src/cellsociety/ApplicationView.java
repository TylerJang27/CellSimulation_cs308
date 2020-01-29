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
    public ApplicationView(double size, Stage primaryStage){
        Node myGridView = new GridView(10,10);
        Node myConsoleView = new ConsoleView();
        Node myDashboardView = new DashboardView(primaryStage);


        BorderPane root = new BorderPane();

        root.setBottom(myConsoleView);
        root.setCenter(myGridView);
        root.setLeft(myDashboardView);
        myScene = new Scene(root, size, size);

        primaryStage.setScene(myScene);
        primaryStage.show();
    }
}
