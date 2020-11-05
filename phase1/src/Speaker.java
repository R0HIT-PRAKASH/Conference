import java.util.ArrayList;
import java.util.List;

public class Speaker extends User {
    private List<String> speakingEvents;

    public Speaker(String name, String address, String email, String userName, String password) {
        super(name, address, email, userName, password);
        this.speakingEvents = new ArrayList<String>();
    }

    public String getUserType() {
        return "speaker";
    }

    public List<String> getSpeakingEvents() {
        return this.speakingEvents;
    }

    public void addSpeakingEvent(String eventName) {
        speakingEvents.add(eventName);
    }
}