import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * A controller that deals with Attendee users
 */
public class AttendeeController{
    protected Scanner scan = new Scanner(System.in);
    UserManager userManager;
    EventManager eventManager;
    MessageManager messageManager;
    String username;
    boolean vip;
    Presenter p;

    /**
     * This constructs an AttendeeController object
     * @param userManager the instance of the User Manager
     * @param eventManager the instance of the Event Manager
     * @param messageManager the instance of the Message Manager
     * @param username the username of the Attendee accessing the AttendeeController
     */
    public AttendeeController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                              String username){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
        this.vip = false;
        this.p = new Presenter();
    }

    // Alternate constructor when attendee is also a VIP
    public AttendeeController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                              String username, boolean vip){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
        this.vip = vip;
        this.p = new Presenter();
    }

    /**
     * Runs the Attendee controller by asking for input and performing the actions
     */
    public void run(){
        p.displayOptions();
        p.displayTaskInput();
        int input = 0;
        input = nextInt();
        while (input != 8){ // 8 is ending condition
            determineInput(input);
            input = nextInt();
        }
    }

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
                // System.out.println("Who would you like to message? (Please enter the username of the recipient). Otherwise, type 'q' to exit");
                String recipient = scan.nextLine();
                if (recipient.equals("q")){
                    break;
                }
                else if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    p.displayEnterMessagePrompt(recipient);
                    // System.out.println("What message would you like to send to: " + recipient + ". " + "If you would no longer like to send a message, type 'q' to exit");
                    String messageContents = scan.nextLine();
                    if (messageContents.equals("q")){
                        break;
                    }
                    sendMessage(recipient, messageContents);
                    p.displayMessageSentPrompt();
                }
                else{
                    p.displayNotMessageableError();
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
                List<String> attendees = getSenders(username);
                p.displayAllSenders(attendees);
                p.displayEnterUserUsernamePrompt();
                // System.out.println("Which user are you replying to (it is case sensitive). If you no longer want to reply to a user, type 'q' to exit: ");
                String recipients = scan.nextLine();
                while (!attendees.contains(recipients)){
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
                Attendee temp = (Attendee) userManager.getUser(this.username);
                if(temp.getAttendingEvents().isEmpty()){
                    p.displayNotAttendingAnyEvents();
                    break;
                }
                viewSignedUpForEvent(this.username);
                p.displayEventCancelPrompt();
                // System.out.println("What is the name of the event you no longer want to attend? Type 'q' if you no longer want to cancel your spot in an event.");
                String cancel = scan.nextLine();
                if (cancel.equals("q")){
                    break;
                }
                if(!userManager.getAttendingEvents(this.username).contains(cancel)) {
                    p.displayEventCancelPrompt();
                    break;
                }
                else if(userManager.getAttendingEvents(this.username).size() == 0){
                    p.displayEventCancellationError2();
                    break;
                }
                cancelSpotInEvent(cancel);
                break;

            case 6:
                List<Event> future = viewFutureEventList();
                p.displayAllFutureEvents(future);
                if (future.size() == 0){
                    break;
                }
                p.displayEventSignUpPrompt();
                // System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
                String eventSignedUp = scan.nextLine();
                if (eventSignedUp.equals("q")){
                    break;
                }
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
                p.displayOptions();
                break;

            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPromptAttendee();
    }


    /**
     * Prints all the messages that this attendee has received
     * @param username: The username of the Attendee
     */
    protected void viewMessages(String username) {
        List<Message> allMessages = messageManager.viewMessages(username);
        p.displayPrintMessages(allMessages);
    }

    /**
     * Sends a message to a specified user
     * @param recipient: The username of the recipient
     * @param messageContents: The content of the message the Attendee is sending
     */
    protected void sendMessage(String recipient, String messageContents) {
        Message newMessage = messageManager.createNewMessage(messageContents, this.username, recipient);
        messageManager.addMessage(recipient, newMessage);
    }

    /**
     * Sends a reply to the oldest message in an attendees inbox
     * @param message: The content of the message the Attendee is sending
     * @param originalMessenger: The username of the User we are replying too
     */
    protected void replyMessage(String message, String originalMessenger) {
        List<Message> userInbox = messageManager.allUserMessages.get(this.username);
        Message replyMessage = messageManager.createNewMessage(message, this.username, originalMessenger);
        messageManager.addMessage(originalMessenger, replyMessage);
        p.displaySuccessfulMessage();
    }

    /**
     * Prints the event list for the entire conference
     */
    protected void viewEventList() {
        List<Event> chronological = eventManager.chronologicalEvents(eventManager.getAllEventNamesOnly());
        p.displayEventList(chronological);
    }

    /**
     * This method returns a list of events that will occur.
     * @return Returns a list of events that have not occurred yet.
     */
    protected List<Event> viewFutureEventList() {
        List<Event> chronological = eventManager.chronologicalEvents(eventManager.getAllEventNamesOnly());
        List<Event> future = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Event curr: chronological){
            if (eventManager.getTime(curr).compareTo(now) > 0){
                future.add(curr);
            }
        }
        return future;
    }

    /**
     * Prints the scheduled events of an attendee
     * @param username: The username of this Attendee
     */
    protected void viewSignedUpForEvent(String username) {
        List<String> signedUpFor = userManager.getAttendingEvents(username);
        List<Event> chronological = eventManager.chronologicalEvents(signedUpFor);
        p.displaySignedUpEvents(chronological);
    }

    /**
     * Removes an attendee from an event they were signed up for
     * @param eventName: The name of the Event we want to cancel our spot in
     */
    protected void cancelSpotInEvent(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.cancelEventSpot(this.username, event, eventManager);
        p.displaySuccessfulCancellation();
    }

    /**
     * Signs an attendee up for a new event
     * @param eventName: The name of the Event we want to sign up for
     */
    protected void signUp(String eventName) {
        Event event = eventManager.getEvent(eventName);
        if (userManager.signUpForEvent(this.username, event, eventManager)){
            p.displayEventSignUp();
        }
        else {
            p.displayEventFull();
        }
    }

    private List<String> getSenders(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> attendees = new ArrayList<>();
        for (Message message: allMessages) {
            String name = messageManager.getSender(message);
            if (!attendees.contains(name)) {
                attendees.add(name);
            }
        }
        return attendees;
    }

    /**
     * Queries the user for an integer
     * @return Returns the integer that user input.
     */
    protected int nextInt() {
        int input = 0;

        do {
            try {
                input = Integer.parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                p.displayInvalidInputError();
            }
        } while(true);

        return input;
    }
}
