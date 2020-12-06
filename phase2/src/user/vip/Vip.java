package user.vip;

import user.attendee.Attendee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all of the characteristics and actions of an attendee. They
 * have a name, address, email, username, and password. Attendees can attend events
 * and sign up for events.
 */
public class Vip extends Attendee {

    private List<String> attendingEvents;
    private HashMap<String, String> supporting;

    /**
     * This method constructs a new vip object with an empty list of attendingEvents.
     * @param name Refers to the name of the vip.
     * @param address Refers to the address of the vip.
     * @param email Refers to the email of the vip.
     * @param userName Refers to the username of the vip.
     * @param password Refers to the password of the vip.
     * @param company Refers to the company of the vip.
     * @param bio Refers to the bio of the vip.
     */
    public Vip(String name, String address, String email, String userName, String password, String company, String bio) {
        super(name, address, email, userName, password, company, bio);
        this.attendingEvents = new ArrayList<>();
        this.supporting = new HashMap<>();
    }

    // Getter Methods

    /**
     * This method gets the type of user this person is.
     * @return Returns a string representation of "vip".
     */
    public String getUserType(){
        return "vip";
    }

    /**
     * This method gets the users that this VIP is financially supporting.
     * @return The users this user is financially supporting.
     */
    public HashMap<String, String> getSupporting(){
        return this.supporting;
    }

    // Setter Methods
    /**
     * This method sets the users that this VIP is financially supporting.
     * @param supporting The users this user is financially supporting.
     */
    public void setSupporting(HashMap<String, String> supporting) {
        this.supporting = supporting;
    }
}


