import java.util.*;

public class AttendeeController extends MainController {
    private Scanner scan = new Scanner(System.in);

    public void run(){
        String input = "";
        input = scan.nextLine().toUpperCase();
        while (!input.equals("END")){
            determineInput(input);
            input = scan.nextLine().toUpperCase();
        }
    }

    private void determineInput(String input) {
        switch (input) {
            case "See Inbox":
                viewMessages(this.username);
                break;
            case "Send Message":
                System.out.println("Who would you like to message?");
                String recipient = scan.nextLine();
                if(checkIsMessageable(recipient, this.username)) {
                    System.out.println("What message would you like to send to: " + recipient);
                    String messageContents = scan.nextLine();
                    sendMessages(recipient, messageContents);
                }
                else{
                    System.out.println("Sorry, this person is not in your contact list. Please try again");
                }
                break;
            case "Reply To Message":
                System.out.println("This is the oldest message in your inbox: '" +
                        messageManager.viewMessages(this.username).get(messageManager.viewMessages(this.username).size()
                                -  1) + "'. How would you like to respond?");
                String response = scan.nextLine();
                String responseUsername = messageManager.viewMessages(this.username).
                        get(messageManager.viewMessages(this.username).size() -  1).getSender();
                replyMessage(response, responseUsername);
                break;
            case "View Event List":
                viewEventList(this.username);
                break;
            case "View My Scheduled Events":
                viewSignedUpForEvent(this.username);
                break;
            case "Cancel Spot":
                System.out.println("What spot would you like to cancel?");
                String cancel = scan.nextLine();
                cancelSpotInEvent(cancel);
                break;
            case "Sign Up For Event":
                System.out.println("What spot would you like to sign up for?");
                String eventSignedUp = scan.nextLine();
                signUp(eventSignedUp);
                break;
            case "Add User To Contact List":
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

    public void viewMessages(String username) { messageManager.printMessages(this.username); }

    public void sendMessages(String recipient, String messageContents) {
        // As there is no set implementation of the createNewMessage method right now, I am going to
        // directly access the message entity just to get a basic framework of this method for the time being
        Message newMessage = new Message(messageContents, this.username, recipient);
        messageManager.allUserMessages.get(recipient).add(newMessage);
    }

    public void replyMessage(String message, String originalMessenger) {
        // As there is no set implementation of the createNewMessage method right now, I am going to
        // directly access the message entity just to get a basic framework of this method for the time being
        List<Message> userInbox = messageManager.allUserMessages.get(this.username);
        userInbox.remove(userInbox.size() - 1);
        Message replyMessage = new Message(message, this.username, originalMessenger);
        messageManager.allUserMessages.get(originalMessenger).add(replyMessage);
    }

    public void viewEventList(String username) {
        Map<String, Event> events = eventManager.getAllEvents();
        System.out.println(events);
    }

    public void viewSignedUpForEvent(String username) { userManager.getAttendingEvents(this.username);}

    public void cancelSpotInEvent(String eventName) {
        // Need method in the User Manager to modify the attendingEvents List
    }

    public void signUp(String eventName) {
        // Need method in the User Manager to modify the attendingEvents List
    }

    public void addUserToMessageable(String username) {
        // How do we implement this as we currently do not have a variable that contains the contacts of an attendee
    }
}
