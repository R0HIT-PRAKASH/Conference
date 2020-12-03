package saver;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Writing {

    Connection conn;
    public Writing(Connection conn){
        this.conn = conn;
    }


    public void insertStudent(String name, double GPA, LocalDateTime start) throws SQLException{
        PreparedStatement statement = conn.prepareStatement("insert into testers (name, gpa, start) values (?, ?, ?)");
        int insertedRows = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = start.format(formatter);
        statement.setString(1, name);
        statement.setDouble(2, GPA);
        statement.setString(3, date);
        insertedRows += statement.executeUpdate();
        System.out.println("I just inserted " + insertedRows + " users");
    }
}
