package cellsociety.View;

import cellsociety.Main;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.util.Map;
import java.util.ResourceBundle;

public class ImageCellState extends CellState{
    private static final Image FISH_IMAGE = new Image(ImageCellState.class.getClassLoader().getResourceAsStream("fish.jpg"));
    private static final Image FIRE_IMAGE = new Image(ImageCellState.class.getClassLoader().getResourceAsStream("fire.jpg"));
    private static final String IMAGE_KEY = "image";
    private Shape myDisplay;

    private void setBackgroundImage(Image image){
        ImagePattern pattern = new ImagePattern(image);
        myDisplay.setFill(pattern);
    }
    public ImageCellState(Map<String, String> parameters, Shape template){
        super();
        myID = parameters.get("id");

        myDisplay = template;
        String imageType = parameters.get(IMAGE_KEY);
        if(imageType.equals("img:FIRE")){
            setBackgroundImage(FIRE_IMAGE);
        } else if(imageType.equals("img:FISH")){
            setBackgroundImage(FISH_IMAGE);
        }
    }

    @Override
    public Node getNode() {
        return myDisplay;
    }
}
