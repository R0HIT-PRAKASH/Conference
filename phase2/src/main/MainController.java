package main;

import event.EventManager;
import message.MessageManager;
import request.RequestManager;
import saver.ReaderWriter;
import user.UserManager;
import user.attendee.AttendeeController;
import user.organizer.OrganizerController;
import user.speaker.SpeakerController;
import user.vip.VipController;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Refers to the controller class that handles all of the other controller classes.
 */
public class MainController {
    protected MessageManager messageManager;
    protected UserManager userManager;
    protected EventManager eventManager;
    protected RequestManager requestManager;
    protected String username;
    protected ReaderWriter RW;
    MainPresenter p;
    boolean startingScratch;

    /**
     * Constructs a MainController object with MessageManager, UserManager, EventManager, ReaderWriter objects,
     * and a String username.
     */
    public MainController() {
        RW = new ReaderWriter();
        messageManager = new MessageManager(RW);
        userManager = new UserManager(RW);
        eventManager = new EventManager(RW);
        username = "";
        p = new MainPresenter();
        requestManager = new RequestManager();
        startingScratch = true;
    }


    /**
     * This method declares three new files for users, messages, and events and returns 0, 1 or 2 based on which files
     * exist.
     * @return Returns 0 if only the users is a file, 1 if users, messages, and events are files, and 2 otherwise.
     */
    public int filesExist() {

        File users = new File("users.ser");
        File messages = new File("messages.ser");
        File events = new File("events.ser");
        File rooms = new File("rooms.ser");
        if (users.isFile() && messages.isFile() && !events.isFile() && !rooms.isFile()) {
            return 0;
        } else if (users.isFile() && messages.isFile() && !events.isFile() && rooms.isFile()) {
            return 1;
        } else if (users.isFile() && messages.isFile() && events.isFile() && rooms.isFile()) {
            return 2;
        } else {
            return 3; // nothing exists
        }
    }

    /**
     * This method is responsible for determining whether or not the program will use pre-existing files. If the user
     * wants to, they can load all of the pre-existing files, except for events and rooms.
     */
    public void fileQUserMssgExists() {

        try {
            String answer = p.displayPreExistingFilePrompt(); // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                answer = p.displayInvalidFileChoice();

            } if (answer.equalsIgnoreCase("Yes")) {
                readInFiles(RW, userManager, messageManager);
                p.displayDownloadCompletion();
                startingScratch = false;
            }
        } catch (InputMismatchException ime) {
            p.displayInvalidInputError();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for determining whether or not the program will use pre-existing files. If the user
     * wants to, they can load all of the pre-existing files, except for events.
     */
    public void fileQUserMssgRoomsExists() {

        try {
            String answer = p.displayPreExistingFilePrompt(); // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                answer = p.displayInvalidFileChoice();
            } if (answer.equalsIgnoreCase("Yes")) {
                readInFiles(RW, userManager, messageManager, eventManager);
                p.displayDownloadCompletion();
                startingScratch = false;
            }
        } catch (InputMismatchException ime) {
            p.displayInvalidInputError();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for determining whether or not the program will use pre-existing files. If the user
     * wants to, they can load all of the pre-existing files.
     */
    public void fileQAllExists() {
        try {
            String answer = p.displayPreExistingFilePrompt(); // This reads the answer they give
            while(!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")) {
                answer = p.displayInvalidFileChoice();
            } if (answer.equalsIgnoreCase("Yes")) {
                readInAllFiles(RW, userManager, messageManager, eventManager);
                p.displayDownloadCompletion();
                startingScratch = false;
            }
        } catch (InputMismatchException ime) {
            p.displayInvalidInputError();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for calling the appropriate controller depending on the user. At the end, it saves
     * all the users, messages, events, and rooms to the appropriate files.
     * @param value Whether or not to start from scratch
     */
    public void run(int value) {
        LoginController log = new LoginController();
        if (startingScratch){
            this.username = log.login(userManager, messageManager, 3);
        }
        else {
            this.username = log.login(userManager, messageManager, value);
        }
        if (username.equals("q")){
            return;
        }
        String type = this.userManager.getUserType(this.username);
        switch (type) {
            case "organizer": {
                OrganizerController controller = new OrganizerController(userManager, eventManager, messageManager, username, requestManager);
                controller.run();
                break;
            }
            case "attendee": {
                AttendeeController controller = new AttendeeController(userManager, eventManager, messageManager, username, requestManager);
                controller.run();
                break;
            }
            case "speaker": {
                SpeakerController controller = new SpeakerController(userManager, eventManager, messageManager, username, requestManager);
                controller.run();
                break;
            }
            case "vip":{
                VipController controller = new VipController(userManager, eventManager, messageManager, username, requestManager);
                controller.run();
                break;
            }
        }

        RW.write(userManager.getUserMap(), "users");
        RW.write(messageManager.getAllUserMessages(), "messages");
        RW.write(eventManager.getAllEvents(), "events");
        RW.writeList(eventManager.getRooms());

        p.displaySignedOut();
    }

    private void readInAllFiles(ReaderWriter RW, UserManager UM, MessageManager MM, EventManager EM) throws IOException, ClassNotFoundException {
//        Object uncastedUsers = RW.readUsers();
//        Object uncastedEvents = RW.readEvents();
//        Object uncastedMessages = RW.readMessages();
//        Object uncastedRooms = RW.readRooms();
        UM.setUserMapReadIn();
        MM.setAllUserMessagesReadIn();
        EM.setAllEventsReadIn();
        EM.setRoomsReadIn();
    }

    private void readInFiles(ReaderWriter RW, UserManager UM, MessageManager MM) throws IOException, ClassNotFoundException {

        UM.setUserMapReadIn();
        MM.setAllUserMessagesReadIn();
    }

    private void readInFiles(ReaderWriter RW, UserManager UM, MessageManager MM, EventManager EM) throws IOException, ClassNotFoundException {
        UM.setUserMapReadIn();
        MM.setAllUserMessagesReadIn();
        EM.setRoomsReadIn();
    }
}
// Give RW to each use case
// change where RW method is called
//move it into the use case instead of controller call it
//controller calls use case which the calls gateway