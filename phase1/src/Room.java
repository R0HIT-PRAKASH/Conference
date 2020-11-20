import java.io.Serializable;

/**
 * This class represents the rooms that events could take place in. The number of people that can be
 * in the room is represented by capacity. The actual number of the room is represented by roomNumber.
 */
public class Room implements Serializable {

    private int capacity;
    private int roomNumber;

    /**
     * This method constructs a room with capacity 2 and room number roomNumber.
     * @param roomNumber Stores the room number of the room.
     */
    public Room(int roomNumber){
        this.roomNumber = roomNumber;
        this.capacity = 2;
    }

    /**
     * This method constructs a room with room number roomNumber and a capacity.
     * @param roomNumber Refers to the room number of the room.
     * @param capacity Refers to the capacity of the room.
     */
    public Room(int roomNumber, int capacity){
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    /**
     * This method gets the capacity of the room.
     * @return Returns the capacity of the room.
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * This method gets the room number.
     * @return Returns the room number of the room.
     */
    public int getRoomNumber(){
        return roomNumber;
    }

    /**
     * This method formats a room object into a string.
     * @return Returns a string representation of the room's attributes.
     */
    public String toString(){
        return "Room " + this.roomNumber + " - Capacity: " + this.capacity;
    }
}

