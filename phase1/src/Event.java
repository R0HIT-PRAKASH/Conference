import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents an Event object. An Event can have a name, speaker, time, duration(in hours), room,
 * and set of attendees attending.
 */
public class Event implements Comparable<Event>, Serializable {

    private String name;
    private String speakerName;
    private LocalDateTime time;
    private int duration;
    private int roomNumber;
    private Set<User> attendeeSet;

    /**
     * This constructs an event
     * @param name The name of the event
     * @param speakerName The speaker at the event
     * @param time The event time
     * @param duration The event duration(in hours).
     * @param roomNumber The event room number
     */

    public Event(String name, String speakerName, LocalDateTime time, int duration, int roomNumber) {
        this.name = name;
        this.speakerName = speakerName;
        this.time = time;
        this.duration = duration;
        this.roomNumber = roomNumber;
        attendeeSet = new HashSet<User>();
    }

    public Event(String name, String speakerName, LocalDateTime time, int roomNumber) {
        this.name = name;
        this.speakerName = speakerName;
        this.time = time;
        this.duration = 1;
        this.roomNumber = roomNumber;
        attendeeSet = new HashSet<User>();
    }

    /**
     * This method is a getter for name
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * This method is a getter for name
     * @return String name
     */
    public String getSpeakerName() {
        return speakerName;
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
    public Set<User> getAttendeeSet() {
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
     * This method is a setter for the speaker
     * @param speakerName The Speaker Name
     */
    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
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
     * This method adds an attendee
     * @param attendee The attendee to be added
     */
    public void addAttendee(User attendee) {
        this.attendeeSet.add(attendee);
    }

    /**
     * CompareTo
     * @param e The Event being compared to
     * @return Returns the compareTo result based on the time of the event
     */
    public int compareTo(Event e) {
        return this.getTime().compareTo(e.getTime());
    }

    /**
     * This method formats an event object into a string.
     * @return Returns a string representation of the attributes of an event.
     */
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String date = getTime().format(formatter);

        return "Title: " + getName() + "| Time: " + date + "| Speaker: " + getSpeakerName() + "| Duration: "
                + getDuration() + " hour/s| Room: " + getRoomNumber();
    }

}
