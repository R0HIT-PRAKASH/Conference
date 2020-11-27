package main;

import message.MessageManager;
import user.User;
import user.UserManager;

import java.util.regex.Pattern;

/**
 * A controller that deals with logging into the program.
 */
public class LoginController {

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
        int input = p.nextInt();

        String password;
        while(input != 1 && input != 2 ){
            input = p.nextInt();
        }
        switch (input){
            case 1:
                username = createAccount();
                break;
            case 2:
                username = p.displayEnterUsernamePrompt();

                while (!this.userManager.checkCredentials(username)){
                    username = p.displayUsernameExistenceError();
                    if (username.equals("q")){
                        break;
                    }
                }
                if (username.equals("q")){
                    break;
                }
                password = p.displayEnterPasswordPrompt();

                while(password.length() < 3) {
                    password = p.displayInvalidPasswordError();
                }
                while(!this.checkLoginInfo(username, password) && !password.equals("q")) {
                    password = p.displayRedoPasswordPrompt();
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
        String username = p.displayEnterUsernamePrompt();

        while(this.userManager.checkCredentials(username) || username.length() < 3){
            if (this.userManager.checkCredentials(username)) {
                username = p.displayUsernameTakenError();
            }
            else if (username.length() < 3) {
                username = p.displayInvalidUsernameError();
            }

        }

        String password = p.displayEnterPasswordPrompt();

        while(password.length() < 3){
            password = p.displayInvalidPasswordError();

        }
        String name = p.displayEnterNamePrompt();

        while(name.length() < 2){
            name = p.displayInvalidNameError();
        }
        String address = p.displayEnterAddressPrompt();

        while(address.length() < 6){
            address = p.displayInvalidAddressError();
        }
        String email = p.displayEnterEmailPrompt();

        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        while(!email_pattern.matcher(email).matches()){
            email = p.displayInvalidEmailError();
        }
        //Modify prompt to allow for VIP
        String type = p.displayEnterStatusPrompt();

        while(!type.equalsIgnoreCase("organizer") && !type.equalsIgnoreCase("attendee") &&
                !type.equalsIgnoreCase("speaker") && !type.equalsIgnoreCase("vip")) {
            type = p.displayInvalidStatusError();

        }
        this.userManager.addUser(name, address, email, username, password, type);
        this.messageManager.addUserInbox(username);
        return username;
    }

}
