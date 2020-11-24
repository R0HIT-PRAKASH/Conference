import java.io.Serializable;

/**
 * This class represents a Message object. Message objects have a string content, username of the sender,
 * and a username of the recipient.
 */
public class Message implements Serializable {

    private String content;
    private String senderUsername;
    private String recipientUsername;

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
}
