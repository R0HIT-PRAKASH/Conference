import java.util.HashMap;
import java.util.ArrayList;

public class EventManager {

    private ArrayList<Event> events = new ArrayList<Event>();
    private HashMap<String, Room> rooms = new HashMap<String, Room>();

    public EventManager(){

    }

    public boolean createNewEvent(String title, int time, int roomNumber, String speakerName){

    }

    public Speaker createNewSpeaker(String name, String address, String email, String username, String password){

    }

    public boolean addEvent(String title){

    }

    public Event getEvent(String eventName){

    }

    public ArrayList<Event> getAllEvents(){

    }

    private boolean checkEventIsValid(Speaker speaker, int roomNumber, int time){

    }

    public boolean checkEventFull(String eventName){

    }

    public boolean addRoom(int roomNumber, int capacity){

    }

}
