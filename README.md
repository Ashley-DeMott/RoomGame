# RoomGame
## Overview

This Java project allows the user to navigate around a room and collect Pickups. Currenlty, the goal is to pick up coins that the Player drops, but because they drop a coin every move, the game is unwinnable. This builds off my previous Kotlin project.

I made this project to learn more about Java and how to implement classes that extend/inherit from eachother. I also learned about Scanners, and how to use them to get the nextInt or the nextLine, depending on what user input is needed.

[Software Demo Video](https://youtu.be/XvJXemo0M7o)

## Development Environment
Visual Studio Code with the Java extension

## Useful Websites
- [W3 Schoools - Java](https://www.w3schools.com/java/default.asp)
- [Stack Overflow - Java User Input](https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java)
- [JavaPoint - Destructors](https://www.javatpoint.com/java-destructor)

## Future Work
- *Make the game winable! Currently lose all coins by moving, no matter what
  - Can add a probability that the coin will drop/fall out of bag
  - Duplicate coin/coin scatter in area Player was standing
  - Coins become a higher point value than dropped, reach a goal number
- Add ability to move between Rooms (just need a way for user to call the function)
- Read and Write Room/map data into a file or a database
- Add existing Pickups to Rooms
- Add other moving GameItems (enemies, moving Pickups, etc)
