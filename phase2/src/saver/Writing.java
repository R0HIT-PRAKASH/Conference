package saver;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.sun.corba.se.spi.ior.ObjectKey;
import user.*;
import event.*;
import request.*;
import room.*;
import message.*;
import user.attendee.Attendee;
import user.organizer.Organizer;
import user.speaker.Speaker;
import user.vip.Vip;

import javax.jws.soap.SOAPBinding;


public class Writing {

    static Connection conn;
    DateTimeFormatter formatter;

    public Writing() {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/conference",
                    "root", "csc@207uoft");
            boolean isValid = conn.isValid(0);
            this.conn = conn;
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        }
    catch(SQLException e){
        System.out.println("An error occurred while connecting MySQL database");
        e.printStackTrace();
    }

}

    public static void clearEverything() throws SQLException {
        int clear = 0;
        PreparedStatement dropEL = conn.prepareStatement("DELETE FROM eventlist");
        clear += dropEL.executeUpdate();
        PreparedStatement dropM = conn.prepareStatement("DELETE FROM messages");
        clear += dropM.executeUpdate();
        PreparedStatement dropOE = conn.prepareStatement("DELETE FROM organizingevents");
        clear += dropOE.executeUpdate();
        PreparedStatement dropRQ = conn.prepareStatement("DELETE FROM requests");
        clear += dropRQ.executeUpdate();
        PreparedStatement dropR = conn.prepareStatement("DELETE FROM room");
        clear += dropR.executeUpdate();
        PreparedStatement dropSE = conn.prepareStatement("DELETE FROM speakingevents");
        clear += dropSE.executeUpdate();
        PreparedStatement dropTP = conn.prepareStatement("DELETE FROM talkpanel");
        clear += dropTP.executeUpdate();
        PreparedStatement dropUE = conn.prepareStatement("DELETE FROM userevent");
        clear += dropUE.executeUpdate();
        PreparedStatement dropU = conn.prepareStatement("DELETE FROM users");
        clear += dropU.executeUpdate();
    }

    public void saveUserManager(UserManager userManager) throws SQLException{
        Set<String> setUsernames = userManager.getUserMap().keySet();
        for (String username : setUsernames) {
            PreparedStatement insertGeneral = conn.prepareStatement("insert into users (username, userType, password," +
                    " address, email, company, bio, name) values (?, ?, ?, ?, ?, ?, ?, ?)");
            insertGeneral.setString(1, username);
            String type = userManager.getUserType(userManager.getUser(username));
            insertGeneral.setString(2, type);
            insertGeneral.setString(3, userManager.getPassword(username));
            insertGeneral.setString(4, userManager.getAddress(username));
            insertGeneral.setString(5, userManager.getEmail(username));
            insertGeneral.setString(6, userManager.getCompany(username));
            insertGeneral.setString(7, userManager.getBio(username));
            insertGeneral.setString(8, userManager.getName(username));
            insertGeneral.executeUpdate();
            if(type.equalsIgnoreCase("speaker")){
                List<String> speakingAt = ((Speaker)userManager.getUser(username)).getSpeakingEvents();
                for (String event : speakingAt) {
                    PreparedStatement insertSpeakingAt = conn.prepareStatement("insert into speakingevents (" +
                            "speakerusername, eventname) values (?, ?)");
                    insertSpeakingAt.setString(1, username);
                    insertSpeakingAt.setString(2, event);
                    insertSpeakingAt.executeUpdate();
                }

            }
            else if(type.equalsIgnoreCase("organizer")){
                List<String> attending = ((Organizer)userManager.getUser(username)).getAttendingEvents();
                for (String event : attending) {
                    PreparedStatement insertSpeakingAt = conn.prepareStatement("insert into userevent (" +
                            "username, eventname) values (?, ?)");
                    insertSpeakingAt.setString(1, username);
                    insertSpeakingAt.setString(2, event);
                    insertSpeakingAt.executeUpdate();
                }
                List<String> organizing = ((Organizer)userManager.getUser(username)).getOrganizingEvents();
                for (String event : organizing){
                    PreparedStatement insertSpeakingAt = conn.prepareStatement("insert into organizingevents(" +
                            "organizerusername, eventname) values (?, ?)");
                    insertSpeakingAt.setString(1, username);
                    insertSpeakingAt.setString(2, event);
                    insertSpeakingAt.executeUpdate();
                }
            }
            else if(type.equalsIgnoreCase("attendee") || type.equalsIgnoreCase("vip")){
                List<String> attending = ((Attendee)userManager.getUser(username)).getAttendingEvents();
                for (String event : attending) {
                    PreparedStatement insertSpeakingAt = conn.prepareStatement("insert into userevent (" +
                            "username, eventname) values (?, ?)");
                    insertSpeakingAt.setString(1, username);
                    insertSpeakingAt.setString(2, event);
                    insertSpeakingAt.executeUpdate();
                }
            }

        }

    }

    //Clean architecture is violated here. Need to refactor how rooms are stored
    public void saveEventManager(EventManager eventManager) throws SQLException {
        List<Room> rooms = eventManager.getRooms();
        for (Room room : rooms){
            PreparedStatement insertRoom = conn.prepareStatement("insert into room (" +
                    "roomnumber, capacity, computers, projector, tables, chairs) values (?, ?, ?, ?, ?, ?)");
            insertRoom.setInt(1, room.getRoomNumber());
            insertRoom.setInt(2, room.getCapacity());
            insertRoom.setInt(3, room.getComputers());
            int projector = 0;
            if(room.getProjector()) projector = 1;
            insertRoom.setInt(4, projector);
            insertRoom.setInt(5, room.getTables());
            insertRoom.setInt(6, room.getChairs());
            insertRoom.executeUpdate();
        }
        List<String> eventNames = eventManager.getAllEventNamesOnly();
        for(String name : eventNames){
            PreparedStatement insertEvent = conn.prepareStatement("insert into eventlist (" +
                    "eventname, time, duration, roomnumber, capacity, requiredComputers, requiredprojector, " +
                    "requiredChairs, requiredTABLES, vipevent, tag, eventtype) values (?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?)");
            insertEvent.setString(1, name);
            LocalDateTime outTime = eventManager.getTime(name);
            String date = outTime.format(formatter);
            insertEvent.setString(2, date);
            insertEvent.setInt(3, eventManager.getDuration(name));
            insertEvent.setInt(4, eventManager.getRoomNumber(name));
            insertEvent.setInt(5, eventManager.getRoomCapacity(eventManager.getRoomNumber(name)));
            insertEvent.setInt(6, eventManager.getComputers(name));
            int outProjector = 0;
            if(eventManager.getProjector(name)) outProjector = 1;
            insertEvent.setInt(7, outProjector);
            insertEvent.setInt(8, eventManager.getChairs(name));
            insertEvent.setInt(9, eventManager.getTables(name));
            int vipEvent = 0;
            if(eventManager.getVipEvent(name)) vipEvent = 1;
            insertEvent.setInt(10, vipEvent);
            insertEvent.setString(11, eventManager.getTag(name));
            insertEvent.setString(12, eventManager.getType(name));
            insertEvent.executeUpdate();
            if (eventManager.getType(name).equalsIgnoreCase("talk")){
                PreparedStatement insertSpeaker = conn.prepareStatement("insert into talkpanel (eventname, " +
                        "speakerusername) values (?, ?)");
                insertSpeaker.setString(1, name);
                insertSpeaker.setString(2, eventManager.getSpeakerName(name));
                insertSpeaker.executeUpdate();
            }
            else if(eventManager.getType(name).equalsIgnoreCase("panel")){
                List<String> speakers = eventManager.getSpeakerList(name);
                for (String speaker : speakers){
                    PreparedStatement insertSpeaker = conn.prepareStatement("insert into talkpanel (eventname, " +
                            "speakerusername) values (?, ?)");
                    insertSpeaker.setString(1, name);
                    insertSpeaker.setString(2, speaker);
                    insertSpeaker.executeUpdate();
                }
            }
        }
    }

    //Also violates clean architecture, can't isolate Messages otherwise
    public void saveMessageManager(MessageManager messageManager) throws SQLException{
        Set<String> usernames = messageManager.getAllUserMessages().keySet();
        for (String username : usernames){
            List<Message> messages = messageManager.viewMessages(username);
            for (Message message : messages){
                PreparedStatement insertMessage = conn.prepareStatement("insert into messages (content, " +
                        "senderUsername, recepientUsername, beenread, starred, deleted, archived, datetimecreated," +
                        " datetimedeleted, datetimecreatedcopy) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                insertMessage.setString(1, messageManager.getMessageContent(message));
                insertMessage.setString(3, username);
                insertMessage.setString(2, messageManager.getSender(message));
                int beenread = 0;
                if(messageManager.getMessageReadStatus(message)) beenread = 1;
                insertMessage.setInt(4, beenread);
                int starred = 0;
                if(messageManager.getStarredStatus(message)) starred = 1;
                insertMessage.setInt(5, starred);
                int deleted = 0;
                if(messageManager.getDeletionStatus(message)) deleted = 1;
                insertMessage.setInt(6, deleted);
                int archived = 0;
                if(messageManager.getArchivedStatus(message)) archived = 1;
                insertMessage.setInt(7, archived);
                String created = messageManager.getTimeCreated(message).format(formatter);
                insertMessage.setString(8, created);
                if (deleted == 1){
                    String deletedAt = messageManager.getDeletionDateInfo(message).format(formatter);
                    insertMessage.setString(9, deletedAt);
                }
                else{
                    insertMessage.setString(9, "-1");
                }
                insertMessage.setString(10, messageManager.getTimeCreatedCopy(message).format(formatter));
                insertMessage.executeUpdate();
            }
        }

    }

    public void saveRequestManager(RequestManager requestManager) throws SQLException{
        Set<String> usernames = requestManager.getAllRequests().keySet();
        for(String username : usernames){
            List<Request> requests = requestManager.getUserRequests(username);
            for (Request request : requests){
                PreparedStatement insertRequests = conn.prepareStatement("insert into requests (requestusername, " +
                        "requeststatus, content) values (?, ?, ?)");
                insertRequests.setString(1, username);
                insertRequests.setString(2, requestManager.getRequestStatus(request));
                insertRequests.setString(3, requestManager.getRequestContent(request));
                insertRequests.executeUpdate();
            }


        }
    }

    public UserManager readInUsers() throws SQLException {
        UserManager userManager = new UserManager();
        PreparedStatement getUsers = conn.prepareStatement("select * from users");
        ResultSet allUsers = getUsers.executeQuery();
        while (allUsers.next()) {
            String username = allUsers.getString("username");
            String password = allUsers.getString("password");
            String address = allUsers.getString("address");
            String email = allUsers.getString("email");
            String company = allUsers.getString("company");
            String bio = allUsers.getString("bio");
            String name = allUsers.getString("name");
            String type = allUsers.getString("userType");
            userManager.addUser(name, address, email, username, password, type, company, bio);
            if (type.equalsIgnoreCase("speaker")) {
                PreparedStatement getSpeaking = conn.prepareStatement("select eventname from speakingevents where" +
                        " speakerusername = ?");
                getSpeaking.setString(1, username);
                ResultSet allSpeaking = getSpeaking.executeQuery();
                List<String> speakingAt = new ArrayList<>();
                while (allSpeaking.next()) {
                    userManager.addSpeakingEvent(username, allSpeaking.getString("eventname"));
                }
            } else {
                PreparedStatement getAttendeeEvent = conn.prepareStatement("select eventname from userevent where " +
                        "username = ?");
                getAttendeeEvent.setString(1, username);
                ResultSet eventsSet = getAttendeeEvent.executeQuery();
                while (eventsSet.next()) {
                    userManager.directSignUp(username, eventsSet.getString("eventname"));
                }
            }
            if (type.equalsIgnoreCase("organizer")) {
                PreparedStatement getCreatedEvent = conn.prepareStatement("select eventname from organizingevents" +
                        " where organizerusername = ?");
                getCreatedEvent.setString(1, username);
                ResultSet createdSet = getCreatedEvent.executeQuery();
                List<String> curr = new ArrayList<>();
                curr.add(username);
                while (createdSet.next()) {
                    userManager.createdEvent(createdSet.getString("eventname"), curr);
                }
            }
        }
        return userManager;
    }

    public MessageManager readInMessages() throws SQLException {
        MessageManager messageManager = new MessageManager();
        PreparedStatement getUsernames = conn.prepareStatement("select username from users");
        ResultSet usernames = getUsernames.executeQuery();
        while(usernames.next()) {
            messageManager.addUserInbox(usernames.getString("username"));
        }
        PreparedStatement getAllMessages = conn.prepareStatement("select content, senderUsername, " +
                "recepientUsername, beenread, starred, deleted, archived, datetimecreated, datetimedeleted, " +
                "datetimecreatedcopy from messages");
        ResultSet messagesSet = getAllMessages.executeQuery();
        while(messagesSet.next()){
            String content = messagesSet.getString("content");
            String senderUsername = messagesSet.getString("senderUsername");
            String recipientUsername = messagesSet.getString("recepientUsername");
            boolean beenread = false;
            boolean starred = false;
            boolean archived = false;
            boolean deleted = false;
            int inbeenread = messagesSet.getInt("beenread");
            int instarred = messagesSet.getInt("starred");
            int inarchived = messagesSet.getInt("archived");
            int indeleted = messagesSet.getInt("deleted");
            if (inbeenread == 1) beenread = true;
            if (instarred == 1) starred = true;
            if (inarchived == 1) archived = true;
            if (indeleted == 1) deleted = true;
            String inCopy = messagesSet.getString("datetimecreatedcopy");
            LocalDateTime dateTimeCreatedCopy = LocalDateTime.parse(inCopy, formatter);
            String inCreated = messagesSet.getString("datetimecreated");
            LocalDateTime dateTimeCreated = LocalDateTime.parse(inCreated, formatter);
            LocalDateTime dateTimeDeleted;
            String inDeleted = messagesSet.getString("datetimedeleted");
            if(inDeleted.equalsIgnoreCase("-1")) dateTimeDeleted = null;
            else  dateTimeDeleted = LocalDateTime.parse(inDeleted, formatter);
            messageManager.addMessage(senderUsername, content, recipientUsername, beenread, dateTimeCreated,
                    dateTimeDeleted, starred, deleted, archived, dateTimeCreatedCopy);
        }
        return messageManager;
    }

    public RequestManager readInRequests() throws SQLException{
        RequestManager requestManager = new RequestManager();
        PreparedStatement getUsernames = conn.prepareStatement("select username from users");
        ResultSet usernames = getUsernames.executeQuery();
        while(usernames.next()) {
            requestManager.addUserRequests(usernames.getString("username"));
        }
        PreparedStatement getAllRequests = conn.prepareStatement("select * from requests");
        ResultSet allRequests = getAllRequests.executeQuery();
        while(allRequests.next()){
            String requesterUsername = allRequests.getString("requestusername");
            String requestStatus = allRequests.getString("requeststatus");
            String content = allRequests.getString("content");
            requestManager.createNewRequest(content, requesterUsername, requestStatus);
        }
        return requestManager;
    }

    public EventManager readInEvents() throws SQLException{
        EventManager eventManager = new EventManager();
        PreparedStatement getAllEvents = conn.prepareStatement("select * from eventlist");
        ResultSet allEvents = getAllEvents.executeQuery();
        while(allEvents.next()){
            String type = allEvents.getString("eventtype");
            String name = allEvents.getString("eventname");
            PreparedStatement getCreators = conn.prepareStatement("select organizerusername from organizingevents " +
                    "where eventname = ?");
            getCreators.setString(1, name);
            ResultSet allCreators = getCreators.executeQuery();
            List<String> creators = new ArrayList<>();
            while(allCreators.next()){
                creators.add(allCreators.getString("organizerusername"));
            }
            PreparedStatement getAttendees = conn.prepareStatement("select username from userevent " +
                    "where eventname = ?");
            getAttendees.setString(1, name);
            ResultSet allAttendees = getAttendees.executeQuery();
            List<String> attendees = new ArrayList<>();
            while(allAttendees.next()){
                attendees.add(allAttendees.getString("username"));
            }
            LocalDateTime time = LocalDateTime.parse(allEvents.getString("time"), formatter);
            int duration = allEvents.getInt("duration");
            int roomNumber = allEvents.getInt("roomnumber");
            int capacity = allEvents.getInt("capacity");
            int computers = allEvents.getInt("requiredComputers");
            int inProjector = allEvents.getInt("requiredprojector");
            boolean projector = false;
            if(inProjector == 1) projector = true;
            int chairs = allEvents.getInt("requiredChairs");
            int tables = allEvents.getInt("requiredTABLES");
            int inVIP = allEvents.getInt("vipevent");
            boolean VIP = false;
            if(inVIP == 1) VIP = true;
            String tag = allEvents.getString("tag");
            if (type.equalsIgnoreCase("party")){
                eventManager.addEvent(type, name, time, duration, roomNumber, capacity, computers, projector, chairs,
                        tables, creators, VIP, null, null, tag);
            }
            else if (type.equalsIgnoreCase("talk")){
                PreparedStatement getSpeaker = conn.prepareStatement("select speakerusername from talkpanel " +
                        "where eventname = ?");
                getSpeaker.setString(1, name);
                ResultSet allSpeaker = getSpeaker.executeQuery();
                allSpeaker.next();
                String speaker = allSpeaker.getString("speakerusername");
                eventManager.addEvent(type, name, time, duration, roomNumber, capacity, computers, projector, chairs,
                        tables, creators, VIP, speaker, null, tag);
            }
            else if (type.equalsIgnoreCase("panel")){
                PreparedStatement getSpeakers = conn.prepareStatement("select speakerusername from talkpanel " +
                        "where eventname = ?");
                getSpeakers.setString(1, name);
                ResultSet allSpeakers = getSpeakers.executeQuery();
                List<String> speakers = new ArrayList<>();
                while(allSpeakers.next()){
                    speakers.add(allSpeakers.getString("speakerusername"));
                }
                eventManager.addEvent(type, name, time, duration, roomNumber, capacity, computers, projector, chairs,
                        tables, creators, VIP, null, speakers, tag);
            }
            for(int i = 0; i < attendees.size(); i++){
                eventManager.addAttendee(name, attendees.get(i));
            }
        }
        PreparedStatement getAllRooms = conn.prepareStatement("select * from room");
        ResultSet allRooms = getAllRooms.executeQuery();
        while(allRooms.next()){
            int roomNumber = allRooms.getInt("roomnumber");
            int capacity = allRooms.getInt("capacity");
            int computers = allRooms.getInt("computers");
            int inProjector = allRooms.getInt("projector");
            boolean projector = false;
            if(inProjector == 1) projector = true;
            int chairs = allRooms.getInt("chairs");
            int tables = allRooms.getInt("tables");
            eventManager.addRoom(roomNumber, capacity, computers, projector, chairs, tables);
        }
        return eventManager;
    }



}
