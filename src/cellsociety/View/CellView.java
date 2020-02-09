package cellsociety.View;

import cellsociety.Main;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A class to render the Appearance of a cell.
 *
 * @author Mariusz Derezinski-Choo
 */
public class CellView extends Pane {

  private List<CellState> cellStateList;
  private CellState myCurrentState;
  private static final ResourceBundle RESOURCES = Main.myResources;

//List<CellState> cellStates
  public CellView(){
    super();

    setUpDefaultConfiguration();
    myCurrentState = cellStateList.get(0);
    for(CellState state : cellStateList){
      state.prefHeightProperty().bind(this.heightProperty());
      state.prefWidthProperty().bind(this.widthProperty());
    }
    getChildren().add(myCurrentState);
  }
  public CellView(List<CellStateConfiguration> configuration){
    super();
    setUpDefaultConfiguration();
    for(CellStateConfiguration config : configuration){
      //FIXME: MORE THINGY HERE
      System.out.println(configuration);
    }
    cellStateList = new ArrayList<>();
    for(int i = 0; i < configuration.size(); i++){
      CellStateConfiguration currentConfiguration = configuration.get(i);
      if(currentConfiguration.getStyle().equals(RESOURCES.getString("Color"))) {
        cellStateList.add(new ColoredCellState(currentConfiguration.getParameters()));
      }else if(currentConfiguration.getStyle().equals(RESOURCES.getString("Image"))){
        cellStateList.add(new ImageCellState(currentConfiguration.getParameters()));
      }
      //FIXME: ADD ADDITIONAL CONFIGURATION STUFF?
      System.out.println("");
    }

    myCurrentState = cellStateList.get(0);
    for(CellState state : cellStateList){
      state.prefHeightProperty().bind(this.heightProperty());
      state.prefWidthProperty().bind(this.widthProperty());
    }
    getChildren().add(myCurrentState);
  }

  private void setUpDefaultConfiguration(){
    cellStateList = new ArrayList<>();
    cellStateList.add(new ImageCellState());
    cellStateList.add(new ColoredCellState(Color.RED));
    cellStateList.add(new ColoredCellState(Color.BLUE));
    cellStateList.add(new ColoredCellState(Color.YELLOW));
  }

  /**
   * Change the appearance of the cell based on the state
   *
   * @param state the next state of the cell
   */
  public void changeState(int state) {
    getChildren().remove(myCurrentState);
    myCurrentState = cellStateList.get(state);
    for (CellState c: cellStateList) {
      System.out.println(c);
    }
    getChildren().add(cellStateList.get(state));
  }

  private ImageCellState getCellConfiguration(CellStateConfiguration configuration){
    String style = configuration.getStyle();
    if(style.equals("IMAGE")){
      return new ImageCellState(configuration.getParameters());
    }
    System.out.println("whoops");
    return null;
  }
}
