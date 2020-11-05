import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SpeakerController extends MainController{
    private Scanner scan = new Scanner(System.in);

    public void run(){
        String input = "";
        input = scan.nextLine().toUpperCase();
        while (!input.equals("END")){
            determineInput(input);
            input = scan.nextLine().toUpperCase();
        }
    }

    private void determineInput(String input){
        switch (input) {
            case "See Inbox":
                viewMessages(this.user.username);
                break;
            case "View My Events":
                viewScheduledEvents(this.user.username);
                break;
            case "Message Event Attendees":
                System.out.println("Would you like to message the attendees of one event or more?");
                System.out.print("1 or 1+: ");
                String number = scan.nextLine();
                if (number.equals("1")) {
                    System.out.println("Please enter the name of the event: ");
                    String eventName = scan.nextLine();
                    System.out.println("Please enter the message: ");
                    String message = scan.nextLine();
                    sendBlastMessage(eventName, message);
                } else if (number.equals("1+")) {
                    System.out.println("Please enter the number of events: ");
                    String strnum = scan.nextLine();
                    int num = Integer.parseInt(strnum);
                    String[] events = new String[num];
                    for (int i = 0; i < num; i++) {
                        if (i == 0) {
                            System.out.println("Please enter the name of the first event: ");
                        } else {
                            System.out.println("Please enter the name of the next event: ");
                        }
                        events[i] = scan.nextLine();

                    }
                    System.out.println("Please enter the message: ");
                    String message = scan.nextLine();
                    sendBlastMessage(events, message);

                }
                break;
        }

    }

    public void viewMessages(String username) {
        String allMessages = messageManager.viewMessages(username);
        System.out.println(allMessages);
    }
    public void viewScheduledEvents(String username){
        String allEvents = userManager.getSpeakingEvents(username);
        System.out.println(allEvents);
    }

    public void sendBlastMessage(String eventName, String message){
        Event thisOne = eventManager.getEvent(eventName);
        List<Attendee> attendees = thisOne.getAttendeeList();
        for(int i = 0; i < attendees.size(); i++){
            boolean toBeSent = messageManager.createNewMessage(message, attendees.get(i).);

        }
    }

    public void sendBlastMessage(String[] eventNames, String message){
        Event [] thisOne = new Event[eventNames.length];
        for (int i = 0; i < thisOne.length; i++){
            thisOne[i] = eventManager.getEvent(eventNames[i]);
        }
        for (int i = 0; i < thisOne.length; i++){
            List<Attendee> attendees = thisOne[i].getAttendeeList();
            for(int i = 0; i < attendees.size(); i++) {
                boolean toBeSent = messageManager.createNewMessage(message, attendees.get(i).);
            }
        }
    }
}
