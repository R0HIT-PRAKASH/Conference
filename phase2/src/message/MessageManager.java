package message;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import event.EventManager;
import saver.ReaderWriter;
import user.UserManager;
// using this https://stackoverflow.com/questions/40715424/printing-out-datetime-in-a-specific-format-in-java/40715452
import java.time.format.DateTimeFormatter;

/**
 * The MessageManager class is responsible for handling message-related actions. allUserMessages
 * is a map of usernames to all of their Message objects.
 */
public class MessageManager implements java.io.Serializable {

    protected Map<String, List<Message>> allUserMessages;
    ReaderWriter RW;

    /**
     * This method constructs a MessageManager object with an empty allUserMessages.
     * @param RW Refers to an instance of the class that reads and writes to files.
     */
    public MessageManager(ReaderWriter RW){
    this.allUserMessages =  new HashMap<String, List<Message>>();
    this.RW = RW;


    }
    /**
     * This method constructs a MessageManager object with an empty allUserMessages.
     */
    public MessageManager(){
        this.allUserMessages = new HashMap<String, List<Message>>();
    }

    /**
     * Creates a new Message object.
     * @param message Refers to the content of the new message.
     * @param senderUsername Refers to the String username of the user sending the message.
     * @param recipientUsername Refers to the String username of the user receiving the message.
     * @return Return the created message.
     */
    private Message createNewMessage(String message, String senderUsername, String recipientUsername){
        return new Message(message, senderUsername, recipientUsername);
    }

    /**
     * Adds a new message to the list of all messages a user has (their "inbox").
     * @param username Refers to the username of the sender.
     * @param message Refers to the message to be added.
     * @param recipientUsername Refers to the username of the recipient.
     */
    public void addMessage(String username, String message, String recipientUsername){
        Message newMessage = createNewMessage(message, username, recipientUsername);
        this.allUserMessages.get(recipientUsername).add(newMessage);
    }

