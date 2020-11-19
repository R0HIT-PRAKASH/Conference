import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
public class Presenter {

    public Presenter(){}

    // Common Methods For All User Controllers (Attendees, Speakers, Organizers) --------------------------------------
    public void displayTaskInput(){
        System.out.println("What would you like to do?\nEnter the corresponding number:");
    }

    public void displayNextTaskPrompt(){
        System.out.println("Please enter next task (reminder, you can type '14' to see what you can do: ");
    }

    public void displayInvalidInputError(){
        System.out.println("Invalid Input, please try again.");
    }

    public void displayMessageSentPrompt(){
        System.out.println("Message Sent\n");
    }

    public void displayMessageSentPrompt2(){
        System.out.println("Messages Sent");
    }

    public void displaySuccessfulMessage(){
        System.out.println("Successfully Replied to Message");
    }

    public void displaySuccessfulCancellation(){
        System.out.println("Successfully Cancelled Spot in Event");
    }

    public void displayInvalidEventError(){
        System.out.println("Invalid Event. Please try again");
    }

    public void displayDateError(){
        System.out.println("Invalid Date. Please try again.");
    }


    // ----------------------------------------------------------------------------------------------------------------

    // Methods for Attendee Controller --------------------------------------------------------------------------------
    // These methods also work with the identical cases in the organizer controller

