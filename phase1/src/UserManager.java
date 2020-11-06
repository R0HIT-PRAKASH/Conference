import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {

    /**
     * The UserManager class stores a list of all of the users. userMap
     * stores every username along with its associated User.
     */
    private Map<String, User> userMap;

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
            return null;
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
}
