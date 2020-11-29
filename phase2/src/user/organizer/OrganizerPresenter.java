package user.organizer;

import event.Event;
import room.Room;
import user.User;
import user.attendee.AttendeePresenter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class is a Presenter Class with specific functionality for Organizer Controllers.
 * It handles asking for user input and printing any error messages.
 */
public class OrganizerPresenter extends AttendeePresenter {

    public OrganizerPresenter() {
    }


    /**
     * Prints all the tasks which an Organizer can do.
     */
    public void displayOptions2() {
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n(6) Sign up for Event" +
                "\n(7) Add Event\n(8) Message All Attendees\n(9) Message Event Attendees" +
                "\n(10) Message All Speakers\n(11) Cancel Event\n(12) Reschedule Event\n(13) Add New User" +
                "\n(14) View Options" + "\n(15) Add Room \n(16) Modify an Event's capacity \n(17) View All Rooms \n(18) View Speakers\n(19) " +
                "View Attendees\n(20) View Organizers\n(21) Display Conference Statistics\n(22) Quit");
    }

    /**
     * Prompts the Organizer that the process of adding an Event will now begin.
     */
    public void displayAddConferencePrompt() {
        System.out.println("To Add an Event to the Conference, enter the following");
    }

    /**
     * Prompts the Organizer to add the Title of the Event they want to create.
     */
    public String displayEventTitlePrompt() {
        System.out.print("Enter the Event Title (or type 'q' to exit): ");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer to add the Title of the Event they want to create.
     */
    public void displayEventTitleNoQuitPrompt() {
        System.out.print("Enter the Event Title: ");
    }

    /**
     * Prompts the Organizer to enter the name of the Speaker for the Event they want to create.
     * @return The speaker username
     */
    public String displayEnterSpeakerPrompt() {
        System.out.print("Enter a Speaker's username: ");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer to enter the name of the Speaker for the Event they want to create.
     * @return The speaker username AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
     */
    public String askNewSpeakerPrompt() {
        System.out.print("Enter a new speaker? (Y/N): ");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer to enter the name of the newly created speaker.
     * @return The speaker username
     */
    public String displayEnterNewSpeakerPrompt() {
        System.out.print("Enter the new Speaker's username: ");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer to enter the room number for the Event they want to create.
     */
    public int displayEnterRoomNumberPrompt() {
        System.out.print("Enter a room number: ");
        return nextInt();
    }

    /**
     * Prints an error message notifying the Organizer that the Speaker they tried to add to their Event does not exist.
     */
    public void displaySpeakerCredentialError() {
        System.out.println("This speaker does not exist. You will be asked to create an account for them.");
    }

    /**
     * Prints the message that the time input is invalid.
     */
    public void displayInvalidHourError(){
        System.out.println("Invalid time. The event must begin between 9:00 and 16:00");
    }

    /**
     * Prints the message the date input is invalid.
     */
    public void displayInvalidDateError(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime endTime = LocalTime.of(16, 0);
        if (currentTime.isAfter(endTime)) {
            System.out.println("Invalid date entered. The soonest you may schedule an event is tomorrow at 9AM.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            String date = currentDate.format(formatter);
            System.out.println("Invalid date entered. An event can only be scheduled for " + date +
                    " any time before 5PM and any following date");
        }
    }

    /**
     * Prints an error message notifying the Organizer that the Room they tried to add to their Event does not exist.
     */
    public void displayRoomNumberError() {
        System.out.println("There is no room with this room number. You will be asked to create a room with this room number.");
    }

    /**
     * Prints an error message notifying the Organizer that the Speaker or Room for the Event they are trying to create will be double booked.
     */
    public void displayEventCreationError() {
        System.out.println("The event was invalid. Please try again.");
    }

    /**
     * Notifies the Organizer that the Event was created successfully.
     */
    public void displaySuccessfulEventCreation() {
        System.out.println("Event created successfully. ");
    }

    /**
     * Prompts the Organizer to enter the Message they want to send to all Attendees in the conference.
     * @return The message
     */
    public String displayAllAttendeeMessagePrompt() {
        System.out.println("Enter what you want to say to all the attendees (1 line) or type 'q' to exit.");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer to enter the Message they want to send to all Attendees in the Event they created.
     * @return The message
     */
    public String displayAllAttendeeEventMessagePrompt() {
        System.out.println("What do you want to say to all the attendees at this event? (1 line)");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer on which Event they want to send a Message to.
     * @return The Event
     */
    public String displayEventMessagePrompt() {
        System.out.print("Enter the event you want to message or type 'q' to exit: ");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer what Message they want to send to all speakers in the conference.
     * @return The message
     */
    public String displayAllSpeakerMessagePrompt() {
        System.out.println("Enter what you want to say to all the speakers (1 line) or type 'q' to exit.");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer as to what Event they want to remove.
     * @return The event
     */
    public String displayEventRemovalPrompt() {
        System.out.print("Enter the event you want to remove or type 'q' to exit: ");
        return scan.nextLine();
    }

    /**
     * Prompts the Organizer on which Event they want to reschedule.
     * @param responses Thee responses
     * @param responsibleEvents The responsible events
     */
    public void displayEventReschedulePrompt(String[] responses, List<String> responsibleEvents) {
        Scanner scan = new Scanner(System.in);
        if (responsibleEvents.size() == 0) {
            System.out.println("You are not responsible for any events. ");
            responses[0] = "qq";
            return;
        }
        System.out.println("Here are all the events you are responsible for ");
        for (int i = 0; i < responsibleEvents.size(); i++) {
            System.out.print(i + ". " + responsibleEvents.get(i) + "   ");
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println("Enter the event you want to reschedule, or type 'q' to exit: ");
        responses[0] = scan.nextLine();
        while (!responsibleEvents.contains(responses[0]) && !responses[0].equalsIgnoreCase("q")) {
            System.out.println("This is not an event you can reschedule, please try again or type 'q' to exit: ");
            responses[0] = scan.nextLine();
        }
    }

    /**
     * Prompts the Organizer to enter the year of the Event they are creating/rescheduling.
     */
    public void displayEnterYearPrompt() {
        System.out.print("Enter a year: ");
    }

    /**
     * Prompts the Organizer to enter the month of the Event they are creating/rescheduling.
     */
    public void displayEnterMonthPrompt() {
        System.out.print("Enter a month (102): ");
    }

    /**
     * Prompts the Organizer to enter the day of the Event they are creating/rescheduling.
     */
    public void displayEnterDayPrompt() {
        System.out.print("Enter a day: ");
    }

    /**
     * Prompts the Organizer to enter the hour of the Event they are creating/rescheduling.
     */
    public void displayEnterHourPrompt() {
        System.out.print("Enter an hour (906): ");
    }

    /**
     * Prompts the Organizer to enter the minute of the Event they are creating/rescheduling.
     */
    public void displayEnterMinutePrompt() {
        System.out.print("Enter a minute (0-59): ");
    }

    /**
     * Prompts the Organizer to enter the number of the Room they would like to create.
     */
    public int displayRoomCreationPrompt() {
        System.out.println("Enter the number of the Room you would " +
                "like to add, or type '0' to quit: ");

        return nextInt();
    }

    /**
     * Prints an error message which notifies the Organizer that the Room they are trying to create already exists.
     */
    public void displayRoomAlreadyExists() {
        System.out.println("This room already exists!");
    }

    /**
     * Prints all the rooms in this conference.
     *
     * @param rooms: a List of rooms in this conference.
     */
    public void displayRoomList(List<Room> rooms) {
        if (rooms.size() == 0) {
            System.out.println("No rooms have been created yet. ");
            return;
        }
        System.out.println("These are all the created rooms");
        for (Room room : rooms) {
            System.out.println("Room #" + room.getRoomNumber());
        }

    }

    /**
     * Displays a series of messages that prompts the user to add more organizers to the list of people responsible
     * for creating the event.
     *
     * @param creators   Refers to the list of creators responsible for creating the event.
     * @param organizers Refers to the list of all of the organizers.
     */
    public void displayAndGetCreators(List<String> creators, List<Organizer> organizers) {
        Scanner scan = new Scanner(System.in);
        List<String> allUsernames = new ArrayList<>();
        if (organizers.size() > 1) {
            System.out.println("Here is the list of all the other organizers at this conference: ");
            for (int i = 0; i < organizers.size(); i++) {
                System.out.print(i + ". " + organizers.get(i).getUsername() + "   ");
                allUsernames.add(organizers.get(i).getUsername());
                if (i < organizers.size() - 1 && (i + 1) % 5 == 0) {
                    System.out.println();
                }
            }
        } else {
            return;
        }
        System.out.println("Would you like to add any of them as additional organizers for this event " +
                "(this gives them the ability to reschedule or cancel this event)? Type their usernames here" +
                "or enter \"done\" when the list is complete ");
        String text = scan.nextLine();
        while (!text.equalsIgnoreCase("done")) {
            if (allUsernames.contains(text) && !creators.contains(text)) {
                creators.add(text);
                System.out.println("Organizer added");
            } else if (allUsernames.contains(text)) {
                System.out.println("This organizer is allowed to edit this event already, please re-enter a " +
                        "valid username");
            } else if (!allUsernames.contains(text)) {
                System.out.println("This user is not allowed to edit this event, please re-enter a " +
                        "valid username");
            }
            System.out.print("Next username (or 'done' to finish): ");
            text = scan.nextLine();
        }
    }

    /**
     * Displays the message that prompts the user to enter whether or not the event is VIP exclusive.
     * @return VIP exclusivity
     */
    public String displayVipPrompt() {
        System.out.println("Is this event exclusive to VIP's? Enter yes if there is and no if there isn't. Press q to quit.");
        return scan.nextLine();
    }

    /**
     * Displays the message that informs the user that the answer to whether or not the event is VIP exclusive is invalid.
     * @return VIP exclusivity
     */
    public String displayInvalidVip() {
        System.out.println("You can only answer yes or no. Enter again or 'q' to quit.");
        return scan.nextLine();
    }


    /**
     * Displays a list of Users
     *
     * @param userList The list to be displayed
     * @param type     The type of User
     */
    public void displayUserList(List<User> userList, String type) {
        Collections.sort(userList);
        System.out.println("Here is the " + type + " List");
        for (User u : userList) {
            System.out.println(u);
        }
    }

    /**
     * Displays a list of events that this organizer created
     *
     * @param futureEvents The list of created events
     */
    public void displayYourCreatedEvents(List<Event> futureEvents) {
        if (futureEvents.size() == 0) {
            System.out.println("There are no upcoming events created by you. ");
            return;
        }
        System.out.println("These are all the events coming up that you created: ");
        int counter = 1;
        for (Event named : futureEvents) {
            System.out.println(counter + ". " + named);
        }
    }

    /**
     * Displays the message that the user can't cancel the event specified.
     * @return The event
     */
    public String displayCannotCancel() {
        System.out.print("You can't cancel that event, re-enter or type 'q' to quit: ");
        return scan.nextLine();
    }

    /**
     * Displays the message that prompts the user for the specified capacity of the event.
     */
    public int displayEventCapacityPrompt() {
        System.out.println("Enter the capacity of the event you would like to add or 0 if you want to quit.");
        return nextInt();
    }

    /**
     * Displays the message that prompts the user for the specified capacity of the room.
     */
    public int displayRoomCapacityPrompt() {
        System.out.println("Enter the capacity of the room you would like to add or 0 if you want to quit.");
        return nextInt();
    }


    /**
     * Displays the message that prompts the user for the wanted duration.
     */

    public int displayDurationPrompt() {
        System.out.println("How long would you like the event to last(in minutes)? You can enter -1 to quit.");
        return nextInt();
    }


    /**
     * Displays the message that prompts the user to enter the number of computers in the room.
     */
    public int displayComputersPrompt() {
        System.out.println("How many computers? Enter -1 to quit.");
        return nextInt();
    }


    /**
     * Displays the message that prompts the user to enter whether or not there is a computer in the room.
     * @return
     */
    public String displayProjectorPrompt() {

        System.out.println("Does this event need/room have a projector? Enter yes if there is and no if there isn't. Press q to quit.");
        String disp = scan.nextLine();

        while(!disp.equalsIgnoreCase("yes") && !disp.equalsIgnoreCase("no") && !disp.equalsIgnoreCase("q")) {
            displayInvalidProjector();
            disp = scan.nextLine();
        }
        return disp;
    }

    /**
     * Displays the message that informs the user that the answer to whether or not the room has a projector is invalid.
     * @return New Projector
     */
    public String displayInvalidProjector() {
        System.out.println("You can only answer yes or no. Enter again or 'q' to quit.");
        return scan.nextLine();
    }

    /**
     * Displays the message that prompts the user to enter the number of chairs in the room.
     */
    public int displayChairsPrompt() {
        System.out.println("How many chairs? Enter -1 to quit.");
        return nextInt();
    }

    /**
     * Displays the message that prompts the user to enter the number of tables in the room.
     */
    public int displayTablesPrompt() {
        System.out.println("How many tables? Enter -1 to quit.");
        return nextInt();
    }


    /**
     * Displays the message that displays all of the recommended rooms.
     *
     * @param capacity  Refers to the capacity of the event.
     * @param computers Refers to the amount of computers required for the event.
     * @param projector Refers to whether or not the event requires a projector.
     * @param chairs    Refers to the number of chairs required for the event.
     * @param tables    Refers to the number of tables required for the event.
     * @param rooms     Refers to the list of rooms.
     */
    public void displayRecommendedRooms(int capacity, int computers, boolean projector, int chairs, int tables, List<Room> rooms) {
        ArrayList<Room> recommendRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCapacity() >= capacity && room.getComputers() >= computers && room.getChairs() >= chairs &&
                    room.getTables() >= tables && (!projector || projector && room.getProjector())) {
                recommendRooms.add(room);
            }
        }
        System.out.println("Recommended Rooms:");
        for (Room room : recommendRooms) {
            System.out.println(room);
        }
    }

    /**
     * Displays the message that tells the user that they cannot change the capacity of any events.
     */
    public void displayNoOrganizedEvents() {
        System.out.println("You have not created any events. You cannot change the capacity of anything.");
    }

    /**
     * Prints the message that the user input is not a speaker.
     * @return The message
     */
    public String displayNotSpeakerError(){
        System.out.print("This user is not a speaker! Please try again or enter 'q' to quit: ");
        return scan.nextLine();
    }

    /**
     * Displays a message that informs the user that the room number they entered doesn't exist.
     * @return The room number
     */
    public String displayRoomNumberQuestion1(){

        System.out.println("There is no room with this room number. \nDo you want to create a new room " +
                "or do you want to be suggested a room from the existing ones? Please enter " +
                "\n(1) 'create' to create a room \n(2) 'q' to quit");
        String ans = scan.nextLine();

        while(!ans.equalsIgnoreCase("create") && !ans.equalsIgnoreCase("q")) {

            displayRoomDecisionQError1();
            ans = scan.nextLine();
        }

        return ans;
    }


    /**
     * Displays the message that their room decision is invalid.
     * @return The room
     */
    public String displayRoomDecisionQError1(){
        System.out.println("Error: Invalid response. Please enter: \n(1) 'create' to create a room " +
                "\n(2) 'q' to quit");
        return scan.nextLine();

    }

    /**
     * Displays the message that informs the user that the room number they entered doesn't exist and asks if they
     * want to get a list of suggestions.
     * @return The room number
     */
    public String displayRoomNumberQuestion2(){
        System.out.println("There is no room with this room number. \nDo you want to create a new room" +
                "or do you want to be suggested a room from the existing ones? Please enter " +
                "\n(1) 'create' to create a room \n(2) 'suggestions' to get a list of suggestions \n(3) 'q' to quit");

        String ans = scan.nextLine();

        while(!ans.equalsIgnoreCase("create") && !ans.equalsIgnoreCase("suggestions") // need to fix it so it doesnt give suggestions as option when empty
                && !ans.equalsIgnoreCase("q")) {
            displayRoomDecisionQError2();
            ans = scan.nextLine();
        }

        return ans;
    }


    /**
     * Displays the message that their response to displayRoomNumberErrorQuestion2() is invalid.
     * @return The room
     */
    public String displayRoomDecisionQError2(){
        System.out.println("Error: Invalid response. Please enter: \n(1) 'create' to create a room " +
                "\n(2) 'suggestions' to get a list of suggestions \n(3) 'q' to quit");
        return scan.nextLine();

    }

    /**
     * Displays the message that prompts the user to enter the maximum capacity of the event.
     * @param maxCapacity Refers to the capacity of the room which must be greater than or equal to the capacity of the event.
     */
    public void displayEnterEventCapacityPrompt(int maxCapacity){
        System.out.println("Enter the number of people that can attend the event: (it cannot be greater than the room's capacity which is " + maxCapacity);
    }

    /**
     * Displays the message that prompts the user to enter the new capacity of the event.
     * @param maxCapacity Refers to the capacity of the room which cannot be less than that of the event.
     * @param minCapacity The minimum possible size
     * @return  The number
     */
    public int displayEnterNewEventCapacityPrompt(int maxCapacity, int minCapacity){
        System.out.println("The room this event is taking place in has a maximum capacity of " + maxCapacity +
                ". Thus the new capacity must be greater than or equal to " + minCapacity + "; the number" +
                " of users already attending the event. Please Enter the new number of people that can attend the event:");

        int capac = nextInt();

        while(capac > maxCapacity || capac < minCapacity) {
            displayModifyRoomCapacityError(maxCapacity, minCapacity);
            capac = nextInt();
        }
        return capac;
    }

    /**
     * Displays the message that informs the user that the capacity entered is too high.
     * @param maxCapacity Refers to the maximum capacity of the room.
     */
    public void displayRoomCapacityError(int maxCapacity){
        System.out.println("Error: The room this event is taking place in has a maximum capacity of " + maxCapacity +
                ". Please Enter the number of people that can attend the event:");
    }


    /**
     * Displays the message that informs the user that their new capacity is too low.
     * @param maxCapacity Refers to he maximum capacity of the room.
     * @param numberUsersAlreadyAttending Refers to the amount of people already attending the event.
     */
    public void displayModifyRoomCapacityError(int maxCapacity, int numberUsersAlreadyAttending){
        System.out.println("Error: The room this event is taking place in has a maximum capacity of " + maxCapacity +
                ". There new capacity must be greater than or equal to " + numberUsersAlreadyAttending + "; the number" +
                " of users already attending the event. Please Enter the new number of people that can attend the event:");
    }



    /**
     * Displays the message that prompts the user to enter the event they want to change the capacity of.
     * @return The event
     */
    public String displayEventModifyPrompt(){
        System.out.print("Enter the event whose capacity you want to modify or type 'q' to exit: ");
        return scan.nextLine();
    }

    /**
     * Displays the message that informs the user that the event they entered isn't an event.
     * @return The event
     */
    public String displayCannotModifyEvent(){
        System.out.print("Error: Please enter the name of an event that you created or press 'q' to quit:");
        return scan.nextLine();
    }


    void displayNumberStats(Map<String, Double> stats) {

        stats.entrySet().stream()
                .sorted()
                .map(e -> e.getKey()+" "+e.getValue())
                .forEach(System.out::println);
    }

    void displayListStats(Map<String, List<String>> lists) {
        lists.entrySet().stream()
                .sorted()
                .map(e -> e.getKey()+" "+e.getValue())
                .forEach(System.out::println);
    }

    public void displayNoStats() {
        System.out.println("There are no Events or Stats to display!");
    }

    /**
     * Asks the organizer what type of user they would like to create.
     * @return The type of the new user.
     */
    public String displayNewUserCreation() {
        System.out.println("What kind of new user would you like to create? (Organizer, Attendee, Speaker, VIP): ");
        return scan.nextLine();
    }

    public String displayInvalidUserTypeError(){
        System.out.println("Sorry, that is not a valid user type. Please try again:");
        return scan.nextLine();
    }

    public void displayNewUserCreated(String username, String password) {
        System.out.println("New user successfully created with the following details:\n" +
                "Username: " + username + "| Password: " + password);
    }

    protected LocalDateTime getTime() throws DateTimeException {
        displayEnterYearPrompt();
        int y = nextInt();
        displayEnterMonthPrompt();
        int m = nextInt();
        displayEnterDayPrompt();
        int d = nextInt();
        displayEnterHourPrompt();
        int h = nextInt();
        displayEnterMinutePrompt();
        int mi = nextInt();
        return LocalDateTime.of(y, m, d, h, mi);
    }

    protected LocalDateTime askTime() {
        LocalDateTime time = LocalDateTime.now();
        do {
            try {
                time = getTime();
                if(time.isBefore(LocalDateTime.now())){
                    displayInvalidEventError();
                }else{
                    break;
                }
            } catch (DateTimeException d) {
                displayDateError();
            }
        } while(true);

        return time;
    }

}