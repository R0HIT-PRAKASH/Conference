package ConferenceGUI;

import javax.swing.*;

public class ConferenceGUI extends JFrame{

    private JPanel mainPanel;

    public ConferenceGUI(String title){
        super(title);
    }

    public static void main(String [] args){
        JFrame frame = new JFrame("Conference");
        frame.setContentPane(new ConferenceGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
