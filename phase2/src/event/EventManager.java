package event;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import room.Room;
import saver.ReaderWriter;
import user.User;
import user.speaker.Speaker;

/**
 * The EventManager class is responsible for handling event-related actions. events is a map that stores
 * the names of events along with its associated event object. rooms is a list of all rooms.
 */
public class EventManager implements Serializable {

    public HashMap<String, Event> events;
    private List<Room> rooms;
    private EventFactory eventFactory;
    private ReaderWriter RW;

    /**
     * Constructs a new EventManager with an empty map of events
     * and an empty list of rooms.
     */
    public EventManager(ReaderWriter RW){
        events = new HashMap<>();
        rooms =  new ArrayList<Room>();
        eventFactory = new EventFactory();
        this.RW = RW;
    }

    /**
     * Creates a new event object.
     * @param name Refers to the name of the event.
     * @param time Refers to the starting time of the event.
     * @param duration The Event Duration.
     * @param roomNumber Refers to the room number of this event.
     * @param capacity The room capacity.
     * @param computers Refers to the number of computers in the room.
     * @param projector Refers to whether or not the room has a projector.
     * @param chairs Refers to the number of chairs in the room.
     * @param tables Refers to the number of tables in the room.
     * @param creators The list of creators.
     * @param vip Refers to whether or not this event is VIP exclusive.
     * @param speaker Refers to the speaker username if this is a talk.
     * @param speakers Refers to the list of speakers if this is a panel.
     * @return Returns the created event.
     */
    public Event createNewEvent(String eventType, String name, LocalDateTime time, Integer duration, int roomNumber,
                                int capacity, int computers, boolean projector, int chairs, int tables,
                                List<String> creators, boolean vip, String speaker, List<String> speakers, String tag){
        Event event = eventFactory.getEvent(eventType, name, time, duration, roomNumber, capacity, computers, projector, chairs, tables, creators, vip, speaker, speakers, tag);
        if (getRoom(roomNumber) == null){
            addRoom(roomNumber, capacity, computers, projector, chairs, tables);
        }

        return event;
    }

    /**
     * Adds an event to the event list.
     * @param name Refers to the name of the event.
     * @param time Refers to the starting time of the event.
     * @param duration The Event Duration.
     * @param roomNumber Refers to the room number of this event.
     * @param capacity The room capacity.
     * @param computers Refers to the number of computers in the room.
     * @param projector Refers to whether or not the room has a projector.
     * @param chairs Refers to the number of chairs in the room.
     * @param tables Refers to the number of tables in the room.
     * @param creators The list of creators.
     * @param vip Refers to whether or not the event is VIP exclusive.
     * @param speaker Refers to the speaker username if this is a talk.
     * @param speakers Refers to the list of speakers if this is a panel.
     * @param tag Refers to the category this event is in.
     * @return Returns true if the event is successfully added. Otherwise, it returns false.
     */
    public boolean addEvent(String eventType, String name, LocalDateTime time, Integer duration, int roomNumber,
                            int capacity, int computers, boolean projector, int chairs, int tables,
                            List<String> creators, boolean vip, String speaker, List<String> speakers, String tag){
        Event event = createNewEvent(eventType, name, time, duration, roomNumber, capacity, computers, projector,
                chairs, tables, creators, vip, speaker, speakers, tag);
        if (!checkEventIsValid(event)){
            return false;
        }


        events.put(event.getName(), event);
        return true;
    }

