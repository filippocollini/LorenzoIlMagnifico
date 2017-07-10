Lorenzo il Magnifico

This is the repository for the java implementation of the board game Lorenzo Il Magnifico grom Cranio Creations.

Repository folders:
    src/: contains the java source of the application
    resources/: contain all the files that will be parsed to make the game customizable

Usage:
    Start the server running:
        src/main/java/it/polimi/ingsw/ServerController/Server.java
    Start the client running:
        src/main/java/it/polimi/ingsw/ServerController/LorenzoIlMagnifico

Than will start the Command Line User Interface and the client will make the choice of what type of connection
between Socket and Rmi he will want to choose
N.B. For the Socket connection we only implemented the login so the game is not playable with socket connection

When two players will be connected they will be sended to a room and then the timer will start.
At the end of that the match will start. If other players will try to connect, the timer will restart, until
the number of players reach 4, then the match will start immediatly.

The player can always use commands like "showPossibleAction", "ShowBoard", "ShowPlayerGoods" and "ShowOtherPlayers",
instead of all the other action (that can be seen with the command "ShowPossibleAction").

The whole architecture follows the Model View Controller pattern.
In our case the model is really amalgamated to the controller.

We implemented a Command Pattern to handle client requests, State pattern to handle the fact that a client
could make only a subset of actions if he already made a primary action, Singleton pattern to handle the unicity
of a board.

Communication between client and server is made by interaction with Event and String responses.

Credits:
    Filippo Collini
    Simone Di Benedetto
