# Simulation Refactoring Discussion
### Team Number: 15
### Names:
- Mariusz Derezinski-Choo
- Tyler Jang
- Thomas Quintanilla

## Overview
Mariusz: 

Tyler:
 * SimulationControl.java has generic or multiple handlings of exceptions. These need to be more specific and concise.
 * There are a couple minor problems where the Event Handlers in SimulationControl.java are just declared and thrown. They should be simplified with the concise event syntax.
 * There are a couple magic numbers in Simulation, along with vague declaration of variables.

Thomas: 

## Priorities
Mariusz: 

Tyler: 
 * I started by tackling the small problems, such as a couple of cases where methods threw multiple exceptions, or constants that weren't declared "private/public static final."
 * I then went to work on XMLParser, a particularly long class. I refactored it to create a GridParser, whose role it was to parse through the initial grid setup in the XML file.
 I will have to add more to it later to handle the additions and edge cases in sprint 2, but I was able to simplify the code dramatically and improve readability. More things are encapsulated now.
 
Thomas: 

## Impressions
Mariusz: 

Tyler: 

Reflecting on the process, I was forced to acknowledge the nagging stench that had been bothering me for the past few days of coding. I needed to refactor my longest and most complicated class,
XMLParser.java, adding an additional GridParser.java within it. This prevented me from having to "wade" through the many different edge cases I had, drastically improving organization and readability.

Thomas: 

