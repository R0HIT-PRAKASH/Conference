public class MainPresenter {

    public MainPresenter() {}

    // Methods for the Login Controller --------------------------------------------------------------------------------

    public void displayNewOrReturningPrompt(){
        System.out.println("Are you a (1)new user or (2)returning user: ");
    }

    public void displayNewOrReturningError(){
        System.out.println("Not a valid input, please try again: ");
    }

    public void displayEnterUsernamePrompt(){
        System.out.println("Enter Username: ");
    }

    public void displayUsernameExistanceError(){
        System.out.println("This username doesn't exist, please re-enter or type \"q\" to quit: ");
    }

    public void displayEnterPasswordPrompt(){
        System.out.println("Enter Password: ");
    }

    public void displayInvalidPasswordError(){
        System.out.println("Error, password must be at least 3 characters.\nPlease enter again:");
    }

    public void displayRedoPasswordPrompt(){
        System.out.println("Re-enter your password:\nTo quit, press \"q\":");
    }

    public void displayNewUserGreeting(){
        System.out.println("It looks like you are a new user!\nPlease enter some information:");
    }

    public void displayUsernameTakenError(){
        System.out.println("That username is already taken, please enter another one: ");
    }

    public void displayInvalidUsernameError(){
        System.out.println("Error, username must be at least 3 characters. please enter another one: ");
    }

    public void displayEnterNamePrompt(){
        System.out.println("Enter your name: ");
    }

    public void displayInvalidNameError(){
        System.out.println("Error, name must be at least 2 characters.\nPlease enter again:");
    }

    public void displayEnterAddressPrompt(){
        System.out.println("Enter your address: ");
    }

    public void displayInvalidAddressError(){
        System.out.println("Error, address must be at least 6 characters.\nPlease enter again:");
    }

    public void displayEnterEmailPrompt(){
        System.out.println("Enter your Email: ");
    }

    public void displayInvalidEmailError(){
        System.out.println("The email is not up to RFC 5322 standards. Try another ");
    }

    public void displayEnterStatusPrompt(){
        System.out.println("Enter your status in the conference. This can be \"organizer\", \"attendee\" or \"speaker\":");
    }

    public void displayInvalidStatusError(){
        System.out.println("That was an invalid input.\nPlease try again:");
    }

    // -----------------------------------------------------------------------------------------------------------------


    // Methods for the Main Controller --------------------------------------------------------------------------------

    public void displayPreExisitingFilePrompt(){
        System.out.println("Do you want to use pre-existing files? Please type 'Yes' or 'No'");
    }

    public void displayInvalidFileChoice(){
        System.out.println("Invalid Input: Please type 'Yes' or 'No'");
    }

    public void displayDownloadCompletion(){
        System.out.println("Files downloaded.");
    }

    public void displayInvalidInputError(){
        System.out.println("Error: Please type 'Yes' or 'No'");
    }

    // -----------------------------------------------------------------------------------------------------------------
}
