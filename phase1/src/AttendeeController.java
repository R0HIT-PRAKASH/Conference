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
                List<String> attendees = getSenders(username);
                p.displayAllSenders(attendees);
                p.displayEnterUserUsernamePrompt();
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
                p.displayEventCancelPrompt();
                String cancel = scan.nextLine();
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
        userManager.signUpForEvent(this.username, event, eventManager);
        p.displayEventSignUp();
    }

    /**
     * Gets all the usernames of users who have messaged this Attendee
     * @param username of this Attendee
     * @return Returns all the usernames
     */
    private List<String> getSenders(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> attendees = new ArrayList<>();
        for (Message message: allMessages){
            attendees.add(messageManager.getSender(message));
        }
        return attendees;
    }

    /**
     * Queries the user for an integer
     * @return int
     */
    protected int nextInt() {
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
