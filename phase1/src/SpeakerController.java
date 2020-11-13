import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A controller that deals with Speaker users
 */
public class SpeakerController{
    private Scanner scan = new Scanner(System.in);
    UserManager userManager;
    EventManager eventManager;
    MessageManager messageManager;
    String username;

    public SpeakerController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                             String username){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
    }
    /**
     * Runs the Speaker controller by asking for input and performing the actions
     */
    public void run(){
        System.out.println("What would you like to do?");
        System.out.println("See Inbox, \nView My Events, \nMessage Event Attendees, \nReply to Attendee, \nEnd: ");
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
    private void determineInput(String input){
        switch (input) {
            case "see inbox":
                viewMessages(this.username);
                break;
            case "view my events":
                viewScheduledEvents(this.username);
                break;
            case "message event attendees":
                System.out.println("How many event's attendees would you like to message: ");
                String number = scan.nextLine();
                System.out.println("Please enter the number of events: ");
                String strnum = scan.nextLine();
                int num = Integer.parseInt(strnum);
                List<String> eventNames = new ArrayList<>();
                for (int i = 0; i < num; i++) {
                    if (i == 0) {
                        System.out.println("Please enter the name of the first event: ");
                        }
                    else {
                        System.out.println("Please enter the name of the next event: ");
                        }
                    String next = scan.nextLine();
                    eventNames.add(next);
                    }
                    System.out.println("Please enter the message: ");
                String message = scan.nextLine();
                sendBlastMessage(eventNames, message);
            case "reply to attendee":
                System.out.println("Which attendee are you replying to: ");
                List<String> attendees = getAttendees(username);
                for (String attendee: attendees){
                    System.out.println(attendee);
                }
                String recipient = scan.nextLine();
                System.out.println("Please enter the message: ");
                String content = scan.nextLine();
                replyMessage(recipient, content);
            case "options":
                viewOptions();
            break;
        }
    }

    /**
     * Prints all the options the user can perform
     */
    private void viewOptions(){
        System.out.println("See Inbox, \nView My Events, \nMessage Event Attendees, \nReply to Attendee, \nEnd");
        System.out.println("Please enter next task: ");
    }

    /**
     * Gets all the usernames of attendees who have messaged this Speaker
     * @param username of this Speaker
     * @return Returns all the usernames
     */
    private List<String> getAttendees(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> attendees = new ArrayList<>();
        for (Message message: allMessages){
            attendees.add(messageManager.getRecipient(message));
        }
        return attendees;
    }

    /**
     * Prints all the messages the Speaker has received
     * @param username of the Speaker
     */
    private void viewMessages(String username) {
        messageManager.printMessages(username);
        System.out.println("Please enter next task (reminder, you can type 'Options' to see what you can do: ");

    }

    /**
     * Prints all the events the Speaker is scheduled for
     * @param username of the Speaker
     */
    private void viewScheduledEvents(String username){
        List<String> allEvents = userManager.getSpeakingEvents(username);
        System.out.println(allEvents);
        System.out.println("Please enter next task (reminder, you can type 'Options' to see what you can do: ");
    }

    /**
     * Sends a message to attendees of events that the Speaker has given
     * @param eventNames: All the events whose attendees the Speaker wants to respond to
     * @param message: What the Speaker is sending
     */
    private void sendBlastMessage(List<String> eventNames, String message){
        messageManager.speakerBlastMessage(eventNames, message, eventManager, this.username);
        System.out.println("Messages sent");
        System.out.println("Please enter next task (reminder, you can type 'Options' to see what you can do: ");
    }

    /**
     * Replies to an attendee that has sent a message to this Speaker
     * @param recipient: The attendee to send to
     * @param content: What the Speaker is sending
     */
    private void replyMessage(String recipient, String content){
        Message message = messageManager.createNewMessage(content, username, recipient);
        System.out.println("Message sent");
        System.out.println("Please enter next task (reminder, you can type 'Options' to see what you can do: ");
    }
}
