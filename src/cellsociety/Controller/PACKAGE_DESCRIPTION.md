#Package Description

This package governs the controller part of the simulation, including reading from the XML files,
setting up the model, and pipelining relevant information to the view.

This package is divided into the following core classes:
 
 * **SimulationControl**: The main control center for managing interactions between the model and the view.
 * **Simulation**: A class returned by XMLParser that stores configuration information about the simulation.
 * **SimType**: An enumerated type for the different simulation types, and a property of Simulation.
 * **XMLParser**: A class to read in information from an XML file and return a Simulation instance if no errors occur.
    * **GridParser**: A class to read in grid configuration from an XML file and return a Map of grid values. Functional for square grid cells and hexagons.
 * **XMLException**: An exception type for XML reading, for cases in which the XML file cannot be found or is formatted incorrectly.
 * **WriteXMLFile**: A class to generate an XML file based on the current configuration parameters and current grid cell states. 