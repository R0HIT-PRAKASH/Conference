import java.util.Map;

public class UserManager {

    /**
     * The UserManager class stores a list of all of the users. userList
     * stores every username along with its associated User.
     */
    private HashMap<String, User> userList = new HashMap<String, User>();

    public UserManager(){}

    /**
     * checkCredentials determines if the user is in userList based on the username.
     * @param username This parameter refers to the username of the user.
     * @return This method returns true if the user is in userList and false if it isn't.
     */
    public boolean checkCredentials(String username){
        if(userList.get(username) != null){
            return true;
        }
        return false;
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
     * @return Returns true if the user was added to userList and false otherwise.
     */
    public boolean setUser(String name, String address, String email, String username, String password){
        if(userList.get(username) != null){
            return False
        }
        new_user = new User(name, address, email, username, password);
        userList.put(username, new_user);
        return true;
    }

    /**
     * This method returns the username of the user.
     * @param user This parameter refers to the user object.
     * @return Returns the username of the user.
     */
    public String getUsername(User user){
        return user.username;
    }

}
