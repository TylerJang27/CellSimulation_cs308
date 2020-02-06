# Simulation Refactoring Discussion
### Team Number: 15
### Names:
- Mariusz Derezinski-Choo
- Tyler Jang
- Thomas Quintanilla

## Overview
Mariusz: 
 - Most of the problems that the analysis revealed in my View code was with the use of magic numbers. In developing the View, I made sure to declare important numerical constants such as the size of the elements, and use the resourse file to define strings. However, smaller values such as the margins and padding on the grid/dashboard were left behind as magic values that I was able to correct today. 
Tyler: 

Thomas: 

## Priorities
Mariusz: 
 - I mainly focused on the refactoring steps that were clean yet effective measures. changing the accessibility of methods/constants made the code a lot cleaner and provided more clear encapsulation between each class. Overall, the View seemed to have the least issues, likely because the JavaFX framework is already so robust that I did not have to write as many lines of code relative to the work I did researching how to render the GUI properly.
Tyler: 

Thomas: 

## Impressions
Mariusz: 
 - My number one priority at the moment for my refactoring is to add an inheritance hierarchy to both GridView and CellView. This is because we plan on implementing different types of Grids with different shapes (ex. triangular, hexagonal) and different Cells with different appearances (Ex. with images or different shapes/patterns). using inheritance will allow for me to easily leverage these differences in implementation. I did not focus on this aspect of refactoring today, because I felt it was more of a big-picture thing that is more along the lines of the overall design of the program. In hindsight, it would have been better to generate the class hierarchy from the beginning, so that I would not have to backtrack and extract the superclass now.
Tyler: 

Thomas: 

