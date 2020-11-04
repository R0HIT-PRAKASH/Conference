import java.util.ArrayList;
import java.util.List;

public class Organizer extends User {
    private List<String> attendingEvents;
    private String organizingEvent;

    public Organizer(String name, String address, String email, String userName, String password, String organizingEvent) {
        super(name, address, email, userName, password);
        this.attendingEvents = new ArrayList<String>();
        this.organizingEvent = organizingEvent; // WHAT IS THIS?
    }

    public String getOrganizingEvent(){
        return this.organizingEvent;
    }
    public List<String> getAttendingEvents(){
        return this.attendingEvents;
    }

    public void setOrganizingEvent(String eventName){
        this.organizingEvent = eventName;
    }
    public void signUpForEvent(Event event){
        this.attendingEvents.add(event.getName());
    }
}