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
                if(messageManager.checkIsMessageable(recipient, this.username)) {
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
                viewEventList(this.username);
                break;
            case "view my scheduled events":
                viewSignedUpForEvent(this.username);
                break;
            case "cancel spot":
                System.out.println("What spot would you like to cancel?");
                String cancel = scan.nextLine();
                cancelSpotInEvent(cancel);
                break;
            case "sign up for event":
                System.out.println("What spot would you like to sign up for?");
                String eventSignedUp = scan.nextLine();
                signUp(eventSignedUp);
                break;
            case "add user to contact list":
                System.out.println("Enter the username you would you like to add to your contact list?");
                String newContactUsername = scan.nextLine();
                // need to implement this method in MessageManager.java
                if(messageManager.checkIfMessageable(newContactUsername, this.username)){
                    addUserToMessageable(newContactUsername);
                }
                else{
                    System.out.println("Invalid username, please try again.");
                }
                break;
        }
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
        // As there is no set implementation of the createNewMessage method right now, I am going to
        // directly access the message entity just to get a basic framework of this method for the time being
        Message newMessage = new Message(messageContents, this.username, recipient);
        messageManager.allUserMessages.get(recipient).add(newMessage);
    }

    /**
     * Sends a reply to the oldest message in an attendees inbox
     * @param message: The content of the message the Attendee is sending
     * @param originalMessenger: The username of the User we are replying too
     */
    public void replyMessage(String message, String originalMessenger) {
        // As there is no set implementation of the createNewMessage method right now, I am going to
        // directly access the message entity just to get a basic framework of this method for the time being
        List<Message> userInbox = messageManager.allUserMessages.get(this.username);
        Message replyMessage = new Message(message, this.username, originalMessenger);
        messageManager.allUserMessages.get(originalMessenger).add(replyMessage);
        userInbox.remove(userInbox.size() - 1); //remove the message we just viewed from our inbox after we reply to it
    }

    /**
     * Prints the event list for the entire conference
     * @param username: The username of this Attendee
     */
    public void viewEventList(String username) {
        Map<String, Event> events = eventManager.getAllEvents();
        System.out.println(events);
    }

    /**
     * Prints the scheduled events of an attendee
     * @param username: The username of this Attendee
     */
    public void viewSignedUpForEvent(String username) {
        List<String> signedUpFor = userManager.getAttendingEvents(username);
        System.out.println(signedUpFor);
    }

    /**
     * Removes an attendee from an event they were signed up for
     * @param eventName: The name of the Event we want to cancel our spot in
     */
    public void cancelSpotInEvent(String eventName) {
        // Need method in the User Manager to modify the attendingEvents List
    }

    /**
     * Signs an attendee up for a new event
     * @param eventName: The name of the Event we want to sign up for
     */
    public void signUp(String eventName) {
        // Need method in the User Manager to modify the attendingEvents List
    }

    /**
     * Adds another user to an attendees contact list
     * @param username: The useranme of the User we want to add to our contacts
     */
    public void addUserToMessageable(String username) {
        // How do we implement this as we currently do not have a variable that contains the contacts of an attendee
    }
}
