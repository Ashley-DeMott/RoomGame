import java.util.ArrayList;
import java.util.List;

class RoomGame {
    public static void main(String[] args) {
        Room test = new Room();
        test.display();

        Room room1 = new Room(25, 10);
        room1.display();

        // getRooms();
    }

    private static void getRooms() {
        // rooms.add(new Room());

    }

    private static List<Room> rooms = new ArrayList<Room>();
}