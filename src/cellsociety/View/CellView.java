package cellsociety.View;

import javafx.scene.Node;
import cellsociety.Main;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * A class to render the Appearance of a cell.
 *
 * @author Mariusz Derezinski-Choo
 */
public class CellView extends Pane {
  private static final int FULL_CIRCLE_DEGREES = 360;
  private static final int ANGLE_STEP = 60;
  private static final double gridLineWidth = 4;
  private static final double DEFAULT_HEXAGON_SIDE_LENGTH = HexagonGridView.DEFAULT_SIDE_LENGTH;




  private List<CellState> cellStateList;
  private CellState myCurrentState;
  private static final ResourceBundle RESOURCES = Main.myResources;


  public CellView(List<CellStateConfiguration> configuration){
    super();

    for(CellStateConfiguration config : configuration){
      //FIXME: MORE THINGY HERE
      System.out.println(configuration);
    }

    cellStateList = new ArrayList<>();

    for(int i = 0; i < configuration.size(); i++){
      CellStateConfiguration currentConfiguration = configuration.get(i);

      Shape cellTemplate = createShape(currentConfiguration.getShape(), currentConfiguration.getParameters());

      if(currentConfiguration.getStyle().equals(RESOURCES.getString("Color"))) {
        cellStateList.add(new ColoredCellState(currentConfiguration.getParameters(), cellTemplate));
      }else if(currentConfiguration.getStyle().equals(RESOURCES.getString("Image"))){
        cellStateList.add(new ImageCellState(currentConfiguration.getParameters(), cellTemplate));
      }
      //FIXME: ADD ADDITIONAL CONFIGURATION STUFF?
    }

    myCurrentState = cellStateList.get(0);

    getChildren().add(myCurrentState.getNode());
  }

  private Shape createShape(String description, Map<String, String> params){
      /*
    if(description.equals("rectangle")){
        System.out.println(params);
        double width, height;
        try {
            width = Double.parseDouble(params.get("width"));
            height = Double.parseDouble(params.get("height"));
        } catch(Exception e){
            width = 50;
            height = 50;
        }
      return new Rectangle(width, height);
    } else if(description.equals("hexagon")){

       */
        double sideLength;
        try {
            sideLength = Double.parseDouble(params.get("sideLength"));
        } catch(Exception e){
            sideLength = DEFAULT_HEXAGON_SIDE_LENGTH;
        }
      return makeHexagon(sideLength);
    //}
    //System.out.println("error in cellview");
    //return null;
  }


  /**
   * Change the appearance of the cell based on the state
   *
   * @param state the next state of the cell
   */
  public void changeState(int state) {
    getChildren().remove(myCurrentState.getNode());
    myCurrentState = cellStateList.get(state);
    getChildren().add(myCurrentState.getNode());
  }


  private Shape makeHexagon(double sideLength){
    Polygon newPolygon = new Polygon();
    for(int angle = 0; angle < FULL_CIRCLE_DEGREES; angle += ANGLE_STEP){
      double coordinateX = (sideLength + gridLineWidth) * Math.cos(Math.toRadians(angle));
      double coordinateY = (sideLength + gridLineWidth)* Math.sin(Math.toRadians(angle));
      newPolygon.getPoints().addAll(new Double[]{coordinateX, coordinateY});
    }
    newPolygon.setStroke(Color.GREEN);
    newPolygon.setStrokeWidth(gridLineWidth / 2);

    return newPolygon;
  }

  public String getCellState(){
      return myCurrentState.getStateDescription();
  }

}
