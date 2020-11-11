import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public class OrganizerController extends AttendeeController {

    /**
     * This method adds an event to the set of events in the conference
     * @param name This parameter refers to the name of the event.
     * @param speaker This parameter refers to the speaker at the event.
     * @param time This parameter refers to the event time.
     * @param roomNumber This parameter refers to the room number.
     * @return Returns true if the user was added to events map and false otherwise.
     */
    boolean addEvent(String name, String speaker, LocalDateTime time, int roomNumber) {
        return eventManager.addEvent(name, speaker, time, roomNumber);
    }

    /**
     * This method sends a message to all the attendees at the conference.
     * @param message This parameter is the message text
     * @return void
     */
    void messageAllAttendees(String message) {
        createBlastMessage("attendee", message);
    }

    /**
     * This method sends a message to all the attendees at a particular event.
     * @param message This parameter is the message text
     * @param name This parameter is the event name
     * @return void
     */
    void messageEventAttendees(String message, String name) {
        messageManager.speakerBlastMessage(Arrays.asList(name), message, eventManager, this.username);
    }

    /**
     * This method sends a message to all the speakers at the conference.
     * @param message This parameter is the message text
     * @return void
     */
    void messageAllSpeakers(String message) {
        createBlastMessage("speaker", message);
    }

    /**
     * This method cancels an event
     * @param name This is the title of the event to be canceled
     * @return void
     */
    void cancelEvent(String name) {
        eventManager.getAllEvents().remove(name);
    }

    /**
     * This method reschedules an event
     * @param name The name of the event
     * @param newTime The new time
     */
    void rescheduleEvent(String name, LocalDateTime newTime) {

        if(eventManager.getAllEvents().containsKey(name)) {
            eventManager.getAllEvents().get(name).setTime(newTime);
        }
    }

    /**
     * This method creates a speaker account
     * @param name This parameter refers to the name of the speaker.
     * @param address This parameter refers to the address of the speaker.
     * @param email This parameter refers to the email of the speaker.
     * @param username This parameter refers to the username of the speaker.
     * @param password This parameter refers to the password of the speaker.
     * @return Returns true if the user was added to userMap and false otherwise.
     */
    void createSpeakerAccount(String name, String address, String email, String username, String password) {
        userManager.addUser(name, address, email, username, password, "speaker");
    }


    /**
     * This method sends messages to all people of a specific type
     * @param blastType This is the type of the individual. Choices are "speaker", "organizer" and "attendee"
     * @param message This is the actual message
     */
    void createBlastMessage(String blastType, String message) {

        Map<String, String> userTypes = userManager.getUserTypes();

        for(String user : userTypes.keySet()) {
            if(userTypes.get(user).equals(blastType)) {
                messageManager.createNewMessage(message, this.username, user);
            }
        }

    }

}
