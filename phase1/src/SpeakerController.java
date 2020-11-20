import java.util.*;


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
        p.displayOptions3();
        p.displayTaskInput();
        int input = nextInt();
        while (input != 6){
            determineInput(input);
            input = nextInt();
        }
    }

    private void determineInput(int input){
        switch (input) {
            case 0:
                viewMessages(this.username);
                break;
            case 1:
                viewScheduledEvents(this.username);
                break;
            case 2:
                List<Event> allEvents = eventManager.chronologicalEvents(userManager.getSpeakingEvents(username));
                p.displayAllEventsGiven(allEvents);
                if (allEvents.size() == 0){
                    break;
                }
                p.displayEnterNumberOfEventsPrompt();
                String strnum = scan.nextLine();
                if (strnum.equals("q")){
                    break;
                }
                int num = Integer.parseInt(strnum);
                while(num < 1 || num > allEvents.size()){
                    p.displayNumberOfEventsError();
                    strnum = scan.nextLine();
                    if (strnum.equals("q")){
                        break;
                    }
                    num = Integer.parseInt(strnum);
                }
                if (strnum.equals("q")){
                    break;
                }
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
                    if (allEvents.contains(eventManager.getEvent(next)) && !eventNames.contains(next)) {
                        eventNames.add(next);
                    }
                    else if(allEvents.contains(eventManager.getEvent(next))){
                        p.displayEventAlreadyAddedError();
                        i--;
                    }
                    else if(!allEvents.contains(next)){
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
                if(messageManager.getAllUserMessages().get(this.username).size() == 0){
                    p.displayNoReply();
                    break;
                }
                else if(userManager.getUserMap().size() == 1) {
                    p.displayConferenceError();
                    break;
                }
                List<String> attendees = getAttendees(username);
                p.displayAllSenders(attendees);
                if (attendees.size() == 0){
                    break;
                }
                p.displayEnterAttendeeUsernamePrompt();
                scan.nextLine();
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
                p.displayEventSelectorPrompt();
                viewScheduledEvents(username);
                String eventName = scan.nextLine();
                if(eventManager.events.containsKey(eventName)){
                    Set<User> eventAttendees = eventManager.getEventAttendees(eventName);
                    p.displayEventAttendeesList(eventAttendees);
                    String toMessage = scan.nextLine();
                    ArrayList<String> usernameList = new ArrayList<String>();
                    for (User user: eventAttendees){
                        usernameList.add(userManager.getUsername(user));
                    }
                    if(usernameList.contains(toMessage)){
                        p.displayEnterMessagePrompt();
                        String messageContent = scan.nextLine();
                        replyMessage(toMessage, messageContent);
                        break;
                    } else {
                        p.displayInvalidInputError();
                    }
                } else{
                    p.displayInvalidInputError();
                    break;
                }

            case 5:
                viewOptions();
                break;
            default:
                p.displayInvalidInputError();
                viewOptions();
                break;
        }
        p.displayNextTaskPromptSpeaker();
    }

    private void viewOptions(){
        p.displayOptions3();
    }

    private List<String> getAttendees(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> attendees = new ArrayList<>();
        for (Message message: allMessages){
            String name = messageManager.getSender(message);
            if(!attendees.contains(name)){
                attendees.add(name);
            }
        }
        return attendees;
    }

    private void viewMessages(String username) {
        List<Message> allMessages = messageManager.viewMessages(username);
        p.displayPrintMessages(allMessages);
    }

    private void viewScheduledEvents(String username){
        List<String> allEvents = userManager.getSpeakingEvents(username);
        List<String> notHappened = eventManager.eventNotHappened(allEvents);
        List<Event> chronological  = eventManager.chronologicalEvents(notHappened);
        p.displayAllFutureEventsGiving(chronological);
    }

    private void sendBlastMessage(List<String> eventNames, String message){
        messageManager.speakerBlastMessage(eventNames, message, eventManager, this.username);
        p.displayMessageSentPrompt2();
    }

    private void replyMessage(String recipient, String content){
        Message message = messageManager.createNewMessage(content, username, recipient);
        messageManager.addMessage(recipient, message);
        p.displayMessageSentPrompt();
    }

    private int nextInt() {
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
