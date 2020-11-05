import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private String name;
    private Speaker speaker;
    private LocalDateTime time;
    private int duration;
    private int roomNumber;
    private List<Attendee> attendeeList;

    public Event(String name, Speaker speaker, LocalDateTime time, int duration, int roomNumber) {
        this.name = name;
        this.speaker = speaker;
        this.time = time;
        this.duration = duration;
        this.roomNumber = roomNumber;
        attendeeList = new ArrayList<Attendee>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public Speaker getSpeaker() {
        return speaker;
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

    public List<Attendee> getAttendeeList() {
        return attendeeList;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
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
        this.attendeeList.add(attendee);
    }


}
