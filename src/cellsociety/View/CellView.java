package cellsociety.View;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to render the Appearance of a cell.
 *
 * @author Mariusz Derezinski-Choo
 */
public class CellView extends Pane {

  private List<CellState> cellStateList;
  private CellState myCurrentState;

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
      System.out.println(configuration);

    }

    for(int i = 0; i < configuration.size(); i++){
      CellStateConfiguration currentConfiguration = configuration.get(0);
      switch(currentConfiguration.getStyle()){

      }
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
    getChildren().add(cellStateList.get(state));
    myCurrentState = cellStateList.get(state);
  }

  private ImageCellState getCellConfiguration(CellStateConfiguration configuration){
    String style = configuration.getStyle();
    if(style.equals("IMAGE")){
      return new ImageCellState(configuration.getParameters());
    }
    return null;
  }
}
