# DESIGN_EXERCISE.md

## Classes

### Cell (abstract)
Constructor to take in rules/thresholds and location
 - Sets status and appearance

Methods for:
 - public abstract void calcNewState()
     - Determines and stores what the next state will be
 - public abstract void updateState()
     - Cycles to the next state that has been calculated
 - public abstract int getStatus()
     - Returns an int referring to the current status of the Cell

Individual implementations for SegregationCell, FireCell, GameOfLifeCell, PercolationCell, PredatorPreyCell

### Grid
Constructor that takes in XML file and holds information about the grid of cells

**Brainstorming implementations:**
getCell(Point p):
    return HashMap.get(Point) -> Cell
getNeighbors(Point p)
    return List of Cells
getNeighbors(own point coordinates)


HashMap(Point+j->Cell)
HashMap(Point->List<Cell\>)


Methods for:
 - public void update()
     - Updates all cells in the grid
 - public Cell getCell()
     - returns the cell at the specified Point
Stores cells in a map...

### GUI/Buttons
 -Configuration screen that lets user select size of grid, type of simulation, parameters for rules, etc.

## Organization

### Configuration

### Simulation

### Visualization



Create hashmap with KVP of point to cell
Create hashmap with KVP of cell to neighbor cells

Things to keep in mind:
- How will we display all the Cells
- How will a Cell know about its neighbors
- Cell shouldn't need to know how Grid works internally and vice versa
- Extension possibilities
- How the display will be updated
- How each cell will know its status according to the rules given