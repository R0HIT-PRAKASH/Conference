package user.organizer;
import event.Event;
import event.EventManager;
import event.Talk;
import event.Panel;
import message.Message;
import message.MessageManager;
import request.RequestManager;
import request.Request;
import room.Room;
import user.User;
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

    OrganizerPresenter p;

    /**
     * Constructs an OrganizerController object.
     * @param userManager Refers to the UserManager object.
     * @param eventManager Refers to the EventManager object.
     * @param messageManager Refers to the MessageManager object.
     * @param username Refers to the username of the organizer.
     * @param requestManager Refers to the RequestManager object.
     */
    public OrganizerController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username, RequestManager requestManager) {
        super(userManager, eventManager, messageManager, username, requestManager);
        p = new OrganizerPresenter();
    }



    /**
     * Runs the OrganizerController by asking for input and performing the actions
     */
    public void run(){
        deletedMessagesCheck();

        p.displayOptions2();
        p.displayTaskInput();

        final int END_CONDITION = 4;
        int input = p.nextInt();
        while (input != END_CONDITION){
            deletedMessagesCheck();
            determineInput(input);
            input = p.nextInt();
        }
    }

    private void determineInput(int input) {
        label:
        switch (input) {
            case 0:
                p.displayMessageOptions();
                int choice = p.nextInt();
                final int endCond = 8;
                while (choice != endCond) {
                    determineInput0(choice);
                    choice = p.nextInt();
                }
                break;

            case 1:
                p.displayEventOptions();
                int choice1 = p.nextInt();
                final int endCond1 = 11;
                while (choice1 != endCond1) {
                    determineInput1(choice1);
                    choice1 = p.nextInt();
                }
                break;

            case 2:
                p.displayUserOptions();
                int choice2 = p.nextInt();
                final int endCond2 = 10;
                while (choice2 != endCond2) {
                    determineInput2(choice2);
                    choice2 = p.nextInt();
                }
                break;

            case 3:
                p.displayRequestOptions();
                int choice3 = p.nextInt();
                final int endCond3 = 3;
                while (choice3 != endCond3) {
                    determineInput3(choice3);
                    choice3 = p.nextInt();
                }
                break;


            case 14:
                p.displayOptions2();
                break;

            default:
                p.displayInvalidInputError();
                break;
        }
        deletedMessagesCheck();
        p.displayNextTaskPromptOrganizer();
        p.displayOptions2();
    }

    protected void determineInput0(int input) {
        switch (input) {
            case 0:
                viewMessages(messageManager.generateEffectiveMessageList(this.username, "inbox"),
                        "inbox");
                break;

            case 1:
                viewMessages(messageManager.generateEffectiveMessageList(this.username, "starred"),
                        "starred");
                break;

            case 2:
                viewMessages(messageManager.generateEffectiveMessageList(this.username, "deleted"),
                        "deleted");
                break;

            case 3:
                viewMessages(messageManager.generateEffectiveMessageList(this.username, "archived"),
                        "archived");
                break;

            case 4:
                if(userManager.getSizeOfUserMap() == 1) {
                    p.displayConferenceError();
                    break;
                }
                String recipient = p.displayMethodPrompt();
                if (recipient.equals("q")){
                    break;
                }
                else if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    String messageContents = p.displayEnterMessagePrompt(recipient);
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

            case 5:
                String message = p.displayAllAttendeeMessagePrompt();

                if (message.equalsIgnoreCase("q")) {
                    break;
                }
                messageAllAttendees(message);
                p.displayMessageSentPrompt();
                break;

            case 6:
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

            case 7:
                String speakermessage = p.displayAllSpeakerMessagePrompt();
                if (speakermessage.equalsIgnoreCase("q")) {
                    break;
                }
                messageAllSpeakers(speakermessage);
                p.displayMessageSentPrompt();
                break;

            case 14:
                p.displayMessageOptions();
                break;

            default:
                p.displayMessageOptionsInvalidChoice();
                break;

        }
        deletedMessagesCheck();
        p.displayNextTaskPromptOrgOptDisplayed();
        p.displayMessageOptions();
    }

    protected void determineInput1(int input) {
        label:
        switch (input) {
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

                determineInputAddEvent();
                break;

            case 5:
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
                    userManager.cancelEventSpot(username, event);
                }

                //Remove event from list of events
                eventManager.removeEvent(eventManager.getEvent(event));
                break;

            case 6:
                String[] responses = new String[1];
                responses[0] = this.username;
                List<String> responsibleEvents = userManager.allCreatedEvents(this.username);
                p.displayEventReschedulePrompt(responses, responsibleEvents);
                String eventName1 = responses[0];
                if (eventName1.equalsIgnoreCase("q")) {
                    break;
                }
                LocalDateTime newTime = p.askTime();
                if(eventManager.roomIsOccupied(eventManager.getEvent(eventName1).getRoomNumber(), newTime,
                        eventManager.getEvent(eventName1).getDuration()) ||
                        eventManager.speakerIsOccupied(userManager.getUser(eventManager.getEvent(eventName1).getSpeakerName()),
                                newTime, eventManager.getEvent(eventName1).getDuration())){
                    p.displayInvalidEventError();
                }else{
                    rescheduleEvent(eventName1, newTime);
                    p.displayEventTimeChanged();
                }
                break;

            case 7:

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

            case 8: // I would suggest putting this in a Modify Event Tab for GUI
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

                // display new capacity prompt
                int newCapacity = p.displayEnterNewEventCapacityPrompt(eventManager.getRoom(eventManager.getEvent(eventNameToModify).getRoomNumber()).getCapacity(), eventManager.getEvent(eventNameToModify).getAttendeeSet().size());
                eventManager.changeEventCapacity(eventManager.getEvent(eventNameToModify), newCapacity);
                break;

            case 9:
                getStats();
                break;
            case 10:
                searchForEvents();
                break;
            case 14:
                p.displayEventOptions();
                break;

            default:
                p.displayEventOptionsInvalidChoice();
                break;

        }
        deletedMessagesCheck();
        p.displayNextTaskPromptOrgOptDisplayed();
        p.displayEventOptions();
    }


    protected void determineInput2(int input) {
        switch (input) {
            case 0:
                String newUserType = p.displayNewUserCreation();
                while (!(newUserType.equalsIgnoreCase("ORGANIZER") ||
                        newUserType.equalsIgnoreCase("ATTENDEE") ||
                        newUserType.equalsIgnoreCase("SPEAKER") ||
                        newUserType.equalsIgnoreCase("VIP"))){
                    newUserType = p.displayInvalidUserTypeError();
                }
                makeUser(newUserType);
                break;

            case 1:
                List<String> stringsOfRooms = eventManager.getToStringsOfRooms();
                p.displayRoomList(stringsOfRooms);
                break;

            case 2:
                List<String> stringsOfSpeakers = userManager.getToStringsOfUsers("speaker");
                p.displayUserList(stringsOfSpeakers, "Speaker");
                break;

            case 3:
                List<String> stringsOfAttendees = userManager.getToStringsOfUsers("attendee");
                p.displayUserList(stringsOfAttendees, "Attendee");
                break;

            case 4:
                List<String> stringsOfOrganizers = userManager.getToStringsOfUsers("organizer");
                p.displayUserList(stringsOfOrganizers, "Organizer");
                break;

            case 5:
                List<String> stringsOfVIP = userManager.getToStringsOfUsers("vip");
                p.displayUserList(stringsOfVIP, "VIP");
                break;

            case 6:
                viewCorporation();
                break;

            case 7:
                String corporation = p.displayEnterCompanyPrompt();
                if(corporation.equalsIgnoreCase("q")){
                    break;
                }
                while(corporation.equalsIgnoreCase("")){
                    corporation = p.displayInvalidCompanyError();
                }
                editCorporation(corporation);
                break;

            case 8:
                viewBio();
                break;

            case 9:
                String bio = p.displayEnterBioPrompt();
                if(bio.equalsIgnoreCase("q")){
                    break;
                }
                while(bio.equalsIgnoreCase("")){
                    bio = p.displayInvalidBioError();
                }
                editBio(bio);
                break;

            default:
                p.displayUserOptionsInvalidChoice();
                break;

        }
        deletedMessagesCheck();
        p.displayNextTaskPromptOrgOptDisplayed();
        p.displayUserOptions();
    }

    protected void determineInput3(int input) {
        switch (input) {
            case 0: //address requests
                int to_change = getRequestToDecide();
                if (to_change != -1) {
                    Request update = requestManager.getStatusRequests("pending").get(to_change - 1);
                    String decision = p.displayRequestDecisionPrompt(update);
                    boolean valid = requestManager.checkIsValidStatus(decision);
                    while (!valid) {
                        p.requestDecisionInvalid();
                        decision = p.displayRequestDecisionPrompt(update);
                        valid = requestManager.checkIsValidStatus(decision);
                    }
                    requestManager.updateRequestStatus(update, decision);
                    if (decision.equals("addressed")) {
                        p.successfullyAddressedRequest();
                    } else {
                        p.successfullyRejectedRequest();
                    }
                }
                break;

            case 1: //view addressed requests
                getAddressedRequests();
                break;

            case 2: //view user requests
                getUserRequests(p.viewUserRequestPrompt());
                break;

            default:
                p.displayRequestsOptionsInvalidChoice();
                break;

        }
        deletedMessagesCheck();
        p.displayNextTaskPromptOrgOptDisplayed();
        p.displayRequestOptions();
    }

    private void determineInputAddEvent() {
        //Ask for type of event
        String eventType = p.displayPromptEventType();
        while(!eventType.equalsIgnoreCase("talk") && !eventType.equalsIgnoreCase("panel")
                && !eventType.equalsIgnoreCase("party") && !eventType.equalsIgnoreCase("q")){
            eventType = p.displayInvalidEventType();
        }
        if(eventType.equalsIgnoreCase("q")){
            return;
        }

        //Ask for category of event
        String eventTag = askForEventTag(eventType);
        if (eventTag.equals("q")) return;


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
            return;
        }else if(answerVip.equalsIgnoreCase("yes")) {
            vip = true;
        }

        //Ask for name of event
        String name = p.displayEventTitlePrompt();
        while(eventManager.getAllEvents().keySet().contains(name) && !name.equalsIgnoreCase("q")){
            name = p.displayInvalidEventName();
        }
        // Adding the option to end the case early here in case a User wants to go back
        if (name.equals("q")){
            return;
        }

        int duration = p.displayDurationPrompt();

        if(duration == 0){
            return;
        }

        // Adding the speakers to the event
        String speaker = "";
        List<String> speakers = new ArrayList<>();
        switch (eventType) {
            case "talk":
                speaker = determineInputGetSpeaker(time, duration);
                if (speaker.equalsIgnoreCase("q")) {
                    return;
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
                return;
            }
            int capacity = p.displayEventCapacityPrompt();

            if(capacity == -1){
                return;
            }

            int comp = p.displayComputersPrompt();

            if(comp == -1){
                return;
            }

            String answerProject = p.displayProjectorPrompt();
            boolean project = false;


            if(answerProject.equalsIgnoreCase("q")){
                return;
            }else if(answerProject.equalsIgnoreCase("yes")) {
                project = true;
            }


            int cha = p.displayChairsPrompt();

            if(cha == -1){
                return;
            }

            int tab = p.displayTablesPrompt();

            if(tab == -1){
                return;
            }

            if(ans.equalsIgnoreCase("suggestions")) {
                p.displayRecommendedRooms(capacity, comp, project, cha, tab, eventManager.getRooms());
                num = p.displayEnterRoomNumberPrompt();
            }

            while(eventManager.roomIsOccupied(num, time, duration)){
                num = p.displayOccupiedRoom();
            }

            List<String> organizers = userManager.getOrganizerUsernames();
            List<String> creators = new ArrayList<>();
            creators.add(this.username);
            p.displayAndGetCreators(creators, organizers);

            eventManager.addEvent(eventType, name, time, duration, num, capacity, comp, project, cha, tab, creators, vip, speaker, speakers, eventTag);
            addEvent(eventType, name, speaker, speakers, creators, eventManager.containsEvent(name));

        }else{ // room exists
            int cap = p.displayEnterEventCapacityPrompt(room.getCapacity());
            while (cap > room.getCapacity() || cap < 0) {
                cap = p.displayRoomCapacityError();
            }
            if(cap == 0){
                return;
            }

            List<String> organizers = userManager.getOrganizerUsernames();
            List<String> creators = new ArrayList<>();
            creators.add(this.username);
            p.displayAndGetCreators(creators, organizers);

            eventManager.addEvent(eventType, name, time, duration, num, room.getCapacity(), room.getComputers(), room.getProjector(), room.getChairs(), room.getTables(), creators, vip, speaker, speakers, eventTag);
            addEvent(eventType, name, speaker, speakers, creators, eventManager.containsEvent(name));
        }
    }

    private String askForEventTag(String eventType) {
        String eventTag = "";
        if (eventType.equalsIgnoreCase("talk")){
            eventTag = p.displayPromptEventTagTalk().toLowerCase();
            while(!eventTag.equals("development") && !eventTag.equals("motivational") && !eventTag.equals("networking") && !eventTag.equals("q")){
                eventTag = p.displayInvalidTagCategoryType();
            }
            if(eventTag.equalsIgnoreCase("q")){
                return "q";
            }
        }
        else if (eventType.equalsIgnoreCase("panel")){
            eventTag = p.displayPromptEventTagPanel().toLowerCase();
            while(!eventTag.equals("development") && !eventTag.equals("motivational") && !eventTag.equals("networking") && !eventTag.equals("q")){
                eventTag = p.displayInvalidTagCategoryType();
            }
            if(eventTag.equalsIgnoreCase("q")){
                return "q";
            }
        }
        else if (eventType.equalsIgnoreCase("party")){
            eventTag = p.displayPromptEventTagParty().toLowerCase();
            while(!eventTag.equals("company") && !eventTag.equals("graduation") && !eventTag.equals("q")){
                eventTag = p.displayInvalidTagCategoryType();
            }
            if(eventTag.equalsIgnoreCase("q")){
                return "q";
            }
        }
        return eventTag;
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
            userManager.createdEvent(name, creators);
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
                chairs, tables, creators, vip, speaker, speakers, "");
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
                messageManager.addMessage(this.username, message, user);
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
        String name = p.displayEnterUserNamePrompt();
        while(name.length() < 2) {
            name = p.displayUserNameError();
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
        String company = p.displayEnterCompanyPrompt();
        String bio = p.displayEnterBioPrompt();
        userManager.addUser(name, address, email, username, password, usertype, company, bio);
        messageManager.addUserInbox(username);
        requestManager.addUserRequests(username);
        p.displayNewUserCreated(username, password);
        return username;
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

        System.out.println(eventManager.getAllEventNamesOnly());

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
                : 1.0 * (eventManager.getAllEvents().values().stream()
                .map(Event::getSize)
                .reduce(0, Integer::sum)) / eventManager.getAllEvents().size()
        );

        stats.put("Average Number of Events Per Speaker: ", numSpeakers == 0.0 ? 0
                : 1.0 * eventManager.numberOfEvents() / numSpeakers
        );

        stats.put("Number of events that have yet to start", (double) eventManager.getAllEvents().values().stream()
                .filter(e -> e.getTime().isAfter(LocalDateTime.now())).count()
        );

        stats.put("Number of Messages", (double) messageManager.getAllUserMessages().values().stream()
                .map(List::size)
                .reduce(0, Integer::sum)
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

        List<Integer> eventSizes = eventManager.getAllEvents().values().stream()
                .map(Event::getSize)
                .collect(Collectors.toList());

        p.displayHistogram(eventSizes, "Distribution of Event Sizes");


    }

    public int getRequestToDecide(){
        List<Request> pending = requestManager.getStatusRequests("pending");
        p.displayPendingRequests(pending);
        if (pending.size() > 0){
            return p.viewRequestPrompt();
        }
        else{
            return -1;
        }
    }

    public void getUserRequests(String username){
        List<Request> user_req = requestManager.getUserRequests(username);
        if (user_req.size() == 0){
            p.noUserRequests();
        }
        else{
            p.displayUserRequests(user_req);
        }
    }

    public void getAddressedRequests(){
        List<Request> addressed = requestManager.getStatusRequests("addressed");
        if (addressed.size() == 0){
            p.noAddressedRequests();
        }
        else{
            p.displayAddressedRequests(addressed);
        }
    }

}