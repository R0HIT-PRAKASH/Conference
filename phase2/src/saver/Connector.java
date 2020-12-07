package saver;

import event.EventManager;
import message.MessageManager;
import request.RequestManager;
import user.UserManager;


import java.sql.*;


public class Connector {
    Connection conn;
    Writing writer;
    Reader reader;

    public Connector() throws SQLException{
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/conference",
                    "root", "");
            writer = new Writing(conn);
            reader = new Reader(conn);
        } catch (SQLException e) {
            System.out.println("An error occured while connecting MySQL database");
            e.printStackTrace();
        }
    }

    public boolean determineExisting() throws SQLException {
        PreparedStatement exists = conn.prepareStatement("SELECT * from users");
        ResultSet rs = exists.executeQuery();
        if (rs.next()) return true;
        return false;
    }

    public void end() throws SQLException {
        conn.close();
    }

    public void clearEverything() throws SQLException {
        writer.clearEverything();
    }

    public void saveUserManager(UserManager userManager) throws SQLException{
        writer.saveUserManager(userManager);
    }

    //Clean architecture is violated here. Need to refactor how rooms are stored
    public void saveEventManager(EventManager eventManager) throws SQLException {
        writer.saveEventManager(eventManager);
    }

    //Also violates clean architecture, can't isolate Messages otherwise
    public void saveMessageManager(MessageManager messageManager) throws SQLException{
        writer.saveMessageManager(messageManager);
    }

    public void saveRequestManager(RequestManager requestManager) throws SQLException{
        writer.saveRequestManager(requestManager);

    }

    public UserManager readInUsers() throws SQLException {
        return reader.readInUsers();
    }

    public MessageManager readInMessages() throws SQLException {
        return reader.readInMessages();
    }

    public RequestManager readInRequests() throws SQLException{
        return reader.readInRequests();
    }

    public EventManager readInEvents() throws SQLException{
       return reader.readInEvents();
    }

}
