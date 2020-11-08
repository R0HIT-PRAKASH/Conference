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

    public Event(String name, String speakerName, LocalDateTime time, int duration, int roomNumber) {
        this.name = name;
        this.speakerName = speakerName;
        this.time = time;
        this.duration = duration;
        this.roomNumber = roomNumber;
        attendeeSet = new HashSet<Attendee>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSpeakerName() {
        return speakerName;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Set<Attendee> getAttendeeSet() {
        return attendeeSet;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void addAttendee(Attendee attendee) {
        this.attendeeSet.add(attendee);
    }


}
