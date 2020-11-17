import java.util.Scanner;

public class LoginController {
    private Scanner scan = new Scanner(System.in);
    private UserManager userManager;
    private MessageManager messageManager;

    public String login(UserManager userManager, MessageManager messageManager){
        this.userManager = userManager;
        this.messageManager = messageManager;
        System.out.println("Are you a (1)new user or (2)returning user: ");
        int input = Integer.parseInt(scan.nextLine());
        String username = "";
        String password = "";
        while(input != 1 && input != 2){
            System.out.println("Not a valid input, please try again: ");
            input = scan.nextInt();
        }
        switch (input){
            case 1:
                username = createAccount();
                break;
            case 2:
                System.out.println("Enter Username: ");
                username = scan.nextLine();
                while (!this.userManager.checkCredentials(username)){
                    System.out.println("This username doesn't exist, please re-enter or type \"q\" to quit: ");
                    username = scan.nextLine();
                    if (username.equals("q")){
                        break;
                    }
                }
                if (username.equals("q")){
                    break;
                }
                System.out.println("Enter Password: ");
                password = scan.nextLine();
                while(password.length() < 3) {
                    System.out.println("Error, password must be at least 3 characters.\nPlease enter again:");
                    password = scan.nextLine();
                }
                while(!this.checkLoginInfo(username, password) && !password.equals("q")) {
                    System.out.println("Re-enter your password:\nTo quit, press \"q\":");
                    password = scan.nextLine();
                }
                break;
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

    private String createAccount(){
        System.out.println("It looks like you are a new user!\nPlease enter some information:");
        System.out.println("Enter Username: ");
        String username = scan.nextLine();
        while(this.userManager.checkCredentials(username)){
            System.out.println("That username is already taken, please enter another one: ");
            username = scan.nextLine();
        }
        System.out.println("Enter Password: ");
        String password = scan.nextLine();
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
        this.messageManager.addUserInbox(username);
        return username;
    }
}
