package user.vip;

import event.EventManager;
import message.MessageManager;
import user.UserFactory;
import user.UserManager;
import request.RequestManager;
import user.attendee.AttendeeController;
import user.attendee.AttendeePresenter;

import java.util.List;

public class VipController extends AttendeeController {

    UserFactory userFactory;
    AttendeePresenter p;

    public VipController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username, RequestManager requestManager) {
        super(userManager, eventManager, messageManager, username, requestManager);
        this.p = new AttendeePresenter();
        this.userFactory = new UserFactory();
    }

    /**
     * Determines the user input for which event they want to sign up for.
     */
    protected void determineInputSignUpEvent() {
        List<String> future = eventManager.getToStringsOfFutureEvents();
        //List<String> stringsOfEvents = getToStringsOfEvents();
        p.displayAllFutureEvents(future);
        if (future.size() == 0){
            return;
        }
        String eventSignedUp = p.displayEventSignUpPrompt();
        // System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
        if (eventSignedUp.equals("q")){
            return;
        }
        while (!eventManager.checkEventIsRegistered(eventSignedUp)){
            eventSignedUp = p.displayInvalidEventSignUp();
            if (eventSignedUp.equalsIgnoreCase("q")){
                break;
            }
        }
        if (!eventSignedUp.equalsIgnoreCase("q")) {
            signUp(eventSignedUp);
        }

    }

}
