# RPS Design

## Overview
A design plan for a scalable game of Rock Paper Scissors.

## Class-Responsibility-Collaborator

### Main
 - Constructs a referee with a list of players to ref over.
 - Calls players' chooseItem() to determine choices.
 - Calls referee's chooseWinner() to determine winner.
 - Calls referee's printStatus() to print status and scores.

### Referee
- Constructor that takes in a file name and uses the file to determine rules
    - public abstract Referee (String fileName, List\<Player> players)
- Method that takes in a List of players and chooses the winner.
    - public abstract void chooseWinner()
 - Keeps track of score.
    - public abstract void resetScore()
 - Adds a new item to the weapon hierarchy.
    - public abstract void addWeapon()


### RPSReferee
 - Constructor that takes in a file name and uses the file to establish its rules determining which items beat other items (for example, using a HashMap implementation).
     - public RPSReferee (String fileName, List\<Player\> players) extends Referee
 - Method that evaluates the winner and prints the winner name.
     - public void chooseWinner ()
 - Returns round status and choices.
     - public String toString() 
 - Keeps track of score.
     - public void resetScore()
 - Adds a new item to the weapon hierarchy.
     - public void addWeapon()

 - Calls on player as necessary for getItem() and getName()


### Player
 - Constructor that takes in name of player to construct player class.
     - public Player (String name)
 - Method that prompts player to pick an item and stores that item in player object.
     - public void chooseItem()
 - Method that returns item chosen for player.
     - public String getItem()
 - Method that returns player name.
     - public String getName()
