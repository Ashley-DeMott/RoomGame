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

    // Get the width of the Room
    public int getWidth() {
        return this.width;
    }

    // Get the height of the Room
    public int getHeight() {
        return this.height;
    }

    // Add the given Player to the center of the Room, returns if successful
    // TODO: Add Player to Room connection in direction entered
    public boolean addPlayer(Player player) {
        return replaceItem(this.height / 2, this.width / 2, player);
    }

    // Move the Player around the Room
    public boolean movePlayer(Player player, int newRow, int newCol) {
        // If the new space is overwritable,
        if (spaces[newRow][newCol].isMovable()) {
            // Remove the Player from previous location
            boolean success = removePlayer(player);
            
            // Add the Player to their new location
            return replaceItem(newRow, newCol, player);
        }

        // Cannot move to given position
        return false;
    }

    // Remove the given Player from the Room, returns if successful
    public boolean removePlayer(Player player) {
        
        // The item the Player will be replaced by, mainly for path
        int pointDrop = 1;
        GameItem replace = new Pickup(pointDrop);
        if (player.hasPath() == true && player.getScore() > pointDrop) {
            // Uh oh. protected does not mean private
            replace.symbol = 'O';

            // Proper way, old object is deleted by garbage collected
            //replace = new GameItem('O');
        }
        
        // Empty the Player's previous spot, and replace with dropped coin
        boolean moved = replaceItem(player.getRow(), player.getCol(), replace);
        if (moved)
        {
            player.addPoints(-pointDrop);
        }

        // Return if the move was successful
        return moved;
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
            }
        }
        
        // Populate the spaces (including making the walls)
        // Will use given
        populateSpaces(items);
    }

    // Asserts that the given x,y coordinate is within the bounds of the board
    private boolean assertValidPos(int row, int col) {
        // Location must be on the board
        try {
            assert (row >= 0);
            assert (row < this.height);
            assert (col >= 0);
            assert (col < this.width);
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
                replaceItem(row, 0, new GameItem('+', false));
                replaceItem(row, spaces[row].length - 1, new GameItem('+', false));

                // Fill in the space between
                for (int col = 1; col < spaces[row].length - 1; col++) {
                    replaceItem(row, col, new GameItem('-', false));
                }
            } else {
                // Create the borders for the left and right
                replaceItem(row, 0, new GameItem('|', false));
                replaceItem(row, spaces[row].length - 1, new GameItem('|', false));
            }
        }

        // Then place each GameItem in their space on the board
        items.forEach(item -> {
            replaceItem(item.getRow(), item.getCol(), item);
        });
    }

    // Replaces a BoardItem at the specified location, (row, col)
    private boolean replaceItem(int row, int col, GameItem newItem) {
        // Location must be on the board,
        // And can only replace the item if it isn't permanent (a wall, etc)
        if (assertValidPos(row, col) && spaces[row][col].isMovable()) {
            // Remove the previous item from the board
            GameItem removedItem = removeItem(row, col);

            // If the removed item is a Pickup, and the Player has removed it,
            if (removedItem.getClass().getName() == "Pickup" && newItem.getClass().getName() == "Player") {
                // Add points to the Player, casting GameItems to proper child class
                ((Player) newItem).addPoints(((Pickup) removedItem).getPoints());
            }

            // Update the position of the newItem
            spaces[row][col] = newItem;
            newItem.setPos(row, col);

            return true; // Replaced piece successfully
        }

        // Nothing was moved/replaced
        return false;
    }

    // Removes the item at a specified row, col position from the board,
    // returns the BoardItem that was removed
    private GameItem removeItem(int row, int col) {
        // If position is on the board
        // And the item there can be overridden,
        if (assertValidPos(row, col) && spaces[row][col].isMovable()) {
            // Remove the item from the board and return what was removed

            // Tell the BoardItem it is no longer on the board
            var removeItem = spaces[row][col];
            removeItem.setPos(-1, -1);

            // The board now has an empty BoardItem in its place
            spaces[row][col] = new GameItem();

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

    // Display the Room and the row and column labels
    public void display() {

        for (int row = 0; row < this.height; row++) {
            if (displayLabel) {
                System.out.print(String.format("%s ", row));
            }
            for (int col = 0; col < this.width; col++) {
                System.out.print(String.format("%s ", spaces[row][col].getSymbol()));
            }
            System.out.println();
        }

        // If the label should be displayed,
        if (displayLabel) {

            // display tens
            if (width > 10) {
                System.out.print("  "); // corner

                // Print the tens
                for (int col = 0; col < width; col++) {
                    if (col < 10) {
                        System.out.print("  ");
                    } else {
                        int tens = col / 10;
                        System.out.print(String.format("%s ", tens));
                    }
                }
                System.out.println();
            }

            // display ones
            System.out.print("  "); // corner
            for (int col = 0; col < width; col++) {
                int ones = col;
                if (ones >= 10)
                {
                    ones = ones % 10;
                }
                System.out.print(String.format("%s ", ones));
            }
            System.out.println();
        }

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