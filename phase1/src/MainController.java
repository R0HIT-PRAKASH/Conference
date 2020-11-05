import java.util.Scanner;

public class MainController {
    protected MessageManager messageManager = new MessageManager();
    protected UserManager userManager = new UserManager();
    protected EventManager eventManager = new EventManager();
    protected String username;

    public void run() {
        LoginController log = new LoginController();
        readInFiles();
        this.username = log.login();
        String type = this.userManager.getUserType(this.username);
        if(type.equals("organizer")){
            OrganizerController controller = new OrganizerController();
            controller.run();
        }
        else if(type.equals("attendee")){
            AttendeeController controller = new AttendeeController();
            controller.run();
        }
        else if(type.equals("speaker")){
            SpeakerController controller = new SpeakerController();
            controller.run();
        }
    }

    private void readInFiles(){

    }
}
