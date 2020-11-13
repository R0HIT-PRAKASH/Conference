import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public class UserManager {

    /**
     * The UserManager class stores a list of all of the users. userMap
     * stores every username along with its associated User.
     */
    private HashMap<String, User> userMap;

    public UserManager(){
        userMap = new HashMap<>();
    }

    /**
     * checkCredentials determines if the user is in userMap based on the username.
     * @param username This parameter refers to the username of the user.
     * @return This method returns true if the user is in userMap and false if it isn't.
     */
    public boolean checkCredentials(String username){
        return userMap.get(username) != null;
    }

    /**
     * This method returns the user associated with the username.
     * @param username This parameter refers to the username of the user.
     * @return This method returns the user associated with the username.
     */
    public User getUser(String username){
        return userMap.get(username);
    }

    /**
     * This method sets the map of users.
     */
    public void setUserMap(HashMap<String, User> userMap) {
        this.userMap = userMap;
    }

    /**
     * This method returns the map of users.
     * @return Returns the map of users.
     */
    public HashMap<String, User> getUserMap(){
        return userMap;
    }

    /**
     * This method adds a new user to userMap if it is not already a user.
     * @param name This parameter refers to the name of the user.
     * @param address This parameter refers to the address of the user.
     * @param email This parameter refers to the email of the user.
     * @param username This parameter refers to the username of the user.
     * @param password This parameter refers to the password of the user.
     * @param usertype This parameter refers to the type of user this person is.
     * @return Returns true if the user was added to userMap and false otherwise.
     */
    public boolean addUser(String name, String address, String email, String username, String password, String usertype){
        if(checkCredentials(username)){
            return false;
        }

        if(usertype.toLowerCase().equals("organizer")){
            userMap.put(username, new Organizer(name, address, email, username, password));
        }else if(usertype.toLowerCase().equals("speaker")){
            userMap.put(username, new Speaker(name, address, email, username, password));
        }else{
            userMap.put(username, new Attendee(name, address, email, username, password));
        }
        return true;
    }

    /**
     * This method returns the username of the user.
     * @param user This parameter refers to the user object.
     * @return Returns the username of the user.
     */
    public String getUsername(User user){
        return user.getUsername();
    }

    /**
     * This method returns the type of user the person is based on username.
     * @param username This parameter refers to the username of the user.
     * @return Returns a string representation of the user's type or null if user is not in userMap.
     */
    public String getUserType(String username){
        if(userMap.get(username) == null){
            return "";
        }
        return userMap.get(username).getUserType();
    }

    /**
     * This method returns the type of user the person is.
     * @param user This parameter refers to the user.
     * @return Returns a string representation of the user's type.
     */
    public String getUserType(User user){
        return user.getUserType();
    }

    /**
     * This method returns the list of events the user will speak at if they are a speaker.
     * @param username This parameter refers to the username of the user.
     * @return Returns the list of events the user will be speaking at if they are a speaker. Returns null otherwise.
     */
    public List<String> getSpeakingEvents(String username){
        if(userMap.get(username) == null || !userMap.get(username).getUserType().equals("speaker")){
            return null;
        }
        return ((Speaker) userMap.get(username)).getSpeakingEvents();

    }

    /**
     * This method returns a list of all of the organizers in userMap.
     * @return A list of all of the organizers in userMap.
     */
    public List<User> getOrganizers(){
        List<User> organizers = new ArrayList<>();
        for(String username : userMap.keySet()){
            if(userMap.get(username) instanceof Organizer){
                organizers.add(userMap.get(username));
            }
        }
        return organizers;
    }

    /**
     * This method goes through the list of usernames and returns a map of usernames to user types.
     * @return A map of the usernames to the type of each user.
     */
    public Map<String, String> getUserTypes(){
        Map<String, String> new_map = new HashMap<>();
        for(String username : userMap.keySet()){
            new_map.put(username, userMap.get(username).getUserType());
        }
        return new_map;
    }

    /**
     * This method returns a list of events that the user will be attending if they are an organizer or attendee.
     * @param username Refers to the username of the user.
     * @return Returns a list of strings that represent the events the user will be attending.
     */
    public List<String> getAttendingEvents(String username){
        if(userMap.get(username).getUserType().equals("attendee")){
            return ((Attendee) userMap.get(username)).getAttendingEvents();
        }else if(userMap.get(username).getUserType().equals("organizer")){
            return ((Organizer) userMap.get(username)).getAttendingEvents();
        }else{
            return null;
        }
    }

    /**
     * This method signs the user up for an event.
     * @param username Refers to the username of the user.
     * @param event Refers to the event.
     * @param eventManager Refers to the class instance that contains all of the events.
     * @return Returns true if the event was added to the list of events the user will attend and false otherwise.
     */
    public boolean signUpForEvent(String username, Event event, EventManager eventManager){
        User user = userMap.get(username);

        if(eventManager.getAllEvents().get(event.getName()) == null || eventManager.checkEventFull(event.getName()) ||
                event.getTime().isBefore(LocalDateTime.now())){
            return false;
        }

        if(user.getUserType().equals("attendee")){
            for(String eventName : ((Attendee) user).getAttendingEvents()){
                Event attendEvent = eventManager.getEvent(eventName);
                double signUpHour = event.getTime().getHour();
                double signedUpHour = attendEvent.getTime().getHour();
                if(event.getTime().getDayOfYear() == attendEvent.getTime().getDayOfYear() &&
                        event.getTime().getYear() == attendEvent.getTime().getYear() &&
                        (signedUpHour < signUpHour && signUpHour - signedUpHour < attendEvent.getDuration()) ||
                        (!(signedUpHour < signUpHour) && signedUpHour - signedUpHour < event.getDuration())) {
                    return false;
                }
            }
            ((Attendee) user).signUpForEvent(event);

        }else if(user.getUserType().equals("organizer")){
            for(String eventName : ((Organizer) user).getAttendingEvents()){
                Event attendEvent = eventManager.getEvent(eventName);
                double signUpHour = event.getTime().getHour();
                double signedUpHour = attendEvent.getTime().getHour();
                if(event.getTime().getDayOfYear() == attendEvent.getTime().getDayOfYear() &&
                        event.getTime().getYear() == attendEvent.getTime().getYear() &&
                        (signedUpHour < signUpHour && signUpHour - signedUpHour < attendEvent.getDuration()) ||
                        (!(signedUpHour < signUpHour) && signedUpHour - signedUpHour < event.getDuration())) {
                    return false;
                }
            }
            ((Organizer) user).signUpForEvent(event);

        }else{
            return false;
        }
        return true;
    }

    /**
     * This method cancels the user's spot in the event.
     * @param username Refers to the username of the user.
     * @param event Refers to the event object.
     * @param eventManager Refers to the class instance that contains all of the events.
     * @return Returns true if the event was removed from the list of events the user will attend or false otherwise.
     */
    public boolean cancelEventSpot(String username, Event event, EventManager eventManager){
        User user = userMap.get(username);
        if(!eventManager.getAllEvents().containsKey(event.getName())){
            return false;
        }

        if(user.getUserType().equals("attendee")){
            if(((Attendee) user).getAttendingEvents().contains(event.getName())){
                ((Attendee) user).getAttendingEvents().remove(event.getName());
            }else{
                return false;
            }
        }else if(user.getUserType().equals("organizer")){
            if(((Organizer) user).getAttendingEvents().contains(event.getName())){
                ((Organizer) user).getAttendingEvents().remove(event.getName());
            }else{
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
}
