import java.util.ArrayList;
import java.util.List;

public class Game {

    // Creates a Game object and generates the
    // Rooms and the Player to be used
    Game() {
        // Generate the Rooms
        getRooms();

        // Create a Player with a set amount of starting points
        this.player = new Player(5);

        // Add the Player to the first Room
        currentRoom = rooms.get(0);
        currentRoom.addPlayer(player);
    }

    // Play the Game
    public void play() {
        System.out.print("You have a hole in your bag! Don't lose all your coins!");

        // If the user chose to quit
        boolean continueGame = true;

        // Current goal is to keep ahold of all coins
        while (!gameOver() && continueGame) {
            // Display the current Room and the UI
            display();

            // Prompt the user to take a turn
            continueGame = player.turn(currentRoom);
        }

        if (gameOver()) {
            System.out.print("\nGame over! No more coins.");
        } else {
            System.out.print("Bye!");
        }
    }

    // Return if the Game is over
    public boolean gameOver() {
        return player.getScore() <= 0;
    }

    // Change the current Room and move the Player there
    // TODO: Add Player option to change Rooms
    // or indicate Door items and move Player near
    // them to change Rooms
    private void changeRoom(Room newRoom) {
        // DEBUG: Room movement
        // System.out.print("Player moved to new Room\n");

        // Remove Player from current Room
        currentRoom.removePlayer(player);

        // Change the Room
        currentRoom = newRoom;

        // Add Player to the new Room
        currentRoom.addPlayer(player);
    }

    // Display the UI and the current Room
    private void display() {
        // Display the Player's current coin/score count and position
        System.out.print(
                "\nCoins: " + player.getScore() + "\nPosition: (" + player.getRow() + ", " + player.getCol() + ")\n");

        // Display the current Room
        currentRoom.display();
    }

    // Generate the Rooms used in this Game
    // TODO: Get from a text file
    // TODO: Add pickups/items to each Room
    private void getRooms() {
        rooms.add(new Room());
        rooms.add(new Room(25, 10));
        rooms.add(new Room(30, 20));

    }

    private List<Room> rooms = new ArrayList<Room>(); // The list of Rooms the Game uses
    private Room currentRoom; // The current Room the Game is focused on
    private Player player; // The player of the Game
}