    /**
     * Adds a read in message to the list of all messages a user has
     * @param username username of the sender
     * @param message content of the message
     * @param recipientUsername username of the recipient
     * @param beenRead whether or not this message has been read
     * @param dateTimeCreated The date and time this message was created and sent
     * @param dateTimeDeleted The date and time this message was deleted (if it has been deleted)
     * @param starred Whether or not this message has been starred
     * @param deleted Whether or not this message has been deleted
     * @param archived Whether or not this message has been archived
     * @param dateTimeCreatedCopy Refers to the date and time the message was created and sent.
     */
    public void addMessage(String username, String message, String recipientUsername, boolean beenRead,
                           LocalDateTime dateTimeCreated, LocalDateTime dateTimeDeleted, boolean starred,
                           boolean deleted, boolean archived, LocalDateTime dateTimeCreatedCopy){
        Message newMessage = new Message(message, username, recipientUsername, beenRead, dateTimeCreated,
                dateTimeDeleted, starred, deleted, archived, dateTimeCreatedCopy);
        this.allUserMessages.get(recipientUsername).add(newMessage);
    }

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
                addMessage(sender, message, receiver);
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
     * This method get a Message's read status as either read or unread.
     * @param message Refers to the message to be interacted with.
     * @return Return true if the message has been read.
     */
    public boolean getMessageReadStatus(Message message){
        return message.hasBeenRead();
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
     * This method returns the time and date a deleted message was deleted.
     * @param message Refers to the message being checked.
     * @return Returns true if the message is in the junk folder, and false otherwise.
     */
    public LocalDateTime getDeletionDateInfo(Message message){
        return message.getDateTimeDeleted();
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
     * This method returns the read status of a message.
     * @param message Refers to the message being checked.
     * @return Returns true if the message is starred, and false otherwise.
     */
    public boolean getStarredStatus(Message message){ return message.isStarred();}

    /**
     * This method sets a Message's deletion status as either deleted or restored.
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

    /**
     * This method sets a Message's archived status as either archived or unarchived.
     * @param message Refers to the message to be interacted with.
     * @param status Refers to the status you want to set the message to have.
     */
    public void setArchiveStatus(Message message, String status){
        if(status.equals("archive")){
            message.setArchived();
        }else{
            message.setUnarchived();
        }
    }

    /**
     * This method sets the date and time a message was created.
     * @param message Refers to the message to be interacted with.
     * @param status Refers to the status you want to set the message to have.
     * @param LDT Refers to the new date and time created you want to set the message to have.
     */
    public void setDateTimeCreatedStatus(Message message, String status, LocalDateTime LDT){
        message.setDateTimeCreated(LDT);
        if(status.equals("pin")){
            message.setPinned();
        }else{
            message.setUnpinned();
        }
    }

    /**
     * Returns the date and time this message was created.
     * @param message The message whose date we are looking for
     * @return Returns the date the message was created
     */
    public LocalDateTime getTimeCreated(Message message){
        return message.getDateTimeCreated();
    }

    /**
     * Returns the copy date and time this message was created.
     * @param message The message whose date we are looking for
     * @return the copy date the message was created
     */
    public LocalDateTime getTimeCreatedCopy(Message message){
        return message.getDateTimeCreatedCopy();
    }

    /**
     * Returns a List of "messages", where messages are encoded as a List of strings.
     * @param username the username of the User who's messages you want to generate.
     * @param inboxType Refers to the type of inbox.
     * @return Returns a list of the user's messages, where messages are of the form [sender, content, timestamp,
     * read status, starred status, deletion status, archive status]
     */
    public List<List<String>> generateEffectiveMessageList(String username, String inboxType){

        List<Message> messageList = new ArrayList<>();
        switch (inboxType) {
            case "inbox": {
                messageList = generateUserInboxList(username);
                break;
            }
            case "deleted": {
                messageList = generateDeletedMessageList(username);
                break;
            }
            case "starred": {
                messageList = generateStarredMessageList(username);
                break;
            }
            case "all": {
                messageList = allUserMessages.get(username);
                break;
            }
            default: {
                messageList = generateArchivedMessageList(username);
                break;
            }
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        List<List<String>> messageSenderContentTimestamp = new ArrayList<>();
        for(Message message: (messageList)){
            List<String> messageInfo = new ArrayList<>();
            messageInfo.add(message.getSender());
            messageInfo.add(message.getContent());
            // using Date Time Created Copy to support pinning messages.
            messageInfo.add(dtf.format(message.getDateTimeCreatedCopy()));
            messageInfo.add(String.valueOf(message.hasBeenRead()));
            messageInfo.add(String.valueOf(message.isStarred()));
            messageInfo.add((String.valueOf(message.isDeleted())));
            messageInfo.add(String.valueOf(message.isArchived()));
            messageInfo.add(String.valueOf(allUserMessages.get(username).indexOf(message)));
            messageInfo.add(username);
            messageInfo.add(String.valueOf(message.isPinned()));
            if(!(getDeletionDateInfo(message) == null)) {
                messageInfo.add(dtf.format(getDeletionDateInfo(message)));
            } else{
                messageInfo.add("null");
            }
            messageInfo.add(dtf.format(getTimeCreatedCopy(message)));
            messageSenderContentTimestamp.add(messageInfo);
        }
        return messageSenderContentTimestamp;
    }

    private List<Message> generateDeletedMessageList(String username){
        List<Message> userMessages = allUserMessages.get(username);
        List<Message> deletedMessages = new ArrayList<Message>();
        for(Message message: userMessages){
            if(message.isDeleted()){
                deletedMessages.add(message);
            }
        }
        Collections.sort(deletedMessages);
        return deletedMessages;
    }

    private List<Message> generateArchivedMessageList(String username){
        List<Message> userMessages = allUserMessages.get(username);
        List<Message> archivedMessages = new ArrayList<Message>();
        for(Message message: userMessages){
            if(message.isArchived()){
                archivedMessages.add(message);
            }
        }
        Collections.sort(archivedMessages);
        return archivedMessages;
    }

    private List<Message> generateStarredMessageList(String username){
        List<Message> userMessages = allUserMessages.get(username);
        List<Message> starredMessages = new ArrayList<Message>();
        for(Message message: userMessages){
            if(message.isStarred()){
                starredMessages.add(message);
            }
        }
        Collections.sort(starredMessages);
        return starredMessages;
    }

    private List<Message> generateUserInboxList(String username){
        List<Message> userMessages = allUserMessages.get(username);
        List<Message> inboxMessages = new ArrayList<Message>();
        for(Message message: userMessages){
            if(!(message.isDeleted()) && !(message.isArchived())){
                inboxMessages.add(message);
            }
        }
        Collections.sort(inboxMessages);
        return inboxMessages;
    }

    /**
     * Get the message in a User's inbox at a specified index
     * @param username the username of the User who's inbox we want to check
     * @param index the location of the message we are getting within the inbox
     * @return Return the message at index in the corresponding User's inbox.
     */
    public Message getMessageAtIndex(String username, int index){
        return allUserMessages.get(username).get(index);
    }


    public void deletedMessagesCheckMM(String username) {
        List<Message> allMessages = viewMessages(username);
        List<Message> deletedMessages = new ArrayList<>();

        for (Message message : allMessages) {
            if (getDeletionStatus(message)) {
                deletedMessages.add(message);
            }
        }
        LocalDateTime currentTime = LocalDateTime.now();
        int currentMinute = currentTime.getMinute();
        for (Message message : deletedMessages) {
            if (getDeletionDateInfo(message).getMinute() + 1 < currentMinute) {
                allMessages.remove(message);
            }
        }
    }
}
