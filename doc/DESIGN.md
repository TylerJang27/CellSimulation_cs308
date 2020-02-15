# Simulation Design Final
### Names

- Tyler Jang
- Thomas Quintanilla
- Mariusz Derezinski-Choo

## Team Roles and Responsibilities

### Tyler
Tyler worked on the Controller part of the project. He implemented the XML parsers, and designed the XML structure so that the XMLs could be converted to a Simulation and a Style. Tyler worked on the logic behind the methods to start, stop, and step through the simulation. The central components of this work are in SimulationControl.
### Thomas
Thomas worked on developing the various different types of cells and Grids for the Model. This was accomplished by adding several subclasses to the abstract Cell and Grid classes in the model. The structured use of inheritance allows for different Grid types to be easily instantiated in Controller depending on the file uploaded.
### Mariusz
Mariusz worked on rendering the various elements of the GUI for the View, and connecting them to the Controller via the use of EventHandlers and ChangeListeners. He constructed generic listeners in the Controller that could be added to the graphics elements once they were rendered. The appearance of the elements were styled with the use of css.

## Design goals

### What Features are Easy to Add
Our design was implemented with scalability in mind. We wanted new Simulations to be easy to add, with the sole requirements being changing of defaults, rules, and styling. This allows for varieties of XMLs to be read for new simulations.

In addition, we wanted the UI to be easily configurable, so GridPanes were used to promote scalability on the front-end. New cell apearances can also be easily added using the CellState hieararchy described in subsequenct sections

We also wanted to allow for easily styling different elements, which was accomplished by putting all styling information in a css file that can easily be modified or be made to be completely different by changing the local-specified file name.

In addition, new language settings can be added by providing a different ResourceBundle in Main, rather than using English.properties.

More specific feature customization can be found below in "Easy to Add Features."

## High-level Design

### Core Classes

**Model**: 
* When a simulation Grid object is constructed, the constructor is fed two maps - one containing the points of any predefined cells and one that defines the parameters to construct the grid.
These parameters include the width and height of the grid, the shape of each cell, the shape of the grid (toroidal, cylindrical, or square), and any parameters specific to the simulation.
* The grid then constructs the cells corresponding to the simulation. 
The cell states are set either randomly or based on the map provided.
Afterwards, the grid constructs and assigns the list of neighbors to each cell.
* The grid is updated whenever nextFrame() is called, which cycles through every cell and collects each one's next state before applying the new state to their respective cells.
Each simulation has a set of rules to update each cell by, which is defined in each cell's nextState().
* The grid also returns useful information that's used by the controller, such as a specific cell's state and the max amount of states for each simulation.

**View**:
The View is responsible for rendering the results generated from the model onto a GUI. The core public classes are ApplicationView and CellStateConfiguration. 
 - The ApplicationView is initialized upon the launching of the program by the Controller. It provides functionality for displaying the Grid, dashboard, and Console. when creating a new grid to be displayed, the Controller must create instances of CellStateConfiguration to pass to the ApplicationView. 
 - CellStateConfigurations define the properties of the CellState, such as the shape, type of styling, and relevant parameters. These configuration objects are used by the GridView to generate CellViews, which hold several CellStates that can be toggled in response to changing the state.
 - Auxilliary classes that aid in the internal implementation of this external API include:
    - **CellClickedEvent**: a class that wraps a MouseEvent and adds instance variables that track the row and column of the cell that was clicked, which allows for the controller to change the state of the cell that was clicked
    - **CellState**: an abstract class that defines behaviour for a CellState, namely that it has a unique String identifier that describes its state in plain language terms
    - **ColoredCellState**: A class to implement a CellState that is filled in with a solid color.
    - **HexagonGridView**: a class to implement GridView with an array of Hexagon-shaped Cells
    - **ImageCellState**: A display of CellStates that are filled in with an image
    - **RectangleGridView**: A simple Grid that consistes of rectangular Shaped CellView
    - **GridView**: The display of all cells' states, divided up into multiple CellViews that can be updated to different states
    - **CellView**: The display of each cell, with potential shapes of Rectangle and Hexagon, and potential styling of image or color
    - **DashboardView**: The display of the editable user interface, allowing the user to choose configuration files, set the speed, and step, run, or pause the simulation.
    - **ConsoleView**: The display of information and any errors that the user may encounter.
