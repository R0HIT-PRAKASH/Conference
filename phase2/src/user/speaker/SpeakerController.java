package user.speaker;

import event.Event;
import event.EventManager;
import message.Message;
import message.MessageManager;
import request.Request;
import request.RequestManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A controller that deals with Speaker users
 */
public class SpeakerController{
    public UserManager userManager;
    public EventManager eventManager;
    public MessageManager messageManager;
    public String username;
    public RequestManager requestManager;
    SpeakerPresenter p;

    /**
     * Creates a Speaker Controller
     * @param userManager user use case
     * @param eventManager event use case
     * @param messageManager message use case
     * @param username username of the user
     */
    public SpeakerController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                             String username, RequestManager request){
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
        this.requestManager = request;
        p = new SpeakerPresenter();
    }
    /**
     * Runs the Speaker controller by asking for input and performing the actions
     */
    public void run(){
        p.displayOptions3();
        p.displayTaskInput();
        int input = p.nextInt();
        final int END_CONDITION = 3;
        while (input != END_CONDITION){
            determineInput(input);
            input = p.nextInt();
        }
    }

//    private void determineInput1(int input){
//        switch (input) {
//            case 0:
//                viewMessages(this.username);
//                break;
//            case 1:
//                viewScheduledEvents(this.username);
//                break;
//            case 2:
//                List<Event> allEvents = eventManager.chronologicalEvents(userManager.getSpeakingEvents(username));
//                p.displayAllEventsGiven(allEvents);
//                if (allEvents.size() == 0){
//                    break;
//                }
//                int num = p.nextInt();
//
//                while(num < 1 || num > allEvents.size()){
//                    num = p.nextInt();
//                    if (num == -1){
//                        break;
//                    }
//                }
//                if (num == -1){
//                    break;
//                }
//                String next = "";
//                List<String> eventNames = new ArrayList<>();
//                for (int i = 0; i < num; i++) {
//                    if (i == 0) {
//                        next = p.displayEnterEventNamePrompt();
//                    }
//                    else {
//                        next = p.displayEnterEventNamePrompt2();
//                    }
//                    if (next.equals("q")){
//                        break;
//                    }
//                    if (allEvents.contains(eventManager.getEvent(next)) && !eventNames.contains(next)) {
//                        eventNames.add(next);
//                    }
//                    else if(allEvents.contains(eventManager.getEvent(next))){
//                        p.displayEventAlreadyAddedError();
//                        i--;
//                    }
//                    else if(!allEvents.contains(next)){
//                        p.displayEventNotGivenError();
//                        i--;
//                    }
//                }
//                if(next.equals("q")) {
//                    break;
//                }
//                String message = p.displayEnterMessagePrompt();
//
//                sendBlastMessage(eventNames, message);
//                break;
//            case 3:
//                if(messageManager.getAllUserMessages().get(this.username).size() == 0){
//                    p.displayNoReply();
//                    break;
//                }
//                else if(userManager.getUserMap().size() == 1) {
//                    p.displayConferenceError();
//                    break;
//                }
//                List<String> attendees = getAttendees(username);
//                p.displayAllSenders(attendees);
//                if (attendees.size() == 0){
//                    break;
//                }
//                String recipient = p.displayEnterAttendeeUsernamePrompt();
//
//                while (!attendees.contains(recipient)){
//                    recipient = p.displayUserReplyError();
//
//                    if (recipient.equals("q")){
//                        break;
//                    }
//                }
//                if (recipient.equals("q")){
//                    break;
//                }
//                String content = p.displayEnterMessagePrompt();
//
//                replyMessage(recipient, content);
//                break;
//
//            case 4:
//                String eventName = p.displayEventSelectorPrompt();
//                viewScheduledEvents(username);
//                if(eventManager.events.containsKey(eventName)){
//                    Set<String> eventAttendees = eventManager.getEventAttendees(eventName);
//                    String toMessage = p.displayEventAttendeesList(eventAttendees);
//                    ArrayList<String> usernameList = new ArrayList<String>();
//                    for (String user: eventAttendees){
//                        usernameList.add(user);
//                    }
//                    if(usernameList.contains(toMessage)){
//                        String messageContent = p.displayEnterMessagePrompt();
//                        replyMessage(toMessage, messageContent);
//                        break;
//                    } else {
//                        p.displayInvalidInputError();
//                    }
//                } else{
//                    p.displayInvalidInputError();
//                }
//                break;
//
//            case 5:
//                viewOptions();
//                break;
//
//            case 6:
//                viewRequests(username);
//                break;
//
//            case 7:
//                String req = p.displayMakeRequest();
//                boolean valid = requestManager.checkIsValidRequest(req);
//                while(!valid){
//                    p.invalidRequest();
//                    req = p.displayMakeRequest();
//                    valid = requestManager.checkIsValidRequest(req);
//                }
//                makeRequest(req, username);
//                p.displaySuccessfulRequestSubmission();
//                break;
//
//            default:
//                p.displayInvalidInputError();
//                viewOptions();
//                break;
//        }
//        p.displayNextTaskPromptSpeaker();
//    }

    /**
     * Used to determine what task the user would like to do based off their keyboard input.
     * @param input: The integer value that corresponds to the task the user would like to do.
     */
    private void determineInput(int input){
        switch (input){
            case 0:
                p.displayMessageOptions();
                int choice = p.nextInt();
                final int endCond = 4;
                while (choice != endCond) {
                    determineInputMessage(choice);
                    choice = p.nextInt();
                }
                break;

            case 1:
                p.displayEventOptions();
                int choice1 = p.nextInt();
                final int endCond1 = 1;
                while (choice1 != endCond1) {
                    determineInputEvent(choice1);
                    choice1 = p.nextInt();
                }
                break;

            case 2:
                p.displayRequestOptions();
                int choice3 = p.nextInt();
                final int endCond3 = 2;
                while (choice3 != endCond3) {
                    determineInputRequest(choice3);
                    choice3 = p.nextInt();
                }
                break;


            case 5:
                p.displayOptions3();
                break;

            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPromptSpeaker();
    }

    /**
     * Used to determine what message task the user would like to do based off their keyboard input.
     * @param input: The integer value that corresponds to the task the user would like to do.
     */
    private void determineInputMessage(int input){
        switch (input) {
            case 0:
                viewMessages(this.username);
                break;

            case 1:
                List<Event> allEvents = eventManager.chronologicalEvents(userManager.getSpeakingEvents(username));
                p.displayAllEventsGiven(allEvents);
                if (allEvents.size() == 0){
                    break;
                }
                int num = p.nextInt();

                while(num < 1 || num > allEvents.size()){
                    num = p.nextInt();
                    if (num == -1){
                        break;
                    }
                }
                if (num == -1){
                    break;
                }
                String next = "";
                List<String> eventNames = new ArrayList<>();
                for (int i = 0; i < num; i++) {
                    if (i == 0) {
                        next = p.displayEnterEventNamePrompt();
                    }
                    else {
                        next = p.displayEnterEventNamePrompt2();
                    }
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
                String message = p.displayEnterMessagePrompt();

                sendBlastMessage(eventNames, message);
                break;

            case 2:
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
                String recipient = p.displayEnterAttendeeUsernamePrompt();

                while (!attendees.contains(recipient)){
                    recipient = p.displayUserReplyError();

                    if (recipient.equals("q")){
                        break;
                    }
                }
                if (recipient.equals("q")){
                    break;
                }
                String content = p.displayEnterMessagePrompt();

                replyMessage(recipient, content);
                break;

            case 3:
                String eventName = p.displayEventSelectorPrompt();
                viewScheduledEvents(username);
                if(eventManager.events.containsKey(eventName)){
                    Set<String> eventAttendees = eventManager.getEventAttendees(eventName);
                    String toMessage = p.displayEventAttendeesList(eventAttendees);
                    ArrayList<String> usernameList = new ArrayList<String>();
                    for (String user: eventAttendees){
                        usernameList.add(user);
                    }
                    if(usernameList.contains(toMessage)){
                        String messageContent = p.displayEnterMessagePrompt();
                        replyMessage(toMessage, messageContent);
                        break;
                    } else {
                        p.displayInvalidInputError();
                    }
                } else{
                    p.displayInvalidInputError();
                }
                break;

            default:
                p.displayMessageOptionsInvalidChoice();
                break;
        }
        p.displayMessageOptions();
    }

    /**
     * Used to determine what event task the user would like to do based off their keyboard input.
     * @param input: The integer value that corresponds to the task the user would like to do.
     */
    private void determineInputEvent(int input){
        switch (input){
            case 0:
                viewScheduledEvents(this.username);
                break;

            default:
                p.displayEventOptionsInvalidChoice();
                break;
        }
        p.displayEventOptions();
    }

    /**
     * Used to determine what request task the user would like to do based off their keyboard input.
     * @param input: The integer value that corresponds to the task the user would like to do.
     */
    private void determineInputRequest(int input){
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
        p.displayRequestOptions();
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

    private void viewRequests(String username){
        List<Request> requests = requestManager.getUserRequests(username);
        p.displayRequests(requests);
    }

    private void makeRequest(String content, String username){
        Request request = requestManager.createNewRequest(content, username);
        requestManager.addRequest(username, request);
        ((Speaker)userManager.getUser(username)).addRequest(request);
    }
}
