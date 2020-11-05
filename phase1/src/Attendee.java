import java.util.ArrayList;
import java.util.List;

public class Attendee extends User {
    private List<String> attendingEvents;

    public Attendee(String name, String address, String email, String userName, String password) {
        super(name, address, email, userName, password);
        this.attendingEvents = new ArrayList<String>();
    }

    public void signUpForEvent(Event event){ //PRIVATE OR PUBLIC?
        this.attendingEvents.add(event.getName());
    }
    public List<String> getAttendingEvents() {
        return this.attendingEvents;
    }

    public String getUserType(){
        return "attendee";
    }
}
