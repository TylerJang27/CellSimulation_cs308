# Simulation Design Final
### Names
- Mariusz Derezinski-Choo
- Tyler Jang
- Thomas Quintanilla

## Team Roles and Responsibilities

#### Tyler
Tyler worked on the Controller part of the project. He implemented the XML parsers, and designed the XML structure so that the XMLs could be converted to a Simulation and a Style. Tyler worked on the logic behind the methods to start, stop, and step through the simulation. The central components of this work are in SimulationControl.
#### Thomas
Thomas worked on developing the various different types of cells and Grids for the Model. This was accomplished by adding several subclasses to the abstract Cell and Grid classes in the model. The structured use of inheritance allows for different Grid types to be easily instantiated in Controller depending on the file uploaded.
#### Mariusz
Mariusz worked on rendering the various elements of the GUI for the View, and connecting them to the Controller via the use of EventHandlers and ChangeListeners. He constructed generic listeners in the Controller that could be added to the graphics elements once they were rendered. The appearance of the elements were styled with the use of css.

## Design goals

#### What Features are Easy to Add


## High-level Design

#### Core Classes


## Assumptions that Affect the Design

#### Features Affected by Assumptions


## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

