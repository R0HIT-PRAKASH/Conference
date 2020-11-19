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
        System.out.println("What would you like to do?\nEnter the corresponding number:");
        int input = 0;
        input = scan.nextInt();
        while (input != 15){ // 15 is ending condition
            determineInput(input);
            input = scan.nextInt();
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
                    p.displayNextTaskPrompt();
                    break;
                }
                p.displayMethodPrompt();
                String recipient = scan.nextLine();
                if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    p.displayEnterMessagePrompt(recipient);
                    String messageContents = scan.nextLine();
                    sendMessages(recipient, messageContents);
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
                p.displayEventSignUpPrompt();
                String eventSignedUp = scan.nextLine();
                if(!eventManager.getAllEvents().containsKey(eventSignedUp)) {
                    p.displaySignUpError1();
                    break;
                }
                else if(eventManager.getAllEvents().size() == 0){
                    p.displaySignUpError2();
                    break;
                }
                signUp(eventSignedUp);
                break;

            case 14:
                p.displayOptions();
                break;

            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPrompt();
    }


    /**
     * Prints all the messages that this attendee has received
     * @param username: The username of the Attendee
     */
    public void viewMessages(String username) {
        messageManager.printMessages(username);
    }

    /**
     * Sends a message to a specified user
     * @param recipient: The username of the recipient
     * @param messageContents: The content of the message the Attendee is sending
     */
    public void sendMessages(String recipient, String messageContents) {
        Message newMessage = messageManager.createNewMessage(messageContents, this.username, recipient);
        messageManager.addMessage(recipient, newMessage);
    }

    /**
     * Sends a reply to the oldest message in an attendees inbox
     * @param message: The content of the message the Attendee is sending
     * @param originalMessenger: The username of the User we are replying too
     */
    public void replyMessage(String message, String originalMessenger) {
        List<Message> userInbox = messageManager.allUserMessages.get(this.username);
        Message replyMessage = messageManager.createNewMessage(message, this.username, originalMessenger);
        messageManager.addMessage(originalMessenger, replyMessage);
        p.displaySuccessfulMessage();
    }

    /**
     * Prints the event list for the entire conference
     */
    public void viewEventList() {
        List<Event> chronological = eventManager.chronologicalEvents();
        p.displayEventList(chronological);
    }

    /**
     * Prints the scheduled events of an attendee
     * @param username: The username of this Attendee
     */
    public void viewSignedUpForEvent(String username) {
        List<String> signedUpFor = userManager.getAttendingEvents(username);
        p.displaySignedUpEvents(signedUpFor);
    }

    /**
     * Removes an attendee from an event they were signed up for
     * @param eventName: The name of the Event we want to cancel our spot in
     */
    public void cancelSpotInEvent(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.cancelEventSpot(this.username, event, eventManager);
        p.displaySuccessfulCancellation();
    }

    /**
     * Signs an attendee up for a new event
     * @param eventName: The name of the Event we want to sign up for
     */
    public void signUp(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.signUpForEvent(this.username, event, eventManager);
        p.displayEventSignUp();
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
