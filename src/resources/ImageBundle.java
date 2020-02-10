package resources;

import java.util.ListResourceBundle;
import javafx.scene.image.Image;

public class ImageBundle extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return new Object[0][];
  }

  private Object[][] contents = {
      {"FISH", new Image(getClass().getClassLoader().getResourceAsStream("fish.jpg"))},
      {"FIRE", new Image(getClass().getClassLoader().getResourceAsStream("fire.jpg"))}
  };
}