    /**
     * @param event Refers to the event being checked.
     * @return Returns True if event is valid, otherwise return false.
     */
    public boolean checkEventIsValid(Event event){

        //GET EVENT TYPE
        String eventType = event.getEventType();

        if (!between9to5(event.getTime()) || event.getCapacity() <= 0 || event.getDuration() <= 0 || !requiredEquipment(event)){
            return false;
        }

        for(Room room : rooms){
            if(room.getRoomNumber() == event.getRoomNumber() && room.getCapacity() < event.getCapacity()){
                return false;
            }
        }

        if(event.getTime().plusHours(event.getDuration()).getHour()*60 + event.getTime().getMinute() > 1020 || event.getDuration() > 8){
            return false;
        }

        for (String i: events.keySet()){
            //Checks Name
            if (event.getName().equals(i)){
                return false;
            }
            Event e = events.get(i);

            //Checks if times are conflicting
            boolean invalidTime = compareTimes(event, e);

            //If the times are conflicting and the room numbers are the same: return false.
            if (invalidTime && e.getRoomNumber() == event.getRoomNumber() ){
                return false;
            }
            //e TYPE
            String eType = e.getEventType();

            //If the times are conflicting and the speakers are the same: return false.
            if (eventType.equals("talk") && eType.equals("talk")){
                if (invalidTime && compareSpeakersTalkTalk(event, e)){
                    return false;
                }
            }
            else if (eventType.equals("talk") && eType.equals("panel")){
                if (invalidTime && compareSpeakersTalkPanel(event, e)){
                    return false;
                }
            }
            else if (eventType.equals("panel") && eType.equals("talk")){
                if (invalidTime && compareSpeakersTalkPanel(e, event)){
                    return false;
                }
            }
            else if (eventType.equals("panel") && eType.equals("panel")){
                if (invalidTime && compareSpeakersPanelPanel(event, e)){
                    return false;
                }
            }

        }
        return true;
    }

    private boolean compareSpeakersTalkTalk(Event event, Event e){
        return event.getSpeakerName().equals(e.getSpeakerName());
    }

    private boolean compareSpeakersTalkPanel(Event event, Event e){
        String eventSpeakerName = event.getSpeakerName();

        List<String> eSpeakersList = e.getSpeakersList();

        for (int i = 0; i < eSpeakersList.size(); i++){
            String eSpeakerName = eSpeakersList.get(0);
            if (eventSpeakerName.equals(eSpeakerName)){
                return true;
            }
        }

        return false;
    }

