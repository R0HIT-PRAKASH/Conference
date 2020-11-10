import java.util.Scanner;

public class MainController {
    protected MessageManager messageManager = new MessageManager();
    protected UserManager userManager = new UserManager();
    protected EventManager eventManager = new EventManager();
    protected String username;
    protected ReaderWriter  RW = new ReaderWriter(); // NEW - CONFIRM THIS IS CORRECT


    public void run() {
        LoginController log = new LoginController();
        readInFiles(RW);
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
        // Need to figure out how we are 100% finished modifying?????
        // When code is done and Users, Messages and Events have been modifiied and no more changes
        // will be made, then we write
        RW.write(userManager.getUserMap());
        RW.write(messageManager.getAllUserMessages());
        RW.write(eventManager.getAllEvents());
    }

    private void readInFiles(ReaderWriter RW){
        RW.read("users");
        RW.read("messages");
        RW.read("events");
    }
}
