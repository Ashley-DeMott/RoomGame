public class GameItem {
    public GameItem() {
        createGameItem(' ', false);
    }

    public GameItem(char symbol, boolean movable) {
        createGameItem(symbol, movable);
    }

    private void createGameItem(char c, boolean m) {
        this.symbol = c;
        this.movable = m;
    }

    public void setPos(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMovable() {
        return movable;
    }

    public char getSymbol() {
        return symbol;
    }

    public void display() {
    }

    private char symbol = ' ';
    private boolean movable = false;
    private int row = 0;
    private int col = 0;
}
