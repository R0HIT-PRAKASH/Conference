import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

// Sources:
// 1) https://beginnersbook.com/2013/12/how-to-serialize-hashmap-in-java/
// 2) https://www.java8net.com/2020/03/serialize-hashmap-in-java.html

public class ReaderWriter {

    // This method should be run everytime the program is about to close with userMap, allUserMessages and events
    // Q's:
    // So does this overwrite filenames which already exist and that's ok?
    // Lots of repeated code - I tried to make a helper method but all of sudden it need an exception check as well
    // When we read, how do we assign what we've read to actually be the list of users, etc..
    public <T> void write(HashMap<String, T> hashmap) { // Q: I dont think this should be static - Need confirmation

        List<Object> list = new ArrayList<>(hashmap.values());

        try {
            if (list.get(0) instanceof User) {
                FileOutputStream fos = new FileOutputStream("users.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(hashmap);
                oos.close();
                fos.close();
            } else if (list.get(0) instanceof Message) {
                FileOutputStream fos = new FileOutputStream("messages.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(hashmap);
                oos.close();
                fos.close();
            } else if (list.get(0) instanceof Event) {
                FileOutputStream fos = new FileOutputStream("events.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(hashmap);
                oos.close();
                fos.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } // Do I want it printing what im serializing? Do I need the iterator stuff afterwards?
    }

    //Should be run everytime a User logs in
    // make this into javadoc format
    public void read(String filename) {
        try {
            if (filename.equalsIgnoreCase("users")) {
                FileInputStream fis = new FileInputStream("users.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashMap<String, User> userHashMap = (HashMap<String, User>) ois.readObject(); // hashmap or userHashMap? - usermap needs to be changed to hash map
                // System.out.println(hashmap); // Do we want it printing this? Displaying it?
                ois.close();
                fis.close();
            } else if (filename.equalsIgnoreCase("messages")) {
                FileInputStream fis = new FileInputStream("messages.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashMap<String, Message> allUserMessages = (HashMap<String, Message>) ois.readObject(); // hashmap?
                // System.out.println(hashmap); // Shouldnt be displaying all messages to a single user?
                ois.close();
                fis.close();
            } else if (filename.equalsIgnoreCase("events")) {
                FileInputStream fis = new FileInputStream("events.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashMap<String, Event> events = (HashMap<String, Event>) ois.readObject(); // hashmap?
                // System.out.println(hashmap);
                ois.close();
                fis.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
            cnfe.printStackTrace();
        } catch(ClassCastException cce) {
            System.out.println("Invalid cast attempt");
            cce.printStackTrace();


            // filename may not exist need to account for this
            // Confirm this wouldnt work because we need to speicifcally cast not as object but as User,...
//            FileInputStream fis = new FileInputStream(filename); // will this even work or do I need to see if the filename equals each case then read a specific file
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            HashMap<String, Object> hashmap = (HashMap<String, Object>) ois.readObject(); // we want it to be User, Event, Message
//            System.out.println(hashmap);
//            ois.close();
//            fis.close();
        }
    }
}
