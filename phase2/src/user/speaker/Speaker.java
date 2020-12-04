package user.speaker;

import request.Request;
import user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the characteristics and actions of a speaker. They have all of the
 * characteristics of a user, except they have a list of events they will speak at.
 */
public class Speaker extends User {

    private List<String> speakingEvents;
    private List<Request> requestsMade;

    /**
     * This method constructs a new speaker object with an empty list of speakingEvents.
     * @param name Refers to the name of the speaker.
     * @param address Refers to the address of the speaker.
     * @param email Refers to the email of the speaker.
     * @param userName Refers to the username of the speaker.
     * @param password Refers to the password of the speaker.
     */
    public Speaker(String name, String address, String email, String userName, String password, String company, String bio) {
        super(name, address, email, userName, password, company, bio);
        this.speakingEvents = new ArrayList<String>();
        this.requestsMade = new ArrayList<Request>();
    }

    /**
     * This method gets the type of user this person is.
     * @return Returns to the string representation of "speaker".
     */
    public String getUserType() {
        return "speaker";
    }

    /**
     * This method gets the list of events the speaker will be speaking at.
     * @return Returns a list of events the speaker will be speaking at.
     */
    public List<String> getSpeakingEvents() {
        return this.speakingEvents;
    }

    /**
     * This method adds a new event to the list of events the speaker will speak at.
     * @param eventName Refers to name of the event.
     */
    public void addSpeakingEvent(String eventName) {
        speakingEvents.add(eventName);
    }

    /**
     * Returns the number of events assigned to this speaker
     * @return int
     */
    public int getNumberOfEvents() { return this.speakingEvents.size(); }

    /**
     * This method gets the list of requests this speaker has made
     * @return Returns the list of requests the speaker has made
     */
    public List<Request> getRequestsMade(){
        return this.requestsMade;
    }

    /**
     * Adds a request to the list of requests made by the speaker.
     * @param request Refers to the speaker that will be added to the list of requests made.
     */
    public void addRequest(Request request){
        requestsMade.add(request);
    }
}