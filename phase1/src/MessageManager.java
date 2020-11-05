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

public void printMessages(String username){
    List<Message> allMessages = viewMessages(username);
        for (int i = allMessages.size() -1; i > -1; i-- ){
            System.out.println(allMessages.get(i).getContent());
        }
}

public void speakerBlastMessage(List<String> eventNames, String message, EventManager eventManager, String sender){
    for(String name : eventNames) {
        for (Attendee receiver : eventManager.getEvent(name).getAttendeeList()) {
            boolean toBeSent = createNewMessage(message, sender, receiver.getUsername());
        }
    }
}
}