**Controller**:
 - Main.java should launch an Application, and in doing so create an instance of SimulationControl.java, passing in the Application stage. On each frame update, it should call SimulationControl.next().
 - SimulationControl.java will create an initialize instances of ApplicationView.java and Grid.java, based on configuration and styling information parsed by ConfigParser.java and StyleParser.java. It also provides a handler to ApplicationView.java with which XML files can be written using WriteXMLFile.java.
    - ConfigParser.java reads in information from an XML file and stores it in a Simulation.java instance that adheres to conventions set by SimType.java. In doing so it also uses parsing mechanisms defined in GridParser.java.
    - StyleParser.java reads in information from an XML file and stores it in a Style.java instance that adheres to conventions set by SimStyle.java.
    - SimType.java and SimStyle.java are enumerated types specifying the necessary fields for each Simulation and Style type.
    - WriteXMLFile.java creates an XML file based on the grid status and configuration parameters of the current simulation and saves it in data/.
 
## Assumptions that Affect the Design

### Features Affected by Assumptions
This design implementation makes the following assumptions:
 - UI components cannot be specified/positioned based on configuration parameters (aside from text content).
 - Different simulations all share the same default cell color scheme (red = 1, blue = 2, black = 0/empty).
 - The Application window cannot be resized.
 - The XML Parsing assumed that XML files would not contain doubles, so only Integer parsing was used, throwing an exception if this failed.
 - Neighbor assignment are specific to each simulation and are unable to be modified (other than by the shape of the cell).
 - Assumptions for simulation rules:
    * **Fire**: each burning neighbor of a not burning tree cell has a set % chance to spread the fire to the cell. 
    The rule description was vague on whether this probability is only checked once no matter how many neighbors are burning or if its checked per neighbor.
    * **PredatorPrey**: A fish who meets breeding threshold but cannot reproduce due to neighboring cells being occupied will reproduce any step after where an adjacent cell is free.
    * **PredatorPrey**: It is interpreted when the rules state "After eating or moving" that a shark can not do both.
    * **Segregation**: Randomly moving unsatisfied cells will eventually balance out.
    * **Percolation**: Water can only move downward, so only the bottom neighbors interact with the target cell.

## New Features HowTo

### Easy to Add Features

**Model**:
* To create a new type of simulation, the Grid and Cell superclasses contain inherited methods that handle basic grid construction, neighbor assignment, and cell updates.
So all that would need to be done is setting the rules for the simulation in the cell's nextState().
* Certain neighbor arrangements are also easy to construct, since the checkAndSetNeighbor() handles any potential issues with grid edges.

**View**:
 - To add new Grid types, simple create a new subclass of GridView that implements the three abstract methods of updateCell, getNode, and getCellCounts. Then, in the initializeGrid method of ApplicationView, add an extra if statement to check for this type of grid and then call the constructor for the new subclass
 - To add new appearances for Cell states, create a new subclass of CellState. it must take in the parameters for the specific type of state, and then be able to render a Node that reflects the design. In the addCellState method of CellView, add another if statement to check for this type of Cell appearance, and call the constructor to the subclass.
**Controller**:
 - To add new simulation types, simply add a value to the enumerated types in SimType.java and SimStyle.java, specifying the necessary fields, max value, and style information. Then, create an XML file with the desired configuration parameters.
 This XML file must have the root element data type = "simulation", and it must have title and the other required mandatory fields specified in SimType.java. In addition, add an element and children to Styling.xml specifying the desired global style
 configuration for that new simulation. 
 - To add new buttons/functionality to the front end, create additional event handlers in SimulationControl.java and pass them in the constructor to ApplicationView.java.

### Other Features not yet Done

**Model**:
* A way to construct the neighbors of a cell for a triangular cells.
* A Pac-Man simulation where a cell would chase food cells across the grid and ghosts that move randomly that are able to kill the Pac-Man.
Food will respawn throughout the grid and the idea is to see how long Pac-Man can survive.
* More types of neighbor construction for cells (i.e. corner neighbors, row neighbors, etc.).

**View**:
 - A way to have multiple simulations displayed at once
 - An implementation of GridView that allows for triangle shaped cells
 - A way for the View to display parameters the the user can change, which subsequently cause the model to be altered before it is run. These parameters could be simulation-specific, so they would have to be read out of the XML and passed to the ApplicationView as a paremeter
 - The graph does not clear if you start a new simulation, so this issue would need to be addressed so that the graph does not become an absolute mess when you run a new simulation.
**Controller**:
 - To add new cell shapes, such as triangles, add a new method getAllSquares() in GridParser.java and modify getAllGrid() accordingly. Ensure that this parsing method adheres to agreed-upon coordinate conventions for the model and view implementation.
 Additionally, modify SimulationControl.getShapeString() to accommodate the change.
 - To add additional grid panes in the view, convert SimulationControl.myGrid into a Collection, allowing iteration over each grid, and modify the View such that each GridView can be modified accordingly, along with specifying event handlers/buttons for either grid.
 