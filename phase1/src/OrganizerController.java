import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class OrganizerController extends AttendeeController {

    public OrganizerController() {}

    void addEvent(String name, Speaker speaker, LocalDateTime time, int duration, int roomNumber) {
        eventManager.createNewEvent(name, speaker, time, duration, roomNumber);
    }

    void messageAllAttendees(String message) {
        createBlastMessage("attendee", message);
    }

    void messageEventAttendees(String message, String name) {
        messageManager.speakerBlastMessage(Arrays.asList(name), message, eventManager, this.username);
    }

    void messageAllSpeakers(String message) {
        createBlastMessage("speaker", message);
    }

    void cancelEvent(String name) {
        eventManager.getAllEvents().remove(name);
    }

    void rescheduleEvent(String name, LocalDateTime newTime) {

        if(eventManager.getAllEvents().containsKey(name)) {
            eventManager.getAllEvents().get(name).setTime(newTime);
        }
    }

    void createSpeakerAccount(String name, String address, String email, String username, String password) {
        userManager.addUser(name, address, email, username, password, "speaker");
    }


    void createBlastMessage(String blastType, String message) {

        Map<String, String> userTypes = userManager.getUserTypes();

        for(String user : userTypes.keySet()) {
            if(userTypes.get(user).equals(blastType)) {
                messageManager.createNewMessage(message, this.username, user);
            }
        }

    }

}
