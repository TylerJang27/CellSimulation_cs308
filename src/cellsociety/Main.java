package cellsociety;

import cellsociety.Controller.Simulation;
import cellsociety.Controller.XMLParser;
import cellsociety.Model.*;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        File dataFile = new File("data/GameOfLife1.xml");
        Simulation mySim = new XMLParser("type").getSimulation(dataFile);

        System.out.printf("Simulation type: %s\n", mySim.getType());
        System.out.printf("rate: \t\t %s\n", mySim.getValue("rate"));
        System.out.printf("width: \t\t %s\n", mySim.getValue("width"));
        System.out.printf("height: \t %s\n", mySim.getValue("height"));
        for (String s : mySim.getType().getFields()) {
            System.out.printf("%s \t\t %s\n", s, mySim.getValue(s));
        }
        System.out.println("grid:");
        for (Point p : mySim.getGrid().keySet()) {
            System.out.printf("x: %f y: %f \t %d\n", p.getX(), p.getY(), mySim.getGrid().get(p));
        }

        HashMap<String, Integer> cellValues = new HashMap<>();
        cellValues.put("width", 5);
        cellValues.put("height", 5);
        cellValues.put("rate", 10);

        HashMap<Point, Integer> cellGrid = new HashMap<>();
        for (int x = 1; x < 5; x++) {
            for (int y = 1; y < 5; y++) {
                cellGrid.put(new Point(x, y), (int)(Math.random()*1.5));
            }
        }
        cellGrid.put(new Point(0, 0), 2);
        PredatorPreyGrid grid = new PredatorPreyGrid(cellGrid, cellValues);
        grid.printGrid();
        for (int i = 0; i < 5; i++) {
            System.out.println("------------------------------------------");
            grid.nextFrame();
            grid.printGrid();
        }
    }
}
