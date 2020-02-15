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

In addition, we wanted the UI to be easily configurable, so GridPanes were used to promote scalability on the front-end.

In addition, new language settings can be added by providing a different ResourceBundle in Main, rather than using English.properties.

More specific feature customization can be found below in "Easy to Add Features."

## High-level Design

### Core Classes

**Model**:

**View**:

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
 - 
 - 

## New Features HowTo

### Easy to Add Features

**Model**:

**View**:

**Controller**:
 - To add new simulation types, simply add a value to the enumerated types in SimType.java and SimStyle.java, specifying the necessary fields, max value, and style information. Then, create an XML file with the desired configuration parameters.
 This XML file must have the root element data type = "simulation", and it must have title and the other required mandatory fields specified in SimType.java. In addition, add an element and children to Styling.xml specifying the desired global style
 configuration for that new simulation. 
 - To add new buttons/functionality to the front end, create additional event handlers in SimulationControl.java and pass them in the constructor to ApplicationView.java.

### Other Features not yet Done

**Model**:

**View**:

**Controller**:
 - To add new cell shapes, such as triangles, add a new method getAllSquares() in GridParser.java and modify getAllGrid() accordingly. Ensure that this parsing method adheres to agreed-upon coordinate conventions for the model and view implementation.
 Additionally, modify SimulationControl.getShapeString() to accommodate the change.
 - To add additional grid panes in the view, convert SimulationControl.myGrid into a Collection, allowing iteration over each grid, and modify the View such that each GridView can be modified accordingly, along with specifying event handlers/buttons for either grid.
 