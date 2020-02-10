package cellsociety.View;

import java.util.Map;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

/**
 * A class that impements a CellState but with images as the background
 *
 * @author Mariusz Derezinski-Choo
 */
public class ImageCellState extends CellState {

  private static final Image FISH_IMAGE = new Image(
      ImageCellState.class.getClassLoader().getResourceAsStream("fish.jpg"));
  private static final Image FIRE_IMAGE = new Image(
      ImageCellState.class.getClassLoader().getResourceAsStream("fire.jpg"));
  private static final String IMAGE_KEY = "image";
  private Shape myDisplay;

  private void setBackgroundImage(Image image) {
    ImagePattern pattern = new ImagePattern(image);
    myDisplay.setFill(pattern);
  }

  public ImageCellState(Map<String, String> parameters, Shape template) {
    super();
    myID = parameters.get("id");
  }

  @Override
  public Node getNode() {
    return myDisplay;
  }
}
