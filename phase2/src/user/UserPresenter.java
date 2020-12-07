package user; /**
 * This class is a Presenter Class with common functionality between Attendee, Organizer and Speaker Controllers.
 * It handles asking for user input and printing any error messages.
 */
import main.Presenter;
import message.Message;
import request.Request;
import java.time.LocalDateTime;
// using this https://stackoverflow.com/questions/40715424/printing-out-datetime-in-a-specific-format-in-java/40715452

import java.time.format.DateTimeFormatter;
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
        System.out.print("Please enter next task (reminder, you can type '6' to see what you can do): ");
    }

    /**
     * Prompts an Organizer to choose another task once they have completed a task.
     */
    public void displayNextTaskPromptOrganizer(){
        System.out.print("Please enter next task (reminder, you can type '14' to see what you can do):\n ");
    }

    public void displayNextTaskPromptOrgOptDisplayed(){
        System.out.print("Please enter next task:\n");
    }

    public void displayNextTaskPrompt(){
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
     * Notifies a User that they successfully canceled their spot in an event.
     */
    public void displayUnsuccessfulCancellation(){
        System.out.println("Cancellation of spot in event was unsuccessful");
    }

    /**
     * Notifies a User that the selected message they attemped to unstar is not starred.
     */
    public void displayUnstarError(){
        System.out.println("Invalid input. The selected message is not starred.");
    }

    /**
     * Notifies a User that the selected message they attemped to star is already starred.
     */
    public void displayStarError(){
        System.out.println("Invalid input. The selected message is already starred.");
    }

    /**
     * Notifies a User that the selected message they attemped to pin is already pinned.
     */
    public void displayPinnedError(){
        System.out.println("Invalid input. The selected message is already pinned.");
    }

    /**
     * Notifies a User that the selected message they attemped to unpin is already unpinned.
     */
    public void displayUnpinnedError(){
        System.out.println("Invalid input. The selected message is already unpinned.");
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String buffer = ("==========================");
        if(allMessages.size() == 0) {
            System.out.println("No Messages :(");
            return;
        }
        System.out.println("==========SYMBOL GLOSSARY=========\nDot = Unread Message\nNo dot = Read Message");
        int counter = 1;
        for (int i = allMessages.size() -1; i > -1; i-- ) {
            // If the message hasn't been read, display preview with a dot.
            if (!(allMessages.get(i).isDeleted())) {
                if ((allMessages.get(i).getContent().length()) >= 10) {
                    if (!(allMessages.get(i).hasBeenRead())) {
                        if (allMessages.get(i).isStarred()) {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\n\u25CF * (PINNED) Message: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy()));
                            } else {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\n\u25CF * Message: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated()));
                            }
                        } else {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\n\u25CF (PINNED) Message: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy()));
                            } else {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\n\u25CF Message: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated()));
                            }
                        }
                        counter++;
                        // If the message has been read, display without a dot.
                    } else {
                        if (allMessages.get(i).isStarred()) {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\n* (PINNED) Message: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy()));
                            } else {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\n* Message: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated()));
                            }
                            counter++;
                        } else {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\n (PINNED) Message: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy()));
                                counter++;
                            } else{
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender() +
                                        "\nMessage: " +
                                        allMessages.get(i).getContent().substring(0, 10) + "..." +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated()));
                                counter++;
                            }
                        }
                    }
                } else {
                    if (!(allMessages.get(i).hasBeenRead())) {
                        if (allMessages.get(i).isStarred()) {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n\u25CF * (PINNED) Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy()));
                            } else{
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n\u25CF * Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated()));
                            }
                            counter++;
                        } else {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n\u25CF (PINNED) Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy())) ;
                            } else {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n\u25CF Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated())) ;
                            }
                            counter++;
                        }
                    } else {
                        if (allMessages.get(i).isStarred()) {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n* (PINNED) Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy()));
                            } else {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n* Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated()));
                            }
                            counter++;
                        } else {
                            if (allMessages.get(i).isPinned()) {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n (PINNED) Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreatedCopy()));
                                counter++;
                            } else {
                                System.out.println(buffer + "\n" + counter + ". Sent By: " + allMessages.get(i).getSender()
                                        + "\n Message: " +
                                        allMessages.get(i).getContent() +
                                        "\n" + dtf.format(allMessages.get(i).getDateTimeCreated()));
                                counter++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints all the starred messages a User has received in order of last arrived
     * @param starredMessages: All the starred messages the user has received
     */
    public void displayPrintStarredMessages(List<Message> starredMessages){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String buffer = ("==========================");
        if(starredMessages.size() == 0) {
            System.out.println("No Starred Messages :(");
            return;
        }
        System.out.println("==========SYMBOL GLOSSARY=========\nDot = Unread Message\nNo dot = Read Message");
        int counter = 1;
        for (int i = starredMessages.size() -1; i > -1; i-- ){
            // If the message hasn't been read, display preview with a dot.
            if((starredMessages.get(i).getContent().length()) >= 10) {
                if(!(starredMessages.get(i).hasBeenRead())) {
                    if(starredMessages.get(i).isStarred()) {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender() +
                                "\n\u25CF * Message: " +
                                starredMessages.get(i).getContent().substring(0, 10) + "..." +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                    } else {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender() +
                                "\n\u25CF Message: " +
                                starredMessages.get(i).getContent().substring(0, 10) + "..." +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                    }
                    counter++;
                    // If the message has been read, display without a dot.
                }else{
                    if(starredMessages.get(i).isStarred()) {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender() +
                                "\n* Message: " +
                                starredMessages.get(i).getContent().substring(0, 10) + "..." +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                        counter++;
                    } else {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender() +
                                "\nMessage: " +
                                starredMessages.get(i).getContent().substring(0, 10) + "..." +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                        counter++;
                    }
                }
            }else{
                if(!(starredMessages.get(i).hasBeenRead())) {
                    if(starredMessages.get(i).isStarred()) {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender()
                                + "\n\u25CF * Message: " +
                                starredMessages.get(i).getContent() +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                        counter++;
                    } else {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender()
                                + "\n\u25CF Message: " +
                                starredMessages.get(i).getContent() +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                        counter++;
                    }
                }else{
                    if(starredMessages.get(i).isStarred()) {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender()
                                + "\n* Message: " +
                                starredMessages.get(i).getContent() +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                        counter++;
                    } else {
                        System.out.println(buffer + "\n" + counter + ". Sent By: " + starredMessages.get(i).getSender()
                                + "\n Message: " +
                                starredMessages.get(i).getContent() +
                                "\n" + dtf.format(starredMessages.get(i).getDateTimeCreated()));
                        counter++;
                    }
                }
            }
        }
    }

    /**
     * Prints all the deleted messages of a User.
     * @param deletedMessages: All the deleted messages the user has received
     */
    public void displayDeletedMessages(List<Message> deletedMessages) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        if (deletedMessages.size() == 0) {
            System.out.println("You have no deleted messages.");
            return;
        }
            String buffer = ("==========================");
            int counter = 1;
            for (int i = deletedMessages.size() - 1; i > -1; i--) {
                if ((deletedMessages.get(i).getContent().length()) >= 10) {
                    System.out.println(buffer + "\n" + counter + ". Sent By: " + deletedMessages.get(i).getSender() +
                            "\n\uD83D\uDDD1 Message: " +
                            deletedMessages.get(i).getContent().substring(0, 10) + "..." +
                            "\n" + dtf.format(deletedMessages.get(i).getDateTimeCreated()));
                    counter++;
                } else {
                    System.out.println(buffer + "\n" + counter + ". Sent By: " + deletedMessages.get(i).getSender()
                            + "\n\uD83D\uDDD1 Message: " +
                            deletedMessages.get(i).getContent() +
                            "\n" + dtf.format(deletedMessages.get(i).getDateTimeCreated()));
                    counter++;
                }
            }
        }

    /**
     * Prints all the archived messages of a User.
     * @param archivedMessages: All the archived messages the user has received
     */
    public void displayArchivedMessages(List<Message> archivedMessages) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        if (archivedMessages.size() == 0) {
            System.out.println("You have no archived messages.");
            return;
        }
        String buffer = ("==========================");
        int counter = 1;
        for (int i = archivedMessages.size() - 1; i > -1; i--) {
            if ((archivedMessages.get(i).getContent().length()) >= 10) {
                System.out.println(buffer + "\n" + counter + ". Sent By: " + archivedMessages.get(i).getSender() +
                        "\n\uD83D\uDD52 * Message: " +
                        archivedMessages.get(i).getContent().substring(0, 10) + "..." +
                        "\n" + dtf.format(archivedMessages.get(i).getDateTimeCreated()));
                counter++;
            } else {
                System.out.println(buffer + "\n" + counter + ". Sent By: " + archivedMessages.get(i).getSender()
                        + "\n\uD83D\uDD52 * Message: " +
                        archivedMessages.get(i).getContent() +
                        "\n" + dtf.format(archivedMessages.get(i).getDateTimeCreated()));
                counter++;
            }
        }
    }

    /**
     * Asks the user if they really want to delete a message from their deleted messages inbox.
     * @return Their choice, either yes or no.
     */
    public String displayDeleteConfirmation(){
        System.out.println("Are you sure you want to delete this message permanently? It cannot be undone. (yes/no): ");
        return scan.nextLine();
    }

    /**
     * A prompt that asks the user which message they want to display from their inbox
     * @return The number of the message they would like to read
     */
    public int displaySelectMessage(){
        System.out.println("Which message would you like to read? (Enter the number of the corresponding message): ");
        int choice = nextInt();
        return choice;
    }

    /**
     * Prompts the user to re-pick a message if they made a nonsensical selection
     */
    public void displayMessageNonExistent(){
        System.out.println("That is not a valid message. Please try again.");
    }

    /**
     * Displays the full contents of the selected message
     * @param message The message to be displayed
     */
    public void displaySelectedMessage(Message message){
        System.out.println("Sent By: " + message.getSender() +
                "\nMessage: " +
                message.getContent());
    }

    /**
     * Displays options that a user can take while looking at their inbox, or their starred messages.
     * @return The option that the user chose.
     */
    public String displayMessageActionPrompt(){
        System.out.println("What would you like to do with this message?(reply, mark as unread, mark as starred, " +
                "unstar, delete, archive, pin, unpin, close)");
        return scan.nextLine();
    }

    /**
     * Displays options that a user can take while looking at their deleted messages.
     * @return The option that the user chose.
     */
    public String displayDeletedActionPrompt(){
        System.out.println("What would you like to do with this message?(reply, delete, restore, " +
                "close)");
        return scan.nextLine();
    }

    /**
     * Displays options that a user can take while looking at their archived messages.
     * @return The option that the user chose.
     */
    public String displayArchivedActionPrompt(){
        System.out.println("What would you like to do with this message?(reply, unarchive, " +
                "close)");
        return scan.nextLine();
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
                "conference. Please try again at a later time.");
    }

    /**
     * This method prints all the requests a user has made.
     * @param requests The requests a user has made
     */
    public void displayRequestsHeader(List<List<String>> requests){
        if(requests.size() == 0){
            System.out.println("You have not made any requests.");
        }
        else{
            System.out.println("Requests you have made: ");
        }
    }

    public void displayRequestsBody(String requestStatus, String requestContent){
        System.out.print(requestStatus + " : ");
        System.out.println(requestContent);
    }
    /**
     * This method prints the corporation a user is currently associated with.
     * @param corporation The corporation the user is associated with.
     */
    public void displayViewCorporation(String corporation){
        System.out.println(corporation);
    }

    /**
     * This method prints the bio of a user.
     * @param bio The bio of a user.
     */
    public void displayViewBio(String bio){
        System.out.println(bio);
    }

}
