simulation
====

This project implements a cellular automata simulator.

Names: 
 * Mariusz Derezinski-Choo
 * Tyler Jang
 * Thomas Quintanilla

### Timeline

Start Date: 1/26/20

Finish Date: 02/04/29

Hours Spent: 40

### Primary Roles
#### Tyler
Tyler worked on the Controller part of the project. He implemented the XMLParser, and designed the XML structure so that the XML could be converted to a Simulation. Tyler worked on the logic behind the methods to start, stop, and step through the simulation. The key components of this work is in SimulationControl.
#### Thomas
Thomas worked on developing the various different types of cells and Grids. This was accomplished by adding several subclasses to the abstract Cell and Grid classes in the model. The structured use of inheritance allows for different Grid types to be easily instantiated in Controller depending on the file uploaded.
#### Mariusz
Mariusz worked on rendering the various elements of the GUI, and connecting them to the Controller via the use of EventHandlers and ChangeListeners. He constructed generic listeners in the Controller that could be added to the graphics elements once they were rendered. The appearance of the elements were styled with the use of css.
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
Overall the project maintains good separation of Model, View, and Controller. The Model stores the details of the Grid without understanding any of the implementation behind viewing, loading, and running the simulations. That job is handled by the Controller. Meanwhile, View, dislays the Grid without worrying about how each state is calculated. This allows for each component to be developed separately by different developers. Model makes use of inheritance to define different types of Grids and Cells, which would allow for future rules to be easy added to the application. View makes use of EventHandlers and ChangeListeners to bind actions performed by the user on the GUI to logic in the Controller. This abstracts away from the Controller, and means the graphic elements can be rendered in whatever manner, as long as they are compatible with the listeners provided. Controller makes use of Exceptions, such as the custom-made XMLParser, to handle bad input cased by illegal actions taken by the user. This information is logged to the ApplicationView console, and allows for error messages to be displayed to the user. This lets the user correct their behavior while simulatneously ensuring that bad input does not crash the program.

