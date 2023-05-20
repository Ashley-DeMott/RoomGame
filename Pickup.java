public class Pickup extends GameItem {

    // Creates a Pickup with a given number of points
    Pickup(int p) {
        super('*');
        this.points = p;
    }

    // Return the number of points this Pickup is worth
    public int getPoints() {
        return points;
    }

    // The number of points this item is worth when picked up
    private int points;
}