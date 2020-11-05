import java.util.Scanner;

public class LoginController extends MainController{
    private Scanner scan = new Scanner(System.in);

    //this is the only method that is actually called
    public String login(){
        //gets input from the User- i.e. their username and password, and returns the User who was able to login
        String username = scan.nextLine();
        String password = scan.nextLine();
    }

    private boolean checkLoginInfo(String username, String password){
        //checks if the username is valid and if the password matches the username,
        //using the checkCredentials method in UserManager and returns if it is valid
    }
    private User getUserInfo(String username){
        //returns the User with this username (use getUser in UserManager)
    }
}
