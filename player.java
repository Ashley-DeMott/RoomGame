import java.util.Scanner;

public class Player extends GameItem {

    // Creates a Player object
    Player() {
        super('X');
        this.score = 0;
    }

    // Creates a Player object with a specfied point amount
    Player(int startPoints) {
        super('X');
        this.score = startPoints;
    }

    // When destroying this object, closes the input scanner
    protected void finalize() {
        inputScanner.close();
    }

    // Return the number of points the Player has collected
    public int getScore() {
        return this.score;
    }

    // Add points to the Player's score
    public void addPoints(int p) {
        this.score += p;
    }

    // If the Player should leave a path behind
    public boolean hasPath() {
        return this.path;
    }

    // Prompt the user to take a turn
    public boolean turn(Room current) {
        // TODO: Add more turn options

        // Return if the Player took their turn
        return this.move(current);
    }

    // Prompt the user to move the Player to a new position
    // Returns if the user chose to quit
    public boolean move(Room current) {
        // Get a row
        int newRow = getUserInt("Please enter a row: ", 0, current.getHeight() - 1);

        // If invalid, user chose to quit, Player won't take turn
        if (newRow < 0) {
            return false;
        }

        // Get a column
        int newCol = getUserInt("Please enter a column: ", 0, current.getWidth() - 1);

        // If invalid, user chose to quit, Player won't take turn
        if (newCol < 0) {
            return false;
        }

        // Move the Player
        current.movePlayer(this, newRow, newCol);
        return true;
    }

    // Get a string from a user, given a message prompt
    private String getUserInput(String message) {
        // Prompt user with a given message
        System.out.print(message);
        return inputScanner.nextLine();
    }

    // Gets an integer from the user, with an option to quit using an escape
    // character
    private int getUserInt(String message, int min, int max) {
        String escape = "q"; // If user enters this, quit

        String userInput;   // What the user enters
        int userInt = -1;   // The integer the user has inputted, -1 for invalid

        while (true) {
            // Prompt user for an integer
            System.out.print(message);
            userInput = inputScanner.nextLine();

            // Try to get an integer, check for escape case if error thrown
            try {
                userInt = Integer.parseInt(userInput);
            } catch (Exception e) {
                // Check for exit case
                if (userInput.length() == 1 && (userInput.equals(escape)|| userInput.equals(escape.toUpperCase()))) {
                    return userInt; // Returns invalid integer, indicates quitting/skipping turn
                }
                // Else an invalid input was given, will repeat prompt
                userInt = -1;
            }

            // Return int if it fits within the range
            if (min < userInt && userInt < max) {
                return userInt;
            } else {
                // Prompt the integer to choose an integer within the given range
                System.out.print(String.format("Please enter an integer between %s and %d\n", min, max));
            }
        }
    }

    private int score; // The Player's score
    private boolean path = false;   // If the Player drops items as they move

    // The terminal by which the user can input string or integers
    // Making multiple scanners/closing it multiple times causes problems
    private Scanner inputScanner = new Scanner(System.in);
}
