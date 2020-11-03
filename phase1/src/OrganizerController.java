import java.time.LocalDateTime;
import java.util.Scanner;

public class OrganizerController extends AttendeeController {

    public OrganizerController() {}

    void addEvent(String title, String speaker, LocalDateTime time, int duration, int roomNumber) {
        eventManager.createNewEvent(title, speaker, time, duration, roomNumber);
    }

    void messageAllAttendees(String message) {
        messageManager.messageAll(message);
    }

    void messageEventAttendees(String message, String title) {
        messageManager.messageEvent(String message, String title);
    }

    void messageAllSpeakers(String message) {
        messageManager.messageSpeakers(message);
    }

    void cancelEvent(String title) {
        eventManager.cancelEvent(title);
    }

    void rescheduleEvent(String title, LocalDateTime newTime) {
        eventManager.reschedule(title, newTime);
    }

    void createSpeakerAccount(String name, String address, String email, String username, String password) {
        userManager.setUser(name, address, email, username, password);
    }

    /* I don't know what this is supposed to do
    void createBlastMessage(String blastType, String message) {}
     */


}
