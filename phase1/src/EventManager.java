import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventManager {

    private List<Event> events;
    private Map<String, Room> rooms;

    public EventManager(){
        events = new ArrayList<Event>();
        rooms =  new HashMap<String, Room>();
    }

    public boolean createNewEvent(String title, String speaker, LocalDateTime time, int duration, int roomNumber){
        return events.add(new Event(name, speaker, time, duration, roomNumber));
    }

    public Speaker createNewSpeaker(String name, String address, String email, String username, String password){

    }
    /*
    private boolean addEvent(String title){

    }
    */
    public Event getEvent(String eventName){

    }

    public List<Event> getAllEvents(){

    }

    private boolean checkEventIsValid(Speaker speaker, int roomNumber, LocalDateTime time){

    }

    public boolean checkEventFull(String eventName){

    }

    public boolean addRoom(int roomNumber, int capacity){

    }

}
