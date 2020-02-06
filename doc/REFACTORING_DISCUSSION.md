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
 * SimulationControl.java has generic or multiple handlings of exceptions. These need to be more specific and concise.
 * There are a couple minor problems where the Event Handlers in SimulationControl.java are just declared and thrown. They should be simplified with the concise event syntax.
 * There are a couple magic numbers in Simulation, along with vague declaration of variables.

Thomas: 

## Priorities
Mariusz: 
 - I mainly focused on the refactoring steps that were clean yet effective measures. changing the accessibility of methods/constants made the code a lot cleaner and provided more clear encapsulation between each class. Overall, the View seemed to have the least issues, likely because the JavaFX framework is already so robust that I did not have to write as many lines of code relative to the work I did researching how to render the GUI properly.

Tyler: 
 - I started by tackling the small problems, such as a couple of cases where methods threw multiple exceptions, or constants that weren't declared "private/public static final."
 - I then went to work on XMLParser, a particularly long class. I refactored it to create a GridParser, whose role it was to parse through the initial grid setup in the XML file.
 I will have to add more to it later to handle the additions and edge cases in sprint 2, but I was able to simplify the code dramatically and improve readability. More things are encapsulated now.
 
Thomas: 

## Impressions
Mariusz: 
 - My number one priority at the moment for my refactoring is to add an inheritance hierarchy to both GridView and CellView. This is because we plan on implementing different types of Grids with different shapes (ex. triangular, hexagonal) and different Cells with different appearances (Ex. with images or different shapes/patterns). using inheritance will allow for me to easily leverage these differences in implementation. I did not focus on this aspect of refactoring today, because I felt it was more of a big-picture thing that is more along the lines of the overall design of the program. In hindsight, it would have been better to generate the class hierarchy from the beginning, so that I would not have to backtrack and extract the superclass now.

Tyler: 
 - Reflecting on the process, I was forced to acknowledge the nagging stench that had been bothering me for the past few days of coding. I needed to refactor my longest and most complicated class,
XMLParser.java, adding an additional GridParser.java within it. This prevented me from having to "wade" through the many different edge cases I had, drastically improving organization and readability.

Thomas: 

