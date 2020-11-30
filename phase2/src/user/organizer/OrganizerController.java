package user.organizer;

import event.Event;
import event.EventManager;
import event.Talk;
import event.Panel;
import message.Message;
import message.MessageManager;
import request.RequestManager;
import room.Room;
import user.User;
import user.UserFactory;
import user.UserManager;
import user.attendee.AttendeeController;
import user.speaker.Speaker;

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

        final int END_CONDITION = 22;
        int input = p.nextInt();
        while (input != END_CONDITION){ // 22 is ending condition
            determineInput(input);
            input = p.nextInt();
        }
    }

    private void determineInput(int input) {
        label:
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
                //Ask user what type of event they would like to create (Talk.java, Panel, Party).
                //Depending on what type of event they chose, ask appropriate questions. (Same except for how we ask for
                //the speakers, i.e one speaker for talk, multiple speakers for panel, no speaker for party).
                //Call addEvent in eventManager and pass in type of event and other prompts.
                //EventManager will then call eventFactory so eventFactory.getEvent("talk", hashmap)
                //Then eventManager will add in the event into the list of events.

                //Ask for type of event
                String eventType = p.displayPromptEventType();
                while(!eventType.equalsIgnoreCase("talk") && !eventType.equalsIgnoreCase("panel")
                        && !eventType.equalsIgnoreCase("party") && !eventType.equalsIgnoreCase("q")){
                    eventType = p.displayInvalidEventType();
                }
                if(eventType.equalsIgnoreCase("q")){
                    break;
                }

                //Ask for time of event
                p.displayAddConferencePrompt();
                LocalDateTime time = p.askTime();
                while(!eventManager.between9to5(time) || !eventManager.checkTimeIsAfterNow(time)) {
                    if (!eventManager.between9to5(time)) {
                        p.displayInvalidHourError();
                        time = p.askTime();
                    } else if (!eventManager.checkTimeIsAfterNow(time)) {
                        p.displayInvalidDateError();
                        time = p.askTime();
                    }
                }

                //Ask if event is for VIP users
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

                //Ask for name of event
                String name = p.displayEventTitlePrompt();
                // Adding the option to end the case early here in case a User wants to go back
                if (name.equals("q")){
                    break;
                }

                int duration = p.displayDurationPrompt();

                if(duration == 0){
                    break;
                }

                // Adding the speakers to the event
                String speaker = "";
                List<String> speakers = new ArrayList<>();
                switch (eventType) {
                    case "talk":
                        speaker = determineInputGetSpeaker(time, duration);
                        if (speaker.equalsIgnoreCase("q")) {
                            break label;
                        }
                        speakers = null;
                        break;
                    case "party":
                        speaker = null;
                        speakers = null;
                        break;
                    case "panel":
                        String response;
                        String speakerName;
                        do {
                            speakerName = determineInputGetSpeaker(time, duration);
                            if (speakerName.equalsIgnoreCase("q")) {
                                break;
                            }else if(speakers.contains(speakerName)){
                                p.displaySpeakerAlreadyAdded();
                            }else{
                                speakers.add(speakerName);
                            }
                            response = p.askNewSpeakerPrompt();
                            if(!response.equals("Y") && speakers.size() < 2) {
                                p.notEnoughPeople();
                            }
                        } while (response.equals("Y") || speakers.size() < 2);
                        speaker = null;
                        break;
                }

                int num = p.displayEnterRoomNumberPrompt();
                while(eventManager.roomIsOccupied(num, time, duration)){
                    num = p.displayOccupiedRoom();
                }
                Room room = eventManager.getRoom(num);
                String ans;
                if(eventManager.getRoom(num) == null) {
                    ans = eventManager.getRooms().isEmpty() ?
                    p.displayRoomNumberQuestion1() : p.displayRoomNumberQuestion2();

                    if (ans.equalsIgnoreCase("q")) {
                        break;
                    }
                    int capacity = p.displayEventCapacityPrompt();

                    if(capacity == -1){
                        break;
                    }

                    int comp = p.displayComputersPrompt();

                    if(comp == -1){
                        break;
                    }

                    String answerProject = p.displayProjectorPrompt();
                    boolean project = false;


                    if(answerProject.equalsIgnoreCase("q")){
                        break;
                    }else if(answerProject.equalsIgnoreCase("yes")) {
                        project = true;
                    }


                    int cha = p.displayChairsPrompt();

                    if(cha == -1){
                        break;
                    }

                    int tab = p.displayTablesPrompt();

                    if(tab == -1){
                        break;
                    }

                    if(ans.equalsIgnoreCase("suggestions")) {
                        p.displayRecommendedRooms(capacity, comp, project, cha, tab, eventManager.getRooms());
                        num = p.displayEnterRoomNumberPrompt();
                    }

                    while(eventManager.roomIsOccupied(num, time, duration)){
                        num = p.displayOccupiedRoom();
                    }

                    List<Organizer> organizers = userManager.getOrganizers();
                    List<String> creators = new ArrayList<>();
                    creators.add(this.username);
                    p.displayAndGetCreators(creators, organizers);

                    Event event = eventManager.createNewEvent(eventType, name, time, duration, num, capacity, comp, project, cha, tab, creators, vip, speaker, speakers);
                    if(eventManager.checkEventIsValid(event)){
                        eventManager.addEvent(eventType, name, time, duration, num, capacity, comp, project, cha, tab, creators, vip, speaker, speakers);
                        addEvent(eventType, name, speaker, speakers, creators, true);
                    }else{
                        addEvent(eventType, name, speaker, speakers, creators, false);
                    }

                }else{ // room exists
                    p.displayEnterEventCapacityPrompt(room.getCapacity());  // need to ask what they want capacity to be and cannot be more then room can hold
                    int cap = p.nextInt();
                    while (cap > room.getCapacity() || cap < 0) {
                        cap = p.displayRoomCapacityError();
                    }
                    if(cap == 0){
                        break;
                    }

                    List<Organizer> organizers = userManager.getOrganizers();
                    List<String> creators = new ArrayList<>();
                    creators.add(this.username);
                    p.displayAndGetCreators(creators, organizers);

                    Event event = eventManager.createNewEvent(eventType, name, time, duration, num, room.getCapacity(), room.getComputers(), room.getProjector(), room.getChairs(), room.getTables(), creators, vip, speaker, speakers);
                    if(eventManager.checkEventIsValid(event)){
                        eventManager.addEvent(eventType, name, time, duration, num, room.getCapacity(), room.getComputers(), room.getProjector(), room.getChairs(), room.getTables(), creators, vip, speaker, speakers);
                        addEvent(eventType, name, speaker, speakers, creators, true);
                    }else {
                        addEvent(eventType, name, speaker, speakers, creators, false);
                    }
                }
                break;

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
                if(userManager.allCreatedEvents(username).isEmpty()){
                    p.displayNoOrganizedEvents();
                    break;
                }
                List<String> eventNames = userManager.allCreatedEvents(this.username);
                List<Event> futureEvents = eventManager.chronologicalEvents(eventManager.eventNotHappened(eventNames));
                p.displayYourCreatedEvents(futureEvents);

                //Getting answer from organizer
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

                //Removing event from of the speakers' lists of speaking events
                if(eventManager.getEvent(event) instanceof Panel){
                    for(String username : eventManager.getEvent(event).getSpeakersList()){
                        userManager.removeSpeakingEvent(username, event);
                    }
                }else if(eventManager.getEvent(event) instanceof Talk){
                    userManager.removeSpeakingEvent(eventManager.getEvent(event).getSpeakerName(), event);
                }

                //Removing event from the organizer's list of organized events
                System.out.println(eventManager.getOrganizersList(event));
                for(String username : eventManager.getOrganizersList(event)){
                    System.out.println("ha");
                    userManager.removeCreatedEvent(username, eventManager.getEvent(event));
                }

                //Remove event from every attendee's list of attending events
                for(String username : eventManager.getEventAttendees(event)){
                    userManager.cancelEventSpot(username, eventManager.getEvent(event), eventManager);
                }

                //Remove event from list of events
                eventManager.removeEvent(eventManager.getEvent(event));
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
                Event event1 = eventManager.getEvent(eventName1);
                if(eventManager.roomIsOccupied(event1.getRoomNumber(), newTime, event1.getDuration()) ||
                        eventManager.speakerIsOccupied(userManager.getUser(event1.getSpeakerName()), newTime,
                                event1.getDuration())){
                    p.displayInvalidEventError();
                }else{
                    rescheduleEvent(eventName1, newTime);
                    p.displayEventTimeChanged();
                }
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

                int roomNumber = p.displayRoomCreationPrompt();
                if (roomNumber == 0) {
                    break;
                }
                int capac = p.displayRoomCapacityPrompt();

                if(capac == -1){
                    break;
                }

                int computers = p.displayComputersPrompt();

                if(computers == -1){
                    break;
                }

                String answerProjector = p.displayProjectorPrompt();

                boolean projector = false;


                if(answerProjector.equalsIgnoreCase("q")){
                    break;
                }else if(answerProjector.equalsIgnoreCase("yes")) {
                    projector = true;
                }

                int chairs = p.displayChairsPrompt();

                if(chairs == -1){
                    break;
                }

                int tables = p.displayTablesPrompt();

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
                int newCapacity = p.displayEnterNewEventCapacityPrompt(room1.getCapacity(), eventToModify.getAttendeeSet().size());
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

            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPromptOrganizer();
    }

    private void addEvent(String eventType, String name, String speaker, List<String> speakers, List<String> creators, boolean added) {
        if(!added) {p.displayEventCreationError();}
        else {
            p.displaySuccessfulEventCreation();
            if (eventType.equals("talk")){
                userManager.addSpeakingEvent(speaker, name);
            }
            else if (eventType.equals("panel")){
                for (String s : speakers) {
                    userManager.addSpeakingEvent(s, name);
                }
            }
            userManager.createdEvent(eventManager.getEvent(name), creators);
        }
    }

    private String determineInputGetSpeaker(LocalDateTime time, int duration) {
        String speaker;
        speaker = p.displayEnterSpeakerPrompt();

        if(!userManager.checkCredentials(speaker)) {
            p.displaySpeakerCredentialError();
            speaker = makeUser("speaker");
        }
        else{
            while (!(userManager.getUserType(speaker).equals("speaker")) ||
                    eventManager.speakerIsOccupied(userManager.getUser(speaker), time, duration)){
                speaker = p.displayNotSpeakerError();
                if (speaker.equalsIgnoreCase("q")) {
                    break;
                }
                if(!userManager.checkCredentials(speaker)) {
                    p.displaySpeakerCredentialError();
                    speaker = makeUser("speaker");
                }
            }
        }
        return speaker;
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
    boolean addEvent(String eventType, String name, LocalDateTime time, Integer duration, int roomNumber, int capacity,
                     int computers, boolean projector, int chairs, int tables, List<String> creators, boolean vip, String speaker, List<String> speakers) {
        return eventManager.addEvent(eventType, name, time, duration, roomNumber, capacity, computers, projector,
                chairs, tables, creators, vip, speaker, speakers);
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

    private String makeUser(String usertype) {
        String username = p.displayEnterUsernamePrompt();
        while(this.userManager.checkCredentials(username) || (username.length() < 3 && username.equalsIgnoreCase("q"))){
            if (username.equalsIgnoreCase("q")) {
                break;
            } else if (this.userManager.checkCredentials(username)) {
                username = p.displayRepeatUsernameError();
            }
            else if (username.length() < 3) {
                username = p.displayUsernameLengthError();
            }

        }
        if (username.equalsIgnoreCase("q")) {
            return null;
        }
        String password = p.displayEnterPasswordPrompt();

        while(password.length() < 3){
            password = p.displayPasswordLengthError();
        }
        String name = p.displayEnterSpeakerNamePrompt();
        while(name.length() < 2) {
            name = p.displaySpeakerNameError();
        }
        String address = p.displayEnterSpeakerAddressPrompt();

        while(address.length() < 6) {
            address = p.displayAddressLengthError();
        }
        String email = p.displayEnterSpeakerEmailPrompt();
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        while(!email_pattern.matcher(email).matches()){
            email = p.displayInvalidEmail();
        }
        User newUser = userFactory.createNewUser(name, address, email, username, password, usertype);
        userManager.addUser(newUser);
        messageManager.addUserInbox(username);
        p.displayNewUserCreated(newUser.getUsername(), newUser.getPassword());
        return newUser.getUsername();
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

        if(events.size() > 5) events = events.subList(0, 4);

        lists.put("Top Five Events (By Capacity):", events);

        List<String> speakers = users("speaker").stream()
                .map(s1 -> (Speaker) s1)
                .sorted(Comparator.comparingInt(Speaker::getNumberOfEvents))
                .map(User::toString)
                .collect(Collectors.toList());

        Collections.reverse(speakers);

        if(speakers.size() > 5) speakers = speakers.subList(0, 4);

        lists.put("Most Popular Speakers (By Number of Events):", speakers);

        p.displayNumberStats(stats);
        p.displayListStats(lists);

    }

}