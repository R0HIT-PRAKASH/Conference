import java.util.Scanner;

public class LoginController extends MainController{
    private Scanner scan = new Scanner(System.in);

    public String login(){
        System.out.println("Enter Username:");
        String username = scan.nextLine();
        System.out.println("Enter Password");
        String password = scan.nextLine();
        if(this.checkLoginInfo(username, password)){
            return username;
        }
        else{
            return null;
        }
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
}
