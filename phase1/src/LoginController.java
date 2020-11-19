import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A controller that deals with logging into the program.
 */
public class LoginController {

    private Scanner scan = new Scanner(System.in);
    private UserManager userManager;
    private MessageManager messageManager;

    /**
     * This constructs a login occurrence
     * @param userManager the instance of the User Manager
     * @param messageManager the instance of the Message Manager
     * @return String username (of the user who was able to log in)
     */
    public String login(UserManager userManager, MessageManager messageManager){
        this.userManager = userManager;
        this.messageManager = messageManager;
        System.out.println("Are you a (1)new user or (2)returning user: ");
        int input = Integer.parseInt(scan.nextLine());
        String username = "";
        String password = "";
        while(input != 1 && input != 2 ){
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
                    if(password.equals("q")){
                        username = "q";
                    }
                }
                break;
        }
        return username;
    }

    //returns if the password matches the password of the User who has given username
    private boolean checkLoginInfo(String username, String password){
        boolean username_valid = this.userManager.checkCredentials(username);
        boolean password_valid = false;
        if(username_valid){
            password_valid = (this.getUserInfo(username).getPassword().equals(password));
        }
        return password_valid;
    }
    //returns the User who has the given username
    private User getUserInfo(String username){
        return this.userManager.getUser(username);
    }

    //creates an Account for a new User, and returns their username
    private String createAccount(){
        System.out.println("It looks like you are a new user!\nPlease enter some information:");
        System.out.println("Enter Username: ");
        String username = scan.nextLine();
        while(this.userManager.checkCredentials(username) || username.length() < 3){
            if (this.userManager.checkCredentials(username)) {
                System.out.println("That username is already taken, please enter another one: ");
            }
            else if (username.length() < 3) {
                System.out.println("Error, username must be at least 3 characters. please enter another one: ");
            }
            username = scan.nextLine();
        }
        System.out.println("Enter Password: ");
        String password = scan.nextLine();
        while(password.length() < 3){
            System.out.println("Error, password must be at least 3 characters.\nPlease enter again:");
            password = scan.nextLine();
        }
        System.out.println("Enter your name: ");
        String name = scan.nextLine();
        while(name.length() < 2){
            System.out.println("Error, name must be at least 2 characters.\nPlease enter again:");
            name = scan.nextLine();
        }
        System.out.println("Enter your address: ");
        String address = scan.nextLine();
        while(address.length() < 3){
            System.out.println("Error, address must be at least 6 characters.\nPlease enter again:");
            address = scan.nextLine();
        }
        System.out.println("Enter your Email: ");
        String email = scan.nextLine();
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        while(!email_pattern.matcher(email).matches()){
            System.out.println("The email is not up to RFC 5322 standards. Try another");
            email = scan.nextLine();
        }
        System.out.println("Enter your status in the conference. This can be \"organizer\", \"attendee\" or \"speaker\":");
        String type = scan.nextLine();
        while(!type.equalsIgnoreCase("organizer") && !type.equalsIgnoreCase("attendee") &&
                !type.equalsIgnoreCase("speaker")) {
            System.out.println("That was an invalid input.\nPlease try again:");
            type = scan.nextLine();
        }
        this.userManager.addUser(name, address, email, username, password, type);
        this.messageManager.addUserInbox(username);
        return username;
    }
}
