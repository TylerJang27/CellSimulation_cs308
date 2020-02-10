package cellsociety.View;

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
    private static final int HEXAGON_ANGLE_PER_SIDE_DEGREES = 60;
    private static final double gridLineWidth = 4;
    private static final double DEFAULT_HEXAGON_SIDE_LENGTH = HexagonGridView.DEFAULT_SIDE_LENGTH;
    private static final ResourceBundle RESOURCES = Main.myResources;
    private static final String COLOR_STYLE = RESOURCES.getString("Color");
    private static final String IMAGE_STYLE = RESOURCES.getString("Image");

    private static final String RECTANGLE = RESOURCES.getString("rectangle");
    private static final String WIDTH = RESOURCES.getString("width");
    private static final String HEIGHT = RESOURCES.getString("height");
    private static final String HEXAGON = RESOURCES.getString("hexagon");
    private static final String SIDE_LENGTH = RESOURCES.getString("side-length");
    private static final int DEFAULT_RECTANGLE_WIDTH = 50;
    private static final int DEFAULT_RECTANGLE_HEIGHT = 50;

    private List<CellState> cellStateList;
    private CellState myCurrentState;


    /**
     * Construct a CellView with the given List of configurations. the index of each configuration in the list will be mirrored
     * in the List of CellStates created
     * @param configuration A list of configurations
     */
  public CellView(List<CellStateConfiguration> configuration){
    super();

    cellStateList = new ArrayList<>();

      for (CellStateConfiguration config : configuration) {
          addCellState(config);
      }

    myCurrentState = cellStateList.get(0);
    getChildren().add(myCurrentState.getNode());
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

    /**
     * get the id of the current cell state for this cell
     * @return the id represented as a string of the current state of the cell
     */
  public String getCellState(){
        return myCurrentState.getStateDescription();
    }


  private Shape makeHexagon(double sideLength){
    Polygon newPolygon = new Polygon();
    for(int angle = 0; angle < FULL_CIRCLE_DEGREES; angle += HEXAGON_ANGLE_PER_SIDE_DEGREES){
      double coordinateX = (sideLength + gridLineWidth) * Math.cos(Math.toRadians(angle));
      double coordinateY = (sideLength + gridLineWidth)* Math.sin(Math.toRadians(angle));
      newPolygon.getPoints().addAll(coordinateX, coordinateY);
    }
    newPolygon.setStroke(Color.GREEN);
    newPolygon.setStrokeWidth(gridLineWidth / 2);

    return newPolygon;
  }

    private void addCellState(CellStateConfiguration currentConfiguration) {
        Shape cellTemplate = createShape(currentConfiguration.getShape(), currentConfiguration.getParameters());
        String configurationStyle = currentConfiguration.getStyle();
        if (configurationStyle.equals(COLOR_STYLE)) {
            cellStateList.add(new ColoredCellState(currentConfiguration.getParameters(), cellTemplate));
        } else if (configurationStyle.equals(IMAGE_STYLE)) {
            cellStateList.add(new ImageCellState(currentConfiguration.getParameters(), cellTemplate));
        }
    }

    private Shape createShape(String description, Map<String, String> params){
        if(description.equals(RECTANGLE)){
            return getRectangle(params);
        } else if(description.equals(HEXAGON)){
            return getHexagon(params);
        }
        return null;
    }

    private Shape getHexagon(Map<String, String> params) {
        double sideLength;
        try {
            sideLength = Double.parseDouble(params.get(SIDE_LENGTH));
        } catch(Exception e){
            sideLength = DEFAULT_HEXAGON_SIDE_LENGTH;
        }
        return makeHexagon(sideLength);
    }

    private Shape getRectangle(Map<String, String> params) {
        double width, height;
        try {
            width = Double.parseDouble(params.get(WIDTH));
            height = Double.parseDouble(params.get(HEIGHT));
        } catch(Exception e){
            width = DEFAULT_RECTANGLE_WIDTH;
            height = DEFAULT_RECTANGLE_HEIGHT;
        }
        return new Rectangle(width, height);
    }
}
