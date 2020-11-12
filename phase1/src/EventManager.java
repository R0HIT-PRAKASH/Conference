import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventManager {

    /**
     * The EventManager class is responsible for handling
     * event-related actions. events is a map that stores
     * the names of events along with its associated event
     * object. rooms is a list of all rooms.
     *
     */

    private HashMap<String, Event> events;
    private List<Room> rooms;

    /**
     * Constructs a new EventManager with an empty map of events
     * and an empty list of rooms.
     */
    public EventManager(){
        events = new HashMap<String, Event>();
        rooms =  new ArrayList<Room>();
    }


    /**
     * Creates a new event object.
     * @param name Refers to the name of the event.
     * @param speakerName Refers to the name of the speaker of this event.
     * @param time Refers to the starting time of the event.
     * @param roomNumber Refers to the room number of this event
     * @return Returns the created event.
     */
    public Event createNewEvent(String name, String speakerName, LocalDateTime time, int roomNumber){
        Event event = new Event(name, speakerName, time, roomNumber);
        return event;
    }


    /**
     * Adds an event to the events map.
     * @param name Refers to the name of the event.
     * @param speakerName Refers to the name of the speaker of this event.
     * @param time Refers to the starting time of the event.
     * @param roomNumber Refers to the room number of this event.
     * @return Returns true if the event is successfully added. Otherwise, it returns false.
     */
    public boolean addEvent(String name, String speakerName, LocalDateTime time, int roomNumber){
        Event event = createNewEvent(name, speakerName, time, roomNumber);
        if (!checkEventIsValid(event)){
            return false;
        }

        events.put(event.getName(), event);
        return true;
    }

    // Don't think we need this method
    private boolean checkEventIsValid(Event event){
        for (String i: events.keySet()){
            if (event.getName().equals(i)){
                return false;
            }
            Event e = events.get(i);
            if (e.getTime().equals(event.getTime()) && e.getRoomNumber() == event.getRoomNumber() ){
                return false;
            }
            if (e.getTime().equals(event.getTime()) && e.getSpeakerName().equals(event.getSpeakerName())){
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a new Speaker user.
     * @param name Refers to the name of the speaker.
     * @param address Refers to the address of the speaker.
     * @param email Refers to the email of the speaker.
     * @param username Refers to the username of the speaker.
     * @param password Refers to the password of the speaker.
     * @return Returns the created speaker.
     */
    public Speaker createNewSpeaker(String name, String address, String email, String username, String password){
        return new Speaker(name, address, email, username, password);
    }


    /**
     * Finds and gets the specified event from the map of events.
     * @param eventName Refers the name of the event.
     * @return Returns the specified event.
     */
    public Event getEvent(String eventName){

        for (String i : events.keySet()){
            if (i.equals(eventName)){
                return events.get(i);
            }
        }

        System.out.println("Event not found.");
        return null;

    }

    /**
     * @return Returns map of events.
     */
    public HashMap<String, Event> getAllEvents(){
        return events;
    }

    public void setAllEvents(HashMap<String, Event> events){
        this.events = events;
    }


    /**
     * Checks if the specified event is full.
     * @param eventName Refers to the name of the event.
     * @return Returns true if event is full. Otherwise, returns false.
     */
    public boolean checkEventFull(String eventName){

        Event event = this.getEvent(eventName);
        if (event == null){
            return true;
        }
        if (event.getAttendeeSet().size() == getRoom(event.getRoomNumber()).getCapacity()){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Adds a room to the list of rooms.
     * @param roomNumber Refers to the room number.
     * @param capacity Refers to the capacity of the room.
     * @return Returns true if adding room was successful. Otherwise, returns false.
     */
    public boolean addRoom(int roomNumber, int capacity){

        Room room = createNewRoom(roomNumber, capacity);
        int i = 0;
        while (i < rooms.size()){
            if (rooms.get(i).getRoomNumber() == room.getRoomNumber()) {
                return false;
            }
            i++;
        }

        rooms.add(room);

        return false;
    }


    /**
     * Creates a new room.
     * @param roomNumber Refers to the room number.
     * @param capacity Refers to the capacity of the room.
     * @return Returns the room object.
     */
    public Room createNewRoom(int roomNumber, int capacity){
        Room room = new Room(roomNumber, capacity);
        return room;
    }


    /**
     * Finds and gets the specified room from the list of rooms.
     * @param roomNumber Refers to the room number.
     * @return Returns the specified room.
     */
    public Room getRoom(int roomNumber){
        for (Room room: rooms){
            if (room.getRoomNumber() == roomNumber){
                return room;
            }
        }

        //Room Not Found
        return null;
    }

}
