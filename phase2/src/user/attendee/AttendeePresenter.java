package user.attendee;

import event.Event;
import user.UserPresenter;

import java.util.List;
import java.util.Scanner;

/**
 * This class is a Presenter Class with specific functionality for Attendee Controllers.
 * It handles asking for user input and printing any error messages.
 */
public class AttendeePresenter extends UserPresenter {


    public AttendeePresenter(){
    }

    /**
     * Prints the tasks which an Attendee is able to do.
     */
    public void displayOptions(){
        System.out.println("(0) See Inbox\n(1) Send Message\n(2) Reply to Message\n(3) View Event List" +
                "\n(4) View My Scheduled Events\n(5) Cancel Event Reservation\n" +
                "(6) Sign up for an event\n(7) View Options \n(8) End");
    }



    /**
     * Prompts an Organizer or Attendee on which User they would like to message.
     */
    public String displayMethodPrompt(){
        System.out.println("Who would you like to message? (Please enter the username of the recipient). Otherwise, type 'q' to exit");
        return scan.nextLine();
    }

    /**
     * Prints an error message which notifies the Organizer that a User they are trying to Message is not in their contacts list.
     */
    public void displayNotMessageableError() {
        System.out.println("Sorry, you are not allowed to message this User. Please try again");
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
    public String displayEnterMessagePrompt(String recipient){
        System.out.println("Enter the message you would like to send to " + recipient + ". " + "If you would no longer like to send a message, type 'q' to exit. ");
        return scan.nextLine();
    }




    /**
     * Prompts an Attendee or Organizer to enter which User they want to reply to.
     * @return The username of the recipient
     */
    public String displayEnterUserUsernamePrompt(){
        System.out.print("Which user are you replying to (it is case sensitive). If you no longer want to reply to a user, type 'q' to exit: ");
        return scan.nextLine();
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
     * Prints the message that the attendee is not attending any events.
     */
    public void displayNotAttendingAnyEvents(){
        System.out.println("You aren't attending any events so there are no event reservations to cancel.");
    }

    /**
     * Prompts an Attendee or Organizer on which Event they would like to cancel their spot in.
     * @return The event
     */
    public String displayEventCancelPrompt(){
        System.out.println("What is the name of the event you no longer want to attend? Type 'q' if you no longer want to cancel your spot in an event. ");
        return scan.nextLine();
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
    public String displayEventSignUpPrompt(){
        System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
        return scan.nextLine();
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
    public String displayInvalidEventSignUp(){
        System.out.print("That is not an Event you can sign up for. Please re-enter the name " +
                "(it is case sensitive) or enter 'q' to quit: ");
        return scan.nextLine();
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
}
