import java.util.HashMap;
import java.util.List;

public class MessageManager {
protected HashMap<String, List<Message>> allUserMessages;

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
     * @param newMessage Refers to the message to be added.
     * @return Returns nothing, as the message will always be added.
     */
public void addMessage(String username, Message newMessage){
    this.allUserMessages.get(username).add(newMessage);
}

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
            if (!(userManager.getUserType(to).equals("organizer"))){
                return true;
            }
        }
    return false;
}

public String getMessageContent(Message message){
        return message.getContent();
}

public void printMessages(String username){
        List<Message> allMessages = viewMessages(username);
        if(allMessages.size() == 0) {
            System.out.println("No Messages :(");
            return;
        }
        for (int i = allMessages.size() -1; i > -1; i-- ){
            System.out.println(allMessages.get(i).getContent());
        }
    }

public HashMap<String, List<Message>> getAllUserMessages(){
    return allUserMessages;
}

public String getRecipient(Message message){
        return message.getRecipient();
}

public String getSender(Message message){
        return message.getSender();
}

public void speakerBlastMessage(List<String> eventNames, String message, EventManager eventManager, String sender){
    for(String name : eventNames) {
        for (Attendee receiver : eventManager.getEvent(name).getAttendeeSet()) {
            Message toBeSent = createNewMessage(message, sender, receiver.getUsername());
            this.addMessage(receiver.getUsername(), toBeSent);
        }
    }
}
}
