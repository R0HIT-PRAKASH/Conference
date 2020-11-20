import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The MessageManager class is responsible for handling message-related actions. allUserMessages
 * is a map of usernames to all of their Message objects.
 */
public class MessageManager implements java.io.Serializable {

    protected HashMap<String, List<Message>> allUserMessages;

    /**
     * This method constructs a MessageManager object with an empty allUserMessages.
     */
    public MessageManager(){
    this.allUserMessages =  new HashMap<String, List<Message>>();
}

    /**
     * Creates a new Message object.
     * @param message Refers to the content of the new message.
     * @param senderUsername Refers to the String username of the user sending the message.
     * @param recipientUsername Refers to the String username of the user receiving the message.
     * @return Return the created message.
     */
    public Message createNewMessage(String message, String senderUsername, String recipientUsername){
        Message newMessage = new Message(message, senderUsername, recipientUsername);
        return newMessage;
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
            if (userManager.getUserType(to).equals("organizer")){
                return true;
            }
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
    return allUserMessages;
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
            for (User receiver : eventManager.getEvent(name).getAttendeeSet()) {
                Message toBeSent = createNewMessage(message, sender, receiver.getUsername());
                this.addMessage(receiver.getUsername(), toBeSent);
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
}
