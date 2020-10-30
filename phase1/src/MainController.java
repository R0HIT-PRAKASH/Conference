import java.util.Scanner;

public class MainController {
    protected MessageManager messageManager = new MessageManager();
    protected UserManager userManager = new UserManager();
    protected EventManager eventManager = new EventManager();
    protected User user;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String [] args) {
        LoginController log = new LoginController();
        log.login();
    }
}
