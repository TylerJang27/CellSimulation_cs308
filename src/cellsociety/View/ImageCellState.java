package cellsociety.View;

import cellsociety.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;
import java.util.ResourceBundle;

public class ImageCellState extends CellState{
    private static final Image FISH_IMAGE = new Image(ImageCellState.class.getClassLoader().getResourceAsStream("fish.jpg"));
    private static final Image FIRE_IMAGE = new Image(ImageCellState.class.getClassLoader().getResourceAsStream("fire.jpg"));
    private static final String IMAGE_KEY = "image-type";
    private static final ResourceBundle IMAGE_RESOURCES = Main.myImageResources;
    private ImageView myImageView;

    public ImageCellState(){
        super();
        initializeImage(FISH_IMAGE);
    }
    private void initializeImage(Image image){
        myImageView = new ImageView(image);
        myImageView.fitWidthProperty().bind(this.widthProperty());
        myImageView.fitHeightProperty().bind(this.heightProperty());

        getChildren().add(myImageView);
    }
    public ImageCellState(Map<String, String> parameters){
        super();
        String imageType = parameters.get(IMAGE_KEY);
        if(imageType.equals("FIRE")){
            initializeImage(FIRE_IMAGE);
        } else if(imageType.equals("FISH")){
            initializeImage(FISH_IMAGE);
        }
    }
}
