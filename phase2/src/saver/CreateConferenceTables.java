package saver;

import java.sql.*;

public class CreateConferenceTables {

    /**
     * Sets up the  tables for the database
     * @param args The passed-in command line arguments
     */
    public static void main(String [] args){
        try( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/conference",
                "root", "csc@207uoft");){

            //list of events
            PreparedStatement dropEL = conn.prepareStatement("CREATE TABLE eventlist(eventname varchar(200) PRIMARY" +
                    " KEY, time varchar(50), duration int, roomnumber int, capacity int, requiredComputers int, " +
                    "requiredprojector int, requiredChairs int, requiredTABLES int, vipevent int, tag varchar(100), " +
                    "eventtype varchar(100))");
            int clear = dropEL.executeUpdate();

            //list of messages
            PreparedStatement dropM = conn.prepareStatement("CREATE TABLE messages (id int  PRIMARY KEY AUTO_INCREMENT" +
                    ", content varchar(500), senderUsername varchar(100), recepientUsername varchar(100), beenread int," +
                    " starred int, deleted int, archived int, datetimecreated varchar(30), datetimedeleted varchar(30), " +
                    "datetimecreatedcopy varchar(30))");
            clear += dropM.executeUpdate();

            //maps organizers to events they have created
            PreparedStatement dropOE = conn.prepareStatement("CREATE TABLE organizingevents (organizerusername " +
                    "varchar(100), eventname varchar(200), PRIMARY KEY (organizerusername, eventname))");
            clear += dropOE.executeUpdate();

            //list of requests
            PreparedStatement dropRQ = conn.prepareStatement("CREATE TABLE requests (id int AUTO_INCREMENT, " +
                    "requestusername varchar(100), requeststatus varchar(100), content varchar(200), PRIMARY KEY (id))");
            clear += dropRQ.executeUpdate();

            //the list of rooms
            PreparedStatement dropR = conn.prepareStatement("CREATE TABLE room(roomnumber int, capacity int, " +
                    "computers int, projector int, tables int, chairs int, PRIMARY KEY (roomnumber))");
            clear += dropR.executeUpdate();

            //maps speakers to events they are giving
            PreparedStatement dropSE = conn.prepareStatement("CREATE TABLE speakingevents(speakerusername varchar(100)" +
                    ", eventname varchar(200), PRIMARY KEY (speakerusername, eventname))");
            clear += dropSE.executeUpdate();

            //maps speakers to talks/panels they are giving
            PreparedStatement dropTP = conn.prepareStatement("CREATE TABLE talkpanel(eventname varchar(100), " +
                    "speakerusername varchar(100), PRIMARY KEY (eventname))");
            clear += dropTP.executeUpdate();

            //maps users to events they are attending
            PreparedStatement dropUE = conn.prepareStatement("CREATE TABLE userevent(username varchar(100), " +
                    "eventname varchar(200), PRIMARY KEY (username, eventname))");
            clear += dropUE.executeUpdate();

            // list of users
            PreparedStatement dropU = conn.prepareStatement("CREATE TABLE users(username varchar(100), " +
                    "userType varchar(20), password varchar(100), address varchar(100), email varchar(150), " +
                    "company varchar(100), bio varchar(300), name varchar(100), PRIMARY KEY (`username`))");
            clear += dropU.executeUpdate();

        }


        catch(SQLException e){
            System.out.println("An error occurred while connecting MySQL database");
            e.printStackTrace();
        }
    }
}
