import java.util.Scanner;

public class LoginController extends MainController{
    private Scanner scan = new Scanner(System.in);

    public String login(){
        System.out.println("Enter Username: ");
        String username = scan.nextLine();
        System.out.println("Enter Password: ");
        String password = scan.nextLine();
        while(password.length() < 3) {
            System.out.println("Error, password must be at least 3 characters.\nPlease enter another:");
            password = scan.nextLine();
        }
        if(!this.userManager.checkCredentials(username)){
            System.out.println("It looks like you are a new user!\nPlease enter some additional information:");
            System.out.println("Enter your name: ");
            String name = scan.nextLine();
            System.out.println("Enter your address: ");
            String address = scan.nextLine();
            System.out.println("Enter your Email: ");
            String email = scan.nextLine();
            System.out.println("Enter your status in the conference. This can be \"organizer\", \"attendee\" or \"speaker\":");
            String type = scan.nextLine();
            while(!type.equals("organizer") && !type.equals("attendee") && !type.equals("speaker")) {
                System.out.println("That was an invalid input.\nPlease try again:");
                type = scan.nextLine();
            }
            this.userManager.addUser(name, address, email, username, password, type);
        }

        while(!this.checkLoginInfo(username, password) && !password.equals("q")) {
            System.out.println("Enter a new password:\nTo quit, press \"q\":");
            password = scan.nextLine();
        }

        return username;
    }

    private boolean checkLoginInfo(String username, String password){
        boolean username_valid = this.userManager.checkCredentials(username);
        boolean password_valid = false;
        if(username_valid){
            password_valid = (this.getUserInfo(username).getPassword().equals(password));
        }
        return password_valid;
    }
    private User getUserInfo(String username){
        return this.userManager.getUser(username);
    }
}
