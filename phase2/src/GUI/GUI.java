package GUI;

import javax.swing.*;

public class GUI {

    public Conference(){

    }

    public static void main(String [] args){
        JFrame frame = new JFrame("Conference");
        frame.setContentPane(new Conference().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
