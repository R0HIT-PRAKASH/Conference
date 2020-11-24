import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A controller that deals with logging into the program.
 */
public class LoginController {

    private Scanner scan = new Scanner(System.in);
    private UserManager userManager;
    private MessageManager messageManager;
    MainPresenter p;

    /**
     * This constructs a login occurrence
     * @param userManager The instance of the User Manager
     * @param messageManager The instance of the Message Manager
     * @param value Whether or not to start from scratch
     * @return String username (of the user who was able to log in)
     */
    public String login(UserManager userManager, MessageManager messageManager, int value){
        this.userManager = userManager;
        this.messageManager = messageManager;
        p = new MainPresenter();
        String username = "";
        if (value != 0 && value != 1 && value != 2){
            p.displayNewFirstUserMessage();
            username = createAccount();
            return username;
        }
        p.displayNewOrReturningPrompt();
        int input = nextInt();

        String password = "";
        while(input != 1 && input != 2 ){
            input = nextInt();
        }
        switch (input){
            case 1:
                username = createAccount();
                break;
            case 2:
                p.displayEnterUsernamePrompt();
                username = scan.nextLine();
                while (!this.userManager.checkCredentials(username)){
                    p.displayUsernameExistanceError();
                    username = scan.nextLine();
                    if (username.equals("q")){
                        break;
                    }
                }
                if (username.equals("q")){
                    break;
                }
                p.displayEnterPasswordPrompt();
                password = scan.nextLine();
                while(password.length() < 3) {
                    p.displayInvalidPasswordError();
                    password = scan.nextLine();
                }
                while(!this.checkLoginInfo(username, password) && !password.equals("q")) {
                    p.displayRedoPasswordPrompt();
                    password = scan.nextLine();
                    if(password.equals("q")){
                        username = "q";
                    }
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
        p.displayNewUserGreeting();
        p.displayEnterUsernamePrompt();
        String username = scan.nextLine();
        while(this.userManager.checkCredentials(username) || username.length() < 3){
            if (this.userManager.checkCredentials(username)) {
                p.displayUsernameTakenError();
            }
            else if (username.length() < 3) {
                p.displayInvalidUsernameError();
            }
            username = scan.nextLine();
        }
        p.displayEnterPasswordPrompt();
        String password = scan.nextLine();
        while(password.length() < 3){
            p.displayInvalidPasswordError();
            password = scan.nextLine();
        }
        p.displayEnterNamePrompt();
        String name = scan.nextLine();
        while(name.length() < 2){
            p.displayInvalidNameError();
            name = scan.nextLine();
        }
        p.displayEnterAddressPrompt();
        String address = scan.nextLine();
        while(address.length() < 6){
            p.displayInvalidAddressError();
            address = scan.nextLine();
        }
        p.displayEnterEmailPrompt();
        String email = scan.nextLine();
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        while(!email_pattern.matcher(email).matches()){
            p.displayInvalidEmailError();
            email = scan.nextLine();
        }
        p.displayEnterStatusPrompt();
        String type = scan.nextLine();
        while(!type.equalsIgnoreCase("organizer") && !type.equalsIgnoreCase("attendee") &&
                !type.equalsIgnoreCase("speaker")) {
            p.displayInvalidStatusError();
            type = scan.nextLine();
        }
        this.userManager.addUser(name, address, email, username, password, type);
        this.messageManager.addUserInbox(username);
        return username;
    }

    /**
     * Queries the user for an integer
     * @return Returns the integer the user input.
     */
    protected int nextInt() {
        int input = 0;

        do {
            try {
                input = Integer.parseInt(scan.nextLine());
                break;
            } catch (NumberFormatException e) {
                p.displayNewOrReturningError();
            }
        } while(true);

        return input;
    }
}
