package user; /**
 * This class is the Presenter Class for the Attendee, Organizer and Speaker Controllers.
 * It handles asking for user input and printing any error messages.
 */
import event.Event;
import message.Message;
import room.Room;
import user.organizer.Organizer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Presenter {

    public Presenter(){}

    // Common Methods For All User Controllers (Attendees, Speakers, Organizers) --------------------------------------

    /**
     * Prompts a User to choose a task.
     */
    public void displayTaskInput(){
        System.out.print("What would you like to do?\nEnter the corresponding number: ");
    }

    /**
     * Prompts an Attendee to choose another task once they have completed a task.
     */
    public void displayNextTaskPromptAttendee(){
        System.out.print("Please enter next task (reminder, you can type '7' to see what you can do): ");
    }

    /**
     * Prompts an Organizer to choose another task once they have completed a task.
     */
    public void displayNextTaskPromptOrganizer(){
        System.out.print("Please enter next task (reminder, you can type '14' to see what you can do): ");
    }

    /**
     * Prompts a Speaker to choose another task once they have completed a task.
     */
    public void displayNextTaskPromptSpeaker(){
        System.out.print("Please enter next task (reminder, you can type '5' to see what you can do): ");
    }

    /**
     * Prints an error message when a User inputs an invalid task.
     */
    public void displayInvalidInputError(){
        System.out.println("Invalid Input, please try again.");
    }

    /**
     * Notifies a User that their message has been sent successfully.
     */
    public void displayMessageSentPrompt(){
        System.out.println("Message Sent\n");
    }

    /**
     * Notifies a User that their message has been sent successfully.
     */
    public void displayMessageSentPrompt2(){
        System.out.println("Messages Sent");
    }

    /**
     * Notifies a User that their reply was successful.
     */
    public void displaySuccessfulMessage(){
        System.out.println("Successfully Replied to Message");
    }

    /**
     * Notifies a User that they successfully canceled their spot in an event.
     */
    public void displaySuccessfulCancellation(){
        System.out.println("Successfully Cancelled Spot in Event");
    }

    /**
     * Prints an error message when a User inputs an invalid Event.
     */
    public void displayInvalidEventError(){
        System.out.println("Invalid Event. Please try again");
    }

    /**
     * Prints an error message when a User inputs an invalid date.
     */
    public void displayDateError(){
        System.out.println("Invalid Date. Please try again.");
    }

    /**
     * Prints all the messages a User has received in order of last arrived
     * @param allMessages: All the messages the user has received
     */
    public void displayPrintMessages(List<Message> allMessages){
        if(allMessages.size() == 0) {
            System.out.println("No Messages :(");
            return;
        }
        int counter = 1;
        for (int i = allMessages.size() -1; i > -1; i-- ){
            System.out.println(counter + ". Sent By: " + allMessages.get(i).getSender() + "\nMessage: " +
                    allMessages.get(i).getContent());
            counter++;
        }
    }


    // ----------------------------------------------------------------------------------------------------------------

    // Methods for Attendee Controller --------------------------------------------------------------------------------
    // These methods also work with the identical cases in the organizer controller

    /**
     * Prints the tasks which an Attendee is able to do.
     */
    public void displayOptions(){
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n" +
                "(6) Sign up for an event\n(7) View Options \n(8) End");
    }

    /**
     * Prints an error message when an Organizer or Attendee tries to message another User when they are the only one in the conference.
     */
    public void displayConferenceError(){
        System.out.println("There are currently no other users who are registered within this " +
                "conference. Please try at a later time.");
    }

    /**
     * Prompts an Organizer or Attendee on which User they would like to message.
     */
    public void displayMethodPrompt(){
        System.out.println("Who would you like to message? (Please enter the username of the recipient). Otherwise, type 'q' to exit");
    }

    /**
     * Prints an error message when an Attendee or Organizer tries to message a User who has not registered for this conference.
     */
    public void displayMessageError(){ // use this for case2 in attendee controller as well
        System.out.println("Sorry, it seems you are unable to message this user. Please wait for this " +
                "user to register for the conference.");
    }

    /**
     * Prompts an Attendee or Organizer to enter the contents of the message they would like to send.
     * @param recipient: The username of the User who is being messaged.
     */
    public void displayEnterMessagePrompt(String recipient){
        System.out.println("Enter the message you would like to send to " + recipient + ". " + "If you would no longer like to send a message, type 'q' to exit. ");
    }

    /**
     * Notifies an Organizer or Attendee that they have no messages to reply to.
     */
    public void displayNoReply(){
        System.out.println("You currently have no messages to reply to.");
    }

    /**
     * Prompts an Attendee or Organizer to enter which User they want to reply to.
     */
    public void displayEnterUserUsernamePrompt(){
        System.out.print("Which user are you replying to (it is case sensitive). If you no longer want to reply to a user, type 'q' to exit: ");
    }


    /**
     * Prints the event list for the conference.
     * @param events: a List of all events in this conference.
     */
    public void displayEventList(List<Event> events){
        if (events.size() == 0){
            System.out.println("There are no events created yet. ");
            return;
        }
        System.out.println("Here is a list of all the available events at this conference: ");
        int counter = 1;
        for (Event curr : events){
            System.out.println(counter + ". " + curr);
            counter ++;
        }
    }

    /**
     * Prints all the events that an Attendee or Organizer has signed up for.
     * @param signedUpFor: a List of all events that this User has signed up for.
     */
    public void displaySignedUpEvents(List<Event> signedUpFor){
        if (signedUpFor.size() == 0){
            System.out.println("You haven't signed up for any events yet. ");
            return;
        }
        System.out.println("Here is the list of events you have signed up for: ");
        int counter = 1;
        for (Event curr : signedUpFor) {
            System.out.println(counter + ": " + curr);
        }
    }

    /**
     * Prompts an Attendee or Organizer on which Event they would like to cancel their spot in.
     */
    public void displayEventCancelPrompt(){
        System.out.println("What is the name of the event you no longer want to attend? Type 'q' if you no longer want to cancel your spot in an event. ");
    }

    /**
     * Prints an error message that tells an Attendee or Organizer that the Event they are trying to cancel is not in the included events for the conference.
     */
    public void displayEventCancellationError1(){
        System.out.println("Cancellation was unsuccessful since this event is not included in the events " +
                "you are attending. Please try again.");
    }

    /**
     * Prints an error message that tells an Attendee or Organizer that the Event they are trying to cancel is not one of the events they have signed up for.
     */
    public void displayEventCancellationError2(){
        System.out.println("You are currently not attending any events. For future use, you must be " +
                "signed up for an event to use this feature.");
    }

    public void displayAllFutureEvents(List<Event> events){
        if (events.size() == 0){
            displaySignUpError2();
            return;
        }
        System.out.println("Here is the list of all future events: ");
        int counter = 1;
        for (Event curr : events){
            System.out.println(counter + ". " + curr);
        }

    }

    /**
     * Prompts an Attendee or Organizer for the name of the Event they would like to sign up for.
     */
    public void displayEventSignUpPrompt(){
        System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
    }

    /**
     * Notifies the Attendee or Organizer that they have successfully signed up for an Event.
     */
    public void displayEventSignUp(){
        System.out.println("Successfully signed up for the event");
    }

    /**
     * Prints an error message that the Event sign up was unsuccessful since this Event does not exist in the conference.
     */
    public void displaySignUpError1(){
        System.out.println("Sign Up was unsuccessful as the event you are trying to sign up for is not" +
                "valid");
    }

    /**
     * Prints an error message that user cannot sign up for this event
     */
    public void displayInvalidEventSignUp(){
        System.out.print("That is not an Event you can sign up for. Please re-enter the name " +
                "(it is case sensitive) or enter 'q' to quit: ");
    }

    /**
     * Prints an error message that the Event sign up was unsuccessful since there are no events in this conference.
     */
    public void displaySignUpError2(){
        System.out.println("There are currently no future events in this conference. Please wait until event(s)" +
                "have been added to use this feature.");
    }

    /**
     * Prints an error message that the Event sign up was unsuccessful as the Event is at capacity.
     */
    public void displayEventFull(){
        System.out.println("This event is full!");
    }

    // ----------------------------------------------------------------------------------------------------------------

    // Methods for Organizer Controller --------------------------------------------------------------------------------

    /**
     * Prints all the tasks which an Organizer can do.
     */
    public void displayOptions2(){
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n(6) Sign up for Event" +
                "\n(7) Add Event\n(8) Message All Attendees\n(9) Message Event Attendees" +
                "\n(10) Message All Speakers\n(11) Cancel Event\n(12) Reschedule Event\n(13) Add Speaker" +
                "\n(14) View Options" + "\n(15) Add Room \n(16) Modify an Event's capacity \n(17) View All Rooms \n(18) View Speakers\n(19) " +
                "View Attendees\n(20) View Organizers\n(21) Quit");
    }

    /**
     * Prompts the Organizer that the process of adding an Event will now begin.
     */
    public void displayAddConferencePrompt(){
        System.out.println("To Add an Event to the Conference, enter the following");
    }

    /**
     * Prompts the Organizer to add the Title of the Event they want to create.
     */
    public void displayEventTitlePrompt(){
        System.out.print("Enter the Event Title (or type 'q' to exit): ");
    }

    /**
     * Prompts the Organizer to add the Title of the Event they want to create.
     */
    public void displayEventTitleNoQuitPrompt(){
        System.out.print("Enter the Event Title: ");
    }

    /**
     * Prompts the Organizer to enter the name of the Speaker for the Event they want to create.
     */
    public void displayEnterSpeakerPrompt(){
        System.out.print("Enter a Speaker's username: ");
    }

    /**
     * Prompts the Organizer to enter the name of the newly created speaker.
     */
    public void displayEnterNewSpeakerPrompt(){
        System.out.print("Enter the new Speaker's username: ");
    }

    /**
     * Prompts the Organizer to enter the room number for the Event they want to create.
     */
    public void displayEnterRoomNumberPrompt(){
        System.out.print("Enter a room number: ");
    }

    /**
     * Prints an error message notifying the Organizer that the Speaker they tried to add to their Event does not exist.
     */
    public void displaySpeakerCredentialError(){
        System.out.println("This speaker does not exist. You will be asked to create an account for them.");
    }

    /**
     * Prints an error message notifying the Organizer that the Room they tried to add to their Event does not exist.
     */
    public void displayRoomNumberError(){
        System.out.println("There is no room with this room number. You will be asked to create a room with this room number.");
    }

    /**
     * Prints an error message notifying the Organizer that the Speaker or Room for the Event they are trying to create will be double booked.
     */
    public void displayEventCreationError(){
        System.out.println("The event was invalid. Please try again.");
    }

    /**
     * Notifies the Organizer that the Event was created successfully.
     */
    public void displaySuccessfulEventCreation(){
        System.out.println("Event created successfully. ");
    }

    /**
     * Prompts the Organizer to enter the Message they want to send to all Attendees in the conference.
     */
    public void displayAllAttendeeMessagePrompt(){
        System.out.println("Enter what you want to say to all the attendees (1 line) or type 'q' to exit.");
    }

    /**
     * Prompts the Organizer to enter the Message they want to send to all Attendees in the Event they created.
     */
    public void displayAllAttendeeEventMessagePrompt() {
        System.out.println("What do you want to say to all the attendees at this event? (1 line)");
    }

    /**
     * Prompts the Organizer on which Event they want to send a Message to.
     */
    public void displayEventMessagePrompt(){
        System.out.print("Enter the event you want to message or type 'q' to exit: ");
    }

    /**
     * Prompts the Organizer what Message they want to send to all speakers in the conference.
     */
    public void displayAllSpeakerMessagePrompt(){
        System.out.println("Enter what you want to say to all the speakers (1 line) or type 'q' to exit.");
    }

    /**
     * Prompts the Organizer as to what Event they want to remove.
     */
    public void displayEventRemovalPrompt(){
        System.out.print("Enter the event you want to remove or type 'q' to exit: ");
    }

    /**
     * Prompts the Organizer on which Event they want to reschedule.
     */
    public void displayEventReschedulePrompt(String[] responses, List<String> responsibleEvents){
        Scanner scan = new Scanner(System.in);
        if (responsibleEvents.size() == 0){
            System.out.println("You are not responsible for any events. ");
            responses[0] = "qq";
            return;
        }
        System.out.println("Here are all the events you are responsible for ");
        for (int i = 0; i < responsibleEvents.size(); i++){
            System.out.print(i + ". " + responsibleEvents.get(i) + "   ");
            if ((i+1)% 5 == 0){
                System.out.println();
            }
        }
        System.out.println("Enter the event you want to reschedule, or type 'q' to exit: ");
        responses[0] = scan.nextLine();
        while(!responsibleEvents.contains(responses[0]) && !responses[0].equalsIgnoreCase("q")){
            System.out.println("This is not an event you can reschedule, please try again or type 'q' to exit: ");
            responses[0] = scan.nextLine();
        }
    }

    /**
     * Prints an error message which notifies the Organizer that a User they are trying to Message is not in their contacts list.
     */
    public void displayNotMessageableError(){
        System.out.println("Sorry, you are not allowed to message this User. Please try again");
    }

    /**
     * Prompts the Organizer to enter the year of the Event they are creating/rescheduling.
     */
    public void displayEnterYearPrompt(){
        System.out.print("Enter a year: ");
    }

    /**
     * Prompts the Organizer to enter the month of the Event they are creating/rescheduling.
     */
    public void displayEnterMonthPrompt(){
        System.out.print("Enter a month (1-12): ");
    }

    /**
     * Prompts the Organizer to enter the day of the Event they are creating/rescheduling.
     */
    public void displayEnterDayPrompt(){
        System.out.print("Enter a day: ");
    }

    /**
     * Prompts the Organizer to enter the hour of the Event they are creating/rescheduling.
     */
    public void displayEnterHourPrompt(){
        System.out.print("Enter an hour (9-16): ");
    }

    /**
     * Prompts the Organizer to enter the minute of the Event they are creating/rescheduling.
     */
    public void displayEnterMinutePrompt(){
        System.out.print("Enter a minute (0-59): ");
    }

    /**
     * Prompts the Organizer to enter the username of the Speaker account they want to add to the conference
     */
    public void displayEnterUsernamePrompt(){
        System.out.print("Enter the Speaker's username or type 'q' to quit: ");
    }

    /**
     * Prints an error message that notifies the Organizer that the Speaker account username they tried to add was already taken.
     */
    public void displayRepeatUsernameError(){
        System.out.print("That username is already taken, please enter another one: ");
    }

    /**
     * Prints an error message which notifies the Organizer that the Speaker account they are trying to create needs a username of at least 3 characters.
     */
    public void displayUsernameLengthError(){
        System.out.print("Error, username must be at least 3 characters. please enter another one: ");
    }

    /**
     * Prompts the Organizer to enter the password for the Speaker account they are creating.
     */
    public void displayEnterPasswordPrompt(){
        System.out.print("Enter Password: ");
    }

    /**
     * Prints an error message notifying the Organizer that the password for the Speaker account must be at least three characters.
     */
    public void displayPasswordLengthError(){
        System.out.print("Error, password must be at least 3 characters.\nPlease enter again: ");
    }

    /**
     * Prompts the Organizer to enter the name of the Speaker for the Speaker account they are creating.
     */
    public void displayEnterSpeakerNamePrompt(){
        System.out.print("Enter the speaker name: ");
    }

    /**
     * Prints an error message that notifies the Organizer that a Speaker must have a name of at least 2 characters.
     */
    public void displaySpeakerNameError(){
        System.out.print("Error, name must be at least 2 characters.\nPlease enter again: ");
    }

    /**
     * Prompts the Organizer to enter the address of the Speaker for the Speaker account they are creating.
     */
    public void displayEnterSpeakerAddressPrompt(){
        System.out.print("Enter the speaker's address: ");
    }

    /**
     * Prints an error message notifying an Organizer that the address of a Speaker must be at least six characters.
     */
    public void displayAddressLengthError(){
        System.out.print("Error, address must be at least 6 characters.\nPlease enter again: ");
    }

    /**
     * Prompts the Organizer to enter the email of the Speaker for the Speaker account they are creating.
     */
    public void displayEnterSpeakerEmailPrompt(){
        System.out.print("Enter the speaker's email: ");
    }

    /**
     * Prints an error message notifying the Organizer that the email address of the Speaker does not match specific requirements.
     */
    public void displayInvalidEmail() {
        System.out.print("The email is not up to RFC 5322 standards. Try another: ");
    }

    /**
     * Prompts the Organizer to enter the number of the Room they would like to create.
     */
    public void displayRoomCreationPrompt(){ System.out.println("Enter the number of the Room you would " +
            "like to add, or type '-1' to quit: ");
    }

    /**
     * Prints an error message which notifies the Organizer that the Room they are trying to create already exists.
     */
    public void displayRoomAlreadyExists(){
        System.out.println("This room already exists!");
    }

    /**
     * Prints all the rooms in this conference.
     * @param rooms: a List of rooms in this conference.
     */
    public void displayRoomList(List<Room> rooms){
        if (rooms.size() == 0){
            System.out.println("No rooms have been created yet. ");
            return;
        }
        System.out.println("These are all the created rooms");
        for(Room room : rooms){
            System.out.println("Room #" + room.getRoomNumber());
        }

    }

    /**
     * Displays a series of messages that prompts the user to add more organizers to the list of people responsible
     * for creating the event.
     * @param creators Refers to the list of creators responsible for creating the event.
     * @param organizers Refers to the list of all of the organizers.
     */
    public void displayAndGetCreators(List<String> creators, List<Organizer> organizers){
        Scanner scan = new Scanner(System.in);
        List<String> allUsernames = new ArrayList<>();
        if (organizers.size() > 1){
            System.out.println("Here is the list of all the other organizers at this conference: ");
            for (int i = 0; i < organizers.size(); i++){
                System.out.print(i + ". " + organizers.get(i).getUsername() + "   ");
                allUsernames.add(organizers.get(i).getUsername());
                if (i < organizers.size() - 1 && (i+1)%5 == 0){
                    System.out.println();
                }
            }
        }
        else{
            return;
        }
        System.out.println("Would you like to add any of them as additional organizers for this event " +
                "(this gives them the ability to reschedule or cancel this event)? Type their usernames here" +
                "or enter \"done\" when the list is complete ");
        String text = scan.nextLine();
        while(!text.equalsIgnoreCase("done")) {
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
     * Displays a list of Users
     * @param userList The list to be displayed
     * @param type The type of User
     */
    public void displayUserList(List<User> userList, String type) {
        Collections.sort(userList);
        System.out.println("Here is the " + type + " List");
        for(User u : userList) {
            System.out.println(u);
        }
    }

    /**
     * Displays a list of events that this organizer created
     * @param futureEvents The list of created events
     */
    public void displayYourCreatedEvents(List<Event> futureEvents){
        if (futureEvents.size() == 0){
            System.out.println("There are no upcoming events created by you. ");
            return;
        }
        System.out.println("These are all the events coming up that you created: ");
        int counter = 1;
        for (Event named : futureEvents){
            System.out.println(counter + ". " + named);
        }
    }

    /**
     * Displays the message that the user can't cancel the event specified.
     */
    public void displayCannotCancel(){
        System.out.print("You can't cancel that event, re-enter or type 'q' to quit: ");
    }

    /**
     * Displays the message that prompts the user for the specified capacity of the event.
     */
    public void displayEventCapacityPrompt(){
        System.out.println("Enter the capacity of the event you would like to add or -1 if you want to quit." );
    }

    /**
     * Displays the message that prompts the user for the specified capacity of the room.
     */
    public void displayRoomCapacityPrompt(){
        System.out.println("Enter the capacity of the room you would like to add or -1 if you want to quit." );
    }

    /**
     * Displays the message that informs the user that the capacity specified is invalid.
     */
    public void displayInvalidCapacity(){
        System.out.println("That is not a valid capacity. Please enter again.");
    }

    /**
     * Displays the message that prompts the user for the wanted duration.
     */
    public void displayDurationPrompt(){
        System.out.println("How long would you like the event to last(in hours)? You can enter -1 to quit.");
    }

    /**
     * Displays the message that informs the user that the duration specified is invalid.
     */
    public void displayInvalidDuration(){
        System.out.println("This is an invalid duration. Please try again.");
    }

    /**
     * Displays the message that prompts the user to enter the number of computers in the room.
     */
    public void displayComputersPrompt(){
        System.out.println("How many computers? Enter -1 to quit.");
    }

    /**
     * Displays the message that informs the user that the number of computers entered is invalid.
     */
    public void displayInvalidComputers(){
        System.out.println("This is an invalid number of computers. Enter -1 to quit.");
    }

    /**
     * Displays the message that prompts the user to enter whether or not there is a computer in the room.
     */
    public void displayProjectorPrompt(){
        System.out.println("Does this event need/room have a projector? Enter yes if there is and no if there isn't. Press q to quit.");
    }

    /**
     * Displays the message that informs the user that the answer to whether or not the room has a projector is invalid.
     */
    public void displayInvalidProjector(){
        System.out.println("You can only answer yes or no. Enter again or 'q' to quit.");
    }

    /**
     * Displays the message that prompts the user to enter the number of chairs in the room.
     */
    public void displayChairsPrompt(){
        System.out.println("How many chairs? Enter -1 to quit.");
    }

    /**
     * Displays the message that informs the user that the number of chairs entered is invalid.
     */
    public void displayInvalidChairs(){
        System.out.println("This is an invalid number of chairs. Enter -1 to quit.");
    }

    /**
     * Displays the message that prompts the user to enter the number of tables in the room.
     */
    public void displayTablesPrompt(){
        System.out.println("How many tables? Enter -1 to quit.");
    }

    /**
     * Displays the message that informs the user that the number of tables entered is invalid.
     */
    public void displayInvalidTables(){
        System.out.println("This is an invalid number of tables. Enter -1 to quit.");
    }

    /**
     * Displays the message that displays all of the recommended rooms.
     * @param capacity Refers to the capacity of the event.
     * @param computers Refers to the amount of computers required for the event.
     * @param projector Refers to whether or not the event requires a projector.
     * @param chairs Refers to the number of chairs required for the event.
     * @param tables Refers to the number of tables required for the event.
     * @param rooms Refers to the list of rooms.
     */
    public void displayRecommendedRooms(int capacity, int computers, boolean projector, int chairs, int tables, List<Room> rooms){
        ArrayList<Room> recommendRooms = new ArrayList<>();
        for(Room room : rooms){
            if(room.getCapacity() >= capacity && room.getComputers() >= computers && room.getChairs() >= chairs &&
                    room.getTables() >= tables && (!projector || projector && room.getProjector())){
                recommendRooms.add(room);
            }
        }
        System.out.println("Recommended Rooms:");
        for(Room room : recommendRooms){
            System.out.println(room);
        }
    }

    /**
     * Displays the message that tells the user that they cannot change the capacity of any events.
     */
    public void displayNoOrganizedEvents(){
        System.out.println("You have not created any events. You cannot change the capacity of anything.");
    }


    // ----------------------------------------------------------------------------------------------------------------


    // Methods for Speaker Controller --------------------------------------------------------------------------------

    /**
     * Prints all the tasks which a Speaker can do.
     */
    public void displayOptions3(){
        System.out.println("(0) See Inbox \n(1) View My Events \n(2) Message Event Attendees " +
                "\n(3) Reply to Attendee \n(4) Message Specific Attendee \n(5) Options \n(6) End");
    }

    /**
     * Prints all the Users who have messaged this Speaker.
     * @param attendees: a List of User usernames that have messaged this Speaker.
     */
    public void displayAllSenders(List<String> attendees){
        if (attendees.size() == 0){
            System.out.println("You haven't received any messages yet. ");
            return;
        }
        System.out.println("These are all the users who have messaged you: ");
        int counter = 1;
        for (String attendee: attendees){
            System.out.println(counter + ": " + attendee);
            counter++;
        }
    }

    /**
     * Prints all events that a Speaker is speaking at.
     * @param events: a List of events which the Speaker is attending.
     */
    public void displayAllEventsGiven(List<Event> events){
        if (events.size() == 0){
            System.out.println("You haven't given any events yet. ");
            return;
        }
        System.out.println("Here are all the events that you have given/will give: ");
        int counter = 1;
        for (Event curr : events){
            System.out.println(counter + ": " + curr);
            counter++;
        }
    }

    /**
     * Prints all future events which this Speaker is attending.
     * @param events: a List of events which the Speaker will be attending
     */
    public void displayAllFutureEventsGiving(List<Event> events){
        if (events.size() == 0){
            System.out.println("You aren't currently signed up to give any future events");
            return;
        }
        System.out.println("These are all the events you will be giving: ");
        for (Event curr : events){
            System.out.println(curr);
        }
    }

    /**
     * Asks how many events' attendees you'd like to message
     */
    public void displayEnterNumberOfEventsPrompt(){
        System.out.print("Please enter the number of events or type 'q' to quit: ");
    }

    /**
     * Asks how many events' attendees you'd like to message
     */
    public void displayNumberOfEventsError(){
        System.out.print("Not an appropriate number of events, please re-enter or enter 'q' to quit: ");
    }

    /**
     * Asks the name of the first event whose attendees you'd like to message
     */
    public void displayEnterEventNamePrompt(){
        System.out.print("Please enter the name of the first event or type 'q' to go back: ");
    }

    /**
     * Asks the name of all other events (not the first one) whose attendees you'd like to message
     */
    public void displayEnterEventNamePrompt2(){
        System.out.print("Please enter the name of the next event or type 'q' to go back: ");
    }

    /**
     * Prints that the Speaker has already selected this event's attendees to be messaged
     */
    public void displayEventAlreadyAddedError(){
        System.out.println("You've already added that event. ");
    }

    /**
     * Prints that the Speaker hasn't given the named event
     */
    public void displayEventNotGivenError(){
        System.out.println("That event isn't one you have given. ");
    }

    /**
     * Asks for the content of the message to be sent
     */
    public void displayEnterMessagePrompt(){
        System.out.println("Please enter the message. ");
    }

    /**
     * Asks the name of the attendee that you are replying to
     */
    public void displayEnterAttendeeUsernamePrompt(){
        System.out.print("Which attendee are you replying to (it is case sensitive): ");
    }

    /**
     * Prints that the username inputted belongs to a user that the Speaker cannot message
     */
    public void displayUserReplyError(){
        System.out.print("That user is not one you can reply to, please re-enter the username " +
                "of someone who has messaged you or enter \"q\" to go back to your options: ");
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
     * Prints the message that the user input is not a speaker.
     */
    public void displayNotSpeakerError(){
        System.out.print("This user is not a speaker! Please try again or enter 'q' to quit: ");
    }

    /**
     * Prints the message that asks the user which attendee they want to message.
     */
    public void displayEventSelectorPrompt(){
        System.out.println("Type the name of the event who's attendee you want to message");
    }

    /**
     * Prints the set of all users attending an event.
     * @param eventAttendees Refers to the set of users attending the event.
     */
    public void displayEventAttendeesList(Set<User> eventAttendees){
        System.out.println(eventAttendees);
        System.out.println("Type the username of the attendee from this event you want to message:");
    }

    /**
     * Prints the message that the attendee is not attending any events.
     */
    public void displayNotAttendingAnyEvents(){
        System.out.println("You aren't attending any events so there are no event reservations to cancel.");
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
     */
    public void displayEnterNewEventCapacityPrompt(int maxCapacity){
        System.out.println("Enter the NEW number of people that can attend the event: (it cannot be greater than the room's capacity which is " + maxCapacity);
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
     * Displays a message that informs the user that the room number they entered doesn't exist.
     */
    public void displayRoomNumberErrorQuestion1(){
        System.out.println("There is no room with this room number. \nDo you want to create a new room " +
                "or do you want to be suggested a room from the existing ones? Please enter " +
                "\n(1) 'create' to create a room \n(2) 'q' to quit");

    }

    /**
     * Displays the message that informs the user that the room number they entered doesn't exist and asks if they
     * want to get a list of suggestions.
     */
    public void displayRoomNumberErrorQuestion2(){
        System.out.println("There is no room with this room number. \nDo you want to create a new room" +
                "or do you want to be suggested a room from the existing ones? Please enter " +
                "\n(1) 'create' to create a room \n(2) 'suggestions' to get a list of suggestions \n(3) 'q' to quit");

    }

    /**
     * Displays the message that their room decision is invalid.
     */
    public void displayRoomDecisionQError1(){
        System.out.println("Error: Invalid response. Please enter: \n(1) 'create' to create a room " +
                "\n(2) 'q' to quit");

    }

    /**
     * Displays the message that their response to displayRoomNumberErrorQuestion2() is invalid.
     */
    public void displayRoomDecisionQError2(){
        System.out.println("Error: Invalid response. Please enter: \n(1) 'create' to create a room " +
                "\n(2) 'suggestions' to get a list of suggestions \n(3) 'q' to quit");

    }

    /**
     * Displays the message that prompts the user to enter the event they want to change the capacity of.
     */
    public void displayEventModifyPrompt(){
        System.out.print("Enter the event whose capacity you want to modify or type 'q' to exit: ");
    }

    /**
     * Displays the message that informs the user that the event they entered isn't an event.
     */
    public void displayCannotModifyEvent(){
        System.out.print("Error: Please enter the name of an event that you created or press 'q' to quit:");
    }






    // ----------------------------------------------------------------------------------------------------------------

}
