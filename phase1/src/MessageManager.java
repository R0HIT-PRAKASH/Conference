import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageManager {
protected HashMap<String, List<Message>> allUserMessages;
// How do we "store" this map..

public MessageManager(){
    this.allUserMessages =  new HashMap<String, List<Message>>();
}

public boolean createNewMessage(String message, String senderUsername, String recipientUsername){
    Message newMessage = new Message(message, senderUsername, recipientUsername);
    return true;
}

public List<Message> viewMessages(String username){
    return allUserMessages.get(username);
}


}
