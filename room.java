abstract class Room {
public Room(int w, int h) {
        this.width = w;
        this.height = h;
        items.add(new GameItem());
    }

public display() {

}

protected ArrayList<GameItem> items = new ArrayList<GameItem>();
protected int width;
protected int height;

}