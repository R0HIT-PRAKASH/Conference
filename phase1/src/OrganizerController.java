import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Refers to the controller class that will deal with the actions of an organizer object.
 */
public class OrganizerController extends AttendeeController {

    private Scanner scan;
    private Presenter p;

    /**
     * Constructs an OrganizerController object.
     * @param userManager Refers to the UserManager object.
     * @param eventManager Refers to the EventManager object.
     * @param messageManager Refers to the MessageManager object.
     * @param username Refers to the username of the organizer.
     */
    public OrganizerController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username) {
        super(userManager, eventManager, messageManager, username);
        scan = new Scanner(System.in);
        p = new Presenter();
    }



    /**
     * Runs the OrganizerController by asking for input and performing the actions
     */
    public void run(){
        p.displayOptions2();
        p.displayTaskInput();
        int input = nextInt();
        while (input != 17){ // 17 is ending condition
            determineInput(input);
            input = nextInt();
        }
    }

    /**
     * Looks at the input from user and decides what to do
     * @param input: The input from the user
     */
    private void determineInput(int input) {
        switch (input) {
            case 0:
                viewMessages(this.username);
                break;
            case 1:
                p.displayMethodPrompt();
                String recipient = scan.nextLine();
                if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    p.displayEnterMessagePrompt(recipient);
                    String messageContents = scan.nextLine();
                    sendMessages(recipient, messageContents);
                    p.displayMessageSentPrompt();
                }
                else{
                    System.out.println("Sorry, this person is not in your contact list. Please try again");
                }
                break;
            case 2:
                p.displayOldestInboxMessage(messageManager.viewMessages(this.username).
                        get(messageManager.viewMessages(this.username).size()
                        -  1).toString());
                String response = scan.nextLine();
                String responseUsername = messageManager.viewMessages(this.username).
                        get(messageManager.viewMessages(this.username).size() -  1).getSender();
                replyMessage(response, responseUsername);
                break;
            case 3:
                viewEventList();
                break;
            case 4:
                viewSignedUpForEvent(this.username);
                break;
            case 5:
                p.displayEventCancelPrompt();
                String cancel = scan.nextLine();
                if(userManager.getAttendingEvents(this.username).contains(cancel)) {
                    cancelSpotInEvent(cancel);
                }
                else{
                    p.displayEventCancellationError1();
                }
                break;
            case 6:
                p.displayEventSignUpPrompt();
                String eventSignedUp = scan.nextLine();
                if(eventManager.getAllEvents().containsKey(eventSignedUp)) {
                    signUp(eventSignedUp);
                }
                else{
                    p.displaySignUpError1();
                }
                break;

            case 7:
                p.displayAddConferencePrompt();
                LocalDateTime time = askTime();

                //scan.nextLine(); //fixes the problem where we can't enter the event title
                p.displayEventTitlePrompt();
                String name = scan.nextLine();
                p.displayEnterSpeakerPrompt();
                String speaker = scan.nextLine();
                if(!userManager.checkCredentials(speaker)) {
                    p.displaySpeakerCredentialError();
                    makeSpeaker();
                }
                p.displayEnterRoomNumberPrompt();
                int num = nextInt();

                while(!addEvent(name, speaker, time, num)) {
                    System.out.println("The event was invalid. Either the speaker or the room would be double booked. " +
                            "Please try again.");
                }
                break;

            case 8:
                p.displayAllAttendeeMessagePrompt();
                messageAllAttendees(scan.nextLine());
                p.displayMessageSentPrompt();
                break;

            case 9:
                System.out.println("Enter the event you want to message");
                String eventname = scan.nextLine();
                if(eventManager.getEvent(eventname) == null) {
                    p.displayInvalidEventError();
                } else {System.out.println("What do you want to say to all the attendees at this event? (1 line)");
                    messageEventAttendees(scan.nextLine(), eventname);
                    p.displayMessageSentPrompt();
                }
                break;

            case 10:
                System.out.println("What do you want to say to all the speakers? (1 line)");
                messageAllSpeakers(scan.nextLine());
                p.displayMessageSentPrompt();
                break;

            case 11:
                p.displayEventRemovalPrompt();
                cancelEvent(scan.nextLine());
                break;

            case 12:
                p.displayEventReschedulePrompt();
                String eventname1 = scan.nextLine();
                if(eventManager.getEvent(eventname1) == null) {
                    p.displayInvalidEventError();
                }
                LocalDateTime newTime = askTime();
                rescheduleEvent(eventname1, newTime);
                break;

            case 13:
                makeSpeaker();
                break;

            case 14:
                p.displayOptions2();
                break;


            case 15:
                p.displayRoomCreationPrompt();
                int roomNumber = Integer.parseInt(scan.nextLine());
                boolean roomAdded = eventManager.addRoom(roomNumber);
                if(roomAdded){
                    break;
                }
                else {
                    p.displayRoomAlreadyExists();
                }
                break;

            case 16:
                p.displayRoomList(eventManager.getRooms());
                break;
            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPrompt();
    }




    /**
     * This method adds an event to the set of events in the conference
     * @param name This parameter refers to the name of the event.
     * @param speaker This parameter refers to the speaker at the event.
     * @param time This parameter refers to the event time.
     * @param roomNumber This parameter refers to the room number.
     * @return Returns true if the user was added to events map and false otherwise.
     */
    boolean addEvent(String name, String speaker, LocalDateTime time, int roomNumber) {
        return eventManager.addEvent(name, speaker, time, roomNumber);
    }

    /**
     * This method sends a message to all the attendees at the conference.
     * @param message This parameter is the message text
     * @return void
     */
    void messageAllAttendees(String message) {
        createBlastMessage("attendee", message);
    }

    /**
     * This method sends a message to all the attendees at a particular event.
     * @param message This parameter is the message text
     * @param name This parameter is the event name
     * @return void
     */
    void messageEventAttendees(String message, String name) {
        messageManager.speakerBlastMessage(Arrays.asList(name), message, eventManager, this.username);
    }

    /**
     * This method sends a message to all the speakers at the conference.
     * @param message This parameter is the message text
     * @return void
     */
    void messageAllSpeakers(String message) {
        createBlastMessage("speaker", message);
    }

    /**
     * This method cancels an event
     * @param name This is the title of the event to be canceled
     * @return void
     */
    void cancelEvent(String name) {
        eventManager.getAllEvents().remove(name);
    }

    /**
     * This method reschedules an event
     * @param name The name of the event
     * @param newTime The new time
     */
    void rescheduleEvent(String name, LocalDateTime newTime) {

        if(eventManager.getAllEvents().containsKey(name)) {
            eventManager.getAllEvents().get(name).setTime(newTime);
        }
    }

    /**
     * This method creates a speaker account
     * @param name This parameter refers to the name of the speaker.
     * @param address This parameter refers to the address of the speaker.
     * @param email This parameter refers to the email of the speaker.
     * @param username This parameter refers to the username of the speaker.
     * @param password This parameter refers to the password of the speaker.
     * @return Returns true if the user was added to userMap and false otherwise.
     */
    void createSpeakerAccount(String name, String address, String email, String username, String password) {
        userManager.addUser(name, address, email, username, password, "speaker");
    }


    /**
     * This method sends messages to all people of a specific type
     * @param blastType This is the type of the individual. Choices are "speaker", "organizer" and "attendee"
     * @param message This is the actual message
     */
    void createBlastMessage(String blastType, String message) {

        Map<String, String> userTypes = userManager.getUserTypes();

        for(String user : userTypes.keySet()) {
            if(userTypes.get(user).equals(blastType)) {
                messageManager.createNewMessage(message, this.username, user);
            }
        }

    }

    /**
     * This method returns a LocalDateTime object queried from the user
     * @return LocalDateTime
     * @throws DateTimeException
     */
    private LocalDateTime getTime() throws DateTimeException {
        System.out.println("Enter a year:");
        int y = nextInt();
        System.out.println("Enter a month (1-12):");
        int m = nextInt();
        System.out.println("Enter a day:");
        int d = nextInt();
        System.out.println("Enter an hour (0-23):");
        int h = nextInt();
        System.out.println("Enter a minute (0-59):");
        int mi = nextInt();
        return LocalDateTime.of(y, m, d, h, mi);
    }

    /**
     * This repeatedly asks a user for a LocalDateTime object until they make one that is valid
     * @return LocalDateTime
     */
    private LocalDateTime askTime() {
        LocalDateTime time = LocalDateTime.now();
        do {
            try {
                time = getTime();
                break;
            } catch (DateTimeException d) {
                System.out.println("Invalid Date. Please try again.");
                d.printStackTrace();
            }
        } while(true);

        return time;
    }

    /**
     * This method makes a speaker account with data queried from the user.
     */
    private void makeSpeaker() {
        System.out.println("Enter Username: ");
        String username = scan.nextLine();
        while(this.userManager.checkCredentials(username) || username.length() < 3){
            if (this.userManager.checkCredentials(username)) {
                System.out.println("That username is already taken, please enter another one: ");
            }
            else if (username.length() < 3) {
                System.out.println("Error, username must be at least 3 characters. please enter another one: ");
            }
            username = scan.nextLine();
        }
        System.out.println("Enter Password: ");
        String password = scan.nextLine();
        while(password.length() < 3){
            System.out.println("Error, password must be at least 3 characters.\nPlease enter again:");
            password = scan.nextLine();
        }
        System.out.println("Enter the speaker name");
        String name = scan.nextLine();
        while(name.length() < 2) {
            System.out.println("Error, name must be at least 2 characters.\nPlease enter again:");
            name = scan.nextLine();
        }
        System.out.println("Enter the speaker address");
        String address = scan.nextLine();
        while(address.length() < 3) {
            System.out.println("Error, address must be at least 6 characters.\nPlease enter again:");
            address = scan.nextLine();
        }
        System.out.println("Enter the speaker Email");
        String email = scan.nextLine();
        while(email.length() < 3 || !email.contains("@")) {
            if (!email.contains("@")) {
                System.out.println("Error, email must contain '@'.\nPlease enter a valid email:");
            }
            else if (email.length() < 3) {
                System.out.println("Error, email must be at least 3 characters.\nPlease enter again:");
            }
            email = scan.nextLine();
        }
        createSpeakerAccount(name, address, email, username, password);
        messageManager.addUserInbox(username);
        System.out.println("Enter the speaker name");
        name = scan.nextLine();
        System.out.println("Enter the speaker address");
        address = scan.nextLine();
        System.out.println("Enter the speaker Email");
        email = scan.nextLine();

        createSpeakerAccount(name, address, email, username, password);
        messageManager.addUserInbox(username);
    }


    private int nextInt() {
        int input = 0;

        do {
            try {
                input = Integer.parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                p.displayInvalidInputError();
                e.printStackTrace();
            }
        } while(true);

        return input;
    }
}
