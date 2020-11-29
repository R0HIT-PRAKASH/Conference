package main;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is the Presenter Class for the Main and Login Controllers.
 * It handles asking for user input and printing any error messages.
 */
public class MainPresenter {

    final Scanner scan;
    final Pattern email_pattern;
    public MainPresenter() {

        scan = new Scanner(System.in);
        email_pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    // Methods for the Login Controller --------------------------------------------------------------------------------

    /**
     * Prompts user to specify if they are a new or returning user.
     */
    public void displayNewOrReturningPrompt(){
        System.out.println("Are you a (1)new user or (2)returning user: ");
    }

    /**
     * Prints a message welcoming the first user to the program.
     */
    public void displayNewFirstUserMessage(){
        System.out.println("Welcome to the conference! ");
    }

    /**
     * Prints an error message when the user does not successfully specify if they are a new or returning user.
     */
    public void displayNewOrReturningError(){
        System.out.println("The input should be a nonnegative integer, please try again: ");
    }

    /**
     * Prompts a user for their username.
     * @return The username
     */
    public String displayEnterUsernamePrompt(){

        System.out.print("Enter Username: ");
        String username = scan.nextLine();

        while (username.length() < 3 || containsWhitespace(username)) {
            username = displayInvalidUsernameError();
        }
        return username;
    }

    /**
     * Prints an error message when the entered username does not exist.
     * @return The username
     */
    public String displayUsernameExistenceError(){
        System.out.print("This username doesn't exist, please re-enter or type \"q\" to quit: ");
        return scan.nextLine();
    }

    /**
     * Prompts a user for their password.
     * @return The password
     */
    public String displayEnterPasswordPrompt(){

        System.out.print("Enter Password: ");
        String password = scan.nextLine();

        while (password.length() < 3 || containsWhitespace(password)) {
            password = displayInvalidPasswordError();
        }
        return password;
    }

    /**
     * Prints an error message that the entered password must be at least 3 characters, asks user to enter another password.
     * @return The password
     */
    public String displayInvalidPasswordError(){
        System.out.print("Error, password must be at least 3 characters and cannot contain whitespace.\nPlease enter again: ");
        return scan.nextLine();
    }

    /**
     * Prompts user to re-enter password.
     * @return The password
     */
    public String displayRedoPasswordPrompt(){
        System.out.print("Incorrect. Re-enter your password (to quit, press \"q\"): ");
        return scan.nextLine();
    }

    /**
     * Greets a new user and asks user to enter information and sign up.
     */
    public void displayNewUserGreeting(){
        System.out.println("It looks like you are a new user. Please enter some information");
    }

    /**
     * Prints to user that the entered username is already taken, and asks user to enter another username.
     * @return The username
     */
    public String displayUsernameTakenError(){
        System.out.print("That username is already taken, please enter another one: ");
        return scan.nextLine();
    }

    /**
     * Prints an error message that the entered username must be at least 3 characters, asks user to enter another username.
     * @return The username
     */
    public String displayInvalidUsernameError(){
        System.out.print("Error, username must be at least 3 characters and cannot contain whitespace. Please enter another one: ");
        return scan.nextLine();
    }

    /**
     * Prompts user to enter their name.
     * @return The name
     */
    public String displayEnterNamePrompt(){

        System.out.print("Enter Your Name: ");
        String name = scan.nextLine();

        while (name.length() < 3 || name.trim().length() != name.length()) {
            name = displayInvalidNameError();
        }
        return name;
    }

    /**
     * Prints an error message that the name must be at least 2 characters.
     * @return The name
     */
    public String displayInvalidNameError(){
        System.out.print("Error, name must be at least 3 characters and cannot start or end with whitespace.\nPlease enter again: ");
        return scan.nextLine();
    }

    /**
     * Prompts the user to enter their address.
     * @return The address
     */
    public String displayEnterAddressPrompt(){

        System.out.print("Enter Your Address: ");
        String address = scan.nextLine();

        while (address.length() < 6 || address.trim().length() != address.length()) {
            address = displayInvalidAddressError();
        }
        return address;
    }

    /**
     * Prints an error message that the address must be at least 6 characters.
     * @return The address
     */
    public String displayInvalidAddressError(){
        System.out.print("Error, address must be at least 6 characters and cannot start or end with whitespace.\nPlease enter again: ");
        return scan.nextLine();
    }

    /**
     * Prompts the user to enter their email.
     * @return The email
     */
    public String displayEnterEmailPrompt(){

        System.out.print("Enter Your Email: ");
        String email = scan.nextLine();

        while (!email_pattern.matcher(email).matches()){
            email = displayInvalidEmailError();
        }
        return email;
    }

    /**
     * Prints an error message that the entered email is not up to RFC 5322 standards, asks user to enter another email.
     * @return The email
     */
    public String displayInvalidEmailError(){
        System.out.print("The email is not up to RFC 5322 standards. Try another: ");
        return scan.nextLine();
    }

    /**
     * Prompts the user to specify their status in the conference: organizer, attendee, speaker.
     * @return The type
     */
    public String displayEnterStatusPrompt(){

        System.out.print("Enter your status in the conference. This can be \"organizer\", \"attendee\", \"VIP\" or \"speaker\": ");
        String type = scan.nextLine();

        while(!type.equalsIgnoreCase("organizer") && !type.equalsIgnoreCase("attendee") &&
                !type.equalsIgnoreCase("speaker") && !type.equalsIgnoreCase("vip")) {
            type = displayInvalidStatusError();

        }

        return type;
    }

    /**
     * Prints an error message that the entered status is an invalid input.
     * @return The status
     */
    public String displayInvalidStatusError(){
        System.out.print("That was an invalid status.\nPlease try again: ");
        return scan.nextLine();
    }

    // -----------------------------------------------------------------------------------------------------------------


    // Methods for the Main Controller --------------------------------------------------------------------------------

    /**
     * Asks program user if they want to use pre-existing files. Asks user to type 'Yes' or 'No'.
     * @return Yes/No
     */
    public String displayPreExistingFilePrompt(){
        System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
        return scan.nextLine();
    }

    /**
     * Prints an error message that the prompt for using pre-existing files is invalid. Asks user to type 'Yes' or 'No'.
     * @return Yes/No
     */
    public String displayInvalidFileChoice(){
        System.out.println("Invalid Input: Please type 'Yes' or 'No'");
        return scan.nextLine();
    }

    /**
     * Notifies the user that the files are downloaded.
     */
    public void displayDownloadCompletion(){ System.out.println("Files downloaded."); }

    /**
     * Prints an error message that the user did not enter 'Yes' or 'No'
     * @return Yes/No
     */
    public String displayInvalidInputError(){
        System.out.println("Error: Please type 'Yes' or 'No'");
        return scan.nextLine();
    }

    /**
     * Prints an  message when a User signs out
     */
    public void displaySignedOut(){System.out.println("You have signed out successfully. ");}

    /**
     * Queries the user for an integer
     * @return Returns the integer the user input.
     */
    protected int nextPositiveInt() {
        int input = 0;

        do {
            try {
                input = Integer.parseInt(scan.nextLine());
                if(input >= 0)
                    break;
            } catch (NumberFormatException e) {
                displayNewOrReturningError();
            }
        } while(true);

        return input;
    }

    private boolean containsWhitespace(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // -----------------------------------------------------------------------------------------------------------------
}
