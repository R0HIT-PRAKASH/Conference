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
     * Prints an error message when the user does not successfully specify if they are a new or returning user.
     */
    public void displayNewOrReturningError(){
        System.out.println("Not a valid input, please try again: ");
    }

    /**
     * Prompts a user for their username.
     */
    public void displayEnterUsernamePrompt(){
        System.out.println("Enter Username: ");
    }

    /**
     * Prints an error message when the entered username does not exist.
     */
    public void displayUsernameExistanceError(){
        System.out.println("This username doesn't exist, please re-enter or type \"q\" to quit: ");
    }

    /**
     * Prompts a user for their password.
     */
    public void displayEnterPasswordPrompt(){
        System.out.println("Enter Password: ");
    }

    /**
     * Prints an error message that the entered password must be at least 3 characters, asks user to enter another password.
     */
    public void displayInvalidPasswordError(){
        System.out.println("Error, password must be at least 3 characters.\nPlease enter again:");
    }

    /**
     * Prompts user to re-enter password.
     */
    public void displayRedoPasswordPrompt(){
        System.out.println("Re-enter your password:\nTo quit, press \"q\":");
    }

    /**
     * Greets a new user and asks user to enter information and sign up.
     */
    public void displayNewUserGreeting(){
        System.out.println("It looks like you are a new user!\nPlease enter some information:");
    }

    /**
     * Prints to user that the entered username is already taken, and asks user to enter another username.
     */
    public void displayUsernameTakenError(){
        System.out.println("That username is already taken, please enter another one: ");
    }

    /**
     * Prints an error message that the entered username must be at least 3 characters, asks user to enter another username.
     */
    public void displayInvalidUsernameError(){
        System.out.println("Error, username must be at least 3 characters. please enter another one: ");
    }

    /**
     * Prompts user to enter their name.
     */
    public void displayEnterNamePrompt(){
        System.out.println("Enter your name: ");
    }

    /**
     * Prints an error message that the name must be at least 2 characters.
     */
    public void displayInvalidNameError(){
        System.out.println("Error, name must be at least 2 characters.\nPlease enter again:");
    }

    /**
     * Prompts the user to enter their address.
     */
    public void displayEnterAddressPrompt(){
        System.out.println("Enter your address: ");
    }

    /**
     * Prints an error message that the address must be at least 6 characters.
     */
    public void displayInvalidAddressError(){
        System.out.println("Error, address must be at least 6 characters.\nPlease enter again:");
    }

    /**
     * Prompts the user to enter their email.
     */
    public void displayEnterEmailPrompt(){
        System.out.println("Enter your Email: ");
    }

    /**
     * Prints an error message that the entered email is not up to RFC 5322 standards, asks user to enter another email.
     */
    public void displayInvalidEmailError(){
        System.out.println("The email is not up to RFC 5322 standards. Try another ");
    }

    /**
     * Prompts the user to specify their status in the conference: organizer, attendee, speaker.
     */
    public void displayEnterStatusPrompt(){
        System.out.println("Enter your status in the conference. This can be \"organizer\", \"attendee\" or \"speaker\":");
    }

    /**
     * Prints an error message that the entered status is an invalid input.
     */
    public void displayInvalidStatusError(){
        System.out.println("That was an invalid input.\nPlease try again:");
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

    // -----------------------------------------------------------------------------------------------------------------
}
