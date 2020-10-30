import java.util.Scanner;

public class MainController {
    protected MessageManager messageManager;
    protected UserManager userManager;
    protected EventManager eventManager;
    protected User user;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String [] args) {
        System.out.println("Input Username: ");
        String username = scan.nextLine();
        System.out.println("Input Password: ");
        String password = scan.nextLine();
        boolean valid = checkLoginInfo(username, password);
        while (! valid){
            System.out.println("Invalid Credentials. Please reenter");
            System.out.println("Input Username: ");
            username = scan.nextLine();
            System.out.println("Input Password: ");
            password = scan.nextLine();
            valid = checkLoginInfo(username, password);
        }
        getUser(username);
    }

    private static boolean checkLoginInfo(String username, String password){
        LoginController log = new LoginController();
        log.login(username, password);
    }

    private static User getUser(String username){
        user = userManager.getUser(username);
    }
}
