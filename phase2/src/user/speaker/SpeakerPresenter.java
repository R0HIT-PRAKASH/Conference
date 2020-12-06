package user.speaker;

import event.Event;
import request.Request;
import user.UserPresenter;
import user.attendee.AttendeePresenter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * This class is a Presenter Class with specific functionality for Speaker Controllers.
 * It handles asking for user input and printing any error messages.
 */
public class SpeakerPresenter extends AttendeePresenter {

    /**
     * Constructs a SpeakerPresenter object.
     */
    public SpeakerPresenter(){}

    /**
     * Prints all the message related tasks that a Speaker can do.
     */
    public void displayMessageOptions(){
        System.out.println("(0) See Inbox\n(1) See Starred Messages\n(2) See Archived Messages" +
                "\n(3) See Deleted Messages\n(4) Message Event Attendees" +
                "\n(5) Message Specific Attendee\n(6) Go Back to Main Screen");
    }

    /**
     * Prints all the event related tasks that a Speaker can do.
     */
    public void displayEventOptions(){
        System.out.println("(0) View My Events \n(1) Go Back to Main Screen");
    }

    /**
     * Prints all the request related tasks that a Speaker can do.
     */
    public void displayRequestOptions(){
        System.out.println("(0) View My Requests\n(1) Make a Request\n(2) Go Back to Main Screen");
    }

    /**
     * Prints all the tasks which a Speaker can do.
     */
    public void displayOptions3(){
        System.out.println("(0) Messages\n(1) Events\n(2) Requests\n(3) User Options\n(4) Quit");
    }

    /**
     * Displays that the user input was out of range.
     */
    public void displayMessageOptionsInvalidChoice() {
        System.out.println("Invalid input. Please enter a number between 0 and 4:");
    }

    /**
     * Displays that the user input was out of range.
     */
    public void displayEventOptionsInvalidChoice() {
        System.out.println("Invalid input. Please enter a number between 0 and 1:");
    }

    /**
     * Displays that the user input was out of range.
     */
    public void displayRequestsOptionsInvalidChoice() {
        System.out.println("Invalid input. Please enter a number between 0 and 2:");
    }

    /**
     * Prints all events that a Speaker is speaking at.
     * @param stringOfEvents: a List of strings for events which the Speaker is speaking at.
     */
    public void displayAllEventsGiven(List<String> stringOfEvents){
        if (stringOfEvents.size() == 0){
            System.out.println("You haven't given any events yet. ");
            return;
        }
        System.out.println("Here are all the events that you have given/will give: ");
        int counter = 1;
        for (String curr : stringOfEvents){
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
        System.out.print("Please enter the number of events or enter -1 to quit: ");
    }

    /**
     * Asks how many events' attendees you'd like to message
     */
    public void displayNumberOfEventsError(){
        System.out.print("Not an appropriate number of events, please re-enter or enter -1 to quit: ");
    }

    /**
     * Asks the name of the first event whose attendees you'd like to message
     * @return The event
     */
    public String displayEnterEventNamePrompt(){
        System.out.print("Please enter the name of the first event or type 'q' to go back: ");
        return scan.nextLine();
    }

    /**
     * Asks the name of all other events (not the first one) whose attendees you'd like to message
     * @return The event
     */
    public String displayEnterEventNamePrompt2(){
        System.out.print("Please enter the name of the next event or type 'q' to go back: ");
        return scan.nextLine();
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
     * Asks the name of the attendee that you are replying to
     * @return The name
     */
    public String displayEnterAttendeeUsernamePrompt(){
        System.out.print("Which attendee are you replying to (it is case sensitive): ");
        return scan.nextLine();
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
     * @return The event
     */
    public String displayEventSelectorPrompt(){
        System.out.println("Type the name of the event who's attendee you want to message");
        return scan.nextLine();
    }

    /**
     * Prints the set of all users attending an event.
     * @param eventAttendees Refers to the set of users attending the event.
     * @return The username
     */
    public String displayEventAttendeesList(Set<String> eventAttendees){
        System.out.println(eventAttendees);
        System.out.println("Type the username of the attendee from this event you want to message:");
        return scan.nextLine();
    }

    /**
     * Prompts the speaker for a request.
     * @return Returns the string representation of their input.
     */
    public String displayMakeRequest(){
        System.out.println("Please enter your request (< 200 characters)");
        return scan.nextLine();
    }

    /**
     * Informs the user that their request is greater than or equal to 200 characters.
     */
    public void invalidRequest(){
        System.out.println("Requests must be less than 200 characters. Please re-enter content");
    }

    /**
     * Informs the user that their request was successfully submitted.
     */
    public void displaySuccessfulRequestSubmission(){
        System.out.println("Your request was successfully submitted.");
    }

    public void displayEnterNumberOfEventsToMessage(){
        System.out.println("How many events would you like to send messages to? Enter -1 to quit.");
    }

    public int displayInvalidNumberOfEventsToMessage(){
        System.out.println("You cannot message that amount of events. Try again or -1 to quit.");
        return nextInt();
    }
}
