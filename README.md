simulation
====

This project implements a cellular automata simulator.

Names: 
 * Mariusz Derezinski-Choo
 * Tyler Jang
 * Thomas Quintanilla

### Timeline

Start Date: 1/26/20

Finish Date: 2/4/20

Hours Spent:

### Primary Roles


### Resources Used
[Spike Simulation](https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation) for XML reading and enumerated types

[Game Of Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life) for Conway's Game of Life specification

[Percolation](https://www2.cs.duke.edu/courses/compsci308/current/assign/02_simulation/PercolationCA.pdf) for Percolation algorithm

[Segregation](https://www2.cs.duke.edu/courses/compsci308/current/assign/02_simulation/nifty/mccown-schelling-model-segregation/) for Segregation specification

[Wa-Tor World](https://www2.cs.duke.edu/courses/compsci308/current/assign/02_simulation/nifty/scott-wator-world/) for Predator-Prey specification

[Fire Spreading](https://www2.cs.duke.edu/courses/compsci308/current/assign/02_simulation/nifty/shiflet-fire/) for fire specification

### Running the Program

Main class: Main.java

   Creates an instance of SimulationControl.java on start, which in turn creates XMLParser.java, ApplicationView.java, and Grid.java instances to build the simulation.


Data files needed: 

   An XML fitting the format for a Simulation type. For examples, see:
 * **Fire1.xml**: a basic Fire simulation with all values specified
 * **Fire2.xml**: a basic Fire simulation with some values specified
 * **Fire3.xml**: a basic Fire simulation with random values
 * **GameOfLife1.xml**: a basic Game of Life simulation with all values specified
 * **GameOfLife2.xml**: a basic Game of Life simulation with some values specified
 * **GameOfLife3.xml**: a basic Game of Life simulation with random values
 * **Percolation1.xml**: a basic Percolation simulation with all values specified
 * **Percolation2.xml**: a basic Percolation simulation with some values specified
 * **Percolation3.xml**: a basic Percolation simulation with random values
 * **PredatorPrey1.xml**: a basic Predator-Prey simulation with all values specified
 * **PredatorPrey2.xml**: a basic Predator-Prey simulation with some values specified
 * **PredatorPrey3.xml**: a basic Predator-Prey simulation with random values
 * **Segregation1.xml**: a basic Segregation simulation with all values specified
 * **Segregation2.xml**: a basic Segregation simulation with some values specified
 * **Segregation3.xml**: a basic Segregation simulation with random values
 
Features implemented:

This project implements a basic user interface in which XML configuration files can be specified to control parameters of five different simulations:
 * **Fire**: A cell may either be *empty*, *tree*, or *burning*. If a tree's neighbor is burning, it has a chance, *catch*, of catching fire. Burning cells turn to empty after one turn.
 * **Game Of Life**: A cell may either be *dead* or *alive*. If a live cell has 0-1 or 4-8 live neighbors, it dies. If a dead cell has three live neighbors, it will become alive.  
 * **Percolation**: A cell may either be *closed*, *opened*, or *filled*. If a cell is opened and has a filled neighbor, it will become filled on the next turn. 
 * **Predator-Prey**: A cell may either be *empty*, *fish*, or *shark*. If a shark has been alive *shark_starve* turns, it will die. If a shark has been alive *shark_breed* turns, it will spawn another shark. If a fish has been alive *fish_breed* turns, it will spawn another shark. If a shark is near a fish, it will move toward the fish. If a shark is neighboring a fish, the fish will die and become empty. 
 * **Segregation**: A cell may either be *empty*, *A*, or *B*. If a cell is A or B, and less than *threshold* % of its neighbors are of the same type, it will move to a random empty space on the next turn.

XML files must adhere to the different simulation types' settings, but allow for "all", "some", and "random" cell specifications.

In addition to specifying a configuration file, the user interface allows for the frame rate to be changed. It displays the grid and a log of any potential error messages.

This front end is made possible by a back end implementation of Grid, in which Cells update based on fixed rules regarding their neighbors.

### Notes/Assumptions

Assumptions or Simplifications:

Interesting data files:

Known Bugs:

Extra credit:


### Impressions

