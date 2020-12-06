package user.vip;

import user.attendee.AttendeePresenter;

public class VipPresenter extends AttendeePresenter {

    public VipPresenter(){

    }

    public void displayVipUserOptions(){
        System.out.println("(0) View Corporation\n(1) Edit Corporation Information\n" +
                "(2) View Bio\n(3) Edit Bio\n(4) Go back to Main Screen");
    }

    public void displayInvalidVipUserChoice(){
        System.out.println("Invalid input. Please enter a number between 0 and 3:");
    }

    public void displayVipOptions(){
        System.out.println("(0) Messages\n(1) Events\n(2) Requests\n(3) User Options\n(4) Quit");
    }

    public void displayNextTaskPromptVip(){
        System.out.println("Enter another task. Reminder, you can press '6' to see what options are available at this moment.");
    }

}
