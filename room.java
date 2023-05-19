import java.util.ArrayList;
import java.util.List;

public class Room {

    // Since Java doesn't support default parameters,
    // have the constructors use a private function to
    // do things like set the width/height, etc
    public Room() {
        // Create Room with a pre-determined width and height
        createRoom(10, 10);
    }

    // Create a Room of a given width/height
    public Room(int w, int h) {
        createRoom(w, h);
    }
    // TODO: May remove above constructors, require Room to be given width, height,
    // and gameItems

    // Create a room of a given width/height,
    // and with the given GameItems
    public Room(int w, int h, List<GameItem> items) {
        createRoom(w, h);
    }

    // Given a Room's width and height, generates a number of
    // randomly placed GameItems, then proceeds with creating the Room
    private void createRoom(int w, int h) {
        // **TODO: Move item generation to another functions
        // TODO: Generate items for a room of given width/height
        List<GameItem> addedItems = new ArrayList<GameItem>();

        // Add items since they were not given
        // TODO: Assert within game bounds/not on player spot (which can either A.
        // default to center or B. (more complex) be the entry from another room (so
        // middle of each wall))
        addedItems.add(new GameItem());

        // Move to the more complex implementation of createRoom
        createRoom(w, h, addedItems);
    }

    // TODO: May remove if other constructors removed
    // Given a Room's width, height, and included items,
    // creates and populates the Room's spaces
    private void createRoom(int w, int h, List<GameItem> items) {
        // Save the local variables to the Room's variables
        this.width = w;
        this.height = h;

        // Create a 2D array of empty GameItems to fill the spaces
        spaces = new GameItem[this.height][this.width];
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                spaces[row][col] = new GameItem(' ', true);
                //System.out.print(spaces[row][col].getSymbol());
            }
        }

        // Does ForEach work with 2D arrays?
        /*
        spaces.forEach(row -> 
            { row.forEach(item -> 
                { System.out.print(item.getSymbol());
                });
        });*/

        // Populate the spaces (including making the walls)
        // Will use given
        populateSpaces(items);
    }

    // Asserts that the given x,y coordinate is within the bounds of the board
    private boolean assertValidPos(int x, int y) {
        // Location must be on the board
        try {
            assert (y >= 0);
            assert (y < this.height);
            assert (x >= 0);
            assert (x < this.width);
        } catch (AssertionError e) {
            System.out.print("Invalid board position");
            return false; // Did not pass all verifications
        }
        return true; // passed all verifications
    }

    // Creates a 2D array of spaces that can each hold a single GameItem
    private void populateSpaces(List<GameItem> items) {
        // First, will place Room edges

        // For every row in the board,
        for (int row = 0; row < spaces.length; row++) {
            // Create the top and bottom edges of the board
            if (row == 0 || row == spaces.length - 1) {
                // The corners of the board (TODO: can make a const/static variable for visual
                // representation)
                replaceItem(0, row, new GameItem('+', false));
                replaceItem(spaces[row].length - 1, row, new GameItem('+', false));

                // Fill in the space between
                for (int col = 1; col < spaces[row].length - 1; col++) {
                    replaceItem(col, row, new GameItem('-', false));
                }
            } else {
                // Create the borders for the left and right
                replaceItem(0, row, new GameItem('|', false));
                replaceItem(spaces[row].length - 1, row, new GameItem('|', false));
            }
        }

        // Then place each GameItem in their space on the board
        items.forEach(item -> {
            replaceItem(item.getCol(), item.getRow(), item);
        });
    }

    // Replaces a BoardItem at the specified location, where yPos is row, and xPos
    // is column
    private boolean replaceItem(int xPos, int yPos, GameItem newItem) {
        // Location must be on the board,
        // And can only replace the item if it isn't permanent (a wall, etc)
        if (assertValidPos(xPos, yPos) && spaces[yPos][xPos].isMovable()) {
            // Remove the previous item from the board
            GameItem removedItem = removeItem(xPos, yPos);

            // If the removed item is a Pickup, and the Player has removed it,
            if (removedItem.getClass().getName() == "Pickup" && newItem.getClass().getName() == "Player") {
                // Add points to the Player, casting GameItems to proper child class
                ((Player) newItem).addPoints(((Pickup) removedItem).getPoints());
            }

            // Update the position of the newItem
            spaces[yPos][xPos] = newItem;
            newItem.setPos(xPos, yPos);

            return true; // Replaced piece successfully
        }

        // Nothing was moved/replaced
        return false;
    }

    // Removes the item at a specified x,y position from the board,
    // returns the BoardItem that was removed
    private GameItem removeItem(int xPos, int yPos) {
        // If position is on the board
        // And the item there can be overridden,
        if (assertValidPos(xPos, yPos) && spaces[yPos][xPos].isMovable()) {
            // Remove the item from the board and return what was removed

            // Tell the BoardItem it is no longer on the board
            var removeItem = spaces[yPos][xPos];
            removeItem.setPos(-1, -1);

            // The board now has an empty BoardItem in its place
            spaces[yPos][xPos] = new GameItem();

            return removeItem;
        }

        // Return an empty BoardItem, nothing was removed
        return new GameItem();
    }

    // Removes a specified BoardItem from the board,
    // returns an empty BoardItem if nothing was removed
    private GameItem removeItem(GameItem item) {
        return removeItem(item.getRow(), item.getCol());
    }

    public void display() {
        // TODO: Use display label, or transfer to a wasd movement?
        /*
         * if (displayLabel) {
         * for(int row = 0; row < spaces.length; row++) {
         * print(rowLabel);
         * rowLabel++;
         * print(" ");
         * }
         * }
         */
        
        
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                System.out.print(spaces[row][col].getSymbol());
            }
            System.out.println();
        }        
        System.out.println();

        /*
        for (int row = 0; row < spaces.length; row++) {
            for (int col = 0; col < spaces.length; col++) {
                spaces[row][col].display();
            }

        }*/
        /*
         * // Only display label if true
         * if (displayLabel) {
         * print("  "); // corner
         * var rowMin: Int = minOf(9, width);
         * for (i in 0..rowMin) print("$i ");
         * 
         * // If the width goes into the double digits,
         * if (width > 10) {
         * for (i in 10 until width) {
         * // Print the first digit
         * val tens = i / 10;
         * print("$tens ");
         * }
         * 
         * println() // Move down a line
         * 
         * // Spacing
         * for (i in 0..10) print("  ") {
         * for (i in 10 until width) {
         * // Print the next digit
         * val remainder = i % 10;
         * print("$remainder ");
         * }
         * }
         * }
         */

        System.out.println();
    }

    // TODO: use enum
    // Add connections for the Room (up, down, left, right)
    public void addConnections() {

    }

    static protected boolean displayLabel = true;
    protected GameItem[][] spaces;
    protected List<Room> connections = new ArrayList<Room>();
    protected int width;
    protected int height;

}