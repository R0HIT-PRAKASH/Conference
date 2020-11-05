public class Message {
    private String content;
    private String senderUsername;
    private String recipientUsername;


public Message(String content, String senderUsername, String recipientUsername){
    this.content = content;
    this.senderUsername = senderUsername;
    this.recipientUsername = recipientUsername;
}

// Getters

public String getContent(){ return content; }

public String getRecipient(){ return recipientUsername; }

public String getSender(){ return senderUsername; }

// Setters

public void setContent(String content){ this.content = content; }

public void setRecipient(String recipientUsername){ this.recipientUsername = recipientUsername; }

public void setSender(String senderUsername){ this.senderUsername = senderUsername; }
}
