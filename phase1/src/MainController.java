import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {
    protected MessageManager messageManager = new MessageManager();
    protected UserManager userManager = new UserManager();
    protected EventManager eventManager = new EventManager();
    protected String username;
    protected ReaderWriter  RW = new ReaderWriter(); // NEW - CONFIRM THIS IS CORRECT

    // Prompt them with the question - do you want to read in files?
    // If yes, de serialize
    // if no, then run the program with a clean slate so no de-serialize

    // Should I even check first if the files exist? If they do, then prompt them?

    // Check if the files even exist before even prompting the User to choose
    public boolean filesExist() {
        File users = new File("users.ser");
        File messages = new File("messages.ser");
        File events = new File("events.ser");
        return users.exists() && messages.exists() && !events.exists();
    }

    public void fileQuestion() {
        Scanner question = new Scanner(System.in);
        try {
            System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
            String answer = question.nextLine();  // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                System.out.println("Invalid Input: Please type 'Yes' or 'No'");
                answer = question.nextLine();
            } if (answer.equalsIgnoreCase("Yes")) {
                readInFiles(RW);
                System.out.println("Files downloaded.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Error: Please type 'Yes' or 'No'");
            question.nextLine();
        }
    }

    public void run() {
        LoginController log = new LoginController();
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
