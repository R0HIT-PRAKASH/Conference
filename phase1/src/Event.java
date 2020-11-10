import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Event {

    private String name;
    private String speakerName;
    private LocalDateTime time;
    private int duration;
    private int roomNumber;
    private Set<Attendee> attendeeSet;

    /**
     * This constructs an event
     * @param name The name of the event
     * @param speakerName The speaker at the event
     * @param time The event time
     * @param duration The event duration
     * @param roomNumber The event room number
     */

    public Event(String name, String speakerName, LocalDateTime time, int duration, int roomNumber) {
        this.name = name;
        this.speakerName = speakerName;
        this.time = time;
        this.duration = duration;
        this.roomNumber = roomNumber;
        attendeeSet = new HashSet<Attendee>();
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
     * @return Set<Attendee> attendeeSet
     */
    public Set<Attendee> getAttendeeSet() {
        return attendeeSet;
    }


    /**
     * This method is a getter for the set of attendees
     * @return Set<Attendee> attendeeSet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is a setter for the speaker
     * @param speakerName
     */
    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    /**
     * This method is a setter for the time
     * @param time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * This method is a setter for the duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * This method is a setter for the roomNumber
     * @param roomNumber
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * This method adds an attendee
     * @param Attendee attendee
     */
    public void addAttendee(Attendee attendee) {
        this.attendeeSet.add(attendee);
    }


}
