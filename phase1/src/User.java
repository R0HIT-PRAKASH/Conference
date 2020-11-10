import java.util.ArrayList;

public abstract class User implements Comparable<User>{
    private String name; // I would suggest doing firstName, LastName instead
    private String address;
    private String email;
    private String username;
    private String password;

    public User(String name, String address, String email, String username, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getName(){
        return this.name;
    }
    public String getAddress(){
        return this.address;
    }
    public String getEmail(){
        return this.email;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setName(String newName){
        this.name = name;
    }
    public void setAddress(String newAddress){
        this.address = address;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    public void setUsername(String newUserName){
        this.username = newUserName;
    }
    public abstract String getUserType();

    public int compareTo(User u) {
        return this.getUsername().compareTo(u.getUsername());
    }

}
