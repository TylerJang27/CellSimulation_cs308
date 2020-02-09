package cellsociety.View;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

public class HexagonGridView extends GridView{
    private static final int FULL_CIRCLE_DEGREES = 360;
    private static final int ANGLE_STEP = 60;

    private Pane myGrid;
    private double mySideLength;
    private 

    public HexagonGridView(double sideLength){
        super();
        myGrid = new Pane();
        myGrid.getChildren().add(makeHexagon(sideLength));
    }

    @Override
    public void updateCell(int row, int column, int state) {

    }

    @Override
    public Node getNode() {
        return myGrid;
    }

    private Polygon makeHexagon(double sideLength){
        Polygon newPolygon = new Polygon();
        for(int angle = 0; angle < FULL_CIRCLE_DEGREES; angle += ANGLE_STEP){
            double coordinateX = sideLength * Math.cos(Math.toRadians(angle));
            double coordinateY = sideLength * Math.sin(Math.toRadians(angle));
            newPolygon.getPoints().addAll(new Double[]{coordinateX, coordinateY});
        }
        return newPolygon;
    }
}
