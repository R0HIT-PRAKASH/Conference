package user.vip;

import event.EventManager;
import message.MessageManager;
import user.UserFactory;
import user.UserManager;
import request.RequestManager;
import user.attendee.AttendeeController;

public class VipController extends AttendeeController {

    UserFactory userFactory;
    VipPresenter p;

    public VipController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username, RequestManager requestManager) {
        super(userManager, eventManager, messageManager, username, requestManager);
        this.p = new VipPresenter();
        this.userFactory = new UserFactory();
    }

    /**
     * Runs the Attendee controller by asking for input and performing the actions
     */
    public void run() {
        p.displayVipOptions();
        p.displayTaskInput();
        int input = 0;
        input = p.nextInt();
        while (input != 4) { // 4 is ending condition
            determineInput(input);
            input = p.nextInt();
        }
    }

    private void determineInput(int input) {
        switch (input) {
            case 0:
                p.displayMessageOptions();
                int choice = p.nextInt();
                final int endCond = 2;
                while (choice != endCond) {
                    determineInput0(choice);
                    choice = p.nextInt();
                }
                break;

            case 1:
                p.displayEventOptions();
                int choice1 = p.nextInt();
                final int endCond1 = 5;
                while (choice1 != endCond1) {
                    determineInput1(choice1);
                    choice1 = p.nextInt();
                }
                break;

            case 2:
                p.displayRequestOptions();
                int choice2 = p.nextInt();
                final int endCond2 = 2;
                while (choice2 != endCond2) {
                    determineInput2(choice2);
                    choice2 = p.nextInt();
                }
                break;

            case 3:
                p.displayVipUserOptions();
                int choice3 = p.nextInt();
                final int endCond3 = 4;
                while (choice3 != endCond3){
                    determineInput3(choice3);
                    choice3 = p.nextInt();
                }
                break;

            case 6:
                p.displayVipOptions();
                break;

            default:
                p.displayInvalidInputError();
                break;
        }
        p.displayNextTaskPromptAttendee();
    }

    private void determineInput3(int input) {
        switch (input) {
            case 0:
                viewCorporation();
                break;

            case 1:
                String corporation = p.displayEnterCompanyPrompt();
                if(corporation.equalsIgnoreCase("q")){
                    break;
                }
                while(corporation.equalsIgnoreCase("")){
                    corporation = p.displayInvalidCompanyError();
                }
                editCorporation(corporation);
                break;

            case 2:
                viewBio();
                break;

            case 3:
                String bio = p.displayEnterBioPrompt();
                if(bio.equalsIgnoreCase("q")){
                    break;
                }
                while(bio.equalsIgnoreCase("")){
                    bio = p.displayInvalidBioError();
                }
                editBio(bio);
                break;

            case 6:
                p.displayVipUserOptions();
                break;

            default:
                p.displayInvalidVipUserChoice();
                break;
        }
        p.displayNextTaskPromptVip();
    }

    private void editCorporation(String corporation){
        userManager.setCorporation(corporation, this.username);
    }

    private void viewBio(){
        p.displayViewBio(userManager.getUser(this.username).getBio());
    }

    private void editBio(String bio){
        userManager.setBio(bio, this.username);
    }

    private void viewCorporation(){
        p.displayViewCorporation(userManager.getUser(this.username).getCompany());
    }
}
