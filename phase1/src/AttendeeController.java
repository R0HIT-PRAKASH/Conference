import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
                messageManager.viewMessages(this.username).remove(messageManager.viewMessages(this.username).
                        size() -  1);
                replyMessage(response, responseUsername);
                break;
            case "View Event List":
                viewEventList(this.username);
                break;
            case "View My Scheduled Events":

                break;
            case "Cancel Spot":

                break;
            case "Sign Up For Event":

                break;
            case "Add User To Contact List":

                break;
        }
    }

    public void viewMessages(String username) {}

    public void sendMessages(String recipient, String messageContents) {}

    public void replyMessage(String message, String originalMessanger) {}

    public void viewEventList(String username) {}

    public void viewSignedUpForEvent(String username) {}

    public void cancelSpotInEvent(String eventName) {}

    public void signUp(String eventName) {}

    public void addUserToMessageable(String username) {}
}
