import java.io.Serializable;

/**
 * This class represents the properties and actions of all users. They all
 * have a name, address, email, username, and password.
 */
public abstract class User implements Comparable<User>, Serializable {

    private String name;
    private String address;
    private String email;
    private String username;
    private String password;

    /**
     * Constructs a user with a name, address, email, username, and passowrd.
     * @param name Refers to the name of the user.
     * @param address Refers to the address of the user.
     * @param email Refers to the email of the user.
     * @param username Refers to the username of the user.
     * @param password Refers to the password of the user.
     */
    public User(String name, String address, String email, String username, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * This method gets the name of the user.
     * @return Returns the name of the user.
     */
    public String getName(){
        return this.name;
    }

    /**
     * This method gets the address of the user.
     * @return Returns the address of the user.
     */
    public String getAddress(){
        return this.address;
    }

    /**
     * This method gets the email of the user.
     * @return Returns the email of the user.
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * This method gets the username of the user.
     * @return Returns the username of the user.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * This method gets the password of the user.
     * @return Returns the password of the user.
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * This method sets the name of the user.
     * @param newName Refers to the new name of the user.
     */
    public void setName(String newName){
        this.name = name;
    }

    /**
     * This method sets the address of the user.
     * @param newAddress Refers to the new address of the user.
     */
    public void setAddress(String newAddress){
        this.address = address;
    }

    /**
     * This method sets the email of the user.
     * @param newEmail Refers to the new email of the user.
     */
    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    /**
     * This method sets the username of the user.
     * @param newUserName Refers to the new username of the user.
     */
    public void setUsername(String newUserName){
        this.username = newUserName;
    }

    /**
     * This method returns the type of user this person is.
     * @return Will return a string representation of the user type.
     */
    public abstract String getUserType();

    /**
     * This method returns the amount of different characters between two usernames.
     * @param u Refers to a user.
     * @return Refers to the amount of unshared characters the two usernames have.
     */
    public int compareTo(User u) {
        return this.getUsername().compareTo(u.getUsername());
    }

    /**
     * This method formats a user object into a string.
     * @return Returns a string representation of the user's attributes.
     */
    public String toString() {
        return "Username: " + getUsername() + "| Name: " + getName() + "| Email: " + getEmail();
    }

}
