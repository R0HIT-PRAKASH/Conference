package main;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is the Presenter Class for the Main and Login Controllers.
 * It handles asking for user input and printing any error messages.
 */
public class MainPresenter extends Presenter{

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
    // -----------------------------------------------------------------------------------------------------------------
}
