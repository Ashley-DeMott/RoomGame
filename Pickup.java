public class Pickup extends GameItem {

    // Create a Pickup with a given number of points
    Pickup(int p) { this.points = p; }

    // Return the number of points this Pickup is worth
    public int getPoints() { return points; }

    // Default number of points
    int points = 1;

}