    public void displayOptions(){
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n" +
                "(6) Add User to Contact List\n(14) View Options \n(15) End");
    }

    public void displayConferenceError(){
        System.out.println("There are currently no other users who are registered within this " +
                "conference. Please try at a later time.");
    }

    public void displayMethodPrompt(){
        System.out.println("Who would you like to message? (Please enter the username of the recipient)");
    }

    public void displayMessageError(){ // use this for case2 in attendee controller as well
        System.out.println("Sorry, it seems you are unable to message this user. Please wait for this " +
                "user to register for the conference.");
    }

    public void displayEnterMessagePrompt(String recipient){
        System.out.println("What message would you like to send to: " + recipient + ".");
    }

    public void displayNoReply(){
        System.out.println("You currently have no messages to reply to.");
    }

    public void displayOldestInboxMessage(String message){
        System.out.println("This is the oldest message in your inbox: '" +
                message + "'. How would you like to respond?");
    }

    public void displayEventList(List<Event> events){
        System.out.println("Here is a list of all the available events at this conference: ");
        for (Event curr : events){
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
            String date = curr.getTime().format(formatter);
            System.out.println(date + ": " + curr);
        }
    }

    public void displaySignedUpEvents(List<String> signedUpFor){
        System.out.println("Here is a list of events you have signed up for: ");
        System.out.println(signedUpFor);
    }

    public void displayEventCancelPrompt(){
        System.out.println("What is the name of the event you no longer want to attend?");
    }


    public void displayEventCancellationError1(){
        System.out.println("Cancellation was unsuccessful since this event is not included in the events " +
                "you are attending. Please try again.");
    }

    public void displayEventCancellationError2(){
        System.out.println("You are currently not attending any events. For future use, you must be " +
                "signed up for an event to use this feature.");
    }

    public void displayEventSignUpPrompt(){
        System.out.println("What is the name of the event you would like to sign up for?");
    }

    public void displayEventSignUp(){
        System.out.println("Successfully signed up for the event");
    }

    public void displaySignUpError1(){
        System.out.println("Sign Up was unsuccessful as the event you are trying to sign up for does not" +
                "exist");
    }

    public void displaySignUpError2(){
        System.out.println("There are currently no events in this conference. Please wait until event(s)" +
                "have been added to use this feature.");
    }

    // ----------------------------------------------------------------------------------------------------------------

    // Methods for Organizer Controller --------------------------------------------------------------------------------

    public void displayOptions2(){
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n(6) Sign up for Event" +
                "\n(7) Add Event\n(8) Message All Attendees\n(9) Message Event Attendees" +
                "\n(10) Message All Speakers\n(11) Cancel Event\n(12) Reschedule Event\n(13) Add Speaker\n(14) View Options" +
                "\n(15) Add Room \n(16) View All Rooms \n(17) End");
    }

    public void displayAddConferencePrompt(){
        System.out.println("To Add an Event to the Conference, Enter the following");
    }

    public void displayEventTitlePrompt(){
        System.out.println("Enter an Event Title:");
    }

    public void displayEnterSpeakerPrompt(){
        System.out.println("Enter a Speaker's username:");
    }

    public void displayEnterRoomNumberPrompt(){
        System.out.println("Enter a room number:");
    }

    public void displaySpeakerCredentialError(){
        System.out.println("This speaker does not exist. You will be asked to create an account for them.");
    }

    public void displayEventCreationError(){
        System.out.println("The event was invalid. Either the speaker or the room would be double booked. " +
                "Please try again.");
    }

    public void displayAllAttendeeMessagePrompt(){
        System.out.println("What do you want to say to all the attendees? (1 line)");
    }

    public void displayAllAttendeeEventMessagePrompt() {
        System.out.println("What do you want to say to all the attendees at this event? (1 line)");
    }

    public void displayEventMessagePrompt(){
        System.out.println("Enter the event you want to message");
    }

    public void displayAllSpeakerMessagePrompt(){
        System.out.println("What do you want to say to all the speakers? (1 line)");
    }

    public void displayEventRemovalPrompt(){
        System.out.println("What event do you want to remove?");
    }

    public void displayEventReschedulePrompt(){
        System.out.println("What Event do you want to reschedule?");
    }

    public void displayContactListError(){
        System.out.println("Sorry, this person is not in your contact list. Please try again");
    }

    public void displayEnterYearPrompt(){
        System.out.println("Enter a year:");
    }

    public void displayEnterMonthPrompt(){
        System.out.println("Enter a month (1-12):");
    }

    public void displayEnterDayPrompt(){
        System.out.println("Enter a day:");
    }

    public void displayEnterHourPrompt(){
        System.out.println("Enter an hour (0-23):");
    }

    public void displayEnterMinutePrompt(){
        System.out.println("Enter a minute (0-59):");
    }

    public void displayEnterUsernamePrompt(){
        System.out.println("Enter Username: ");
    }

    public void displayRepeatUsernameError(){
        System.out.println("That username is already taken, please enter another one: ");
    }

    public void displayUsernameLengthError(){
        System.out.println("Error, username must be at least 3 characters. please enter another one: ");
    }

    public void displayEnterPasswordPrompt(){
        System.out.println("Enter Password: ");
    }

    public void displayPasswordLengthError(){
        System.out.println("Error, password must be at least 3 characters.\nPlease enter again:");
    }

    public void displayEnterSpeakerNamePrompt(){
        System.out.println("Enter the speaker name");
    }

    public void displaySpeakerNameError(){
        System.out.println("Error, name must be at least 2 characters.\nPlease enter again:");
    }

    public void displayEnterSpeakerAddressPrompt(){
        System.out.println("Enter the speaker address");
    }

    public void displayAddressLengthError(){
        System.out.println("Error, address must be at least 6 characters.\nPlease enter again:");
    }

    public void displayEnterSpeakerEmailPrompt(){
        System.out.println("Enter the speaker Email");
    }

    public void displaySpeakerEmailError1(){
        System.out.println("Error, email must contain '@'.\nPlease enter a valid email:");
    }

    public void displaySpeakerEmailError2(){
        System.out.println("Error, email must be at least 3 characters.\nPlease enter again:");
    }

    public void displayInvalidEmail() {
        System.out.println("The email is not up to RFC 5322 standards. Try another:");
    }

    public void displayRoomCreationPrompt(){ System.out.println("What is the number of the Room you would like to add?");
    }

    public void displayRoomAlreadyExists(){
        System.out.println("This room already exists! Please try again.");
    }

    public void displayRoomList(Object rooms){
        System.out.println(rooms);
    }

    // ----------------------------------------------------------------------------------------------------------------


    // Methods for Speaker Controller --------------------------------------------------------------------------------

    public void displayOptions3(){
        System.out.println("What would you like to do? \nEnter the corresponding number: ");
        System.out.println("(0) See Inbox, \n(1) View My Events, \n(2) Message Event Attendees, " +
                "\n(3) Reply to Attendee, \n(4) Options, \n(5) End: ");
    }

    public void displayNextTaskPrompt2(){
        System.out.println("Please enter next task (reminder, you can type '4' to see what you can do: ");
    }

    public void displayAllEventsEntered(){
        System.out.println("Here are all the events that you have given: ");
    }

    public void displayEnterNumberOfEventsPrompt(){
        System.out.println("Please enter the number of events or type 'q' to quit: ");
    }

    public void displayEnterEventNamePrompt(){
        System.out.println("Please enter the name of the first event or type 'q' to go back: ");
    }

    public void displayEnterEventNamePrompt2(){
        System.out.println("Please enter the name of the next event or type 'q' to go back: ");
    }

    public void displayEventAlreadyAddedError(){
        System.out.println("You've already added that event. ");
    }

    public void displayEventNotGivenError(){
        System.out.println("That event isn't one you have given. ");
    }

    public void displayEnterMessagePrompt(){
        System.out.println("Please enter the message: ");
    }

    public void displayEnterAttendeeUsernamePrompt(){
        System.out.println("Which attendee are you replying to (it is case sensitive): ");
    }

    public void displayUserReplyError(){
        System.out.println("That user is not one you can reply to, please re-enter the username " +
                "of someone who has messaged you or enter \"q\" to go back to your options: ");
    }

    // ----------------------------------------------------------------------------------------------------------------

}
