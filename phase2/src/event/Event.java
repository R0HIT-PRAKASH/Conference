package event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import user.User;

/**
 * This class represents an Event object. An Event can have a name, speaker, time, duration(in hours), room,
 * capacity, and set of attendees attending. The event also contains how many computers, chairs, tables, and
 * whether or not a projector is required for the event to run.
 */
public abstract class Event implements Comparable<Event>, Serializable {

    private String name;
    //private String speakerName;
    private LocalDateTime time;
    private int duration;
    private int roomNumber;
    private int capacity;
    private int requiredComputers;
    private boolean requiredProjector;
    private int requiredChairs;
    private int requiredTables;
    private Set<String> attendeeSet;
    private List<String> creators;
    private boolean vipEvent;

    /**
     * This constructs an event
     * @param name The name of the event
     * @param time The event time
     * @param duration The event duration(in hours).
     * @param roomNumber The event room number
     * @param capacity This refers to the capacity of the event.
     * @param requiredComputers Refers to the amount of computers required for the event.
     * @param requiredProjector Refers to whether or not a projector is required for the event.
     * @param requiredChairs Refers to number of chairs required for the event.
     * @param requiredTables Refers to the number of tables required for the event.
     * @param creators Refers to the list of usernames of users that created the event.
     * @param vipEvent Refers to whether or not this event is limited to VIP's only
     */
    public Event(String name,  LocalDateTime time, Integer duration, int roomNumber, int capacity,
                 int requiredComputers, boolean requiredProjector, int requiredChairs, int requiredTables,
                 List<String> creators, boolean vipEvent) {
        this.name = name;
        //this.speakerName = speakerName;
        this.time = time;
        this.duration = duration;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.requiredComputers = requiredComputers;
        this.requiredProjector = requiredProjector;
        this.requiredChairs = requiredChairs;
        this.requiredTables = requiredTables;
        attendeeSet = new HashSet<String>();
        this.creators = creators;
        this.vipEvent = vipEvent;
    }

    /**
     * This method is a getter for name
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * This method is a getter for event time
     * @return LocalDateTime time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * This method is a getter for duration
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * This method is a getter for roomNumber
     * @return roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * This method is a getter for the set of attendees
     * @return attendeeSet
     */
    public Set<String> getAttendeeSet() {
        return attendeeSet;
    }


    /**
     * This method is a setter for the event name
     * @param name The Event Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is a setter for the time
     * @param time The Event Time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * This method is a setter for the duration
     * @param duration The Event Duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * This method is a setter for the roomNumber
     * @param roomNumber The Event Room Number
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * This method sets the capacity of the event.
     * @param capacity Refers to the new capacity of the event.
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * This method gets the capacity of the event.
     * @return Returns the capacity of the event.
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * This method gets the required number of computers in the event.
     * @return Returns the required number of computers in the event.
     */
    public int getRequiredComputers(){
        return this.requiredComputers;
    }

    /**
     * This method sets the number of computers in the room.
     * @param requiredComputers Refers to the new number of required computers for the event.
     */
    public void setRequiredComputers(int requiredComputers){
        this.requiredComputers = requiredComputers;
    }

    /**
     * This method gets whether or not a projector is required for the event.
     * @return Returns true if a projector is required and false otherwise.
     */
    public boolean getRequiredProjector(){
        return this.requiredProjector;
    }

    /**
     * This method sets whether or not a projector is required for the event.
     * @param requiredProjector Refers to whether or not the event will now require a projector.
     */
    public void setRequiredProjector(boolean requiredProjector){
        this.requiredProjector = requiredProjector;
    }

    /**
     * This method gets the number of chairs required for the event.
     * @return Returns the required number of chairs in the event.
     */
    public int getRequiredChairs(){
        return this.requiredChairs;
    }

    /**
     * This method sets the number of chairs required for the event.
     * @param requiredChairs Refers to the new number of required chairs for the event.
     */
    public void setRequiredChairs(int requiredChairs){
        this.requiredChairs = requiredChairs;
    }

    /**
     * This method gets the required number of tables required for the event.
     * @return Returns the required number of tables required for the event.
     */
    public int getRequiredTables(){
        return this.requiredTables;
    }

    /**
     * This method sets the number of tables required for the room.
     * @param requiredTables Refers to the new number of required tables for the event.
     */
    public void setRequiredTables(int requiredTables){
        this.requiredTables = requiredTables;
    }

    /**
     * This method gets the list of creator usernames that were responsible for creating the event.
     * @return Returns the list of creators.
     */
    public List<String> getCreators(){
        return this.creators;
    }

    /**
     * This method adds an attendee
     * @param attendee The attendee to be added
     */
    public void addAttendee(User attendee) {
        this.attendeeSet.add(attendee.getUsername());
    }

    /**
     * This method gets whether or not the event is VIP exclusive.
     * @return Refers to whether or not this event is exclusive to VIP's only.
     */
    public boolean getVipEvent(){ return this.vipEvent; }

    /**
     * This methods sets whether or not the event is VIP exclusive.
     * @param vipEvent The status of whether or not the event is VIP exclusive.
     */
    public void setVipEvent(boolean vipEvent){ this.vipEvent = vipEvent;}

    /**
     * CompareTo
     * @param e The Event being compared to
     * @return Returns the compareTo result based on the time of the event
     */
    public int compareTo(Event e) {
        return this.getTime().compareTo(e.getTime());
    }

    public int getSize() {
        return attendeeSet.size();
    }
}
