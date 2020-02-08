package cellsociety.View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageCellState extends CellState{
    private static final Image myImage = new Image(ImageCellState.class.getClassLoader().getResourceAsStream("fish.jpg"));;
    private ImageView myImageView;

    public ImageCellState(){
        super();
        myImageView = new ImageView(myImage);
        myImageView.fitWidthProperty().bind(this.widthProperty());
        myImageView.fitHeightProperty().bind(this.heightProperty());

        getChildren().add(myImageView);
    }
}
