package user.attendee;

import event.Event;
import event.EventManager;
import message.Message;
import message.MessageManager;
import request.Request;
import request.RequestManager;
import room.Room;
import user.UserController;
import user.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A controller that deals with Attendee users
 */
public class AttendeeController extends UserController {

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
        super(userManager, eventManager, messageManager, username, requestManager);
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
                final int endCond1 = 5;
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
                viewDeletedMessages(this.username);
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
        p.displayNextTaskPromptOrgOptDisplayed();
        p.displayMessageOptions();
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
            case 4:
                searchForEvents();
                break;
            default:
                p.displayEventOptionsInvalidChoice();
                break;
        }
        p.displayNextTaskPromptOrgOptDisplayed();
        p.displayEventOptions();
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
        p.displayNextTaskPromptOrgOptDisplayed();
        p.displayRequestOptions();
    }

    private void determineInputSignUpEvent() {
        List<Event> future = viewFutureEventList();
        //List<String> stringsOfEvents = getToStringsOfEvents();
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
     * Sends a message to a specified user
     * @param recipient: The username of the recipient
     * @param messageContents: The content of the message the Attendee is sending
     */
    protected void sendMessage(String recipient, String messageContents) {
        Message newMessage = messageManager.createNewMessage(messageContents, this.username, recipient);
        messageManager.addMessage(recipient, newMessage);
    }


//    /**
//     * Prints the event list for the entire conference
//     */
//    protected void viewEventList() {
//        List<Event> chronological = eventManager.chronologicalEvents(eventManager.getAllEventNamesOnly());
//        p.displayEventList(chronological);
//    }

    /**
     * This method prints the event list for the entire conference
     */
    protected void viewEventList() {
        List<Event> chronological = eventManager.chronologicalEvents(eventManager.getAllEventNamesOnly());
        List<String> strings = eventManager.getToStringsOfEvents(chronological);
        p.displayEventList(strings);
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

//    /**
//     * Prints the scheduled events of an attendee
//     * @param username: The username of this Attendee
//     */
//    protected void viewSignedUpForEvent(String username) {
//        List<String> signedUpFor = userManager.getAttendingEvents(username);
//        List<Event> chronological = eventManager.chronologicalEvents(signedUpFor);
//        p.displaySignedUpEvents(chronological);
//    }

    /**
     * Prints the scheduled events of an attendee
     * @param username: The username of this Attendee
     */
    protected void viewSignedUpForEvent(String username) {
        List<String> signedUpFor = userManager.getAttendingEvents(username);
        List<Event> chronological = eventManager.chronologicalEvents(signedUpFor);
        List<String> stringsOfEvents = eventManager.getToStringsOfEvents(chronological);
        p.displaySignedUpEvents(stringsOfEvents);
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

    protected void searchForEvents(){
        List<Event> chronological = eventManager.chronologicalEvents(eventManager.getAllEventNamesOnly());
        String resp = "y";
        do{
            String response = p.displayPromptSearchForEvents().toLowerCase();
            while (!response.equals("name") && !response.equals("tag")){
                p.displayInvalidPromptSearchForEvents();
                response = p.displayPromptSearchForEvents().toLowerCase();
            }
            if (response.equals("name")){
                String name = p.displayPromptSearchForEventsByName().toLowerCase();
                p.displayEventByName(chronological, name);
            }
            else if (response.equals("tag")){
                String tag = p.displayPromptSearchForEventsByTag().toLowerCase();
                p.displayEventsByTag(chronological, tag);
            }
            resp = p.displayPromptSearchForAnotherEvent().toLowerCase();
            while (!resp.equals("y") && !resp.equals("n")){
                p.displayErrorSearchForAnotherEvent();
                resp = p.displayPromptSearchForAnotherEvent().toLowerCase();
            }
        }while(!resp.equals("n"));
    }
}
