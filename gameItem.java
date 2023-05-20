public class GameItem {
    // Creates a GameItme with no symbol and default movabilty
    public GameItem() {
        createGameItem(' ', this.movable);
    }

    // Creates a GameItme with the given symbol and default movabilty
    public GameItem(char s) {
        createGameItem(s, this.movable);
    }

    // Creates a GameItem given a symbol and if the item is movable
    // Some objects like walls cannot be moved/overwritten
    public GameItem(char s, boolean m) {
        createGameItem(s, m);
    }

    // Called by the constructors to set initial values
    private void createGameItem(char c, boolean m) {
        this.symbol = c;
        this.movable = m;
    }

    // Set the GameItem's position to the given row and column
    public void setPos(int r, int c) {
        this.row = r;
        this.col = c;
    }

    // Returns the GameItem's current row
    public int getRow() {
        return row;
    }

    // Returns the GameItem's current column
    public int getCol() {
        return col;
    }

    // Returns if the GameItem can move/be replaced
    public boolean isMovable() {
        return movable;
    }

    // Returns the GameItem's display symbol
    public char getSymbol() {
        return symbol;
    }

    protected char symbol = ' '; // How the GameItem is displayed
    protected boolean movable = true; // If the item can be moved/written over
    protected int row = 0; // The GameItem's current row/x position
    protected int col = 0; // The GameItem's current column/y position
}
