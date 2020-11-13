import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Organizer extends User implements Serializable {
    private List<String> attendingEvents;
    private List<String> organizingEvents;

    public Organizer(String name, String address, String email, String userName, String password) {
        super(name, address, email, userName, password);
        this.attendingEvents = new ArrayList<String>();
        this.organizingEvents = new ArrayList<String>();
    }

    public List<String> getOrganizingEvents(){
        return this.organizingEvents;
    }
    public List<String> getAttendingEvents(){
        return this.attendingEvents;
    }
    public void signUpForEvent(Event event){
        this.attendingEvents.add(event.getName());
    }

    public String getUserType(){
        return "organizer";
    }
}
