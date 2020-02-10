package cellsociety.View;

import cellsociety.Main;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * Implements a CellState but for a Cell who's design will be rendered using an Image
 *
 * @author Mariusz Derezinski-Choo
 */
public class ImageCellState extends CellState{
    private static final Image FISH_IMAGE = new Image(ImageCellState.class.getClassLoader().getResourceAsStream("fish.jpg"));
    private static final Image FIRE_IMAGE = new Image(ImageCellState.class.getClassLoader().getResourceAsStream("fire.jpg"));
    private static final ResourceBundle RESOURCES = Main.myResources;
    private static final String IMAGE_KEY = RESOURCES.getString("image");
    private static final String FIRE_IMAGE_KEY = RESOURCES.getString("fire-image");
    private static final String FISH_IMAGE_KEY = RESOURCES.getString("fish-image");

    private Shape myDisplay;

    /**
     * Construct an ImageCellState onto the given shape with the image background rendered accordng to the image stored in the parameters
     * @param parameters the parameters that hold information about what image to use
     * @param template the Shape that the Image will be set as a background for
     */
    public ImageCellState(Map<String, String> parameters, Shape template){
        super();
        myID = parameters.get(CellState.CELL_STATE_PARAMETER_KEY);

        myDisplay = template;
        String imageType = parameters.get(IMAGE_KEY);
        if(imageType.equals(FIRE_IMAGE_KEY)){
            setBackgroundImage(FIRE_IMAGE);
        } else if(imageType.equals(FISH_IMAGE_KEY)){
            setBackgroundImage(FISH_IMAGE);
        }
    }

    @Override
    public Node getNode() {
        return myDisplay;
    }

    private void setBackgroundImage(Image image){
        ImagePattern pattern = new ImagePattern(image);
        myDisplay.setFill(pattern);
    }
}