    private boolean compareSpeakersPanelPanel(Event event, Event e){

        List<String> eventSpeakersList = event.getSpeakersList();
        List<String> eSpeakersList = e.getSpeakersList();

        for (int i = 0; i < eventSpeakersList.size(); i++){
            String eventSpeakerName = eventSpeakersList.get(i);
            for (int k=0; k < eSpeakersList.size(); k++){
                String eSpeakerName = eSpeakersList.get(k);
                if (eventSpeakerName.equals(eSpeakerName)){
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * This method gets the attendees attending the event.
     * @param eventName Refers to the name of the event you want the attending attendees of.
     * @return Returns a list of attendee usernames of those attending the event.
     */
    public Set<String> getEventAttendees(String eventName){
        return getEvent(eventName).getAttendeeSet();
    }

    private boolean requiredEquipment(Event event){
        Room foundRoom = null;
        for(Room room : rooms){
            if(event.getRoomNumber() == room.getRoomNumber()) {
                foundRoom = room;
            }
        }
        if(foundRoom == null){
            return false;
        }

        return event.getRequiredChairs() <= foundRoom.getChairs() && event.getRequiredComputers() <=
                foundRoom.getComputers() && event.getRequiredTables() <= foundRoom.getTables() &&
                (!event.getRequiredProjector() || (event.getRequiredProjector() && foundRoom.getProjector()));
    }

    /**
     * Checks if the time of a LocalDateTime object is between 9AM and 5PM.
     * @param time Refers to the time being evaluated
     * @return Returns true if the time of the LocalDateTime object is between 9AM and 5PM
     */

    public boolean between9to5(LocalDateTime time){
        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();
        LocalDateTime dateAt9AM = LocalDateTime.of(year, month, day, 9, 0);
        LocalDateTime dateAt4PM = LocalDateTime.of(year, month, day, 16, 0);

        int compare1 = time.compareTo(dateAt9AM);
        int compare2 = time.compareTo(dateAt4PM);

        return (compare1 >= 0 && compare2 <= 0);
    }
    /**
     * @param e1 Refers to the event that is requested to be added.
     * @param e2 Refers to an already scheduled event.
     * @return Returns true if the requested time of e1 conflicts the scheduled time of e2. Otherwise returns false.
     */
    public boolean compareTimes(Event e1, Event e2){
        LocalDateTime beginningTime1 = e1.getTime();
        LocalDateTime endTime1 = e1.getTime().plusMinutes(e1.getDuration());

        LocalDateTime beginningTime2 = e2.getTime();
        LocalDateTime endTime2 = e2.getTime().plusMinutes(e2.getDuration());

        int compare1 = beginningTime1.compareTo(beginningTime2);
        int compare2 = beginningTime1.compareTo(endTime2);

        int compare3 = endTime1.compareTo(beginningTime2);
        int compare4 = endTime1.compareTo(endTime2);

        return compare1 == 0 || compare3 == 0 || (compare1 > 0 && compare2 < 0) || (compare3 > 0 && compare4 < 0);
    }


    /**
     * @param time Refers to the time that you want to see if it is after the current time.
     * @return Returns true if the time is after the current time, otherwise returns false.
     */
    public boolean checkTimeIsAfterNow(LocalDateTime time){
        return time.isAfter(LocalDateTime.now());
    }

    /**
     * Creates a new Speaker user.
     * @param name Refers to the name of the speaker.
     * @param address Refers to the address of the speaker.
     * @param email Refers to the email of the speaker.
     * @param username Refers to the username of the speaker.
     * @param password Refers to the password of the speaker.
     * @param company Refers to the company of the speaker.
     * @param bio Refers to the bio of the speaker.
     * @return Returns the created speaker.
     */
    public Speaker createNewSpeaker(String name, String address, String email, String username, String password, String company, String bio){
        return new Speaker(name, address, email, username, password, company, bio);
    }

    /**
     * Finds and gets the specified event from the map of events.  SHOULD BE PRIVATE NOT PUBLIC!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * @param eventName Refers the name of the event.
     * @return Returns the specified event.
     */
    public Event getEvent(String eventName){
        for (String i : events.keySet()){
            if (i.equals(eventName)){
                return events.get(i);
            }
        }
        return null;
    }

    public boolean checkEventIsRegistered(String eventName){
        for (String i : events.keySet()){
            if (i.equals(eventName)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return Returns map of events.
     */
    public HashMap<String, Event> getAllEvents(){
        return events;
    }

    /**
     * Gets all of the names of the events.
     * @return Returns a list of strings of event names.
     */
    public List<String> getAllEventNamesOnly(){
        List<String> allNames = new ArrayList<>(events.keySet());
        return allNames;
    }

    /**
     * @return Returns list of all rooms
     */
    public List<Room> getRooms(){
        return this.rooms;
    }
    /**
     * Sets events to the events passed in
     * @param events Refers to the events
     */
    public void setAllEvents(HashMap<String, Event> events){
        this.events = events;
    }

    /**
     * Sets rooms to the rooms passed in
     * @param rooms Refers to the rooms
     */
    public void setRooms(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    /**
     * Checks if the specified event is full.
     * @param eventName Refers to the name of the event.
     * @return Returns true if event is full. Otherwise, returns false.
     */
    public boolean checkEventFull(String eventName){
        Event event = this.getEvent(eventName);

        return (event == null || event.getAttendeeSet().size() == event.getCapacity());
    }

    /**
     * Adds a room to the list of rooms.
     * @param roomNumber Refers to the room number.
     * @param capacity Refers to the capacity of the room.
     * @param computers Refers to the number of computers in the room.
     * @param projector Refers to whether or not the room has a projector.
     * @param chairs Refers to the number of chairs in the room.
     * @param tables Refers to the number of tables in the room.
     * @return Returns true if adding room was successful. Otherwise, returns false.
     */
    public boolean addRoom(int roomNumber, Integer capacity, int computers, boolean projector, int chairs, int tables){
        Room room = createNewRoom(roomNumber, capacity, computers, projector, chairs, tables);
        int i = 0;
        while (i < rooms.size()){
            if (rooms.get(i).getRoomNumber() == room.getRoomNumber()) {
                return false;
            }
            i++;
        }
        rooms.add(room);
        return true;
    }

    /**
     * Creates a new room.
     * @param roomNumber Refers to the room number.
     * @param capacity Refers to the capacity of the room.
     * @param computers Refers to the number of computers in the room.
     * @param projector Refers to whether or not the room has a projector.
     * @param chairs Refers to the number of chairs in the room.
     * @param tables Refers to the number of tables in the room.
     * @return Returns the room object.
     */
    public Room createNewRoom(int roomNumber, Integer capacity, int computers, boolean projector, int chairs, int tables){
        return new Room(roomNumber, capacity, computers, projector, chairs, tables);
    }

    /**
     * Finds and gets the specified room from the list of rooms.
     * @param roomNumber Refers to the room number.
     * @return Returns the specified room.
     */
    public Room getRoom(int roomNumber){
        for (Room room: rooms){
            if (room.getRoomNumber() == roomNumber){
                return room;
            }
        }
        //Room Not Found
        return null;
    }

    /**
     * Yields the events that have already happened.
     * @param allEvents Refers to all the events in this conference.
     * @return All the events that have already happened.
     */
    public List<String> eventHappened(List<String> allEvents){
        LocalDateTime time = LocalDateTime.now();
        List<String> priorEvents = new ArrayList<>();
        for(String event: allEvents){
            Event check = getEvent(event);
            int compare = time.compareTo(check.getTime());
            if (compare > 0){
                priorEvents.add(event);
            }
        }
        return priorEvents;
    }

    /**
     * Yields the events that have yet to happen.
     * @param allEvents Refers to all the events in this conference.
     * @return All the events that have yet to happen
     */
    public List<String> eventNotHappened(List<String> allEvents){
        List<String> futureEvents = new ArrayList<>();
        for(String event: allEvents){
            Event check = getEvent(event);
            if (check.getTime().isAfter(LocalDateTime.now())){
                futureEvents.add(event);
            }
        }
        return futureEvents;
    }

    /**
     * Returns a List of events in chronological order based on the inputted event names
     * @param theseEvents Names of the events to be sorted
     * @return The list of chronologically sorted events
     */
    public List<Event> chronologicalEvents(List<String> theseEvents){
        List<Event> onlyEvents = new ArrayList<>();
        for (String key : theseEvents) {
            onlyEvents.add(events.get(key));
        }
        Collections.sort(onlyEvents);
        return onlyEvents;
    }

    /**
     * Retrieves the date and time an event occurs
     * @param event the event we want to get the date time for
     * @return The event's date time
     */
    public LocalDateTime getTime(Event event){
        return event.getTime();
    }

    /**
     * Adds an user to the list of an event's attendees.
     * @param event the event who's list of attendees we want to update.
     * @param user the user being added to the list of attendees.
     */
    public void addAttendee(Event event, User user) {
        event.addAttendee(user);
    }

    /**
     * Changes the capacity of an event.
     * @param event Refers to the event for which you want to change the capacity.
     * @param capacity Refers to the new capacity of the event.
     * @return Returns true if the capacity was changed and false otherwise.
     */
    public boolean changeEventCapacity(Event event, int capacity){
        if(event.getAttendeeSet().size() > capacity){
            return false;
        }
        event.setCapacity(capacity);
        return true;
    }

    /**
     * Returns the number of scheduled events
     * @return int
     */
    public int numberOfEvents() {
        return events.size();
    }

    /**
     * Checks to see if the user is occupied at the time.
     * @param user Refers to the speaker who is giving the event.
     * @param time Refers to the time the speaker will give the talk.
     * @param eventDuration Refers to how long the event will last.
     * @return True if the speaker is occupied and false otherwise.
     */
    public boolean speakerIsOccupied(User user, LocalDateTime time, int eventDuration){
        for(String eventName : ((Speaker) user).getSpeakingEvents()){
            Event event = events.get(eventName);
            if(event.getTime().getDayOfYear() == time.getDayOfYear() && event.getTime().getYear() == time.getYear()){
                int oldHoursPlusMinutes = event.getTime().getHour()*60 + event.getTime().getMinute() + event.getDuration()*60;
                int newHoursPlusMinutes = time.getHour()*60 + time.getMinute() + eventDuration*60;
                if(time.isEqual(event.getTime()) || (oldHoursPlusMinutes > time.getHour()*60 + time.getMinute() &&
                        event.getTime().isBefore(time)) || (newHoursPlusMinutes > event.getTime().getHour()*60 +
                        event.getTime().getMinute() && time.isBefore(event.getTime()))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the room is occupied at the specified time.
     * @param roomNumber Refers to the room number of the room.
     * @param time Refers to the time the event is taking place.
     * @param eventDuration Refers to how long the event lasts.
     * @return True if the room is occupied and false otherwise.
     */
    public boolean roomIsOccupied(int roomNumber, LocalDateTime time, int eventDuration){
        for(String eventName : events.keySet()){
            Event event = events.get(eventName);
            if(roomNumber == event.getRoomNumber() && event.getTime().getDayOfYear() == time.getDayOfYear() &&
                    event.getTime().getYear() == time.getYear()){
                int oldHoursPlusMinutes = event.getTime().getHour()*60 + event.getTime().getMinute() + event.getDuration()*60;
                int newHoursPlusMinutes = time.getHour()*60 + time.getMinute() + eventDuration*60;
                if(time.isEqual(event.getTime()) || (oldHoursPlusMinutes > time.getHour()*60 + time.getMinute() &&
                        event.getTime().isBefore(time)) || (newHoursPlusMinutes > event.getTime().getHour()*60 +
                        event.getTime().getMinute() && time.isBefore(event.getTime()))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method sets the map of events to the deserialized HashMap object containing event names as keys
     * and the corresponding Events as values.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from users.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find users.
     */
    public void setAllEventsReadIn() throws IOException, ClassNotFoundException {
        Object uncastedEvents = RW.readEvents();
        HashMap<String, Event> events = (HashMap<String, Event>) uncastedEvents;
        setAllEvents(events);
    }

    /**
     * This method sets the list of rooms to the deserialized ArrayList object containing the rooms.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from users.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find users.
     */
    public void setRoomsReadIn() throws IOException, ClassNotFoundException {
        Object uncastedRooms = RW.readRooms();
        ArrayList<Room> rooms = (ArrayList<Room>) uncastedRooms;
        setRooms(rooms);
    }

    /**
     * Gets the list of usernames responsible for creating the event.
     * @param eventName Refers to the event you want the list of creators for.
     * @return Returns the list of creators who made the event.
     */
    public List<String> getOrganizersList(String eventName){
        Event event = events.get(eventName);
        return event.getCreators();
    }

    /**
     * Removes the event from the list of events that will occur.
     * @param event Refers to the event that will be cancelled.
     */
    public void removeEvent(Event event){
        events.remove(event.getName());
    }

    /**
     * This method returns a list of strings describing all events given in a list of events.
     * @return Returns a list of strings describing all events given in a list of events.
     */
    public List<String> getToStringsOfEvents(){
        List<Event> events = chronologicalEvents(getAllEventNamesOnly());
        List<String> strings = new ArrayList<String>();
        for (Event event: events){
            strings.add(event.toString());
        }
        return strings;
    }

    /**
     * Returns a list of strings that represent all of the events that have not occurred yet.
     * @return The list of strings that represent events that have not occurred yet.
     */
    public List<String> getToStringsOfFutureEvents(){
        List<Event> chronological = chronologicalEvents(getAllEventNamesOnly());
        List<String> strings = new ArrayList<String>();
        LocalDateTime now = LocalDateTime.now();
        for (Event curr: chronological){
            if (getTime(curr).compareTo(now) > 0){
                strings.add(curr.toString());
            }
        }
        return strings;
    }

    /**
     * Gets the list of strings that represent the events that have been signed up for that have not occurred.
     * @param signedupFor Refers to the list of strings that represent events that a user has signed up for, but has not attended.
     * @return Returns a list of strings that represent events that the user has signed up for that have not occurred.
     */
    public List<String> getToStringsOfSignedUpEvents(List<String> signedupFor){
        List<Event> chronological = chronologicalEvents(signedupFor);
        List<String> strings = new ArrayList<String>();
        LocalDateTime now = LocalDateTime.now();
        for (Event curr: chronological){
            if (getTime(curr).compareTo(now) > 0){
                strings.add(curr.toString());
            }
        }
        return strings;
    }

    public String getToStringOfEventByName(String eventName){
        Event event = getEvent(eventName);
        if (event == null){
            return "NoEvent";
        }
        return event.toString();
    }

    public List<String> getToStringsOfEventsByTag(String tag){
        List<Event> chronological = chronologicalEvents(getAllEventNamesOnly());
        List<String> strings = new ArrayList<String>();

        for (Event event: chronological){
            if (event.getTag().equals(tag)){
                strings.add(event.toString());
            }
        }
        return strings;
    }

    public List<String> getToStringsOfRooms(){
        List<String> stringsOfRooms = new ArrayList<>();
        for (Room room: rooms){
            stringsOfRooms.add("Room #" + room.getRoomNumber());
        }
        return stringsOfRooms;
    }

    /**
     * Checks to see if the event is only for VIP's.
     * @param eventName Refers to the name of the event.
     * @return Returns true if the event is only for VIP's and false otherwise.
     */
    public boolean checkIfEventIsVIp(String eventName){
        Event event = getEvent(eventName);
        return event.getVipEvent();
    }
}