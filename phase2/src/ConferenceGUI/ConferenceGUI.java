package ConferenceGUI;

import javax.swing.*;

public class ConferenceGUI extends JFrame{

    private JPanel mainPanel;

    public ConferenceGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String [] args){
        JFrame frame = new ConferenceGUI("Conference");
        frame.setVisible(true);
    }
}
