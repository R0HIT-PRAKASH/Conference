import java.time.LocalDateTime;
import java.util.Scanner;

public class OrganizerController extends AttendeeController {

    void addEvent(String title, String speaker, LocalDateTime time, int duration, int roomNumber) {
        eventManager.createNewEvent(title, speaker, time, duration, roomNumber);
    }

}
