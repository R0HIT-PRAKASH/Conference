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

import java.time.LocalDateTime;
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
     * Prints all the messages that this attendee has received
     * @param username: The username of the Attendee
     */
    protected void viewMessages(String username) {
        List<Message> allMessages = messageManager.viewMessages(username);
        p.displayPrintMessages(allMessages);
        if(allMessages.size()>0) {
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
                    !messageAction.equalsIgnoreCase("CLOSE") &&
                    !messageAction.equalsIgnoreCase("MARK AS STARRED") &&
                    !messageAction.equalsIgnoreCase("UNSTAR") &&
                    !messageAction.equalsIgnoreCase("DELETE")  &&
                    !messageAction.equalsIgnoreCase("ARCHIVE")) {
                messageAction = p.displayMessageActionPrompt();
            }
            if (messageAction.equalsIgnoreCase("REPLY")) {
                String content = p.displayEnterMessagePrompt();
                replyMessage(content, selectedMessage.getSender());
            } else if (messageAction.equalsIgnoreCase("MARK AS UNREAD")) {
                messageManager.setMessageReadStatus(selectedMessage, "unread");
            } else if (messageAction.equalsIgnoreCase("MARK AS STARRED")) {
                if (selectedMessage.isStarred()) {
                    p.displayStarError();
                } else {
                    messageManager.setMessageStarredStatus(selectedMessage, "starred");
                }
            } else if (messageAction.equalsIgnoreCase("UNSTAR")) {
                if (selectedMessage.isStarred()) {
                    messageManager.setMessageStarredStatus(selectedMessage, "unstar");
                } else {
                    p.displayUnstarError();
                }
            } else if (messageAction.equalsIgnoreCase("CLOSE")){
            } else if (messageAction.equalsIgnoreCase("DELETE")){
                messageManager.setDeletionStatus(selectedMessage, "delete");
            } else if (messageAction.equalsIgnoreCase("ARCHIVE")){
                messageManager.setArchiveStatus(selectedMessage, "archive");
            }
        }
    }

    /**
     * Prints all the messages that this attendee has starred
     * @param username: The username of the Attendee
     */
    protected void viewStarredMessages(String username) {
        List<Message> allMessages = messageManager.viewMessages(username);
        // get starred messages
        List<Message> starredMessages = new ArrayList<Message>();

        for (Message message: allMessages) {
            if (message.isStarred()) {
                starredMessages.add(message);
            }
        }
        p.displayPrintStarredMessages(starredMessages);
        if(starredMessages.size()>0) {
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
                    !messageAction.equalsIgnoreCase("CLOSE") &&
                    !messageAction.equalsIgnoreCase("MARK AS STARRED") &&
                    !messageAction.equalsIgnoreCase("UNSTAR")&&
                    !messageAction.equalsIgnoreCase("DELETE")  &&
                    !messageAction.equalsIgnoreCase("ARCHIVE")) {
                messageAction = p.displayMessageActionPrompt();
            }
            if (messageAction.equalsIgnoreCase("REPLY")) {
                String content = p.displayEnterMessagePrompt();
                replyMessage(content, selectedMessage.getSender());
            } else if (messageAction.equalsIgnoreCase("MARK AS UNREAD")) {
                messageManager.setMessageReadStatus(selectedMessage, "unread");
            } else if (messageAction.equalsIgnoreCase("MARK AS STARRED")) {
                if (selectedMessage.isStarred()) {
                    p.displayStarError();
                } else {
                    messageManager.setMessageStarredStatus(selectedMessage, "starred");
                }
            } else if (messageAction.equalsIgnoreCase("UNSTAR")) {
                if (selectedMessage.isStarred()) {
                    messageManager.setMessageStarredStatus(selectedMessage, "unstar");
                } else {
                    p.displayUnstarError();
                }
            } else if (messageAction.equalsIgnoreCase("CLOSE")){
            } else if (messageAction.equalsIgnoreCase("DELETE")){
                messageManager.setDeletionStatus(selectedMessage, "delete");
            } else if (messageAction.equalsIgnoreCase("ARCHIVE")){
                messageManager.setArchiveStatus(selectedMessage, "archive");
            }
        }
    }

    protected void viewDeletedMessages(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        // get deleted messages
        List<Message> deletedMessages = new ArrayList<>();

        for (Message message: allMessages) {
            if (messageManager.getDeletionStatus(message)) {
                deletedMessages.add(message);
            }
        }
        p.displayDeletedMessages(deletedMessages);
        if(deletedMessages.size()>0) {
            int requestedMessage = p.displaySelectMessage();
            while (requestedMessage > allMessages.size() || requestedMessage < 1) {
                p.displayMessageNonExistent();
                requestedMessage = p.displaySelectMessage();
            }

            Message selectedMessage = (allMessages.get(allMessages.size() - requestedMessage));
            p.displaySelectedMessage(selectedMessage);
            messageManager.setMessageReadStatus(selectedMessage, "read");

            // this method may be too large now, but this prompts the user to take an action on the selected message
            String messageAction = p.displayDeletedActionPrompt();
            while (!messageAction.equalsIgnoreCase("REPLY") &&
                    !messageAction.equalsIgnoreCase("DELETE") &&
                    !messageAction.equalsIgnoreCase("CLOSE") &&
                    !messageAction.equalsIgnoreCase("RESTORE")) {
                messageAction = p.displayDeletedActionPrompt();
            }
            if (messageAction.equalsIgnoreCase("REPLY")) {
                String content = p.displayEnterMessagePrompt();
                replyMessage(content, selectedMessage.getSender());
            } else if (messageAction.equalsIgnoreCase("CLOSE")){
            } else if (messageAction.equalsIgnoreCase("DELETE")){
                if(p.displayDeleteConfirmation().equalsIgnoreCase("YES")){
                    deletedMessages.remove(selectedMessage);
                    allMessages.remove(selectedMessage);
                }
            } else if (messageAction.equalsIgnoreCase("RESTORE")){
                deletedMessages.remove(selectedMessage);
                messageManager.setDeletionStatus(selectedMessage, "restore");
            }
        }
    }

    protected void viewArchivedMessages(String username) {
        List<Message> allMessages = messageManager.viewMessages(username);
        // get archived messages
        List<Message> archivedMessages = new ArrayList<>();

        for (Message message : allMessages) {
            if (messageManager.getArchivedStatus(message)) {
                archivedMessages.add(message);
            }
        }
        p.displayArchivedMessages(archivedMessages);
        if (archivedMessages.size() > 0) {
            int requestedMessage = p.displaySelectMessage();
            while (requestedMessage > allMessages.size() || requestedMessage < 1) {
                p.displayMessageNonExistent();
                requestedMessage = p.displaySelectMessage();
            }

            Message selectedMessage = (allMessages.get(allMessages.size() - requestedMessage));
            p.displaySelectedMessage(selectedMessage);
            messageManager.setMessageReadStatus(selectedMessage, "read");

            String messageAction = p.displayArchivedActionPrompt();
            while (!messageAction.equalsIgnoreCase("REPLY") &&
                    !messageAction.equalsIgnoreCase("CLOSE") &&
                    !messageAction.equalsIgnoreCase("UNARCHIVE")) {
                messageAction = p.displayDeletedActionPrompt();
            }
            if (messageAction.equalsIgnoreCase("REPLY")) {
                String content = p.displayEnterMessagePrompt();
                replyMessage(content, selectedMessage.getSender());
            } else if (messageAction.equalsIgnoreCase("CLOSE")) {
            } else if (messageAction.equalsIgnoreCase("UNARCHIVE")) {
                archivedMessages.remove(selectedMessage);
                messageManager.setArchiveStatus(selectedMessage, "restore");
            }
        }
    }

    protected void deletedMessagesCheck() {
        // deleted message check
        List<Message> allMessages = messageManager.viewMessages(username);
        List<Message> deletedMessages = new ArrayList<>();

        for (Message message : allMessages) {
            if (messageManager.getDeletionStatus(message)) {
                deletedMessages.add(message);
            }
        }
        LocalDateTime currentTime = LocalDateTime.now();
        int currentMinute = currentTime.getMinute();
        for (Message message : deletedMessages) {
            if (messageManager.getDeletionDateInfo(message).getMinute() < currentMinute) {
                allMessages.remove(message);
            }
        }
    }


    /**
     * Sends a reply to the oldest message in an attendees inbox
     * @param recipient: The username of the User we are replying too
     * @param content: The content of the message the Attendee is sending
     */
    protected void replyMessage(String content, String recipient){
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


