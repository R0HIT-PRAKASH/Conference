package user;

import event.Event;
import event.EventManager;
import message.Message;
import message.MessageManager;
import request.Request;
import request.RequestManager;
import user.UserController;
import user.UserManager;
import user.attendee.Attendee;
import user.speaker.SpeakerPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class UserController {
    public UserManager userManager;
    public EventManager eventManager;
    public MessageManager messageManager;
    public String username;
    public RequestManager requestManager;
    UserPresenter p;

    /**
     * Creates a Speaker Controller
     *
     * @param userManager    user use case
     * @param eventManager   event use case
     * @param messageManager message use case
     * @param username       username of the user
     */
    public UserController(UserManager userManager, EventManager eventManager, MessageManager messageManager,
                          String username, RequestManager request) {
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.username = username;
        this.requestManager = request;
        p = new UserPresenter();
    }


    /**
     * Prints all the messages that this user has received
     * @param username: The username of the user
     */
    public void viewMessages (String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        p.displayPrintMessages(allMessages);
        if (allMessages.size() > 0) {
            int requestedMessage = p.displaySelectMessage();
            while (requestedMessage > allMessages.size() || requestedMessage < 1) {
                    p.displayMessageNonExistent();
                    requestedMessage = p.displaySelectMessage();
                }

            Message selectedMessage = (allMessages.get(allMessages.size() - requestedMessage));
            p.displaySelectedMessage(selectedMessage);
            messageManager.setMessageReadStatus(selectedMessage, "read");

            // this method may be too large now, but this prompts the user to take an action on the selected message
            String messageAction = p.displayMessageActionPrompt();
            while (!messageAction.equalsIgnoreCase("REPLY") &&
                    !messageAction.equalsIgnoreCase("MARK AS UNREAD") &&
                    !messageAction.equalsIgnoreCase("CLOSE")) {
                messageAction = p.displayMessageActionPrompt();
                }
            if (messageAction.equalsIgnoreCase("REPLY")) {
                    String content = p.displayEnterMessagePrompt();
                    replyMessage(content, selectedMessage.getSender());
            } else if (messageAction.equalsIgnoreCase("MARK AS UNREAD")) {
                    messageManager.setMessageReadStatus(selectedMessage, "unread");
            } else if (messageAction.equalsIgnoreCase("CLOSE")) {
            }
        }
    }

    protected void replyMessage(String recipient, String content){
        Message message = messageManager.createNewMessage(content, username, recipient);
        messageManager.addMessage(recipient, message);
        p.displayMessageSentPrompt();
    }

    protected void viewRequests(String username){
        List<Request> requests = requestManager.getUserRequests(username);
        p.displayRequests(requests);
    }

    protected void makeRequest(String content, String username){
        Request request = requestManager.createNewRequest(content, username);
        requestManager.addRequest(username, request);
        ((Attendee)userManager.getUser(username)).addRequest(request);
    }
}


