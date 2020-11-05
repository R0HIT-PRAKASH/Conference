import java.time.LocalDateTime;
import java.util.Scanner;

public class OrganizerController extends AttendeeController {

    public OrganizerController() {}

    void addEvent(String title, String speaker, LocalDateTime time, int duration, int roomNumber) {
        eventManager.createNewEvent(title, speaker, time, duration, roomNumber);
    }

    void messageAllAttendees(String message) {
        createBlastMessage("attendee", message);
    }

    void messageEventAttendees(String message, String title) {
        messageManager.speakerBlastMessage();
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


    void createBlastMessage(String blastType, String message) {
        if(blastType.equals("attendee")) {
            for(Event e : eventManager.getAllEvents()) {
                for(Attendee a : e.getAttendeeList()) {
                    messageManager.createNewMessage(message, this.user.getUsername(), a.getUsername());
                }
            }
        }

        if(blastType.equals())

    }



}
