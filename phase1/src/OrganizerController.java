import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Refers to the controller class that will deal with the actions of an organizer object.
 */
public class OrganizerController extends AttendeeController {


    /**
     * Constructs an OrganizerController object.
     * @param userManager Refers to the UserManager object.
     * @param eventManager Refers to the EventManager object.
     * @param messageManager Refers to the MessageManager object.
     * @param username Refers to the username of the organizer.
     */
    public OrganizerController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username) {
        super(userManager, eventManager, messageManager, username);
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
                if(userManager.getUserMap().size() == 1) {
                    p.displayConferenceError();
                    break;
                }
                p.displayMethodPrompt();
                String recipient = scan.nextLine();
                if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    p.displayEnterMessagePrompt(recipient);
                    String messageContents = scan.nextLine();
                    sendMessage(recipient, messageContents);
                    p.displayMessageSentPrompt();
                }
                else{
                    p.displayContactListError();
                }
                break;
            case 2:
                if(messageManager.getAllUserMessages().get(this.username).size() == 0){
                    p.displayNoReply();
                    break;
                }
                else if(userManager.getUserMap().size() == 1) {
                    p.displayConferenceError();
                    break;
                }
                List<String> users = getSenders(username);
                p.displayAllSenders(users);
                p.displayEnterUserUsernamePrompt();
                String recipients = scan.nextLine();
                while (!users.contains(recipients)){
                    p.displayUserReplyError();
                    recipients = scan.nextLine();
                    if (recipients.equals("q")){
                        break;
                    }
                }
                if (recipients.equals("q")){
                    break;
                }
                p.displayEnterMessagePrompt();
                String content = scan.nextLine();
                replyMessage(content, recipients);
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
                    p.displaySuccessfulCancellation();
                }
                else{
                    p.displayEventCancellationError1();
                }
                break;
            case 6:
                List<Event> future = viewFutureEventList();
                p.displayAllFutureEvents(future);
                if (future.size() == 0){
                    break;
                }
                p.displayEventSignUpPrompt();
                String eventSignedUp = scan.nextLine();
                while (eventManager.getEvent(eventSignedUp) == null ||
                        !future.contains(eventManager.getEvent(eventSignedUp))){
                    p.displayInvalidEventSignUp();
                    eventSignedUp = scan.nextLine();
                    if (eventSignedUp.equalsIgnoreCase("q")){
                        break;
                    }
                }
                if (eventSignedUp.equalsIgnoreCase("q")){
                    break;
                }
                signUp(eventSignedUp);
                break;

            case 7:
                p.displayAddConferencePrompt();
                LocalDateTime time = askTime();
                while(!eventManager.between9to5(time) || !eventManager.checkTimeIsAfterNow(time)) {
                    if (eventManager.between9to5(time)) {
                        p.displayInvalidHourError();
                        time = askTime();
                    } else if (!eventManager.checkTimeIsAfterNow(time)) {
                        p.displayInvalidDateError();
                        time = askTime();
                    }
                }
                p.displayEventTitlePrompt();
                String name = scan.nextLine();
                p.displayEnterSpeakerPrompt();
                String speaker = scan.nextLine();
                if(!userManager.checkCredentials(speaker)) {
                    p.displaySpeakerCredentialError();
                    makeSpeaker();
                } while (!(userManager.getUserType(speaker) == "speaker")){
                    System.out.println("This user is not a speaker! Please try again or enter 'q' to quit.");
                    speaker = scan.nextLine();
                    if (speaker.equalsIgnoreCase("q")) {
                        break;
                    }
                }
                if (speaker.equalsIgnoreCase("q")) {
                    break;
                }
                p.displayEnterRoomNumberPrompt();
                int num = nextInt();

                boolean added = addEvent(name, speaker, time, num);
                userManager.addSpeakingEvent(speaker, name);
                if(!added) p.displayEventCreationError();

                break;

            case 8:
                p.displayAllAttendeeMessagePrompt();
                messageAllAttendees(scan.nextLine());
                p.displayMessageSentPrompt();
                break;

            case 9:
                p.displayEventMessagePrompt();
                String eventname = scan.nextLine();
                if(eventManager.getEvent(eventname) == null) {
                    p.displayInvalidEventError();
                } else {
                    p.displayAllAttendeeEventMessagePrompt();
                    messageEventAttendees(scan.nextLine(), eventname);
                    p.displayMessageSentPrompt();
                }
                break;

            case 10:
                p.displayAllSpeakerMessagePrompt();
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
                int roomNumber = nextInt();
                boolean roomAdded = eventManager.addRoom(roomNumber);
                if(!roomAdded) p.displayRoomAlreadyExists();

                break;

            case 16:
                p.displayRoomList(eventManager.getRooms());
                break;
            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPromptOrganizer();
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
        p.displayEnterYearPrompt();
        int y = nextInt();
        p.displayEnterMonthPrompt();
        int m = nextInt();
        p.displayEnterDayPrompt();
        int d = nextInt();
        p.displayEnterHourPrompt();
        int h = nextInt();
        p.displayEnterMinutePrompt();
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
                p.displayDateError();
                d.printStackTrace();
            }
        } while(true);

        return time;
    }

    /**
     * This method makes a speaker account with data queried from the user.
     */
    private void makeSpeaker() {
        p.displayEnterUsernamePrompt();
        String username = scan.nextLine();
        while(this.userManager.checkCredentials(username) || username.length() < 3){
            if (this.userManager.checkCredentials(username)) {
                p.displayRepeatUsernameError();
            }
            else if (username.length() < 3) {
                p.displayUsernameLengthError();
            }
            username = scan.nextLine();
        }
        p.displayEnterPasswordPrompt();
        String password = scan.nextLine();
        while(password.length() < 3){
            p.displayPasswordLengthError();
            password = scan.nextLine();
        }
        p.displayEnterSpeakerNamePrompt();
        String name = scan.nextLine();
        while(name.length() < 2) {
            p.displaySpeakerNameError();
            name = scan.nextLine();
        }
        p.displayEnterSpeakerAddressPrompt();
        String address = scan.nextLine();
        while(address.length() < 6) {
            p.displayAddressLengthError();
            address = scan.nextLine();
        }
        p.displayEnterSpeakerEmailPrompt();
        String email = scan.nextLine();
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        while(!email_pattern.matcher(email).matches()){
            p.displayInvalidEmail();
            email = scan.nextLine();
        }
        createSpeakerAccount(name, address, email, username, password);
        messageManager.addUserInbox(username);
    }
    /**
     * Gets all the usernames of users who have messaged this Organizer
     * @param username of this Organizer
     * @return Returns all the usernames
     */
    private List<String> getSenders(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> users = new ArrayList<>();
        for (Message message: allMessages){
            String name = messageManager.getSender(message);
            if(!users.contains(name)){
                users.add(name);
            }
        }
        return users;
    }
}
