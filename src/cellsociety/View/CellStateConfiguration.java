package cellsociety.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellStateConfiguration {
    private String myShape;
    private String myStyle;
    private Map<String, String> myParams;

    public CellStateConfiguration(String shape, String style, Map<String, String> params){
        myShape = shape;
        myStyle = style;
        myParams = new HashMap<>(params);
    }

    @Override
    public String toString(){
        return "Shape: " + myShape + " Style: " + myStyle + " Parameters: " + myParams;
    }

    public String getStyle(){
        return myStyle;
    }

    public Map<String, String> getParameters(){
        return myParams;
    }
}
