public class Message {
    private String message;
    private String senderUsername;
    private String recipientUsername;


public Message(String message, String senderUsername, String recipientUsername){
    this.message = message;
    this.senderUsername = senderUsername;
    this.recipientUsername = recipientUsername;
}

// Getters

public String getMessage(){ return message; }

public String getRecipient(){ return recipientUsername; }

public String getSender(){ return senderUsername; }

// Setters

public void setMessage(String message){ this.message = message; }

public void setRecipient(String recipientUsername){ this.recipientUsername = recipientUsername; }

public void setSender(String senderUsername){ this.senderUsername = senderUsername; }
}
