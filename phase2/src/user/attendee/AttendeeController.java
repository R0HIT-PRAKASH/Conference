package user.attendee;

import event.Event;
import event.EventManager;
import message.Message;
import message.MessageManager;
import request.Request;
import request.RequestManager;
import user.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A controller that deals with Attendee users
 */
public class AttendeeController{

    public UserManager userManager;
    public EventManager eventManager;
    public MessageManager messageManager;
    public RequestManager requestManager;
    public String username;
    AttendeePresenter p;

    /**
     * This constructs an AttendeeController object
     * @param userManager the instance of the User Manager
     * @param eventManager the instance of the Event Manager
     * @param messageManager the instance of the Message Manager
     * @param username the username of the Attendee accessing the AttendeeController
     */
    public AttendeeController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                              String username, RequestManager requestManager){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
        this.requestManager = requestManager;
        this.p = new AttendeePresenter();
    }

    /**
     * Runs the Attendee controller by asking for input and performing the actions
     */
    public void run(){
        p.displayOptions();
        p.displayTaskInput();
        int input = 0;
        input = p.nextInt();
        while (input != 3){ // 3 is ending condition
            determineInput(input);
            input = p.nextInt();
        }
    }

    private void determineInput(int input) {
        switch (input) {
            case 0:
                p.displayMessageOptions();
                int choice = p.nextInt();
                final int endCond = 5;
                while (choice != endCond) {
                    determineInput0(choice);
                    choice = p.nextInt();
                }
                break;

            case 1:
                p.displayEventOptions();
                int choice1 = p.nextInt();
                final int endCond1 = 4;
                while (choice1 != endCond1){
                    determineInput1(choice1);
                    choice1 = p.nextInt();
                }
                break;

            case 2:
                p.displayRequestOptions();
                int choice2 = p.nextInt();
                final int endCond2 = 2;
                while (choice2 != endCond2){
                    determineInput2(choice2);
                    choice2 = p.nextInt();
                }
                break;

            case 6:
                p.displayOptions();
                break;

            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPromptAttendee();
    }

    protected void determineInput0(int input){
        switch (input){
            case 0:
                viewMessages(this.username);
                break;
            case 1:
                viewStarredMessages(this.username);
                break;
            case 2:
                //viewDeletedMessages(this.username);
                break;
            case 3:
                //viewArchivedMessages(this.username);
                break;
            case 4:
                determineInputSendMessages();
                break;
            default:
                p.displayMessageOptionsInvalidChoice();
                break;
        }
        p.displayNextTaskPromptAttendee();
    }

    protected void determineInput1(int input){
        switch (input){
            case 0:
                viewEventList();
                break;

            case 1:
                viewSignedUpForEvent(this.username);
                break;

            case 2:
                determineInputCancelEventReservation();
                break;

            case 3:
                determineInputSignUpEvent();
                break;
            default:
                p.displayEventOptionsInvalidChoice();
                break;
        }
        p.displayNextTaskPromptAttendee();
    }

    protected void determineInput2(int input){
        switch (input){
            case 0:
                viewRequests(username);
                break;

            case 1:
                String req = p.displayMakeRequest();
                boolean valid = requestManager.checkIsValidRequest(req);
                while(!valid){
                    p.invalidRequest();
                    req = p.displayMakeRequest();
                    valid = requestManager.checkIsValidRequest(req);
                }
                makeRequest(req, username);
                p.displaySuccessfulRequestSubmission();
                break;
            default:
                p.displayRequestsOptionsInvalidChoice();
                break;
        }
        p.displayNextTaskPromptAttendee();
    }

    private void determineInputSignUpEvent() {
        List<Event> future = viewFutureEventList();
        p.displayAllFutureEvents(future);
        if (future.size() == 0){
            return;
        }
        String eventSignedUp = p.displayEventSignUpPrompt();
        // System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
        if (eventSignedUp.equals("q")){
            return;
        }
        while (eventManager.getEvent(eventSignedUp) == null ||
                !future.contains(eventManager.getEvent(eventSignedUp)) ||
                eventManager.getEvent(eventSignedUp).getVipEvent()){    // Checks if the event is VIP exclusive and then prompts attendee that they cannot sign up for that event
            eventSignedUp = p.displayInvalidEventSignUp();
            if (eventSignedUp.equalsIgnoreCase("q")){
                break;
            }
        }
        if (!eventSignedUp.equalsIgnoreCase("q")) {
            signUp(eventSignedUp);
        }

    }

    private void determineInputCancelEventReservation() {
        Attendee temp = (Attendee) userManager.getUser(this.username);
        if(temp.getAttendingEvents().isEmpty()){
            p.displayNotAttendingAnyEvents();
            return;
        }
        viewSignedUpForEvent(this.username);
        String cancel = p.displayEventCancelPrompt();
        // System.out.println("What is the name of the event you no longer want to attend? Type 'q' if you no longer want to cancel your spot in an event.");
        if (cancel.equals("q")){
            return;
        }
        if(!userManager.getAttendingEvents(this.username).contains(cancel)) {
            p.displayEventCancelPrompt();
            return;
        }
        else if(userManager.getAttendingEvents(this.username).size() == 0){
            p.displayEventCancellationError2();
            return;
        }
        cancelSpotInEvent(cancel);

    }
    // Commented out because replying it built in to viewing messages now
//    private void determineInputViewEventList() {
//        if(messageManager.getAllUserMessages().get(this.username).size() == 0){
//            p.displayNoReply();
//            return;
//        }
//        else if(userManager.getUserMap().size() == 1) {
//            p.displayConferenceError();
//            return;
//        }
//        List<String> attendees = getSenders(username);
//        p.displayAllSenders(attendees);
//        String recipients = p.displayEnterUserUsernamePrompt();
//        // System.out.println("Which user are you replying to (it is case sensitive). If you no longer want to reply to a user, type 'q' to exit: ");
//        while (!attendees.contains(recipients)){
//            recipients = p.displayUserReplyError();
//            if (recipients.equals("q")){
//                break;
//            }
//        }
//        if (recipients.equals("q")){
//            return;
//        }
//        String content = p.displayEnterMessagePrompt();
//        replyMessage(content, recipients);
//
//    }

    private void determineInputSendMessages() {
        if(userManager.getUserMap().size() == 1) {
            p.displayConferenceError();
            return;
        }
        String recipient = p.displayMethodPrompt();
        // System.out.println("Who would you like to message? (Please enter the username of the recipient). Otherwise, type 'q' to exit");
        if (recipient.equals("q")){
            return;
        }
        else if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
            String messageContents = p.displayEnterMessagePrompt(recipient);
            // System.out.println("What message would you like to send to: " + recipient + ". " + "If you would no longer like to send a message, type 'q' to exit");
            if (messageContents.equals("q")){
                return;
            }
            sendMessage(recipient, messageContents);
            p.displayMessageSentPrompt();
        }
        else{
            p.displayNotMessageableError();
        }

    }


    /**
     * Prints all the messages that this attendee has received
     * @param username: The username of the Attendee
     */
    protected void viewMessages(String username) {
        List<Message> allMessages = messageManager.viewMessages(username);
        p.displayPrintMessages(allMessages);
        if(allMessages.size()>0) {
            int requestedMessage = p.displaySelectMessage();
            while (requestedMessage > allMessages.size() || requestedMessage < 1) {
                p.displayMessageNonExistent();
                requestedMessage = p.displaySelectMessage();
            }

            Message selectedMessage = (allMessages.get(allMessages.size() - requestedMessage));
            p.displaySelectedMessage(selectedMessage);
            messageManager.setMessageReadStatus(selectedMessage, "read");

            // this method may be too large now, but this prompts the user to take an action on the selected message
            String messageAction = p.displayMessageActionPrompt();
            while (!messageAction.equalsIgnoreCase("REPLY") &&
                    !messageAction.equalsIgnoreCase("MARK AS UNREAD") &&
                            !messageAction.equalsIgnoreCase("CLOSE") &&
                    !messageAction.equalsIgnoreCase("MARK AS STARRED")) {
                messageAction = p.displayMessageActionPrompt();
            }
            if (messageAction.equalsIgnoreCase("REPLY")) {
                String content = p.displayEnterMessagePrompt();
                replyMessage(content, selectedMessage.getSender());
            } else if (messageAction.equalsIgnoreCase("MARK AS UNREAD")) {
                messageManager.setMessageReadStatus(selectedMessage, "unread");
            } else if (messageAction.equalsIgnoreCase("MARK AS STARRED")) {
                messageManager.setMessageStarredStatus(selectedMessage, "starred");
            } else if (messageAction.equalsIgnoreCase("CLOSE")){
            }
        }
    }

    /**
     * Prints all the messages that this attendee has starred
     * @param username: The username of the Attendee
     */
    protected void viewStarredMessages(String username) {
        List<Message> allMessages = messageManager.viewMessages(username);
        // get starred messages
        List<Message> starredMessages = new ArrayList<Message>();

        for (Message message: allMessages) {
            if (message.isStarred()) {
                starredMessages.add(message);
            }
        }
        p.displayPrintStarredMessages(starredMessages);
        if(allMessages.size()>0) {
            int requestedMessage = p.displaySelectMessage();
            while (requestedMessage > allMessages.size() || requestedMessage < 1) {
                p.displayMessageNonExistent();
                requestedMessage = p.displaySelectMessage();
            }

            Message selectedMessage = (allMessages.get(allMessages.size() - requestedMessage));
            p.displaySelectedMessage(selectedMessage);
            messageManager.setMessageReadStatus(selectedMessage, "read");

            // this method may be too large now, but this prompts the user to take an action on the selected message
            String messageAction = p.displayMessageActionPrompt();
            while (!messageAction.equalsIgnoreCase("REPLY") &&
                    !messageAction.equalsIgnoreCase("MARK AS UNREAD") &&
                    !messageAction.equalsIgnoreCase("CLOSE") &&
                    !messageAction.equalsIgnoreCase("MARK AS STARRED")) {
                messageAction = p.displayMessageActionPrompt();
            }
            if (messageAction.equalsIgnoreCase("REPLY")) {
                String content = p.displayEnterMessagePrompt();
                replyMessage(content, selectedMessage.getSender());
            } else if (messageAction.equalsIgnoreCase("MARK AS UNREAD")) {
                messageManager.setMessageReadStatus(selectedMessage, "unread");
            } else if (messageAction.equalsIgnoreCase("MARK AS STARRED")) {
                messageManager.setMessageStarredStatus(selectedMessage, "starred");
            } else if (messageAction.equalsIgnoreCase("CLOSE")){
            }
        }
    }


    /**
     * Sends a message to a specified user
     * @param recipient: The username of the recipient
     * @param messageContents: The content of the message the Attendee is sending
     */
    protected void sendMessage(String recipient, String messageContents) {
        Message newMessage = messageManager.createNewMessage(messageContents, this.username, recipient);
        messageManager.addMessage(recipient, newMessage);
    }

    /**
     * Sends a reply to the oldest message in an attendees inbox
     * @param message: The content of the message the Attendee is sending
     * @param originalMessenger: The username of the User we are replying too
     */
    protected void replyMessage(String message, String originalMessenger) {
        Message replyMessage = messageManager.createNewMessage(message, this.username, originalMessenger);
        messageManager.addMessage(originalMessenger, replyMessage);
        p.displaySuccessfulMessage();
    }

    /**
     * Prints the event list for the entire conference
     */
    protected void viewEventList() {
        List<Event> chronological = eventManager.chronologicalEvents(eventManager.getAllEventNamesOnly());
        p.displayEventList(chronological);
    }

    /**
     * This method returns a list of events that will occur.
     * @return Returns a list of events that have not occurred yet.
     */
    protected List<Event> viewFutureEventList() {
        List<Event> chronological = eventManager.chronologicalEvents(eventManager.getAllEventNamesOnly());
        List<Event> future = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Event curr: chronological){
            if (eventManager.getTime(curr).compareTo(now) > 0){
                future.add(curr);
            }
        }
        return future;
    }

    /**
     * Prints the scheduled events of an attendee
     * @param username: The username of this Attendee
     */
    protected void viewSignedUpForEvent(String username) {
        List<String> signedUpFor = userManager.getAttendingEvents(username);
        List<Event> chronological = eventManager.chronologicalEvents(signedUpFor);
        p.displaySignedUpEvents(chronological);
    }

    /**
     * Removes an attendee from an event they were signed up for
     * @param eventName: The name of the Event we want to cancel our spot in
     */
    protected void cancelSpotInEvent(String eventName) {
        Event event = eventManager.getEvent(eventName);
        userManager.cancelEventSpot(this.username, event, eventManager);
        p.displaySuccessfulCancellation();
    }

    /**
     * Signs an attendee up for a new event
     * @param eventName: The name of the Event we want to sign up for
     */
    protected void signUp(String eventName) {
        Event event = eventManager.getEvent(eventName);
        if (userManager.signUpForEvent(this.username, event, eventManager)){
            p.displayEventSignUp();
        }
        else {
            p.displayEventFull();
        }
    }

    private List<String> getSenders(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> attendees = new ArrayList<>();
        for (Message message: allMessages) {
            String name = messageManager.getSender(message);
            if (!attendees.contains(name)) {
                attendees.add(name);
            }
        }
        return attendees;
    }

    private void viewRequests(String username){
        List<Request> requests = requestManager.getUserRequests(username);
        p.displayRequests(requests);
    }

    private void makeRequest(String content, String username){
        Request request = requestManager.createNewRequest(content, username);
        requestManager.addRequest(username, request);
        ((Attendee)userManager.getUser(username)).addRequest(request);
    }
}
