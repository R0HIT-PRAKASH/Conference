public class Room{

    /**
     * This class represents the rooms that events could
     * take place in. The number of people that can be
     * in the room is represented by capacity. The actual
     * number of the room is represented by roomNumber.
     */

    private int capacity = 2;
    private int roomNumber;

    /**
     * This method constructs a room with capacity 2 and
     * room number roomNumber.
     * @param roomNumber Stores the room number of the room.
     */
    public Room(int roomNumber){
        this.roomNumber = roomNumber;
    }

    /**
     *
     * @return Returns the capacity of the room.
     */
    public int getCapacity(){
        return capacity;
    }
}