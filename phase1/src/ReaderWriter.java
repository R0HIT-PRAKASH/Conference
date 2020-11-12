import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

// Sources:
// 1) https://beginnersbook.com/2013/12/how-to-serialize-hashmap-in-java/
// 2) https://www.java8net.com/2020/03/serialize-hashmap-in-java.html

public class ReaderWriter {
    // Lots of repeated code - I tried to make a helper method but all of sudden it need an exception check as well


    public <T> void writeHelper(FileOutputStream fos, HashMap<String, T> hashmap) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hashmap);
        oos.close();
        fos.close();
    }

    /**
     * @param hashmap
     * @param <T>
     */
    public <T> void write(HashMap<String, T> hashmap) { // Q: I dont think this should be static - Need confirmation

        List<Object> list = new ArrayList<>(hashmap.values());
        if (list.isEmpty()) return;
        try {
            if (list.get(0) instanceof User) {
                FileOutputStream fos = new FileOutputStream("users.ser");
                writeHelper(fos, hashmap);
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//                oos.writeObject(hashmap);
//                oos.close();
//                fos.close();
            } else if (list.get(0) instanceof Message) {
                FileOutputStream fos = new FileOutputStream("messages.ser");
                writeHelper(fos, hashmap);
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//                oos.writeObject(hashmap);
//                oos.close();
//                fos.close();
            } else if (list.get(0) instanceof Event) {
                FileOutputStream fos = new FileOutputStream("events.ser");
                writeHelper(fos, hashmap);
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//                oos.writeObject(hashmap);
//                oos.close();
//                fos.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
// in helper method - throws IOException in the header


    public HashMap<String, User> readUsers(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("users.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, User> userHashMap = (HashMap<String, User>) ois.readObject();
        ois.close();
        fis.close();
        return userHashMap;
    }
    public HashMap<String, List<Message>> readMessages(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("messages.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, List<Message>> allUserMessages = (HashMap<String, List<Message>>) ois.readObject();
        ois.close();
        fis.close();
        return allUserMessages;
    }
    public HashMap<String, Event> readEvents(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("events.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, Event> events = (HashMap<String, Event>) ois.readObject();
        ois.close();
        fis.close();
        return events;
}

//    public void read(String filename) {
//        try {
//            if (filename.equalsIgnoreCase("users")) {
//                FileInputStream fis = new FileInputStream("users.ser");
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                HashMap<String, User> userHashMap = (HashMap<String, User>) ois.readObject();
//                ois.close();
//                fis.close();
//            } else if (filename.equalsIgnoreCase("messages")) {
//                FileInputStream fis = new FileInputStream("messages.ser");
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                HashMap<String, Message> allUserMessages = (HashMap<String, Message>) ois.readObject();
//                ois.close();
//                fis.close();
//            } else if (filename.equalsIgnoreCase("events")) {
//                FileInputStream fis = new FileInputStream("events.ser");
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                HashMap<String, Event> events = (HashMap<String, Event>) ois.readObject();
//                ois.close();
//                fis.close();
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        } catch (ClassNotFoundException cnfe) {
//            System.out.println("Class not found");
//            cnfe.printStackTrace();
//        } catch(ClassCastException cce) {
//            System.out.println("Invalid cast attempt");
//            cce.printStackTrace();
//        }
//    }
}
