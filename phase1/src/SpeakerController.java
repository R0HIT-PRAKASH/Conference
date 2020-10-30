import java.util.Scanner;

public class SpeakerController extends MainController{
    private Scanner scan = new Scanner(System.in);

    public void run(){
        String input = "";
        input = scan.nextLine();
        input = input.toUpperCase();
        while (!input.equals("END")){

            if(input.equals("VIEW INBOX")){

            }
            else if(input.equals("SEE MY TALKS")){

            }
            else if(input.equals("MESSAGE EVENT ATTENDEES")){

            }
            input = scan.nextLine();
            input = input.toUpperCase();
        }
    }
}
