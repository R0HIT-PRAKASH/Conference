package ConferenceGUI.AttendeeGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import user.attendee.AttendeePresenter;
import user.attendee.AttendeeController;



public class AttendeeMainPage extends JFrame{
    private JPanel panel1;
    private JButton messagesButton;
    private JButton logoutButton;
    private JButton requestsButton;
    private JButton eventsButton;

    public AttendeeMainPage(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        messagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new AttendeeMessagesPage("Attendee Messages Page");
                frame.setVisible(true);
            }
        });
        eventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        requestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new AttendeeMainPage("Attendee Main Page");
        frame.setVisible(true);
    }
}
// action performed method
// action
// get information in the text field
// pass it on to attendee presenter
// presenter parses, makes sure tis valid input
// send success or failure message from back end to presenter to gui

//