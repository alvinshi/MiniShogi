# MiniShogi: 2017 box-take-home
### By Alvin Shi

## Correctness
The implementation passes all the test cases provided.

To verify this, run the JUnit test provided in the `src/test/java` folder.
The test case directly compares the output of the file mode with the corresponding reference solution.

## To Run
Execute the main method in Main.java with appropriate arguments.

## Project Structure
The project is separated into four packages.

`minishogi.core` and `minishogi.piece` contain the core logic of this particular MiniShogi implementation. 

`minishogi.game` contains the implementation of user interface. In this particular case, a terminal-based interface is implemented. All user interfaces only depend on the MiniShogi interface. This effectively separates the back-end from the front-end.

`minishogi.utils` contains some useful helper functions and data structures.

Under  `config/game`, the `game.init` file contains the data which determines how a new game should be initialized. Java reflection is used to construct new objects based on the data contained in this file. To create a new piece on the board, simply use the format `<Piece Class Name>` `<Owner Name(uppercase)>` `<Address>`

`resources/test_cases ` contains all test cases provided to verify the correctness of the project.

## Design Decisions
* The terminal-based user interface is `decoupled` from the MiniShogi core implementation. This allows the backend or the frontend to be modified and changed without having to worry about the other.
* `Observer pattern` is used for the core to communicate with the user interface. This not only enhances decoupling but also allows users to register multiple listeners to the core, making a cross-platform multi-player game possible.
* `Template method pattern` is used when implementing the different pieces. All concrete pieces extend from the AbstractPiece class and implement the Piece interface. The design facilitates code-reuse across different pieces and makes the component extensible.
* The core is structured in a way to reduce the `representational gap` as much as possible. Almost all concrete minishogi concepts are represented by an object in the core implementation.

## Design Tradeoff
The idea of immutability was considered during the design process. The idea is that by making all objects (board, player, piece) in the game immutable, the game is modeled as moving from one state to another. Several advantages of this design are:
1. It makes it possible to incorporate the idea of History in the game.
2. It makes it easier to implement check and checkMate.

However, the idea was not adopted because:
1. Immutable design demands more computing power and memory. It is not justified when History is not a required feature.
2. At least for me personally, it is more intuitive to model change in the model as the change of information in each object than as the transition from one state to another. The former modeling reduces the representational gap and possibly enhances the readability of the code.
   
## Possible improvement 
In retrospect, the game should probably have a Move class which models a move (either a move or a drop). With that, passing move information around and storing them will be easier and more intuitive.

## Notes
a. To turn MiniShogi into a full-scale shogi takes 4 steps. 1.Change the value of the BOARD_SIZE constant in the Board class. 2.Modify the game.init file to initialize the pieces correctly. 3. Change the regex format string in Board to allow new possible addresses. 4.Change the name of the project from MiniShogi to Shogi.

b. The factory method `produce` in AbstractPiece is only there for the purpose of testing the game under file mode.