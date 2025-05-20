/**
The Main class initializes the game by creating instances of Player, GameView, and GameController. 
It then starts the game by calling the startGame method on the GameController. 
The code is wrapped in a try-catch block to handle potential exceptions during initialization, 
such as file reading errors or invalid room data.
*/

import model.Player;
import view.GameView;
import controller.GameController;
import model.InvalidRoomException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Provide four parameters for the Player constructor
            Player player = new Player("Map.txt", "Item.txt", "Puzzle.txt", "Monsters.txt");
            GameView view = new GameView();
            GameController controller = new GameController(player, view);
            controller.startGame();
        } catch (InvalidRoomException | IOException e) {
            e.printStackTrace();
        }
    }
}