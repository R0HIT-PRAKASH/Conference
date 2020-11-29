package user.organizer;

import event.Event;
import event.EventManager;
import message.Message;
import message.MessageManager;
import request.Request;
import request.RequestManager;
import room.Room;
import user.User;
import user.UserFactory;
import user.UserManager;
import user.attendee.AttendeeController;
import user.speaker.Speaker;
import request.RequestManager;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Refers to the controller class that will deal with the actions of an organizer object.
 */
public class OrganizerController extends AttendeeController {

    UserFactory userFactory;
    OrganizerPresenter p;

    /**
     * Constructs an OrganizerController object.
     * @param userManager Refers to the UserManager object.
     * @param eventManager Refers to the EventManager object.
     * @param messageManager Refers to the MessageManager object.
     * @param username Refers to the username of the organizer.
     */
    public OrganizerController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username, RequestManager requestManager) {
        super(userManager, eventManager, messageManager, username, requestManager);
        p = new OrganizerPresenter();
        userFactory = new UserFactory();
    }



    /**
     * Runs the OrganizerController by asking for input and performing the actions
     */
    public void run(){
        p.displayOptions2();
        p.displayTaskInput();
        final int END_CONDITION = 25;
        int input = p.nextInt();
        while (input != END_CONDITION){ // 25 is ending condition
            determineInput(input);
            input = p.nextInt();
        }
    }

    private void determineInput(int input) {
        switch (input) {
            case 0:
                viewMessages(this.username);
                break;

            case 1:
                if(userManager.getUserMap().size() == 1) {
                    p.displayConferenceError();
                    break;
                }
                String recipient = p.displayMethodPrompt();
                // System.out.println("Who would you like to message? (Please enter the username of the recipient). Otherwise, type 'q' to exit");
                if (recipient.equals("q")){
                    break;
                }
                else if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    String messageContents = p.displayEnterMessagePrompt(recipient);
                    // System.out.println("What message would you like to send to: " + recipient + ". " + "If you would no longer like to send a message, type 'q' to exit");
                    if (messageContents.equals("q")){
                        break;
                    }
                    sendMessage(recipient, messageContents);
                    p.displayMessageSentPrompt();
                }
                else{
                    p.displayNotMessageableError();
                }
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
                List<String> attendees = getSenders(username);
                p.displayAllSenders(attendees);
                String recipients = p.displayEnterUserUsernamePrompt();
                // System.out.println("Which user are you replying to (it is case sensitive). If you no longer want to reply to a user, type 'q' to exit: ");
                while (!attendees.contains(recipients)){
                    recipients = p.displayUserReplyError();
                    if (recipients.equals("q")){
                        break;
                    }
                }
                if (recipients.equals("q")){
                    break;
                }
                String content = p.displayEnterMessagePrompt();
                replyMessage(content, recipients);
                break;

            case 3:
                viewEventList();
                break;

            case 4:
                viewSignedUpForEvent(this.username);
                break;

            case 5:
                Organizer temp = (Organizer) userManager.getUser(this.username);
                if(temp.getAttendingEvents().isEmpty()){
                    p.displayNotAttendingAnyEvents();
                    break;
                }
                viewSignedUpForEvent(this.username);
                String cancel = p.displayEventCancelPrompt();
                // System.out.println("What is the name of the event you no longer want to attend? Type 'q' if you no longer want to cancel your spot in an event.");
                if (cancel.equals("q")){
                    break;
                }
                if(!userManager.getAttendingEvents(this.username).contains(cancel)) {
                    p.displayEventCancelPrompt();
                    break;
                }
                else if(userManager.getAttendingEvents(this.username).size() == 0){
                    p.displayEventCancellationError2();
                    break;
                }
                cancelSpotInEvent(cancel);
                break;

            case 6:
                List<Event> future = viewFutureEventList();
                p.displayAllFutureEvents(future);
                if (future.size() == 0){
                    break;
                }
                String eventSignedUp = p.displayEventSignUpPrompt();
                // System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
                if (eventSignedUp.equals("q")){
                    break;
                }
                while (eventManager.getEvent(eventSignedUp) == null ||
                        !future.contains(eventManager.getEvent(eventSignedUp))){
                    eventSignedUp = p.displayInvalidEventSignUp();
                    if (eventSignedUp.equalsIgnoreCase("q")){
                        break;
                    }
                }
                if (eventSignedUp.equalsIgnoreCase("q")){
                    break;
                }
                signUp(eventSignedUp);
                break;

            case 7:
                //Ask user what type of event they would like to create (Talk, Panel, Party).
                //Depending on what type of event they chose, ask appropriate questions. (Same except for how we ask for
                //the speakers, i.e one speaker for talk, multiple speakers for panel, no speaker for party).
                //Call addEvent in eventManager and pass in type of event and other prompts.
                //EventManager will then call eventFactory so eventFactory.getEvent("talk", hashmap)
                //Then eventManager will add in the event into the list of events.

                //Scanner scan = new Scanner(System.in);
                //System.out.println("What kind of event would you like to create? A talk, panel, or party?");
                //String eventType = scan.nextLine();

                p.displayAddConferencePrompt();
                LocalDateTime time = p.askTime();
                System.out.println(time);
                while(!eventManager.between9to5(time) || !eventManager.checkTimeIsAfterNow(time)) {
                    if (!eventManager.between9to5(time)) {
                        p.displayInvalidHourError();
                        time = p.askTime();
                    } else if (!eventManager.checkTimeIsAfterNow(time)) {
                        p.displayInvalidDateError();
                        time = p.askTime();
                    }
                }

                String answerVip = p.displayVipPrompt();
                boolean vip = false;
                while(!answerVip.equalsIgnoreCase("yes") && !answerVip.equalsIgnoreCase("no")){
                    if(answerVip.equalsIgnoreCase("q")){
                        break;
                    }
                    answerVip = p.displayInvalidVip();
                }

                if(answerVip.equalsIgnoreCase("q")){
                    break;
                }else if(answerVip.equalsIgnoreCase("yes")) {
                    vip = true;
                }


                String name = p.displayEventTitlePrompt();
                // Adding the option to end the case early here in case a User wants to go back
                if (name.equals("q")){
                    break;
                }
                p.displayDurationPrompt();
                int duration = p.nextInt();
                while(duration <= 0){
                    if(duration == -1){
                        break;
                    }
                    p.displayInvalidDuration();
                    duration = p.nextInt();
                }
                if(duration == -1){
                    break;
                }



                String speaker = p.displayEnterSpeakerPrompt();

                if(!userManager.checkCredentials(speaker)) {
                    p.displaySpeakerCredentialError();
                    makeUser("speaker");
                    speaker = p.displayEnterNewSpeakerPrompt();
                }
                else{
                    while (!(userManager.getUserType(speaker).equals("speaker"))){
                        speaker = p.displayNotSpeakerError();
                        if (speaker.equalsIgnoreCase("q")) {
                            break;
                        }
                    }
                }
                if (speaker.equalsIgnoreCase("q")) {
                    break;
                }

                p.displayEnterRoomNumberPrompt();
                int num = p.nextInt();
                Room room = eventManager.getRoom(num);
                String ans;
                if(eventManager.getRoom(num) == null) {
                    if (eventManager.getRooms().isEmpty()) {
                        ans = p.displayRoomNumberErrorQuestion1();
                        while (!ans.equalsIgnoreCase("create") && !ans.equalsIgnoreCase("q")) {
                            ans = p.displayRoomDecisionQError1();
                        }
                    } else {
                        ans = p.displayRoomNumberErrorQuestion2();
                        while (!ans.equalsIgnoreCase("create") && !ans.equalsIgnoreCase("suggestions") // need to fix it so it doesnt give suggestions as option when empty
                                && !ans.equalsIgnoreCase("q")) {
                            ans = p.displayRoomDecisionQError2();
                        }
                    }
                    if (ans.equalsIgnoreCase("q")) {
                        break;
                    }
                    p.displayEventCapacityPrompt();
                    int capacity = p.nextInt();
                    while(capacity <= 0){
                        if(capacity == -1){
                            break;
                        }
                        p.displayInvalidCapacity();
                        capacity = p.nextInt();
                    }
                    if(capacity == -1){
                        break;
                    }

                    p.displayComputersPrompt();
                    int comp = p.nextInt();
                    while(comp < 0){
                        if(comp == -1){
                            break;
                        }
                        p.displayInvalidComputers();
                        comp = p.nextInt();
                    }
                    if(comp == -1){
                        break;
                    }

                    String answerProject = p.displayProjectorPrompt();
                    boolean project = false;
                    while(!answerProject.equalsIgnoreCase("yes") && !answerProject.equalsIgnoreCase("no")){
                        if(answerProject.equalsIgnoreCase("q")){
                            break;
                        }
                        answerProject = p.displayInvalidProjector();
                    }

                    if(answerProject.equalsIgnoreCase("q")){
                        break;
                    }else if(answerProject.equalsIgnoreCase("yes")) {
                        project = true;
                    }

                    p.displayChairsPrompt();
                    int cha = p.nextInt();
                    while(cha < 0){
                        if(cha == -1){
                            break;
                        }
                        p.displayInvalidChairs();
                        cha = p.nextInt();
                    }
                    if(cha == -1){
                        break;
                    }

                    p.displayTablesPrompt();
                    int tab = p.nextInt();
                    while(tab < 0){
                        if(tab == -1){
                            break;
                        }
                        p.displayInvalidTables();
                        tab = p.nextInt();
                    }
                    if(tab == -1){
                        break;
                    }

                    if (ans.equalsIgnoreCase("create")) {
                        p.displayEnterRoomNumberPrompt();
                    }else if(ans.equalsIgnoreCase("suggestions")) {
                        p.displayRecommendedRooms(capacity, comp, project, cha, tab, eventManager.getRooms());
                        p.displayEnterRoomNumberPrompt();
                    }
                    num = p.nextInt();
                    List<Organizer> organizers = userManager.getOrganizers();
                    List<String> creators = new ArrayList<>();
                    creators.add(this.username);
                    p.displayAndGetCreators(creators, organizers);
                    boolean added = addEvent(name, speaker, time, duration, num, capacity, comp, project, cha, tab, creators, vip);
                    if(!added) {p.displayEventCreationError();}
                    else {
                        p.displaySuccessfulEventCreation();
                        userManager.addSpeakingEvent(speaker, name);
                        userManager.createdEvent(eventManager.getEvent(name), creators);
                    }
                    break;
                }
                else { // room exists
                    p.displayEnterEventCapacityPrompt(room.getCapacity());  // need to ask what they want capacity to be and cannot be more then room can hold
                    int cap = p.nextInt();
                    while (cap > room.getCapacity()) {
                        p.displayRoomCapacityError(room.getCapacity());
                        cap = p.nextInt();
                    }
                    List<Organizer> organizers = userManager.getOrganizers();
                    List<String> creators = new ArrayList<>();
                    creators.add(this.username);
                    p.displayAndGetCreators(creators, organizers);
                    boolean added = addEvent(name, speaker, time, duration, num, room.getCapacity(), room.getComputers(), room.getProjector(), room.getChairs(), room.getChairs(), creators, vip);
                    if(!added) {p.displayEventCreationError();}
                    else {
                        p.displaySuccessfulEventCreation();
                        userManager.addSpeakingEvent(speaker, name);
                        userManager.createdEvent(eventManager.getEvent(name), creators);
                    }
                    break;
                }

            case 8:
                String message = p.displayAllAttendeeMessagePrompt();

                if (message.equalsIgnoreCase("q")) {
                    break;
                }
                messageAllAttendees(message);
                p.displayMessageSentPrompt();
                break;

            case 9:
                String eventname = p.displayEventMessagePrompt();
                if (eventname.equalsIgnoreCase("q")) {
                    break;
                }
                else if(eventManager.getEvent(eventname) == null) {
                    p.displayInvalidEventError();
                } else {
                    messageEventAttendees(p.displayAllAttendeeEventMessagePrompt(), eventname);
                    p.displayMessageSentPrompt();
                }
                break;

            case 10:
                String speakermessage = p.displayAllSpeakerMessagePrompt();
                if (speakermessage.equalsIgnoreCase("q")) {
                    break;
                }
                messageAllSpeakers(speakermessage);
                p.displayMessageSentPrompt();
                break;

            case 11:
                List<String> eventNames = userManager.allCreatedEvents(this.username);
                List<Event> futureEvents = eventManager.chronologicalEvents(eventManager.eventNotHappened(eventNames));
                p.displayYourCreatedEvents(futureEvents);
                String event = p.displayEventRemovalPrompt();
                if (event.equalsIgnoreCase("q")) {
                    break;
                }
                while(!futureEvents.contains(eventManager.getEvent(event))){
                    event = p.displayCannotCancel();
                    if (event.equalsIgnoreCase("q")) {
                        break;
                    }

                }
                if (event.equalsIgnoreCase("q")) {
                    break;
                }
                cancelEvent(event);
                break;

            case 12:
                String[] responses = new String[1];
                responses[0] = this.username;
                List<String> responsibleEvents = userManager.allCreatedEvents(this.username);
                p.displayEventReschedulePrompt(responses, responsibleEvents);
                String eventName1 = responses[0];
                if (eventName1.equalsIgnoreCase("q")) {
                    break;
                }
                LocalDateTime newTime = p.askTime();
                rescheduleEvent(eventName1, newTime);
                break;

            case 13:
                String newUserType = p.displayNewUserCreation();
                while (!(newUserType.equalsIgnoreCase("ORGANIZER") ||
                        newUserType.equalsIgnoreCase("ATTENDEE") ||
                        newUserType.equalsIgnoreCase("SPEAKER") ||
                        newUserType.equalsIgnoreCase("VIP"))){
                    newUserType = p.displayInvalidUserTypeError();
                }
                makeUser(newUserType);
                break;

            case 14:
                p.displayOptions2();
                break;


            case 15:
                p.displayRoomCreationPrompt();
                int roomNumber = p.nextInt();
                if (roomNumber == -1) {
                    break;
                }
                p.displayRoomCapacityPrompt();
                int capac = p.nextInt();
                while(capac <= 0){
                    if(capac == -1){
                        break;
                    }
                    p.displayInvalidCapacity();
                    capac = p.nextInt();
                }
                if(capac == -1){
                    break;
                }

                p.displayComputersPrompt();
                int computers = p.nextInt();
                while(computers < 0){
                    if(computers == -1){
                        break;
                    }
                    p.displayInvalidComputers();
                    computers = p.nextInt();
                }
                if(computers == -1){
                    break;
                }

                String answerProjector = p.displayProjectorPrompt();

                boolean projector = false;
                while(!answerProjector.equalsIgnoreCase("yes") && !answerProjector.equalsIgnoreCase("no")){
                    if(answerProjector.equalsIgnoreCase("q")){
                        break;
                    }
                    answerProjector = p.displayInvalidProjector();
                }

                if(answerProjector.equalsIgnoreCase("q")){
                    break;
                }else if(answerProjector.equalsIgnoreCase("yes")) {
                    projector = true;
                }

                p.displayChairsPrompt();
                int chairs = p.nextInt();
                while(chairs < 0){
                    if(chairs == -1){
                        break;
                    }
                    p.displayInvalidChairs();
                    chairs = p.nextInt();
                }
                if(chairs == -1){
                    break;
                }

                p.displayTablesPrompt();
                int tables = p.nextInt();
                while(tables < 0){
                    if(tables == -1){
                        break;
                    }
                    p.displayInvalidTables();
                    tables = p.nextInt();
                }
                if(tables == -1){
                    break;
                }

                boolean roomAdded = eventManager.addRoom(roomNumber, capac, computers, projector, chairs, tables);
                if (!roomAdded) {
                    p.displayRoomAlreadyExists();
                }
                break;

            case 16: // I would suggest putting this in a Modify Event Tab for GUI
                List<String> namesOfEvents = userManager.allCreatedEvents(this.username);
                if(namesOfEvents.isEmpty()){
                    p.displayNoOrganizedEvents();
                    break;
                }
                System.out.println(namesOfEvents);
                List<Event> usersFutureEvents = eventManager.chronologicalEvents(eventManager.eventNotHappened(namesOfEvents));
                p.displayYourCreatedEvents(usersFutureEvents);

                String eventNameToModify = p.displayEventModifyPrompt();
                if (eventNameToModify.equalsIgnoreCase("q")) {
                    break;
                }
                while(!namesOfEvents.contains(eventNameToModify)){
                    eventNameToModify = p.displayCannotModifyEvent();
                    // extend so you handle when they do an event you didnt create with specific error
                    if (eventNameToModify.equalsIgnoreCase("q")) {
                        break;
                    }

                }
                if (eventNameToModify.equalsIgnoreCase("q")) {
                    break;
                }

                Event eventToModify = eventManager.getEvent(eventNameToModify);
                Room room1 = eventManager.getRoom(eventToModify.getRoomNumber());
                // display new capacity prompt
                p.displayEnterNewEventCapacityPrompt(room1.getCapacity());
                int newCapacity = p.nextInt();
                while (newCapacity > room1.getCapacity() || newCapacity < eventToModify.getAttendeeSet().size()) {
                    p.displayModifyRoomCapacityError(room1.getCapacity(), eventToModify.getAttendeeSet().size());
                    newCapacity = p.nextInt();}
                eventManager.changeEventCapacity(eventToModify, newCapacity);
                break;
            // Here are the FUTURE events which you can modify:


            // display only event for which they are an organizer of (have access to)

            //choose one of them
            // choose an event

            case 17:
                p.displayRoomList(eventManager.getRooms());
                break;

            case 18:
                p.displayUserList(users("speaker"), "Speaker");
                break;

            case 19:
                p.displayUserList(users("attendee"), "Attendee");
                break;

            case 20:
                p.displayUserList(users("organizer"), "Organizer");
                break;

            case 21:
                getStats();
                break;

            case 22: //address requests
                int to_change = getRequestToDecide();
                Request update = requestManager.getStatusRequests("pending").get(to_change - 1);
                String decision = p.displayRequestDecisionPrompt(update);
                boolean valid = requestManager.checkIsValidStatus(decision);
                while(!valid){
                    p.requestDecisionInvalid();
                    decision = p.displayRequestDecisionPrompt(update);
                    valid = requestManager.checkIsValidStatus(decision);
                }
                requestManager.updateRequestStatus(update, decision);
                if(decision.equals("addressed")){
                    p.successfullyAddressedRequest();
                }
                else{
                    p.successfullyRejectedRequest();
                }
                break;

            case 23: //view addressed requests
                getAddressedRequests();
                break;

            case 24: //view user requests
                getUserRequests(p.viewUserRequestPrompt());
                break;

            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPromptOrganizer();
    }




    /**
     * Adds an event to the event list.
     * @param name Refers to the name of the event.
     * @param speaker Refers to the name of the speaker of this event.
     * @param time Refers to the starting time of the event.
     * @param duration The Event Duration.
     * @param roomNumber Refers to the room number of this event.
     * @param capacity The room capacity.
     * @param computers Refers to the number of computers in the room.
     * @param projector Refers to whether or not the room has a projector.
     * @param chairs Refers to the number of chairs in the room.
     * @param tables Refers to the number of tables in the room.
     * @param creators The list of creators.
     * @param vip Refers to whether or not this event is VIP exclusive.
     * @return Returns the created event.
     */
    boolean addEvent(String name, String speaker, LocalDateTime time, Integer duration, int roomNumber, int capacity,
                     int computers, boolean projector, int chairs, int tables, List<String> creators, boolean vip) {
        return eventManager.addEvent(name, speaker, time, duration, roomNumber, capacity, computers, projector,
                chairs, tables, creators, vip);
    }

    /**
     * This method sends a message to all the attendees at the conference.
     * @param message This parameter is the message text
     */
    void messageAllAttendees(String message) {
        createBlastMessage("attendee", message);
    }

    /**
     * This method sends a message to all the attendees at a particular event.
     * @param message This parameter is the message text
     * @param name This parameter is the event name
     */
    void messageEventAttendees(String message, String name) {
        messageManager.speakerBlastMessage(Arrays.asList(name), message, eventManager, this.username);
    }

    /**
     * This method sends a message to all the speakers at the conference.
     * @param message This parameter is the message text
     */
    void messageAllSpeakers(String message) {
        createBlastMessage("speaker", message);
    }

    /**
     * This method cancels an event
     * @param name This is the title of the event to be canceled
     */
    void cancelEvent(String name) {
        eventManager.getAllEvents().remove(name);
    }

    /**
     * This method reschedules an event if the event is in the list of events and the new time is after the current time
     * @param name The name of the event
     * @param newTime The new time
     */
    void rescheduleEvent(String name, LocalDateTime newTime) {

        if(eventManager.getAllEvents().containsKey(name) && !newTime.isBefore(LocalDateTime.now())){
            eventManager.getAllEvents().get(name).setTime(newTime);
        }
    }


    /**
     * This method sends messages to all people of a specific type
     * @param blastType This is the type of the individual. Choices are "speaker", "organizer" and "attendee"
     * @param message This is the actual message
     */
    void createBlastMessage(String blastType, String message) {

        Map<String, String> userTypes = userManager.getUserTypes();

        for(String user : userTypes.keySet()) {
            if(userTypes.get(user).equals(blastType)) {
                Message mssg = messageManager.createNewMessage(message, this.username, user);
                messageManager.addMessage(user, mssg);
            }
        }

    }

    private void makeUser(String usertype) {
        String username = p.displayEnterUsernamePrompt();
        while(this.userManager.checkCredentials(username) || (username.length() < 3 && username.equalsIgnoreCase("q"))){
            if (username.equalsIgnoreCase("q")) {
                break;
            } else if (this.userManager.checkCredentials(username)) {
                p.displayRepeatUsernameError();
            }
            else if (username.length() < 3) {
                username = p.displayUsernameLengthError();
            }

        }
        if (username.equalsIgnoreCase("q")) {
            return;
        }
        String password = p.displayEnterPasswordPrompt();

        while(password.length() < 3){
            password = p.displayPasswordLengthError();
        }
        String name = p.displayEnterUsersNamePrompt();
        while(name.length() < 2) {
            name = p.displaySpeakerNameError();
        }
        String address = p.displayEnterUserAddressPrompt();

        while(address.length() < 6) {
            address = p.displayAddressLengthError();
        }
        String email = p.displayEnterUserEmailPrompt();
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        while(!email_pattern.matcher(email).matches()){
            email = p.displayInvalidEmail();
        }
        User newUser = userFactory.createNewUser(name, address, email, username, password, usertype);
        userManager.addUser(newUser);
        messageManager.addUserInbox(username);
        p.displayNewUserCreated(newUser.getUsername(), newUser.getPassword());
    }

    private List<String> getSenders(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> users = new ArrayList<>();
        for (Message message: allMessages){
            users.add(messageManager.getSender(message));
        }
        return users;
    }


    private List<User> users(String type) {

        if(userManager == null || userManager.getUserMap() == null) return new ArrayList<>();

        return userManager.getUserMap().values().stream()
                .filter(user -> user.getUserType().equals(type))
                .collect(Collectors.toList());
    }

    private void getStats() {

        Map<String, Double> stats = new HashMap<>();
        Map<String, List<String>> lists = new HashMap<>();

        double numSpeakers = users("organizer").size();
        stats.put("Number of Organizers: ", numSpeakers);
        stats.put("Number of Speakers: ", (double) users("speaker").size());
        stats.put("Number of Attendees: ", (double) users("attendee").size());

        if(stats.get("Number of Speakers: ") == 0) {
            p.displayNoStats();
            return;
        }

        stats.put("Average Event Size: ", eventManager.numberOfEvents() == 0 ? 0
                : 1.0 * (eventManager.getAllEventNamesOnly().stream()
                .map(event -> eventManager.getEventAttendees(event).size())
                .reduce(0, Integer::sum)) / eventManager.getAllEventNamesOnly().size()
        );

        stats.put("Average Number of Events Per Speaker: ", numSpeakers == 0.0 ? 0
                : 1.0 * eventManager.numberOfEvents() / numSpeakers
        );

        stats.put("Number of events that have yet to start", (double) eventManager.getAllEvents().values().stream()
                .filter(e -> e.getTime().isAfter(LocalDateTime.now())).count()
        );


        List<String> events = eventManager.getAllEvents().values().stream()
                .sorted(Comparator.comparingInt(Event::getSize))
                .map(Event::toString)
                .collect(Collectors.toList());

        Collections.reverse(events);

        lists.put("Top Five Events (By Capacity):", events.subList(0, 4));

        List<String> speakers = users("speaker").stream()
                .map(s1 -> (Speaker) s1)
                .sorted(Comparator.comparingInt(Speaker::getNumberOfEvents))
                .map(User::toString)
                .collect(Collectors.toList());

        Collections.reverse(speakers);

        lists.put("Most Popular Speakers (By Number of Events):", speakers.subList(0, 4));

        p.displayNumberStats(stats);
        p.displayListStats(lists);

    }

    public int getRequestToDecide(){
        List<Request> pending = requestManager.getStatusRequests("pending");
        p.displayPendingRequests(pending);
        return p.viewRequestPrompt();
    }

    public void getUserRequests(String username){
        List<Request> user_req = requestManager.getUserRequests(username);
        p.displayUserRequests(user_req);
    }

    public void getAddressedRequests(){
        List<Request> addressed = requestManager.getStatusRequests("addressed");
        p.displayAddressedRequests(addressed);
    }

}
