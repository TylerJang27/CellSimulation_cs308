#Package Description

This package governs the view part of the simulation, including outputting values of the model,
displaying error messages, and allowing the user to change configuration settings.

This package is divided into the following core classes:
 
 * **ApplicationView**: The main control center for the different views, using a BorderPane to separate the items.
 * **GridView**: The display of all cells' states, divided up into multiple CellViews.
 * **CellView**: The display of each cell, with values and colors of Black, Red, Blue, and Yellow for 0, 1, 2, and 3, repectively.
 * **DashboardView**: The display of the editable user interface, allowing the user to choose configuration files, set the speed, and step, run, or pause the simulation.
 * **ConsoleView**: The display of information and any errors that the user may encounter.