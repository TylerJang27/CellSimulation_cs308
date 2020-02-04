#Package Description

This package handles the construction 

This package is divided into two superclasses
 * **Grid**: The main class to create the data structure used to store all cells. Comes with two default methods to create list of neighbors for each cell, as well a basic update for each cell state.
 * **Cell**: An abstract class that all simulation cells inherit from.
 
 This package also comes with 5 different cell and grid subclasses to represent each simulation. Each grid/cell simulation pair has their own constructors and rules to update each cell by, which can be found in the calculateNextState() method in the specific cell class and nextFrame() in specific grid class.