package message;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class represents a Message object. Message objects have a string content, username of the sender,
 * and a username of the recipient. NEED TO FIX!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class Message implements Serializable, Comparable<Message> {

    private String content;
    private String senderUsername;
    private String recipientUsername;
    private boolean beenRead;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeCreatedCopy;
    private LocalDateTime dateTimeDeleted;
    private boolean starred;
    private boolean deleted;
    private boolean archived;
    private boolean pinned;

    /**
     * This method constructs a Message object.
     * @param content Refers to the string content of the message.
     * @param senderUsername Refers to the username of the sender.
     * @param recipientUsername Refers to the username of the recipient.
     */
    public Message(String content, String senderUsername, String recipientUsername){
        this.content = content;
        this.senderUsername = senderUsername;
        this.recipientUsername = recipientUsername;
        this.beenRead = false;
        this.dateTimeCreated = LocalDateTime.now();
        this.dateTimeCreatedCopy = LocalDateTime.now();
        this.dateTimeDeleted = null;
        this.pinned = false;
    }

    /**
     * This method constructs a read in Message object.
     * @param content content of the message
     * @param senderUsername username of the sender
     * @param recipientUsername username of the recipient
     * @param beenRead whether or not this message has been read
     * @param dateTimeCreated The date and time this message was created and sent
     * @param dateTimeDeleted The date and time this message was deleted (if it has been deleted)
     * @param starred Whether or not this message has been starred
     * @param deleted Whether or not this message has been deleted
     * @param archived Whether or not this message has been archived
     */
    public Message(String content, String senderUsername, String recipientUsername, boolean beenRead,
                   LocalDateTime dateTimeCreated, LocalDateTime dateTimeDeleted, boolean starred, boolean deleted,
                   boolean archived){
        this.content = content;
        this.senderUsername = senderUsername;
        this.recipientUsername = recipientUsername;
        this.dateTimeCreated = dateTimeCreated;
        this.dateTimeDeleted = dateTimeDeleted;
        this.beenRead = beenRead;
        this.starred = starred;
        this.deleted = deleted;
        this.archived = archived;
    }

    // Getters

    /**
     * Gets the string content of the message.
     * @return Returns the string content of the message.
     */
    public String getContent(){ return content; }

    /**
     * Gets the recipient of the message.
     * @return Returns the username of the recipient of the message.
     */
    public String getRecipient(){ return recipientUsername; }

    /**
     * Gets the sender of the message.
     * @return Returns the username of the sender of the message.
     */
    public String getSender(){ return senderUsername; }

    /**
     * Gets the the date and time of creation of the message.
     * @return Returns dateTimeCreated variable of the message.
     */
    public LocalDateTime getDateTimeCreated(){ return dateTimeCreated;}

    /**
     * Gets the the copy of date and time of creation of the message.
     * @return Returns dateTimeCreatedCopy variable of the message.
     */
    public LocalDateTime getDateTimeCreatedCopy(){ return dateTimeCreatedCopy;}

    /**
     * Gets the the date and time of a deleted message was deleted.
     * @return Returns dateTimeDeleted variable of the message.
     */
    public LocalDateTime getDateTimeDeleted(){ return dateTimeDeleted;}

    /**
     * Gets the read or unread status of the message.
     * @return Returns a boolean, where true means the message has been read.
     */
    public boolean hasBeenRead(){ return beenRead;}

    /**
     * Gets the starred or unstarred status of the message.
     * @return Returns a boolean, where true means the message has been starred.
     */
    public boolean isStarred(){ return starred;}

    /**
     * Gets the deletion status of the message.
     * @return Returns a boolean, where true means that message is in the junk folder.
     */
    public boolean isDeleted(){ return deleted;}

    /**
     * Gets the archive status of the message.
     * @return Returns a boolean, where true means the message is in the archive folder.
     */
    public boolean isArchived(){ return archived;}

    /**
     * Gets the pinned status of the message.
     * @return Returns a boolean, where true means the message is pinned.
     */
    public boolean isPinned(){ return pinned;}

    // Setters

    /**
     * Sets the string content of the message.
     * @param content Refers to the string content of the message.
     */
    public void setContent(String content){ this.content = content; }

    /**
     * Sets the username of the recipient of the message.
     * @param recipientUsername Refers to the username of the recipient.
     */
    public void setRecipient(String recipientUsername){ this.recipientUsername = recipientUsername; }

    /**
     * Sets the username of sender of the message.
     * @param senderUsername Refers to the username of the sender.
     */
    public void setSender(String senderUsername){ this.senderUsername = senderUsername; }

    /**
     * Sets the message as having been read.
     */
    public void setBeenRead(){this.beenRead = true; }

    /**
     * Sets the message's read status as unread.
     */
    public void setUnread(){this.beenRead = false;}

    /**
     * Sets the message as having been starred.
     */
    public void setStarred(){this.starred = true; }

    /**
     * Sets the message's starred status as unstarred.
     */
    public void setUnstarred(){this.starred = false;}

    /**
     * Sets the message's deletion status as deleted.
     */
    public void setDeleted(){
        this.deleted = true;
        this.starred = false;
        this.dateTimeDeleted = LocalDateTime.now();
    }

    /**
     * Sets the message's deletion status as not deleted.
     */
    public void setUndeleted(){
        this.deleted = false;
        this.dateTimeDeleted = null;
    }

    /**
     * Sets the message's archived status as archived.
     */
    public void setArchived(){
        this.archived = true;
        this.starred = false;
    }

    /**
     * Sets the message's archived status as unarchived.
     */
    public void setUnarchived(){this.archived = false;}

    /**
     * Sets the message's pinned status as pinned.
     */
    public void setPinned(){this.pinned = true;}

    /**
     * Sets the message's pinned status as unpinned.
     */
    public void setUnpinned(){this.pinned = false;}

    /**
     * Sets the message's dateTimeCreated variable.
     */
    public void setDateTimeCreated(LocalDateTime LDT){
        this.dateTimeCreated = LDT;
    }

    @Override
    public int compareTo(Message message) {
        return this.getDateTimeCreated().compareTo(message.getDateTimeCreated());
    }
}

