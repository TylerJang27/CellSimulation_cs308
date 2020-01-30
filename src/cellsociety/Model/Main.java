package cellsociety.Model;

import cellsociety.Controller.Simulation;
import cellsociety.Controller.XMLParser;

import java.awt.*;
import java.io.File;

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
        for (String s: mySim.getType().getFields()) {
            System.out.printf("%s \t\t %s\n", s, mySim.getValue(s));
        }
        System.out.println("grid:");
        for (Point p: mySim.getGrid().keySet()) {
            System.out.printf("x: %f y: %f \t %d\n", p.getX(), p.getY(), mySim.getGrid().get(p));
        }
    }
}
