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
                viewMessages(this.username);
                break;
            case "View My Events":
                viewScheduledEvents(this.username);
                break;
            case "Message Event Attendees":
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
            break;
        }
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
