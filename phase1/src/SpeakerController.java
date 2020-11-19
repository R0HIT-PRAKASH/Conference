import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * A controller that deals with Speaker users
 */
public class SpeakerController{
    private Scanner scan = new Scanner(System.in);
    public UserManager userManager;
    public EventManager eventManager;
    public MessageManager messageManager;
    public String username;
    Presenter p;

    /**
     * Creates a Speaker Controller
     * @param userManager user use case
     * @param eventManager event use case
     * @param messageManager message use case
     * @param username username of the user
     */
    public SpeakerController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                             String username){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
        p = new Presenter();
    }
    /**
     * Runs the Speaker controller by asking for input and performing the actions
     */
    public void run(){
        p.displayTaskInput();
        p.displayNextTaskPromptSpeaker();
        int input = scan.nextInt();
        while (input != 5){
            determineInput(input);
            input = scan.nextInt();
        }
    }

    /**
     * Looks at the input from user and decides what to do
     * @param input: The input from the user
     */
    private void determineInput(int input){
        switch (input) {
            case 0:
                viewMessages(this.username);
                break;
            case 1:
                viewScheduledEvents(this.username);
                break;
            case 2:
                List<String> allEvents = userManager.getSpeakingEvents(username);
                List<Event> priorEvents = eventManager.chronologicalEvents(eventManager.eventHappened(allEvents));
                p.displayAllEventsGiven(priorEvents);
                p.displayEnterNumberOfEventsPrompt();
                String strnum = scan.nextLine();
                if (strnum.equals("q")){
                    break;
                }
                int num = Integer.parseInt(strnum);
                String next = "";
                List<String> eventNames = new ArrayList<>();
                for (int i = 0; i < num; i++) {
                    if (i == 0) {
                        p.displayEnterEventNamePrompt();
                        }
                    else {
                        p.displayEnterEventNamePrompt2();
                    }
                    next = scan.nextLine();
                    if (next.equals("q")){
                        break;
                    }
                    if (priorEvents.contains(next) && !eventNames.contains(next)) {
                        eventNames.add(next);
                    }
                    else if(priorEvents.contains(next)){
                        p.displayEventAlreadyAddedError();
                        i--;
                    }
                    else if(!priorEvents.contains(next)){
                        p.displayEventNotGivenError();
                        i--;
                    }
                }
                if(next.equals("q")) {
                    break;
                }
                p.displayEnterMessagePrompt();
                String message = scan.nextLine();
                sendBlastMessage(eventNames, message);
                break;
            case 3:
                p.displayEnterAttendeeUsernamePrompt();
                List<String> attendees = getAttendees(username);
                for (String attendee: attendees){
                    System.out.println(attendee);
                }
                String recipient = scan.nextLine();
                while (!attendees.contains(recipient)){
                    p.displayUserReplyError();
                    recipient = scan.nextLine();
                    if (recipient.equals("q")){
                        break;
                    }
                }
                if (recipient.equals("q")){
                    break;
                }
                p.displayEnterMessagePrompt();
                String content = scan.nextLine();
                replyMessage(recipient, content);
                break;
            case 4:
                viewOptions();
                break;
            default:
                p.displayInvalidInputError();
                viewOptions();
                break;
        }
        p.displayNextTaskPromptSpeaker();
    }

    /**
     * Prints all the options the user can perform
     */
    private void viewOptions(){
        p.displayOptions3();
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

    }

    /**
     * Prints all the events the Speaker is scheduled for
     * @param username of the Speaker
     */
    private void viewScheduledEvents(String username){
        List<String> allEvents = userManager.getSpeakingEvents(username);
        List<String> notHappened = eventManager.eventNotHappened(allEvents);
        List<Event> chronological  = eventManager.chronologicalEvents(notHappened);
        p.displayAllFutureEvents(chronological);
    }

    /**
     * Sends a message to attendees of events that the Speaker has given
     * @param eventNames: All the events whose attendees the Speaker wants to respond to
     * @param message: What the Speaker is sending
     */
    private void sendBlastMessage(List<String> eventNames, String message){
        messageManager.speakerBlastMessage(eventNames, message, eventManager, this.username);
        p.displayMessageSentPrompt2();
    }

    /**
     * Replies to an attendee that has sent a message to this Speaker
     * @param recipient: The attendee to send to
     * @param content: What the Speaker is sending
     */
    private void replyMessage(String recipient, String content){
        Message message = messageManager.createNewMessage(content, username, recipient);
        p.displayMessageSentPrompt();
    }
}
