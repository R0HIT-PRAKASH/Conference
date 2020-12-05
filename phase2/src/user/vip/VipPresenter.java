package user.vip;

import user.attendee.AttendeePresenter;

public class VipPresenter extends AttendeePresenter {

    public VipPresenter(){

    }

    public void displayVipUserOptions(){
        System.out.println("(0) Edit Corporation Information\n(1) View Donations\n(2) Edit Donations \n" +
                "(3) View Bio\n(4) Edit Bio\n(5) Go back to Main Screen");
    }

    public void displayInvalidVipUserChoice(){
        System.out.println("Invalid input. Please enter a number between 0 and 4:");
    }
}
