import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * A controller that deals with Attendee users
 */
public class AttendeeController{
    private Scanner scan = new Scanner(System.in);
    UserManager userManager;
    EventManager eventManager;
    MessageManager messageManager;
    String username;

    public AttendeeController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                              String username){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
    }

    /**
     * Runs the Attendee controller by asking for input and performing the actions
     */
    public void run(){
        viewOptions();
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
                System.out.println("Who would you like to message? (Please enter the username of the recipient)");
                String recipient = scan.nextLine();
                if(userManager.getUserMap().size() == 1) {
                    System.out.println("There are currently no other users who are registered within this " +
                            "conference. Please try at a later time.");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                else if(!messageManager.checkIsMessageable(recipient, this.username, userManager)){
                    System.out.println("Sorry, it seems you are unable to message this user. Please wait for this " +
                            "user to register for the conference.");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                System.out.println("What message would you like to send to: " + recipient + ".");
                String messageContents = scan.nextLine();
                sendMessages(recipient, messageContents);
                break;

            case 2:
                if(messageManager.getAllUserMessages().get(this.username).size() == 0){
                    System.out.println("You currently have no messages to reply to.");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                else if(userManager.getUserMap().size() == 1) {
                    System.out.println("You are unable to reply to any messages as there are currently no other " +
                            "registered users. Please try at a later time.");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                System.out.println("This is the oldest message in your inbox: '" +
                        messageManager.viewMessages(this.username).get(messageManager.viewMessages(this.username).size()
                                -  1) + "'. How would you like to respond?");
                String response = scan.nextLine();
                String responseUsername = messageManager.viewMessages(this.username).
                        get(messageManager.viewMessages(this.username).size() -  1).getSender();
                replyMessage(response, responseUsername);
                break;

            case 3:
                System.out.println("Here is a list of all the available events at this conference: ");
                viewEventList();
                break;

            case 4:
                System.out.println("Here is a list of events you have signed up for: ");
                viewSignedUpForEvent(this.username);
                break;

            case 5:
                System.out.println("What is the name of the event you no longer want to attend?");
                String cancel = scan.nextLine();
                if(!userManager.getAttendingEvents(this.username).contains(cancel)) {
                    System.out.println("Cancellation was unsuccessful since this event is not included in the events " +
                            "you are attending. Please try again.");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                else if(userManager.getAttendingEvents(this.username).size() == 0){
                    System.out.println("You are currently not attending any events. For future use, you must be " +
                            "signed up for an event to use this feature.");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                cancelSpotInEvent(cancel);
                break;

            case 6:
                System.out.println("What is the name of the event you would like to sign up for?");
                String eventSignedUp = scan.nextLine();
                if(!eventManager.getAllEvents().containsKey(eventSignedUp)) {
                    System.out.println("Sign Up was unsuccessful as the event you are trying to sign up for does not" +
                            "exist");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                else if(eventManager.getAllEvents().size() == 0){
                    System.out.println("There are currently no events in this conference. Please wait until event(s)" +
                            "have been added to use this feature.");
                    System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                    break;
                }
                signUp(eventSignedUp);
                break;

            case 14:
                viewOptions();
                break;

            default:
                System.out.println("Invalid Input, please try again.");
                System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
                break;
        }
    }

    /**
     * Prints all the options the user can perform
     */
    private void viewOptions(){
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n" +
                "(6) Add User to Contact List\n(14) View Options \n(15) End");
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }

    /**
     * Prints all the messages that this attendee has received
     * @param username: The username of the Attendee
     */
    public void viewMessages(String username) {
        messageManager.printMessages(this.username);
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }

    /**
     * Sends a message to a specified user
     * @param recipient: The username of the recipient
     * @param messageContents: The content of the message the Attendee is sending
     */
    public void sendMessages(String recipient, String messageContents) {
        Message newMessage = messageManager.createNewMessage(messageContents, this.username, recipient);
        messageManager.addMessage(recipient, newMessage);
        System.out.println("Message Sent");
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
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
        // Should we have a method in MessageManager to handle removing a message?
        userInbox.remove(userInbox.size() - 1); //remove the message we just viewed from our inbox after replying
        System.out.println("Successfully Replied to Message");
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }

    /**
     * Prints the event list for the entire conference
     */
    public void viewEventList() {
        HashMap<String, Event> events = eventManager.getAllEvents();
        System.out.println("Here are all the available events: ");
        System.out.println(events);
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }

    /**
     * Prints the scheduled events of an attendee
     * @param username: The username of this Attendee
     */
    public void viewSignedUpForEvent(String username) {
        List<String> signedUpFor = userManager.getAttendingEvents(username);
        System.out.println(signedUpFor);
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }

    /**
     * Removes an attendee from an event they were signed up for
     * @param eventName: The name of the Event we want to cancel our spot in
     */
    public void cancelSpotInEvent(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.cancelEventSpot(this.username, event, eventManager);
        System.out.println("Successfully Cancelled Spot in Event");
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }

    /**
     * Signs an attendee up for a new event
     * @param eventName: The name of the Event we want to sign up for
     */
    public void signUp(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.signUpForEvent(this.username, event, eventManager);
        System.out.println("Successfully Signed up for Event");
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }
}
