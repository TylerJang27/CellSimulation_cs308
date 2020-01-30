# Simulation Design Plan
### Team Number: 15
### Names:
- Mariusz Derezinski-Choo
- Tyler Jang
- Thomas Quintanilla

## Introduction
### Description
We will develop a program that can simulate and visually display a [cellular automaton](https://en.wikipedia.org/wiki/Cellular_automaton) simulation. The user will be able to choose between a variety of simulations, adjust relevant simulation parameters (e.g. number of rows and columns, simulation rate, etc.) and initial states, and then run the simulation for some period of time.

The code design will be divided into three sections: 
 - **Configuration:** The code will read through an XML file and parse the initial settings for the chosen simulation, including what type of simulation to present, the dimensions of the grid used for simulation, and the initial states of each cell within the grid. 
 - **Simulation:** The code will process the specification determined by configuration, in order to regularly update all cells based on pre-defined rules. 
 - **Visualization:** The code will display the results of the simulation to the user, as well as allow the user to interact by being able to control the speed of the simulation and reset it to adjust the configuration parameters.

### Design goals
 - Easy addition of new simulation types.
     - Our program design should allow for new simulations to be introduced with ease. To start with, we will implement the [game of life](http://en.wikipedia.org/wiki/Conway's_Game_of_Life), [percolation](https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/PercolationCA.pdf), [segregation](https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/mccown-schelling-model-segregation), [predator-prey](https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/scott-wator-world), and [fire](https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/shiflet-fire) simulations, but our program design and accompanying documentation should be such that an outside developer would be able to add another simulation without having to drastically change the existing code.
 - Mindful abstraction to allow for modification.
     - Our program design will involve abstraction at the configuration and simulation levels, allowing for straightforward modification in the event of design specification changes.

## Overview
![](https://i.imgur.com/mFTaFLf.jpg)

### Model
The Model will be divided into the following classes:
 - **Grid:** will store all of the cells, with either an internal HashMap or List of Lists implementation, with the ability to update all cells using nextFrame() or retrieve the information about a specific *Cell* as needed using getState().
 - **Cell:** will be an abstract class that holds three basic methods - calculateNextState(), updateState(), and getState(). The Cell class will also be able to access its neighboring cells. There will be five different type of cells that inherit from this abstract cell class - GameOfLifeCell, PercolationCell, SegregateCell, FireCell, and PredatorPreyCell.
### View
The View will be divided into the following classes:
 - **DashboardView:** will extend Node and store all the elements of the Dashboard, including the options to change different settings. The DashboardView will also handle those different event handlers through methods like handleUserInput(), calling *SimulationControl*'s changemodelSetup() as needed.
 - **GridView:** will extend Node and store all the elements of the Grid, including Rectangles to represent the different cells. It will then update cells' appearance using the updateCell() method. Like *Grid*, this can use either a HashMap or List of Lists implementation.
 - **ConsoleView:** will extend Node and display any exceptions or errors encountered in the simulation process using displayError(). It will also display the frame counter to show progress of the simulation and set the frame counter using setFrameNumber().
 - **ApplicationView:** will have the ability to construct a Scene and add it to the Stage, consisting of all the Nodes in *DashboardView*, *GridView*, and *ConsoleView*, in its constructor. For now, this will serve to consolidate the different view areas into a BorderPane.

### Control
The Control will be divided into the following classes:
 - **XMLReader** will hae a single static method readXMLDData() that takes as a parameter a string specifying a file name. readXMLData() will read the XML file according to the formatting we decide upon, and return the Model parameters that are read in.
 - **SimulationControl** will control the interface between the View and the Model. it will have a .next() method that is called by the View to render the next frame of the simulation. This method will call the nextFrame method in *Grid*, then iteratively retrieve the states of each of the cells in the *Grid* and call *GridView*'s updateCell method to update the GUI accordingly. simulationControl will also have a initializeModel method that initializes an instance of *Grid* with the width and height parameters provided. This method is called when the Application is launched. *SimulatioControl* will also have a changeModelSetup method that is called by *DashboardView* in response to user input on the dashboard, this method will change the Model in accordance with the parameters the user has modified.

## User Interface
![The user interface](https://i.imgur.com/KDMndwe.jpg)
The user interface is divided into three key areas: 
 - **Dashboard:** On the left side of the app, the user will be able to set different configuration variables, including (1) choosing from the simulation type; (2) setting the width and height of the simulation (i.e. number of cells); (3) adjusting the speed of the simulation; and (4) starting, stopping, and stepping the simulation.
 - **Console:** The console will display any error reports relating to either improper user input (e.g. non-integer grid dimensions) or invalid XML data. The console will also indicate which frame the user is in. The console will be located underneath the grid. 
 - **Grid:** The main grid screen will display all cells involved and update active cells on screen based on the simulation rules. The grid will continually update frame by frame depending on the speed chosen. The grid's dimensions will update based on the width and height provided by the user.

## Design Details

### Start-up Design Details
 - Upon launching the application, *SimulationControl* will call initializeModel to create an instance of a model. It will also construct an ApplicationView object to render the GUI. SimulationControl is the only class that has to understand the design details of both the View and the Model, since its role is to be the bridge between the two classes, allowing for them to interact while also operating independently of each other.
### Update Design Details
 - Once the initial configuration is set, *SimulationControl* will begin updating the cell states within both grid and gridView by calling next(), which takes a discrete time step in the simulation. At the same time, *DashboardView* will be handling any user input and send any updated parameters to *SimulationControl*, which will then update the model setup using changeModelSetup(). 

## Design Considerations

### Components

The Cell will need to know how to access its neighbors. This could be handled by expanding the signature of calculateNextState() or by having another method, such as setNeighbors() that gives it access to a List of other Cells. For now, we will favor the latter approach because of its benefit to short code and latency later in the program's runtime. setNeighbors() should be called when the Grid is fully constructed.

ApplicationView will consolidate the visual elements of the app using a BorderPane set up. It will receive the Stage as an argument and set its Scene with all the necessary Nodes. It is possible this class will be unnecessary and this can be handled in Main or SimulationControl, but for now we will proceed with this implementation.

### Use Cases

- **Apply the rules to a middle cell:** Upon construction of a Grid instance by SimulationControl, the constructor of Grid will execute some algorithm to store the neighbors of each cell. When a cell is updated, its calculateNextState() method is called, whose implmentation will have conditionals that account for the number of neighbors on all sides, and apply the rules appropriately.
- **Apply the rules to an edge cell:** Upon construction of a Grid instance by SimulationControl, the constructor of Grid will execute some algorithm to store the neighbors of each cell. When a cell is updated, its calculateNextState() method is called, whose implementation will have conditionals that account for the number of neighbors for some sides, and apply the rules appropriately.
- **Move to the next generation:** Upon spawning a new generation, SimulationControl.next() will be called, in turn calling Grid.nextFrame(). This will loop through all the Cells, calling calculateNewState() and then updateState() once all the calculations have been made. After the model has been updated, SimulationControl.next() will call GridView.updateCell() to update the display of all the cells.
- **Set a simulation parameter:** Upon changing a global configuration parameter in the XML file, SimulationControl.changeModelSetup() will be called, which will pause the simulation and create a new Grid of Cells with newly defined configuration parameters/thresholds. Once this construction is complete, GridView will be able to display these changes as the simulation runs.
- **Switch simulations:** Upon selecting a different XML file from the drop down and clicking Update, SimulationControl.changeModelSetup() will be called, which will pause the simulation (Segregation) in order to build a new Grid from the specifications and set initial states for the new simulation (Wator). Once this construction is complete, a new instance of GridView will be created to display the new configuration, and the simulation will be paused with its new settings.

## Team Responsibilities

 * Team Member #1: Mariusz Derezinski-Choo will work on the View. He will be working on creating the individual GUI elements of DashboardView, GridView, and ConsoleView, and combining all of these elements in the Application view to create a single scene that can be displayed in the primary stage. Once the GUI is configured, he will be working on making it interactive by adding the appropriate event handlers that call methods in control to handle user input.
    
 * Team Member #2: Tyler Jang will work on the Control. He will be working on creating the XMLReader to read in configuration parameters, as well as SimulationControl, which will manage the different components and communication between DashboardView and the rest of the Model. This will involve working closely with the open components of the Model as well as the handlers used in DashboardView.

 * Team Member #3: Thomas Quintanilla will work on the Model. He wil be constructing the Grid to hold the cells as well as be responsible for creating the abstract Cell class. This will involve close communication with Control to ensure the Cell components are compatible with the Display.

The team agrees to have a "barebones" implementation by Thursday, January 30.