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
                System.out.println("What message would you like to send to: " + recipient);
                String messageContents = scan.nextLine();
                sendMessages(recipient, messageContents);
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

    public void sendMessages(String recipient, String messageContents) {}

    public void replyMessage(String message, String originalMessenger) {
        // need to remove message rom inbox once we respond to it
    }

    public void viewEventList(String username) {
        Map<String, Event> events = eventManager.getAllEvents();
        System.out.println(events);
    }

    public void viewSignedUpForEvent(String username) { userManager.getAttendingEvents(this.username);}

    public void cancelSpotInEvent(String eventName) {}

    public void signUp(String eventName) {}

    public void addUserToMessageable(String username) {}
}
