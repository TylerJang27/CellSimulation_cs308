simulation
====

This project implements a cellular automata simulator.

Names: 
 * Mariusz Derezinski-Choo
 * Tyler Jang
 * Thomas Quintanilla

### Timeline

Start Date: 1/26/20

Finish Date: 

Hours Spent:

### Primary Roles


### Resources Used


### Running the Program

Main class:

Data files needed: 

Features implemented:



### Notes/Assumptions

Assumptions or Simplifications:
* Different simulations all share the same cell color scheme (red = 1, blue = 2, black = 0/empty).
* Each cell has access to its neighboring cells so that calculating the next state for each cell would be easier to handle.
* Assumptions for simulation rules:
   - Fire: each burning neighbor of a not burning tree cell has a set % chance to spread the fire to the cell. 
   The rule description was vague on whether this probability is only checked once no matter how many neighbors are burning or if its checked per neighbor.
   - PredatorPrey: A fish who meets breeding threshold but cannot reproduce due to neighboring cells being occupied will reproduce any step after where an adjacent cell is free.
   - PredatorPrey: It is interpreted when the rules state "After eating or moving" that a shark can not do both.
   - Segregation: Randomly moving unsatisfied cells will eventually balance out.

Interesting data files: The 3rd data file for each simulation type creates a 30x30 grid of cells with a random state for each cell. 
So it's interesting to see patterns that occur when ran multiple times.

Known Bugs: When the slider to control speed is initially changed from its default value, then a new file is loaded, the simulation will still update based on the default speed rather than the new chosen value.

Extra credit: The XML reader can take in different reading types. 
If the type is all, then every cell type and state is specified within the XML file. 
If the type is some, then some cells are specified and others are randomly generated.
If the type is random, then all cells are randomly generated.


### Impressions


