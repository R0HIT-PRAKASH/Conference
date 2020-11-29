package user; /**
 * This class is a Presenter Class with common functionality between Attendee, Organizer and Speaker Controllers.
 * It handles asking for user input and printing any error messages.
 */
import main.Presenter;
import message.Message;

import java.util.*;

public class UserPresenter extends Presenter {

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
        System.out.println("The input should be in the proper range. Please try again.");
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
     * Asks for the content of the message to be sent
     * @return The message text
     */
    public String displayEnterMessagePrompt(){
        System.out.println("Please enter the message. ");
        return scan.nextLine();
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
     * Prints that the username inputted belongs to a user that the Speaker cannot message
     * @return The username
     */
    public String displayUserReplyError(){
        System.out.print("That user is not one you can reply to, please re-enter the username " +
                "of someone who has messaged you or enter \"q\" to go back to your options: ");
        return scan.nextLine();
    }

    /**
     * Notifies an Organizer or Attendee that they have no messages to reply to.
     */
    public void displayNoReply(){
        System.out.println("You currently have no messages to reply to.");
    }


    /**
     * Prints an error message when an Organizer or Attendee tries to message another User when they are the only one in the conference.
     */
    public void displayConferenceError(){
        System.out.println("There are currently no other users who are registered within this " +
                "conference. Please try at a later time.");
    }


}
