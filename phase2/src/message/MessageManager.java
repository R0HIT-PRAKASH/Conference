package message;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import event.EventManager;
import saver.ReaderWriter;
import user.User;
import user.UserManager;

/**
 * The MessageManager class is responsible for handling message-related actions. allUserMessages
 * is a map of usernames to all of their Message objects.
 */
public class MessageManager implements java.io.Serializable {

    protected Map<String, List<Message>> allUserMessages;
    ReaderWriter RW;

    /**
     * This method constructs a MessageManager object with an empty allUserMessages.
     */
    public MessageManager(ReaderWriter RW){
    this.allUserMessages =  new HashMap<String, List<Message>>();
    this.RW = RW;


    }
    /**
     * This method constructs a MessageManager object with an empty allUserMessages.
     */
    public MessageManager(HashMap<String, List<Message>> allUserMessages){
        this.allUserMessages = allUserMessages;
    }

    /**
     * Creates a new Message object.
     * @param message Refers to the content of the new message.
     * @param senderUsername Refers to the String username of the user sending the message.
     * @param recipientUsername Refers to the String username of the user receiving the message.
     * @return Return the created message.
     */
    public Message createNewMessage(String message, String senderUsername, String recipientUsername){
        return new Message(message, senderUsername, recipientUsername);
    }

    /**
     * Adds a new message to the list of all messages a user has (their "inbox").
     * @param username Refers to the username of the user.
     * @param newMessage Refers to the message to be added.
     */
    public void addMessage(String username, Message newMessage){ this.allUserMessages.get(username).add(newMessage); }

    /**
     * This method allows the user to see all of their messages.
     * @param username Refers to the username of the user.
     * @return Returns a list of messages relating to the user.
     */
    public List<Message> viewMessages(String username){
        return allUserMessages.get(username);
    }

    /**
     * Checks if the user sending the message should be able to contact the recipient.
     * @param to Refers to the username of the recipient.
     * @param from Refers to the username of the sender.
     * @param userManager Refers to the UserManager object used to correlate usernames with User objects.
     * @return Return true if the sender should be able to message the recipient, and false otherwise.
     */
    public boolean checkIsMessageable(String to, String from, UserManager userManager){

        if(userManager.getUserType(from).isEmpty() || userManager.getUserType(to).isEmpty()) return false;

        if (userManager.getUserType(from).equals("attendee")){
            return userManager.getUserType(to).equals("attendee") || userManager.getUserType(to).equals("speaker");
        } else if (userManager.getUserType(from).equals("organizer")){
            return true;
        }
    return false;
    }

    /**
     * Returns the actual written component of a Message object
     * @param message the message whose content variable we want
     * @return returns the content variable of the Message object
     */
    public String getMessageContent(Message message){
        return message.getContent();
}


    /**
     * This method gets all of the user messages.
     * @return Refers to the map allUserMessages.
     */
    public HashMap<String, List<Message>> getAllUserMessages(){
        return (HashMap<String, List<Message>>) allUserMessages;
    }

    /**
     * This method sets the map of usernames to the list of messages relating to the user.
     * @param allUserMessages Refers to the map of usernames to list of messages relating to the user.
     */
    public void setAllUserMessages(HashMap<String, List<Message>> allUserMessages){
        this.allUserMessages = allUserMessages;
    }

    /**
     * This method gets the recipient of the message object.
     * @param message Refers to the message object.
     * @return Returns the username of the recipient of the message.
     */
    public String getRecipient(Message message){
        return message.getRecipient();
}

    /**
     * This method gets the sender of the message object.
     * @param message Refers to the message object.
     * @return Returns the username of the sender of the recipient.
     */
    public String getSender(Message message){
        return message.getSender();
}

    /**
     * This method sends a message to all of the attendees of the specified event.
     * @param eventNames Refers to the list of events to which you want to send the attendees a message.
     * @param message Refers to the string content of the message.
     * @param eventManager Refers to the class handling all of the events.
     * @param sender Refers to the username of the sender.
     */
    public void speakerBlastMessage(List<String> eventNames, String message, EventManager eventManager, String sender){
        for(String name : eventNames) {
            for (String receiver : eventManager.getEvent(name).getAttendeeSet()) {
                Message toBeSent = createNewMessage(message, sender, receiver);
                this.addMessage(receiver, toBeSent);
            }
        }
    }

    /**
     * This method adds a new username and list of message objects to the map.
     * @param username Refers to the username of the user.
     */
    public void addUserInbox(String username) {
        this.allUserMessages.put(username, new ArrayList<Message>());
}

    /**
     * This method sets the map of all messages to the deserialized HashMap object containing usernames as keys
     * and the corresponding user's messages received as values.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from users.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find users.
     */
    public void setAllUserMessagesReadIn() throws IOException, ClassNotFoundException {
        Object uncastedMessages = RW.readMessages();
        HashMap<String, List<Message>> allUserMessages = (HashMap<String, List<Message>>) uncastedMessages;
        setAllUserMessages(allUserMessages);
    }

    /**
     * This method sets a Message's read status as either read or unread.
     * @param message Refers to the message to be interacted with.
     * @param status Refers to the status you want to set the message to have.
     */
    public void setMessageReadStatus(Message message, String status){
        // don't check in manager, check input from presenter
        if(status.equals("read")){
            message.setBeenRead();
        }else{
            message.setUnread();
        }
    }

    /**
     * This method sets a Message's starred status as either starred or unstarred.
     * @param message Refers to the message to be interacted with.
     * @param status Refers to the status you want to set the message to have.
     */
    public void setMessageStarredStatus(Message message, String status){
        if(status.equals("starred")){
            message.setStarred();
        }else{
            message.setUnstarred();
        }
    }

    /**
     * This method gets the date and time of creation information of the message object.
     * @param message Refers to the message object.
     * @return Returns the dateTimeCreated variable of the message object.
     */
    public LocalDateTime getCreationInfo(Message message){
        return message.getDateTimeCreated();
    }

    /**
     * This method returns the deletion status of a message.
     * @param message Refers to the message being checked.
     * @return Returns true if the message is in the junk folder, and false otherwise.
     */
    public boolean getDeletionStatus(Message message){
        return message.isDeleted();
    }

    /**
     * This method returns the archive status of a message.
     * @param message Refers to the message being checked.
     * @return Returns true if the message is in the archive folder, and false otherwise.
     */
    public boolean getArchivedStatus(Message message){
        return message.isArchived();
    }

    /**
     * This method sets a Message's deletion status as either deleted or restored;
     * @param message Refers to the message to be interacted with.
     * @param status Refers to the status you want to set the message to have.
     */
    public void setDeletionStatus(Message message, String status){
        if(status.equals("delete")){
            message.setDeleted();
        }else{
            message.setUndeleted();
        }
    }

    public void setArchiveStatus(Message message, String status){
        if(status.equals("archive")){
            message.setArchived();
        }else{
            message.setUnarchived();
        }
    }
}
