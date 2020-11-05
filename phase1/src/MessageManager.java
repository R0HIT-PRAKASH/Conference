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

public void speakerBlastMessage(String [] eventNames, String message, EventManager eventManager, String sender){
    Event [] thisOne = new Event[eventNames.length];
    for (int i = 0; i < thisOne.length; i++){
        thisOne[i] = eventManager.getEvent(eventNames[i]);
    }
    for (int i = 0; i < thisOne.length; i++){
        List<Attendee> attendees = thisOne[i].getAttendeeList();
        for(int j = 0; j < attendees.size(); j++) {
            boolean toBeSent = createNewMessage(message, sender,attendees.get(i).getUsername());
        }
    }
}
}
