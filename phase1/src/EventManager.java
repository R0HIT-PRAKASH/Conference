import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventManager {

    private Map<String, Event> events;
    private Map<String, Room> rooms;

    public EventManager(){
        events = new HashMap<String, Event>();
        rooms =  new HashMap<String, Room>();
    }

    public boolean createNewEvent(String name, Speaker speaker, LocalDateTime time, int duration, int roomNumber){
        if(events.containsKey(name)) return false;
        events.put(name, new Event(name, speaker, time, duration, roomNumber));
        return true;
    }

    public Speaker createNewSpeaker(String name, String address, String email, String username, String password){
        //return new Speaker(name, address, email, username, password);
    }
    /*
    private boolean addEvent(String name){

    }
    */
    public Event getEvent(String eventName){
        /*
        for (Event event: events){
            if (event.getName().equals(eventName)){
                return event;
            }
        }
         */

        return events.get(eventName);
        //TRY Exception Needed...
    }

    public Map<String, Event> getAllEvents(){
        return events;
    }

    private boolean checkEventIsValid(Speaker speaker, int roomNumber, LocalDateTime time){

    }

    public boolean checkEventFull(String eventName){
        // Maybe Event event = events.get(eventName);
        Event event = this.getEvent(eventName);
        //if ()
    }

    public boolean addRoom(int roomNumber, int capacity){

    }

}
