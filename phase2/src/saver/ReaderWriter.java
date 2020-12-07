package saver;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
     * @param file Refers to the name of the file.
     */
    public <T> void write(HashMap<String, T> hashmap, String file) {
        List<Object> list = new ArrayList<>(hashmap.values());
        if (list.isEmpty()) return;
        try {
            if (file.equalsIgnoreCase("users")) {
                FileOutputStream fos = new FileOutputStream("users.ser");
                writeHelper(fos, hashmap);
            } else if (file.equalsIgnoreCase("events")) {
                FileOutputStream fos = new FileOutputStream("events.ser");
                writeHelper(fos, hashmap);
            } else if (file.equalsIgnoreCase("messages")) {
                FileOutputStream fos = new FileOutputStream("messages.ser");
                writeHelper(fos, hashmap);
            } else if (file.equalsIgnoreCase("requests")) {
                FileOutputStream fos = new FileOutputStream("requests.ser");
                writeHelper(fos, hashmap);
            } else if (file.equalsIgnoreCase("requestStatuses")) {
                FileOutputStream fos = new FileOutputStream("requestStatuses.ser");
                writeHelper(fos, hashmap);
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the serialized Object from the users.ser file.
     * @return returns the Object read from the users.ser file.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from users.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find users.
     */

    public Object readUsers() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("users.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object tempObj = ois.readObject();
        ois.close();
        fis.close();
        return tempObj;
        // cast this in the main controller
        // cast occurs in use case layer is ideal - create a method in the corresponding manager
    }

    /**
     * Reads the serialized Object from the events.ser file.
     * @return returns the Object read from the events.ser file.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from events.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find events.
     */

    public Object readEvents() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("events.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object tempObj = ois.readObject();
        ois.close();
        fis.close();
        return tempObj;
        // cast this in the main controller
        // cast occurs in use case layer is ideal - create a method in the corresponding manager
    }

    /**
     * Reads the serialized Object from the messages.ser file.
     * @return returns the Object read from the messages.ser file.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from messages.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find messages.
     */

    public Object readMessages() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("messages.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object tempObj = ois.readObject();
        ois.close();
        fis.close();
        return tempObj;
        // cast this in the main controller
        // cast occurs in use case layer is ideal - create a method in the corresponding manager
    }

     /**
     * Reads the serialized Object from the rooms.ser file.
     * @return returns the Object read from the rooms.ser file.
     * @throws IOException Refers to the exception that is raised when the program can't get input or outputs from rooms.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find rooms.
     */
    public Object readRooms() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("rooms.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object tempObj = ois.readObject();
        ois.close();
        fis.close();
        return tempObj;
        // cast this in the main controller
        // cast occurs in use case layer is ideal - create a method in the corresponding manager
    }

    /**
     * Reads the serialized Object from the requests.ser file.
     * @return returns the Object read from the requests.ser file.
     * @throws IOException Refers to the exception that is raised when the program can't get input or outputs from rooms.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find rooms.
     */
    public Object readRequests() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("requests.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object tempObj = ois.readObject();
        ois.close();
        fis.close();
        return tempObj;
        // cast this in the main controller
        // cast occurs in use case layer is ideal - create a method in the corresponding manager
    }

    /**
     * Reads the serialized Object from the requestStatuses.ser file.
     * @return returns the Object read from the requestStatuses.ser file.
     * @throws IOException Refers to the exception that is raised when the program can't get input or outputs from rooms.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find rooms.
     */
    public Object readRequestStatuses() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("requestStatuses.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object tempObj = ois.readObject();
        ois.close();
        fis.close();
        return tempObj;
        // cast this in the main controller
        // cast occurs in use case layer is ideal - create a method in the corresponding manager
    }
}
