package resources;

import javafx.scene.image.Image;

import java.util.ListResourceBundle;

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
