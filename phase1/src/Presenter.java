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

    // ----------------------------------------------------------------------------------------------------------------

    public void displayMethodPrompt(){
        System.out.println("Who would you like to message? (Please enter the username of the recipient)");
    }

    // Methods for Attendee Controller --------------------------------------------------------------------------------

    public void displayConferenceError(){
        System.out.println("There are currently no other users who are registered within this " +
                "conference. Please try at a later time.");
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

    public void SignUpError1(){
        System.out.println("Sign Up was unsuccessful as the event you are trying to sign up for does not" +
                "exist");
    }

    public void SignUpError2(){
        System.out.println("There are currently no events in this conference. Please wait until event(s)" +
                "have been added to use this feature.");
    }

    // ----------------------------------------------------------------------------------------------------------------

}
