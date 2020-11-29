package user.vip;

import event.Event;
import event.EventManager;
import message.MessageManager;
import user.UserFactory;
import user.UserManager;
import request.Request;
import request.RequestManager;
import user.attendee.AttendeeController;
import user.attendee.AttendeePresenter;

import java.util.List;

public class VipController extends AttendeeController {

    public UserManager userManager;
    public EventManager eventManager;
    public MessageManager messageManager;
    public String username;
    UserFactory userFactory;
    AttendeePresenter p;

    public VipController(UserManager userManager, EventManager eventManager, MessageManager messageManager, String username, RequestManager requestManager){
        super(userManager, eventManager, messageManager, username, requestManager);
        this.p = new AttendeePresenter();
        this.userFactory = new UserFactory();
    }

    private void determineInputSignUpEvent() {
        List<Event> future = viewFutureEventList();
        p.displayAllFutureEvents(future);
        if (future.size() == 0){
            return;
        }
        String eventSignedUp = p.displayEventSignUpPrompt();
        // System.out.println("What is the name of the event you would like to sign up for? Type 'q' if you would no longer like to sign up for an event.");
        if (eventSignedUp.equals("q")){
            return;
        }
        while (eventManager.getEvent(eventSignedUp) == null ||
                !future.contains(eventManager.getEvent(eventSignedUp))){
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


