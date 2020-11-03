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

    public boolean createNewEvent(String title, Speaker speaker, LocalDateTime time, int duration, int roomNumber){
        return events.add(new Event(title, speaker, time, duration, roomNumber));
    }

    public Speaker createNewSpeaker(String name, String address, String email, String username, String password){
        //return new Speaker(name, address, email, username, password);
    }
    /*
    private boolean addEvent(String title){

    }
    */
    public Event getEvent(String eventName){
        for (Event event: events){
            if (event.name.equals(eventName)){
                return event;
            }
        }
        //TRY Exception Needed...
    }

    public List<Event> getAllEvents(){
        return events;
    }

    private boolean checkEventIsValid(Speaker speaker, int roomNumber, LocalDateTime time){

    }

    public boolean checkEventFull(String eventName){
        Event event = this.getEvent(eventName);
        //if ()
    }

    public boolean addRoom(int roomNumber, int capacity){

    }

}
