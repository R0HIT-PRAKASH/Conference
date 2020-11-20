import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the characteristics and actions of an organizer. They have the
 * same characteristics as a user, except they have a list of events they will attend
 * and a list of events they will organize.
 */
public class Organizer extends User implements Serializable {

    private List<String> attendingEvents;
    private List<String> organizingEvents;

    /**
     * Constructs a new organizer object with empty lists of attendingEvents and organizingEvents.
     * @param name Refers to the name of the organizer.
     * @param address Refers to the address of the organizer.
     * @param email Refers to the email of the organizer.
     * @param userName Refers to the username of the organizer.
     * @param password Refers to the password of the organizer.
     */
    public Organizer(String name, String address, String email, String userName, String password) {
        super(name, address, email, userName, password);
        this.attendingEvents = new ArrayList<String>();
        this.organizingEvents = new ArrayList<String>();
    }

    /**
     * This method gets the list of events the organizer is organizing.
     * @return Returns the list of events the organizer is organizing.
     */
    public List<String> getOrganizingEvents(){
        return this.organizingEvents;
    }

    /**
     * This method gets the list of events the organizer will attend.
     * @return Returns the list of events the organizer will attend.
     */
    public List<String> getAttendingEvents(){
        return this.attendingEvents;
    }

    /**
     * This method adds an event to the list of events the organizer will attend.
     * @param event Refers to the event the organizer will now attend.
     */
    public void signUpForEvent(Event event){
        this.attendingEvents.add(event.getName());
    }

    /**
     * This method adds an event to the list of events the organizer has created.
     * @param event Refers to the event the organizer has created.
     */
    public void createdEvent(Event event){ this.organizingEvents.add(event.getName());}

    /**
     * This method returns a string representation of the type of user this person is.
     * @return Returns "organizer".
     */
    public String getUserType(){
        return "organizer";
    }
}
