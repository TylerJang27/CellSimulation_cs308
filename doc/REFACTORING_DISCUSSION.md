# Simulation Refactoring Discussion
### Team Number: 15
### Names:
- Mariusz Derezinski-Choo
- Tyler Jang
- Thomas Quintanilla

## Overview
Mariusz: 

Tyler: 

Thomas: 
- The nextFrame() method in PredatorPreyGrid contained the most amount of lines of any method within the project.
The method will remain, but the functional contents will be refactored into separate private methods.
This will better fit the design of nextFrame() to retain its identity as simply a way to update every cell for outside classes.

- The buildSquareNeighbors() method seems to contain to many levels of nested lines of code.
To combat this, the inner loops and conditional can be refactored into its own method.

- There were an excess of protected instance variables, most of which do not have to be.
For most, the variables can just be set to private, and will fit the scope of the class.
That will reduce the amount of access each class will have to each other.

## Priorities
Mariusz: 

Tyler: 

Thomas: 
- Refactored PredatorPreyGrid.nextFrame() so that the three things that need to be updated (eat/starve, move, and breed) are separated into private methods.

- Fixed buildSquareNeigbors() so that there aren't too many levels of nested cases.

- Changed multiple instance variables from protected to private.

## Impressions
Mariusz: 

Tyler: 

Thomas: Overall, refactoring went well for the Model package. 
There were no major errors that arose from the refactoring process.
The PredatorPreyGrid class is cleaner and the design is more refined.

