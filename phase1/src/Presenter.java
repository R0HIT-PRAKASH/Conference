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

    public void displaySuccessfulMessage(){
        System.out.println("Successfully Replied to Message");
    }

    public void displaySuccessfulCancellation(){
        System.out.println("Successfully Cancelled Spot in Event");
    }

    public void displayInvalidEventError(){
        System.out.println("Invalid Event. Please try again");
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

    public void displayMessageError(){ // use this for case2 in attendeecontroller as well
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

    public void displayEventList(){
        System.out.println("Here is a list of all the available events at this conference: ");
    }

    public void displaySignedUpEvents(){
        System.out.println("Here is a list of events you have signed up for: ");
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

    public void displaySignUpError1(){
        System.out.println("Sign Up was unsuccessful as the event you are trying to sign up for does not" +
                "exist");
    }

    public void displaySignUpError2(){
        System.out.println("There are currently no events in this conference. Please wait until event(s)" +
                "have been added to use this feature.");
    }

    // ----------------------------------------------------------------------------------------------------------------

    // Methods for Attendee Controller --------------------------------------------------------------------------------

    public void displayOptions2(){
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n(6) Sign up for Event" +
                "\n(7) Add Event\n(8) Message All Attendees\n(9) Message Event Attendees" +
                "\n(10) Message All Speakers\n(11) Cancel Event\n(12) Reschedule Event\n(13) Add Speaker\n(14) View Options" +
                "\n(15) End");
    }

    public void displayAddConferencePrompt(){
        System.out.println("To Add an Event to the Conference, Enter the following");
    }

    public void displayEventTitlePrompt(){
        System.out.println("Enter an Event Title:");
    }

    public void displayEnterSpeakerPrompt(){
        System.out.println("Enter a Speaker:");
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

    // ----------------------------------------------------------------------------------------------------------------
}
