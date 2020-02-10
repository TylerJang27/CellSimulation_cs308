package cellsociety.View;

import cellsociety.Main;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * A simple class to encapsulate a the configuration for a CellState. different
 * CellStateConfigurations can be instantiated and then passed to the View to render Cells that can
 * switch between different CellStates. The basic elements required for a CellState configuration
 * are a shape, a style, and other style-specific parameters, represented as a map of key-value
 * pairs
 *
 * @author Mariusz Derezinski-Choo
 */
public class CellStateConfiguration {

  private static final ResourceBundle RESOURCES = Main.myResources;
  private static final String TO_STRING_TEMPLATE = RESOURCES
      .getString("cell-state-configuration-to-string");

  private String myShape;
  private String myStyle;
  private Map<String, String> myParams;

  /**
   * Construct a CellStateConfiguration object
   *
   * @param shape  the shape that this cellstate should be rendered onto
   * @param style  the style that should dictate the appearance of the state. may be colored, filled
   *               with an image, etc
   * @param params any style-specific parameters, such as the id, color, size, etc.
   */
  public CellStateConfiguration(String shape, String style, Map<String, String> params) {
    myShape = shape;
    myStyle = style;
    myParams = new HashMap<>(params);
  }

  /**
   * getter method for the shape of the configuration
   *
   * @return the shape of the configuration
   */
  public String getShape() {
    return myShape;
  }

  /**
   * getter method for the style of the configuration
   *
   * @return the style of the configuration
   */
  public String getStyle() {
    return myStyle;
  }

  /**
   * getter method for the parameters of the configuration parameters are usually style specific and
   * specify unique attributes for this state
   *
   * @return a map with key-value pairs specifying parameters
   */
  public Map<String, String> getParameters() {
    return myParams;
  }

  @Override
  public String toString() {
    return String.format(TO_STRING_TEMPLATE, myShape, myStyle, myParams);
  }
}
