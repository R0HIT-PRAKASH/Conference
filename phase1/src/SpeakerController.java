import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpeakerController extends MainController{
    private Scanner scan = new Scanner(System.in);

    public void run(){
        System.out.println("What would you like to do?");
        System.out.println("See Inbox, View My Events, Message Event Attendees, Reply to Attendee, End");
        String input = "";
        input = scan.nextLine().toLowerCase();
        while (!input.equals("end")){
            determineInput(input);
            input = scan.nextLine().toLowerCase();
        }
    }

    private void determineInput(String input){
        switch (input) {
            case "see inbox":
                viewMessages(this.username);
                break;
            case "view my events":
                viewScheduledEvents(this.username);
                break;
            case "message event attendees":
                System.out.println("How many event's attendees would you like to message: ");
                String number = scan.nextLine();
                System.out.println("Please enter the number of events: ");
                String strnum = scan.nextLine();
                int num = Integer.parseInt(strnum);
                List<String> eventNames = new ArrayList<>();
                for (int i = 0; i < num; i++) {
                    if (i == 0) {
                        System.out.println("Please enter the name of the first event: ");
                        }
                    else {
                        System.out.println("Please enter the name of the next event: ");
                        }
                    String next = scan.nextLine();
                    eventNames.add(next);
                    }
                    System.out.println("Please enter the message: ");
                String message = scan.nextLine();
                sendBlastMessage(eventNames, message);
            case "reply to attendee":
                System.out.println("Which attendee are you replying to: ");
                List<String> attendees = getAttendees(username);
                for (String attendee: attendees){
                    System.out.println(attendee);
                }
                String recipient = scan.nextLine();

            break;
        }
    }

    private List<String> getAttendees(String username){
        List<Message> allMessages = messageManager.viewMessages(username);
        List<String> attendees = new ArrayList<>();
        for (Message message: allMessages){
            attendees.add(messageManager.getRecipient(message));
        }
        return attendees;
    }

    public void viewMessages(String username) {
        messageManager.printMessages(username);

    }
    public void viewScheduledEvents(String username){
        List<String> allEvents = userManager.getSpeakingEvents(username);
        System.out.println(allEvents);
    }

    public void sendBlastMessage(List<String> eventNames, String message){
        messageManager.speakerBlastMessage(eventNames, message, eventManager, this.username);
    }
}
