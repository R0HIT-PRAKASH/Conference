/**
 * This class is the Presenter Class for the Main and Login Controllers.
 * It handles asking for user input and printing any error messages.
 */
public class MainPresenter {

    public MainPresenter() {}

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
        System.out.println("Not a valid input, please try again: ");
    }

    /**
     * Prompts a user for their username.
     */
    public void displayEnterUsernamePrompt(){
        System.out.print("Enter Username: ");
    }

    /**
     * Prints an error message when the entered username does not exist.
     */
    public void displayUsernameExistanceError(){
        System.out.print("This username doesn't exist, please re-enter or type \"q\" to quit: ");
    }

    /**
     * Prompts a user for their password.
     */
    public void displayEnterPasswordPrompt(){
        System.out.print("Enter Password: ");
    }

    /**
     * Prints an error message that the entered password must be at least 3 characters, asks user to enter another password.
     */
    public void displayInvalidPasswordError(){
        System.out.print("Error, password must be at least 3 characters.\nPlease enter again: ");
    }

    /**
     * Prompts user to re-enter password.
     */
    public void displayRedoPasswordPrompt(){
        System.out.print("Incorrect. Re-enter your password (to quit, press \"q\"): ");
    }

    /**
     * Greets a new user and asks user to enter information and sign up.
     */
    public void displayNewUserGreeting(){
        System.out.println("It looks like you are a new user. Please enter some information");
    }

    /**
     * Prints to user that the entered username is already taken, and asks user to enter another username.
     */
    public void displayUsernameTakenError(){
        System.out.print("That username is already taken, please enter another one: ");
    }

    /**
     * Prints an error message that the entered username must be at least 3 characters, asks user to enter another username.
     */
    public void displayInvalidUsernameError(){
        System.out.print("Error, username must be at least 3 characters. please enter another one: ");
    }

    /**
     * Prompts user to enter their name.
     */
    public void displayEnterNamePrompt(){
        System.out.print("Enter your name: ");
    }

    /**
     * Prints an error message that the name must be at least 2 characters.
     */
    public void displayInvalidNameError(){
        System.out.print("Error, name must be at least 2 characters.\nPlease enter again: ");
    }

    /**
     * Prompts the user to enter their address.
     */
    public void displayEnterAddressPrompt(){
        System.out.print("Enter your address: ");
    }

    /**
     * Prints an error message that the address must be at least 6 characters.
     */
    public void displayInvalidAddressError(){
        System.out.print("Error, address must be at least 6 characters.\nPlease enter again: ");
    }

    /**
     * Prompts the user to enter their email.
     */
    public void displayEnterEmailPrompt(){
        System.out.print("Enter your Email: ");
    }

    /**
     * Prints an error message that the entered email is not up to RFC 5322 standards, asks user to enter another email.
     */
    public void displayInvalidEmailError(){
        System.out.print("The email is not up to RFC 5322 standards. Try another: ");
    }

    /**
     * Prompts the user to specify their status in the conference: organizer, attendee, speaker.
     */
    public void displayEnterStatusPrompt(){
        System.out.print("Enter your status in the conference. This can be \"organizer\", \"attendee\" or \"speaker\": ");
    }

    /**
     * Prints an error message that the entered status is an invalid input.
     */
    public void displayInvalidStatusError(){
        System.out.print("That was an invalid input.\nPlease try again: ");
    }

    // -----------------------------------------------------------------------------------------------------------------


    // Methods for the Main Controller --------------------------------------------------------------------------------

    /**
     * Asks program user if they want to use pre-existing files. Asks user to type 'Yes' or 'No'.
     */
    public void displayPreExistingFilePrompt(){
        System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
    }

    /**
     * Prints an error message that the prompt for using pre-existing files is invalid. Asks user to type 'Yes' or 'No'.
     */
    public void displayInvalidFileChoice(){
        System.out.println("Invalid Input: Please type 'Yes' or 'No'");
    }

    /**
     * Notifies the user that the files are downloaded.
     */
    public void displayDownloadCompletion(){
        System.out.println("Files downloaded.");
    }

    /**
     * Prints an error message that the user did not enter 'Yes' or 'No'
     */
    public void displayInvalidInputError(){
        System.out.println("Error: Please type 'Yes' or 'No'");
    }

    /**
     * Prints an  message when a User signs out
     */
    public void displaySignedOut(){System.out.println("You have signed out successfully. ");}


    // -----------------------------------------------------------------------------------------------------------------
}
