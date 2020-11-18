import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Refers to the controller class that handles all of the other controller classes.
 */
public class MainController {
    protected MessageManager messageManager;
    protected UserManager userManager;
    protected EventManager eventManager;
    protected String username;
    protected ReaderWriter RW;

    /**
     * Constructs a MainController object with MessageManager, UserManager, EventManager, ReaderWriter objects,
     * and a String username.
     */
    public MainController() {
        messageManager = new MessageManager();
        userManager = new UserManager();
        eventManager = new EventManager();
        RW = new ReaderWriter();
        username = "";
    }


    // Prompt them with the question - do you want to read in files?
    // If yes, do serialize
    // if no, then run the program with a clean slate so no de-serialize

    // Should I even check first if the files exist? If they do, then prompt them?

    // Check if the files even exist before even prompting the User to choose

    /**
     * This method declares three new files for users, messages, and events and returns 0, 1 or 2 based on which files
     * exist.
     * @return Returns 0 if only the users is a file, 1 if users, messages, and events are files, and 2 otherwise.
     */
    public int filesExist() {

        File users = new File("users.ser");
        File messages = new File("messages.ser");
        File events = new File("events.ser");
        if (users.isFile() && !messages.isFile() && !events.isFile()) {
            return 0;
        } else if (users.isFile() && messages.isFile() && events.isFile()) {
            return 1;
        }
        else {
            return 2;
        }
    }

    /**
     * This method is responsible for getting the yser to determine whether or not they want to use pre-existing files
     * when all users, messages, and events already exist. If they choose to use pre-existing files, those files will
     * be read in.
     */
    public void fileQuestion() {
        Scanner question = new Scanner(System.in);
        try {
            System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
            String answer = question.nextLine();  // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                System.out.println("Invalid Input: Please type 'Yes' or 'No'");
                answer = question.nextLine();
            } if (answer.equalsIgnoreCase("Yes")) {
                readInFiles(RW, userManager, messageManager, eventManager);
                System.out.println("Files downloaded.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Error: Please type 'Yes' or 'No'");
            question.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for getting the yser to determine whether or not they want to use pre-existing files
     * when only users exists. If they choose to use pre-existing files, those files wil be read in.
     */
    public void fileQuestionUserOnlyExists() {
        Scanner question = new Scanner(System.in);
        try {
            System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
            String answer = question.nextLine();  // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                System.out.println("Invalid Input: Please type 'Yes' or 'No'");
                answer = question.nextLine();
            } if (answer.equalsIgnoreCase("Yes")) {
                readInFiles(RW, userManager);
                System.out.println("Files downloaded.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Error: Please type 'Yes' or 'No'");
            question.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for calling the appropriate controller depending on the user.
     */
    public void run() {
        LoginController log = new LoginController();
        this.username = log.login(userManager, messageManager);
        if (username.equals("q")){
            return;
        }
        String type = this.userManager.getUserType(this.username);
        if(type.equals("organizer")){
            OrganizerController controller = new OrganizerController(userManager, eventManager, messageManager, username);
            controller.run();
        }
        else if(type.equals("attendee")){
            AttendeeController controller = new AttendeeController(userManager, eventManager, messageManager, username);
            controller.run();
        }
        else if(type.equals("speaker")){
            SpeakerController controller = new SpeakerController(userManager, eventManager, messageManager, username);
            controller.run();
        }

        RW.write(userManager.getUserMap());
        RW.write(messageManager.getAllUserMessages());
        RW.write(eventManager.getAllEvents());
    }

    /**
     * This method is responsible for setting the maps of the managers from the data in the files.
     * @param RW Refers to the ReaderWriter object that reads and writes to files.
     * @param UM Refers to the UserManager object that is responsible for dealing with anything user-related.
     * @param MM Refers to the MessageManager object that is responsible for dealing with anything message-related.
     * @param EM Refers to the EventManager object that is responsible for dealing with anything event-related.
     * @throws IOException Refers to the exception that occurs when the program cannot read from file.
     * @throws ClassNotFoundException Refers to the exception that occurs if the class specified is not found.
     */
    private void readInFiles(ReaderWriter RW, UserManager UM, MessageManager MM, EventManager EM) throws IOException, ClassNotFoundException {
        UM.setUserMap(RW.readUsers("users"));
        MM.setAllUserMessages(RW.readMessages("messages"));
        EM.setAllEvents(RW.readEvents("events"));
    }

    /**
     * This method is responsible for setting the map of users in the UserManager's userMap.
     * @param RW Refers to the UserManager object that is responsible for dealing with anything user-related.
     * @param UM Refers to the ReaderWriter object that is responsible for reading from the file.
     * @throws IOException Refers to the exception that occurs when the program cannot read from file.
     * @throws ClassNotFoundException Refers to the exception that occurs if the class specified is not found.
     */
    private void readInFiles(ReaderWriter RW, UserManager UM) throws IOException, ClassNotFoundException {
        UM.setUserMap(RW.readUsers("users"));
    }
}
