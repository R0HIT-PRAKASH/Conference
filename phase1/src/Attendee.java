import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all of the characteristics and actions of an attendee. They
 * have a name, address, email, username, and password. Attendees can attend events
 * and sign up for events.
 */
public class Attendee extends User {

    private List<String> attendingEvents;

    /**
     * This method constructs a new attendee object with an empty list of attendingEvents.
     * @param name Refers to the name of the attendee.
     * @param address Refers to the address of the attendee.
     * @param email Refers to the email of the attendee.
     * @param userName Refers to the username of the attendee.
     * @param password Refers to the password of the attendee.
     */
    public Attendee(String name, String address, String email, String userName, String password) {
        super(name, address, email, userName, password);
        this.attendingEvents = new ArrayList<String>();
    }

    /**
     * Adds an event to the list of events the attendee will attend.
     * @param event Refers to the event that will be added to the list of events that will be attended.
     */
    public void signUpForEvent(Event event){ //PRIVATE OR PUBLIC?
        this.attendingEvents.add(event.getName());
    }

    /**
     * This method gets the list of events the attendee will attend.
     * @return Refers to the list of events the attendee will attend.
     */
    public List<String> getAttendingEvents() {
        return this.attendingEvents;
    }

    /**
     * This method gets the type of user this person is.
     * @return Returns a string representation of "attendee".
     */
    public String getUserType(){
        return "attendee";
    }
}


