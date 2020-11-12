import java.util.*;

/**
 * A controller that deals with Attendee users
 */
public class AttendeeController extends MainController {
    private Scanner scan = new Scanner(System.in);

    /**
     * Runs the Attendee controller by asking for input and performing the actions
     */
    public void run(){
        System.out.println("What would you like to do?");
        System.out.println("See Inbox, \nSend Message, \nReply to Message, \nView Event List, " +
                "\nView My Scheduled Events, \nCancel Event Reservation, \n Sign up for Event, " +
                "\nAdd User to Contact List  ,\nEnd: ");
        String input = "";
        input = scan.nextLine().toLowerCase();
        while (!input.equals("end")){
            determineInput(input);
            input = scan.nextLine().toLowerCase();
        }
    }

    /**
     * Looks at the input from user and decides what to do
     * @param input: The input from the user
     */
    private void determineInput(String input) {
        switch (input) {
            case "see inbox":
                viewMessages(this.username);
                break;
            case "send message":
                System.out.println("Who would you like to message?");
                String recipient = scan.nextLine();
                if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    System.out.println("What message would you like to send to: " + recipient);
                    String messageContents = scan.nextLine();
                    sendMessages(recipient, messageContents);
                }
                else{
                    System.out.println("Sorry, this person is not in your contact list. Please try again");
                }
                break;
            case "reply to message":
                System.out.println("This is the oldest message in your inbox: '" +
                        messageManager.viewMessages(this.username).get(messageManager.viewMessages(this.username).size()
                                -  1) + "'. How would you like to respond?");
                String response = scan.nextLine();
                String responseUsername = messageManager.viewMessages(this.username).
                        get(messageManager.viewMessages(this.username).size() -  1).getSender();
                replyMessage(response, responseUsername);
                break;
            case "view event list":
                viewEventList();
                break;
            case "view my scheduled events":
                viewSignedUpForEvent(this.username);
                break;
            case "cancel event reservation":
                System.out.println("What spot would you like to cancel?");
                String cancel = scan.nextLine();
                if(userManager.getAttendingEvents(this.username).contains(cancel)) {
                    cancelSpotInEvent(cancel);
                }
                else{
                    System.out.println("Cancellation was unsuccessful since this event is not included in the events " +
                            "you are attending");
                }
                break;
            case "sign up for event":
                System.out.println("What spot would you like to sign up for?");
                String eventSignedUp = scan.nextLine();
                if(eventManager.getAllEvents().containsKey(eventSignedUp)) {
                    signUp(eventSignedUp);
                }
                else{
                    System.out.println("Sign Up was unsuccessful as the event you are trying to sign up for does not" +
                            "exist");
                }
                break;
            case "add user to contact list":
                System.out.println("Enter the username you would you like to add to your contact list?");
                String newContactUsername = scan.nextLine();
                if(messageManager.checkIsMessageable(newContactUsername, this.username, userManager)){
                    addUserToMessageable(newContactUsername);
                }
                else{
                    System.out.println("Invalid username, please try again.");
                }
                break;
            case "options":
                viewOptions();
                break;
            default:
                System.out.println("Invalid Input, please try again.");
                break;
        }
    }

    /**
     * Prints all the options the user can perform
     */
    private void viewOptions(){
        System.out.println("See Inbox, \nSend Message, \nReply to Message, \nView Event List, " +
                "\nView My Scheduled Events, \nCancel Event Reservation, \n Sign up for Event, " +
                "\nAdd User to Contact List  ,\nEnd: ");
        System.out.println("Please enter next task: ");
    }

    /**
     * Prints all the messages that this attendee has received
     * @param username: The username of the Attendee
     */
    public void viewMessages(String username) { messageManager.printMessages(this.username); }

    /**
     * Sends a message to a specified user
     * @param recipient: The username of the recipient
     * @param messageContents: The content of the message the Attendee is sending
     */
    public void sendMessages(String recipient, String messageContents) {
        Message newMessage = messageManager.createNewMessage(messageContents, this.username, recipient);
        messageManager.addMessage(recipient, newMessage);
        System.out.println("Message Sent");
        System.out.println("Please enter next task (reminder, you can type 'options' to see what you can do: ");
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
        System.out.println("Please enter next task (reminder, you can type 'options' to see what you can do: ");
    }

    /**
     * Prints the event list for the entire conference
     */
    public void viewEventList() {
        HashMap<String, Event> events = eventManager.getAllEvents();
        System.out.println("Here are all the available events: ");
        System.out.println(events);
        System.out.println("Please enter next task (reminder, you can type 'options' to see what you can do: ");
    }

    /**
     * Prints the scheduled events of an attendee
     * @param username: The username of this Attendee
     */
    public void viewSignedUpForEvent(String username) {
        List<String> signedUpFor = userManager.getAttendingEvents(username);
        System.out.println(signedUpFor);
        System.out.println("Please enter next task (reminder, you can type 'options' to see what you can do: ");
    }

    /**
     * Removes an attendee from an event they were signed up for
     * @param eventName: The name of the Event we want to cancel our spot in
     */
    public void cancelSpotInEvent(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.cancelEventSpot(this.username, event, eventManager);
        System.out.println("Successfully Cancelled Spot in Event");
        System.out.println("Please enter next task (reminder, you can type 'options' to see what you can do: ");
    }

    /**
     * Signs an attendee up for a new event
     * @param eventName: The name of the Event we want to sign up for
     */
    public void signUp(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.signUpForEvent(this.username, event, eventManager);
        System.out.println("Successfully Signed up for Event");
        System.out.println("Please enter next task (reminder, you can type 'options' to see what you can do: ");
    }

    /**
     * Adds another user to an attendees contact list
     * @param username: The useranme of the User we want to add to our contacts
     */
    public void addUserToMessageable(String username) {
        System.out.println("Successfully Added" + username + "to Your Contact List");
        System.out.println("Please enter next task (reminder, you can type 'options' to see what you can do: ");
    }
}
