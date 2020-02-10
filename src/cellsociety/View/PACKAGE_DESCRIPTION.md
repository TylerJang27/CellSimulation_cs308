#Package Description

This package governs the view part of the simulation, including outputting values of the model,
displaying error messages, and allowing the user to change configuration settings.

This package is divided into the following core classes:
 
 * **ApplicationView**: The main control center for the different views, using a BorderPane to separate the items.
 * **CellClickedEvent**: a class that wraps a MouseEvent and adds instance variables that track the row and column of the cell that was clicked, which allows for the controller to change the state of the cell that was clicked
 * **CellState**: an abstract class that defines behaviour for a CellState, namely that it has a unique String identifier that describes its state in plain language terms
 * **CellStateConfiguration**: a class that encapsulates all the information necessary to render a CellState. Namely, the Shape, Design, and any design-specific parameters
 * **ColoredCellState**: A class to implement a CellState that is filled in with a solid color.
 * **HexagonGridView**: a class to implement GridView with an array of Hexagon-shaped Cells
 * **ImageCellState**: A display of CellStates that are filled in with an image
 * **RectangleGridView**: A simple Grid that consistes of rectangular Shaped CellView
 * **GridView**: The display of all cells' states, divided up into multiple CellViews that can be updated to different states
 * **CellView**: The display of each cell, with potential shapes of Rectangle and Hexagon, and potential styling of image or color
 * **DashboardView**: The display of the editable user interface, allowing the user to choose configuration files, set the speed, and step, run, or pause the simulation.
 * **ConsoleView**: The display of information and any errors that the user may encounter.