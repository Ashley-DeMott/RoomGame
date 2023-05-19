public class Player extends GameItem {

    Player() {
        this.score = 0;
    }

    public void addPoints(int p) {
        this.score += p;
    }

    private int score;
}
