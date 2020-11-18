import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {
    protected MessageManager messageManager;
    protected UserManager userManager;
    protected EventManager eventManager;
    protected String username;
    protected ReaderWriter RW;

    public MainController() {
        messageManager = new MessageManager();
        userManager = new UserManager();
        eventManager = new EventManager();
        RW = new ReaderWriter();
        username = "";
    }


    // Prompt them with the question - do you want to read in files?
    // If yes, de serialize
    // if no, then run the program with a clean slate so no de-serialize

    // Should I even check first if the files exist? If they do, then prompt them?

    // Check if the files even exist before even prompting the User to choose
    public int filesExist() {

        File users = new File("users.ser");
        File messages = new File("messages.ser");
        File events = new File("events.ser");
        File rooms = new File("rooms.ser");
        if (users.isFile() && messages.isFile() && !events.isFile() && !rooms.isFile()) {
            return 0;
        } else if (users.isFile() && messages.isFile() && !events.isFile() && rooms.isFile()) {
            return 1;
        } else if (users.isFile() && messages.isFile() && events.isFile() && rooms.isFile()) {
            return 2;
        } else {
            return 3; // nothing exists
        }
    }

    public void fileQUserMssgExists() {
        Scanner question = new Scanner(System.in);
        try {
            System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
            String answer = question.nextLine();  // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                System.out.println("Invalid Input: Please type 'Yes' or 'No'");
                answer = question.nextLine();
            } if (answer.equalsIgnoreCase("Yes")) {
                readInFiles(RW, userManager, messageManager);
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

    public void fileQUserMssgRoomsExists() {
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

    public void fileQAllExists() {
        Scanner question = new Scanner(System.in);
        try {
            System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
            String answer = question.nextLine();  // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                System.out.println("Invalid Input: Please type 'Yes' or 'No'");
                answer = question.nextLine();
            } if (answer.equalsIgnoreCase("Yes")) {
                readInAllFiles(RW, userManager, messageManager, eventManager);
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
        RW.writeRoom(eventManager.getRooms());
    }

    private void readInAllFiles(ReaderWriter RW, UserManager UM, MessageManager MM, EventManager EM) throws IOException, ClassNotFoundException {
        UM.setUserMap(RW.readUsers("users"));
        MM.setAllUserMessages(RW.readMessages("messages"));
        EM.setAllEvents(RW.readEvents("events"));
        EM.setRooms(RW.readRooms("rooms"));
    }

    private void readInFiles(ReaderWriter RW, UserManager UM, MessageManager MM) throws IOException, ClassNotFoundException {
        UM.setUserMap(RW.readUsers("users"));
        MM.setAllUserMessages(RW.readMessages("messages"));
    }

    private void readInFiles(ReaderWriter RW, UserManager UM, MessageManager MM, EventManager EM) throws IOException, ClassNotFoundException {
        UM.setUserMap(RW.readUsers("users"));
        MM.setAllUserMessages(RW.readMessages("messages"));
        EM.setRooms(RW.readRooms("rooms"));
    }
}
