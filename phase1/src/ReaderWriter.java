import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

// Sources:
// 1) https://beginnersbook.com/2013/12/how-to-serialize-hashmap-in-java/
// 2) https://www.java8net.com/2020/03/serialize-hashmap-in-java.html

/**
 * This class is used to read and write .ser files.
 */

public class ReaderWriter {

    private <T> void writeHelper(FileOutputStream fos, HashMap<String, T> hashmap) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hashmap);
        oos.close();
        fos.close();
    }

    /**
     * Writes a HashMap object to a specific .ser file depending on the type
     * of the HashMap object's values
     * @param hashmap the HashMap object we want to save
     * @param <T> the type of the values in the HashMap object
     */
    public <T> void write(HashMap<String, T> hashmap) {
        List<Object> list = new ArrayList<>(hashmap.values());
        if (list.isEmpty()) return;
        try {
            if (list.get(0) instanceof User) {
                FileOutputStream fos = new FileOutputStream("users.ser");
                writeHelper(fos, hashmap);
            } else if (list.get(0) instanceof ArrayList) {
                FileOutputStream fos = new FileOutputStream("messages.ser");
                writeHelper(fos, hashmap);
            } else if (list.get(0) instanceof Event) {
                FileOutputStream fos = new FileOutputStream("events.ser");
                writeHelper(fos, hashmap);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Writes the rooms.ser file
     * @param rooms The list of Rooms to write into the file
     */
    public void writeRoom(List<Room> rooms) {
        if (rooms.isEmpty()) return;
        try {
            FileOutputStream fos = new FileOutputStream("rooms.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(rooms);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Reads the users.ser file
     * @param filename name of the file we want to read (excluding .ser part)
     * @return returns the deserialized HashMap object containing usernames as keys and the corresponding
     * Users as values
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from users.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find users.
     */
    public HashMap<String, User> readUsers(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("users.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, User> userHashMap = (HashMap<String, User>) ois.readObject();
        ois.close();
        fis.close();
        return userHashMap;
    }

    /**
     * Reads the messages.ser file
     * @param filename name of the file we want to read (excluding .ser part)
     * @return returns the deserialized HashMap object containing usernames as keys and the corresponding
     * user's messages received as values.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from messages.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find messages.
     */
    public HashMap<String, List<Message>> readMessages(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("messages.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, List<Message>> allUserMessages = (HashMap<String, List<Message>>) ois.readObject();
        ois.close();
        fis.close();
        return allUserMessages;
    }

    /**
     * Reads the events.ser file
     * @param filename name of the file we want to read (excluding .ser part)
     * @return returns the deserialized HashMap object containing event names as keys and the corresponding
     * Events as values
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from events.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find events.
     */
    public HashMap<String, Event> readEvents(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("events.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, Event> events = (HashMap<String, Event>) ois.readObject();
        ois.close();
        fis.close();
        return events;

        // I think I can make all these readhashmaps into one method
    }

    /**
     * Reads the rooms.ser file
     * @param filename the name of the file we want to read
     * @return returns the deserialized ArrayList object containing the rooms
     * @throws IOException Refers to the exception that is raised when the program can't get input or outputs from rooms.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find rooms.
     */
    public ArrayList<Room> readRooms(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("rooms.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Room> rooms = (ArrayList<Room>) ois.readObject();
        ois.close();
        fis.close();
        return rooms;
    }
}
