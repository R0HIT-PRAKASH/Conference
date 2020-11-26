import com.sun.org.apache.xpath.internal.operations.Or;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Refers to the controller class that will deal with the actions of an organizer object.
 */
public class OrganizerController extends AttendeeController {
    private Scanner scan = new Scanner(System.in);
    public UserManager userManager;
    public EventManager eventManager;
    public MessageManager messageManager;
    public String username;
    OrganizerPresenter p;

    /**
     * Constructs an OrganizerController object.
     * @param userManager Refers to the UserManager object.
     * @param eventManager Refers to the EventManager object.
     * @param messageManager Refers to the MessageManager object.
     * @param username Refers to the username of the organizer.
     */
    public OrganizerController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username) {
        super(userManager, eventManager, messageManager, username);
        p = new OrganizerPresenter();
    }



    /**
     * Runs the OrganizerController by asking for input and performing the actions
     */
    public void run(){
        p.displayOptions2();
        p.displayTaskInput();
        final int END_CONDITION = 22;
        int input = nextInt();
        while (input != END_CONDITION){ // 21 is ending condition
            determineInput(input);
            input = nextInt();
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
                p.displayMethodPrompt();
                // System.out.println("Who would you like to message? (Please enter the username of the recipient). Otherwise, type 'q' to exit");
                String recipient = scan.nextLine();
                if (recipient.equals("q")){
                    break;
                }
                else if(messageManager.checkIsMessageable(recipient, this.username, userManager)) {
                    p.displayEnterMessagePrompt(recipient);
                    // System.out.println("What message would you like to send to: " + recipient + ". " + "If you would no longer like to send a message, type 'q' to exit");
                    String messageContents = scan.nextLine();
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
                p.displayEnterUserUsernamePrompt();
                // System.out.println("Which user are you replying to (it is case sensitive). If you no longer want to reply to a user, type 'q' to exit: ");
                String recipients = scan.nextLine();
                while (!attendees.contains(recipients)){
                    p.displayUserReplyError();
                    recipients = scan.nextLine();
                    if (recipients.equals("q")){
                        break;
                    }
                }
                if (recipients.equals("q")){
                    break;
                }
                p.displayEnterMessagePrompt();
                String content = scan.nextLine();
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
                p.displayEventCancelPrompt();
                // System.out.println("What is the name of the event you no longer want to attend? Type 'q' if you no longer want to cancel your spot in an event.");
                String cancel = scan.nextLine();
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
                p.displayEventSignUpPrompt();
                // System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
                String eventSignedUp = scan.nextLine();
                if (eventSignedUp.equals("q")){
                    break;
                }
                while (eventManager.getEvent(eventSignedUp) == null ||
                        !future.contains(eventManager.getEvent(eventSignedUp))){
                    p.displayInvalidEventSignUp();
                    eventSignedUp = scan.nextLine();
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
                p.displayAddConferencePrompt();
                LocalDateTime time = askTime();
                while(!eventManager.between9to5(time) || !eventManager.checkTimeIsAfterNow(time)) {
                    if (!eventManager.between9to5(time)) {
                        p.displayInvalidHourError();
                        time = askTime();
                    } else if (!eventManager.checkTimeIsAfterNow(time)) {
                        p.displayInvalidDateError();
                        time = askTime();
                    }
                }
                p.displayEventTitlePrompt();
                String name = scan.nextLine();
                // Adding the option to end the case early here in case a User wants to go back
                if (name.equals("q")){
                    break;
                }
                p.displayDurationPrompt();
                int duration = nextInt();
                while(duration <= 0){
                    if(duration == -1){
                        break;
                    }
                    p.displayInvalidDuration();
                    duration = nextInt();
                }
                if(duration == -1){
                    break;
                }

                p.displayEnterSpeakerPrompt();
                String speaker = scan.nextLine();
                if(!userManager.checkCredentials(speaker)) {
                    p.displaySpeakerCredentialError();
                    makeSpeaker();
                    p.displayEnterNewSpeakerPrompt();
                    speaker = scan.nextLine();
                }
                else{
                    while (!(userManager.getUserType(speaker) == "speaker")){
                        p.displayNotSpeakerError();
                        speaker = scan.nextLine();
                        if (speaker.equalsIgnoreCase("q")) {
                            break;
                        }
                    }
                }
                if (speaker.equalsIgnoreCase("q")) {
                    break;
                }

                p.displayEnterRoomNumberPrompt();
                int num = nextInt();
                Room room = eventManager.getRoom(num);
                String ans;
                if(eventManager.getRoom(num) == null) {
                    if (eventManager.getRooms().isEmpty()) {
                        p.displayRoomNumberErrorQuestion1();
                        ans = scan.nextLine();
                        while (!ans.equalsIgnoreCase("create") && !ans.equalsIgnoreCase("q")) {
                            p.displayRoomDecisionQError1();
                            ans = scan.nextLine();
                        }
                    } else {
                        p.displayRoomNumberErrorQuestion2();
                        ans = scan.nextLine();
                        while (!ans.equalsIgnoreCase("create") && !ans.equalsIgnoreCase("suggestions") // need to fix it so it doesnt give suggestions as option when empty
                                && !ans.equalsIgnoreCase("q")) {
                            p.displayRoomDecisionQError2();
                            ans = scan.nextLine();
                        }
                    }
                    if (ans.equalsIgnoreCase("q")) {
                        break;
                    }
                    p.displayEventCapacityPrompt();
                    int capacity = nextInt();
                    while(capacity <= 0){
                        if(capacity == -1){
                            break;
                        }
                        p.displayInvalidCapacity();
                        capacity = nextInt();
                    }
                    if(capacity == -1){
                        break;
                    }

                    p.displayComputersPrompt();
                    int comp = nextInt();
                    while(comp < 0){
                        if(comp == -1){
                            break;
                        }
                        p.displayInvalidComputers();
                        comp = nextInt();
                    }
                    if(comp == -1){
                        break;
                    }

                    p.displayProjectorPrompt();
                    String answerProject = scan.nextLine();
                    boolean project = false;
                    while(!answerProject.equalsIgnoreCase("yes") && !answerProject.equalsIgnoreCase("no")){
                        if(answerProject.equalsIgnoreCase("q")){
                            break;
                        }
                        p.displayInvalidProjector();
                        answerProject = scan.nextLine();
                    }

                    if(answerProject.equalsIgnoreCase("q")){
                        break;
                    }else if(answerProject.equalsIgnoreCase("yes")) {
                        project = true;
                    }

                    p.displayChairsPrompt();
                    int cha = nextInt();
                    while(cha < 0){
                        if(cha == -1){
                            break;
                        }
                        p.displayInvalidChairs();
                        cha = nextInt();
                    }
                    if(cha == -1){
                        break;
                    }

                    p.displayTablesPrompt();
                    int tab = nextInt();
                    while(tab < 0){
                        if(tab == -1){
                            break;
                        }
                        p.displayInvalidTables();
                        tab = nextInt();
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
                    num = nextInt();
                    List<Organizer> organizers = userManager.getOrganizers();
                    List<String> creators = new ArrayList<>();
                    creators.add(this.username);
                    p.displayAndGetCreators(creators, organizers);
                    boolean added = addEvent(name, speaker, time, duration, num, capacity, comp, project, cha, tab, creators);
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
                    int cap = nextInt();
                    while (cap > room.getCapacity()) {
                        p.displayRoomCapacityError(room.getCapacity());
                        cap = scan.nextInt();
                    }
                    List<Organizer> organizers = userManager.getOrganizers();
                    List<String> creators = new ArrayList<>();
                    creators.add(this.username);
                    p.displayAndGetCreators(creators, organizers);
                    boolean added = addEvent(name, speaker, time, duration, num, room.getCapacity(), room.getComputers(), room.getProjector(), room.getChairs(), room.getChairs(), creators);
                    if(!added) {p.displayEventCreationError();}
                    else {
                        p.displaySuccessfulEventCreation();
                        userManager.addSpeakingEvent(speaker, name);
                        userManager.createdEvent(eventManager.getEvent(name), creators);
                    }
                    break;
                }

            case 8:
                p.displayAllAttendeeMessagePrompt();
                String message = scan.nextLine();
                if (message.equalsIgnoreCase("q")) {
                    break;
                }
                messageAllAttendees(message);
                p.displayMessageSentPrompt();
                break;

            case 9:
                p.displayEventMessagePrompt();
                String eventname = scan.nextLine();
                if (eventname.equalsIgnoreCase("q")) {
                    break;
                }
                else if(eventManager.getEvent(eventname) == null) {
                    p.displayInvalidEventError();
                } else {
                    p.displayAllAttendeeEventMessagePrompt();
                    messageEventAttendees(scan.nextLine(), eventname);
                    p.displayMessageSentPrompt();
                }
                break;

            case 10:
                p.displayAllSpeakerMessagePrompt();
                String speakermessage = scan.nextLine();
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
                p.displayEventRemovalPrompt();
                String event = scan.nextLine();
                if (event.equalsIgnoreCase("q")) {
                    break;
                }
                while(!futureEvents.contains(eventManager.getEvent(event))){
                    p.displayCannotCancel();
                    event = scan.nextLine();
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
                LocalDateTime newTime = askTime();
                rescheduleEvent(eventName1, newTime);
                break;

            case 13:
                makeSpeaker();
                break;

            case 14:
                p.displayOptions2();
                break;


            case 15:
                p.displayRoomCreationPrompt();
                int roomNumber = nextInt();
                if (roomNumber == -1) {
                    break;
                }
                p.displayRoomCapacityPrompt();
                int capac = nextInt();
                while(capac <= 0){
                    if(capac == -1){
                        break;
                    }
                    p.displayInvalidCapacity();
                    capac = nextInt();
                }
                if(capac == -1){
                    break;
                }

                p.displayComputersPrompt();
                int computers = nextInt();
                while(computers < 0){
                    if(computers == -1){
                        break;
                    }
                    p.displayInvalidComputers();
                    computers = nextInt();
                }
                if(computers == -1){
                    break;
                }

                p.displayProjectorPrompt();
                String answerProjector = scan.nextLine();
                boolean projector = false;
                while(!answerProjector.equalsIgnoreCase("yes") && !answerProjector.equalsIgnoreCase("no")){
                    if(answerProjector.equalsIgnoreCase("q")){
                        break;
                    }
                    p.displayInvalidProjector();
                    answerProjector = scan.nextLine();
                }

                if(answerProjector.equalsIgnoreCase("q")){
                    break;
                }else if(answerProjector.equalsIgnoreCase("yes")) {
                    projector = true;
                }

                p.displayChairsPrompt();
                int chairs = nextInt();
                while(chairs < 0){
                    if(chairs == -1){
                        break;
                    }
                    p.displayInvalidChairs();
                    chairs = nextInt();
                }
                if(chairs == -1){
                    break;
                }

                p.displayTablesPrompt();
                int tables = nextInt();
                while(tables < 0){
                    if(tables == -1){
                        break;
                    }
                    p.displayInvalidTables();
                    tables = nextInt();
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

                p.displayEventModifyPrompt();
                String eventNameToModify = scan.nextLine();
                if (eventNameToModify.equalsIgnoreCase("q")) {
                    break;
                }
                while(!namesOfEvents.contains(eventNameToModify)){
                    p.displayCannotModifyEvent();
                    // extend so you handle when they do an event you didnt create with specific error
                    eventNameToModify = scan.nextLine();
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
                int newCapacity = nextInt();
                while (newCapacity > room1.getCapacity() || newCapacity < eventToModify.getAttendeeSet().size()) {
                    p.displayModifyRoomCapacityError(room1.getCapacity(), eventToModify.getAttendeeSet().size());
                    newCapacity = scan.nextInt();}
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




    /**
     * This method adds an event to the set of events in the conference
     * @param name This parameter refers to the name of the event.
     * @param speaker This parameter refers to the speaker at the event.
     * @param time This parameter refers to the event time.
     * @param roomNumber This parameter refers to the room number.
     * @return Returns true if the user was added to events map and false otherwise.
     */
    boolean addEvent(String name, String speaker, LocalDateTime time, Integer duration, int roomNumber, int capacity,
                     int computers, boolean projector, int chairs, int tables, List<String> creators) {
        return eventManager.addEvent(name, speaker, time, duration, roomNumber, capacity, computers, projector,
                chairs, tables, creators);
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
     * This method creates a speaker account
     * @param name This parameter refers to the name of the speaker.
     * @param address This parameter refers to the address of the speaker.
     * @param email This parameter refers to the email of the speaker.
     * @param username This parameter refers to the username of the speaker.
     * @param password This parameter refers to the password of the speaker.
     */
    void createSpeakerAccount(String name, String address, String email, String username, String password) {
        userManager.addUser(name, address, email, username, password, "speaker");
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

    private LocalDateTime getTime() throws DateTimeException {
        p.displayEnterYearPrompt();
        int y = nextInt();
        p.displayEnterMonthPrompt();
        int m = nextInt();
        p.displayEnterDayPrompt();
        int d = nextInt();
        p.displayEnterHourPrompt();
        int h = nextInt();
        p.displayEnterMinutePrompt();
        int mi = nextInt();
        return LocalDateTime.of(y, m, d, h, mi);
    }

    private LocalDateTime askTime() {
        LocalDateTime time = LocalDateTime.now();
        do {
            try {
                time = getTime();
                if(time.isBefore(LocalDateTime.now())){
                    p.displayInvalidEventError();
                }else{
                    break;
                }
            } catch (DateTimeException d) {
                p.displayDateError();
            }
        } while(true);

        return time;
    }

    private void makeSpeaker() {
        p.displayEnterUsernamePrompt();
        String username = scan.nextLine();
        while(this.userManager.checkCredentials(username) || (username.length() < 3 && username.equalsIgnoreCase("q"))){
            if (username.equalsIgnoreCase("q")) {
                break;
            } else if (this.userManager.checkCredentials(username)) {
                p.displayRepeatUsernameError();
            }
            else if (username.length() < 3) {
                p.displayUsernameLengthError();
            }
            username = scan.nextLine();
        }
        if (username.equalsIgnoreCase("q")) {
            return;
        }
        p.displayEnterPasswordPrompt();
        String password = scan.nextLine();
        while(password.length() < 3){
            p.displayPasswordLengthError();
            password = scan.nextLine();
        }
        p.displayEnterSpeakerNamePrompt();
        String name = scan.nextLine();
        while(name.length() < 2) {
            p.displaySpeakerNameError();
            name = scan.nextLine();
        }
        p.displayEnterSpeakerAddressPrompt();
        String address = scan.nextLine();
        while(address.length() < 6) {
            p.displayAddressLengthError();
            address = scan.nextLine();
        }
        p.displayEnterSpeakerEmailPrompt();
        String email = scan.nextLine();
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        while(!email_pattern.matcher(email).matches()){
            p.displayInvalidEmail();
            email = scan.nextLine();
        }
        createSpeakerAccount(name, address, email, username, password);
        messageManager.addUserInbox(username);
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


        lists.put("Top Five Events (By Capacity):", eventManager.getAllEvents().values().stream()
                .sorted(Comparator.comparingInt(Event::getSize))
                .map(e -> e.toString())
                .collect(Collectors.toList())
                .subList(0, 4)
        );

        lists.put("Most Popular Speakers (By Number of Events):", users("speaker").stream()
                .map(s1 -> (Speaker) s1)
                .sorted(Comparator.comparingInt(Speaker::getNumberOfEvents))
                .map(e -> e.toString())
                .collect(Collectors.toList())
                .subList(0, 4)
        );


    }

}
