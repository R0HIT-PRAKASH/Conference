import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventManager {

    private Map<String, Event> events;
    private List<Room> rooms;

    public EventManager(){
        events = new HashMap<String, Event>();
        rooms =  new ArrayList<Room>();
    }


    public Event createNewEvent(String name, String speakerName, LocalDateTime time, int duration, int roomNumber){
        Event event = new Event(name, speakerName, time, duration, roomNumber);
        return event;
    }


    public boolean addEvent(String name, String speakerName, LocalDateTime time, int duration, int roomNumber){
        Event event = createNewEvent(name, speakerName, time, duration, roomNumber);
        for (String i: events.keySet()){
            if (event.getName().equals(i)){
                return false;
            }
            Event e = events.get(i);
            if (e.getTime().equals(event.getTime()) && e.getRoomNumber() == event.getRoomNumber() ){
                return false;
            }
        }

        events.put(event.getName(), event);
        return true;
    }

    // Don't think we need this method
    private boolean checkEventIsValid(Speaker speaker, int roomNumber, LocalDateTime time){

    }

    public Speaker createNewSpeaker(String name, String address, String email, String username, String password){
        return new Speaker(name, address, email, username, password);
    }


    public Event getEvent(String eventName){

        for (String i : events.keySet()){
            if (i.equals(eventName)){
                return events.get(i);
            }
        }

        System.out.println("Event not found.");
        return null;

    }

    public Map<String, Event> getAllEvents(){
        return events;
    }

    public boolean checkEventFull(String eventName){

        Event event = this.getEvent(eventName);
        if (event == null){
            return true;
        }
        if (event.getAttendeeSet().size() == getRoom(event.getRoomNumber()).getCapacity()){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean addRoom(int roomNumber, int capacity){

        Room room = createNewRoom(roomNumber, capacity);
        int i = 0;
        while (i < rooms.size()){
            if (rooms.get(i).getRoomNumber() == room.getRoomNumber()) {
                return false;
            }
            i++;
        }

        rooms.add(room);

        return false;
    }

    public Room createNewRoom(int roomNumber, int capacity){
        Room room = new Room(roomNumber, capacity);
        return room;
    }

    public Room getRoom(int roomNumber){
        for (Room room: rooms){
            if (room.getRoomNumber() == roomNumber){
                return room;
            }
        }

        //Room Not Found
        return null;
    }

}
