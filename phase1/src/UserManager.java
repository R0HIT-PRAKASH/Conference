import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {

    /**
     * The UserManager class stores a list of all of the users. userList
     * stores every username along with its associated User.
     */
    private Map<String, User> userList;

    public UserManager(){
        userList = new HashMap<>();
    }

    /**
     * checkCredentials determines if the user is in userList based on the username.
     * @param username This parameter refers to the username of the user.
     * @return This method returns true if the user is in userList and false if it isn't.
     */
    public boolean checkCredentials(String username){
        return userList.get(username) != null;
    }

    /**
     * This method returns the user associated with the username.
     * @param username This parameter refers to the username of the user.
     * @return This method returns the user associated with the username.
     */
    public User getUser(String username){
        return userList.get(username);
    }

    /**
     * This method adds a new user to userList if it is not already a user.
     * @param name This parameter refers to the name of the user.
     * @param address This parameter refers to the address of the user.
     * @param email This parameter refers to the email of the user.
     * @param username This parameter refers to the username of the user.
     * @param password This parameter refers to the password of the user.
     * @param usertype This parameter refers to the type of user this person is.
     * @return Returns true if the user was added to userList and false otherwise.
     */
    public boolean addUser(String name, String address, String email, String username, String password, String usertype){
        if(checkCredentials(username)){
            return false;
        }

        if(usertype.toLowerCase().equals("organizer")){
            userList.put(username, new Organizer(name, address, email, username, password));
        }else if(usertype.toLowerCase().equals("speaker")){
            userList.put(username, new Speaker(name, address, email, username, password));
        }else{
            userList.put(username, new Attendee(name, address, email, username, password));
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
     * @return Returns a string representation of the user's type or null if user is not in userList.
     */
    public String getUserType(String username){
        if(userList.get(username) == null){
            return null;
        }
        return userList.get(username).getUserType();
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
        if(userList.get(username) == null || !userList.get(username).getUserType().equals("speaker")){
            return null;
        }
        return ((Speaker) userList.get(username)).getSpeakingEvents();

    }

    /**
     * This method returns a list of all of the organizers in userList.
     * @return A list of all of the organizers in userList.
     */
    public List<User> getOrganizers(){
        List<User> organizers = new ArrayList<>();
        for(String username : userList.keySet()){
            if(userList.get(username) instanceof Organizer){
                organizers.add(userList.get(username));
            }
        }
        return organizers;
    }
}
