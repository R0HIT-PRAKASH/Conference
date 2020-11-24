import java.io.Serializable;

/**
 * This class represents the rooms that events could take place in. The number of people that can be
 * in the room is represented by capacity. The actual number of the room is represented by roomNumber.
 * Rooms can have some number of computers, a projector, tables, and chairs.
 */
public class Room implements Serializable {

    private int capacity;
    private int roomNumber;
    private int computers;
    private boolean projector;
    private int tables;
    private int chairs;

    /**
     * This method constructs a room with capacity 2 and room number roomNumber.
     * @param roomNumber Refers to the room number of the room.
     * @param capacity Refers to the capacity of the room.
     * @param computers Refers to the number of computers in the room.
     * @param projector Refers to whether or not the room contains a projector.
     * @param chairs Refers to the number of chairs in the room.
     * @param tables Refers to the number of tables in the room.
     */
    public Room(int roomNumber, Integer capacity, int computers, boolean projector, int chairs, int tables){
        this.roomNumber = roomNumber;
        if(capacity == null){
            this.capacity = 2;
        }else{
            this.capacity = capacity;
        }
        this.computers = computers;
        this.projector = projector;
        this.tables = tables;
        this.chairs = chairs;
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
     * This method gets the number of computers in the room.
     * @return Returns the number of computers in the room.
     */
    public int getComputers(){
        return this.computers;
    }

    /**
     * This method sets the number of computers in the room.
     * @param computers Refers to the new number of computers in the room.
     */
    public void setComputers(int computers){
        this.computers = computers;
    }

    /**
     * This method returns whether or not there is a projector in the room.
     * @return Returns true if there is a projector in the room and false otherwise.
     */
    public boolean getProjector(){
        return this.projector;
    }

    /**
     * This method sets whether or not there is a projector in the room.
     * @param projector Refers to whether or not a projector will be added or removed from the room.
     */
    public void setProjector(boolean projector){
        this.projector = projector;
    }

    /**
     * This method gets the number of chairs in the room.
     * @return Returns the number of chairs in the room.
     */
    public int getChairs(){
        return this.chairs;
    }

    /**
     * This method sets the number of chairs in the room.
     * @param chairs Refers to the number of chairs that will now be in the room.
     */
    public void setChairs(int chairs){
        this.chairs = chairs;
    }

    /**
     * This method gets the number of tables in the room.
     * @return Returns the number of tables in the room.
     */
    public int getTables(){
        return this.tables;
    }

    /**
     * This method sets the number of tables in the room.
     * @param tables Refers to the new number of tables in the room.
     */
    public void setTables(int tables){
        this.tables = tables;
    }

    /**
     * This method formats a room object into a string.
     * @return Returns a string representation of the room's attributes.
     */
    public String toString(){
        return "Room " + this.roomNumber + " - Capacity: " + this.capacity + ", Equipment: " +
                this.computers + " Computers, " + this.projector + " Projector(s), " + this.chairs +
                " Chairs, " + this.tables + " Tables ";
    }
}

