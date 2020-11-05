import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OrganizerController extends AttendeeController {

    public OrganizerController() {}

    void addEvent(String title, Speaker speaker, LocalDateTime time, int duration, int roomNumber) {
        eventManager.createNewEvent(title, speaker, time, duration, roomNumber);
    }

    void messageAllAttendees(String message) {
        createBlastMessage("attendee", message);
    }

    void messageEventAttendees(String message, String title) {
        messageManager.speakerBlastMessage(Arrays.asList(title), message, eventManager, this.username);
    }

    void messageAllSpeakers(String message) {
        createBlastMessage("speaker", message);
    }

    void cancelEvent(String title) {
        eventManager.cancelEvent(title);
    }

    void rescheduleEvent(String title, LocalDateTime newTime) {
        eventManager.reschedule(title, newTime);
    }

    void createSpeakerAccount(String name, String address, String email, String username, String password) {
        userManager.addUser(name, address, email, username, password, "speaker");
    }


    void createBlastMessage(String blastType, String message) {

        for(User u : userManager.getUserList().values()) {
            if(u.getUserType().equals(blastType)) {
                messageManager.createNewMessage(message, this.username, u.getUsername());
            }
        }

    }



}
