#Package Description

This package governs the model part of the simulation, including receiving data from the XML file,
creating a Grid of cells, and updating the cells based on preset rules.

This package is divided into the following core classes:
 
 * **Grid**: An abstraction of the different grid types used in the simulation, including Cells with an internal Map implementation.
    * **FireGrid**: A grid for the fire simulation, in which trees have a chance of catching fire if next to a burning tree.
    * **GameOfLifeGrid**: A grid for Conway's Game of Life, in which cells either die, live, or spawn based on the number of live neighboring cells.
    * **PercolationGrid**: A grid for the percolation simulation, in which "full" cells spread to neighboring open, unblocked cells.
    * **PredatorPreyGrid**: A grid for the predator-prey simulation, in which sharks die from starvation or consume fish and reproduce. Fish may also reproduce after a fixed amount of time.
    * **Segregation**: A grid for the segregation simulation, in which cells of one state will relocate to a random empty state if not near enough similar neighbors. 
 * **Cell**: An abstraction of the different cell types used in the simulation, with basic rules defined for some of the common cell types.
    * **FireCell**: A cell for the fire simulation.
    * **GameOfLifeCell**: A cell for Conway's Game of Life.
    * **PercolationCell**: A cell for the percolation simulation.
    * **PredatorPreyCell**: A cell for the predator-prey simulation.
    * **SegregationCell**: A cell for the segregation simulation.
