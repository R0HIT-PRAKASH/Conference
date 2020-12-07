package main;

import saver.Connector;
import saver.Writing;

import java.sql.SQLException;

/**
 * Refers to the controller class that can run the entire program. It calls the MainController, when run, which calls
 * all of the other classes.
 */
public class ProgramMain {

    /**
     * This method is responsible for calling the appropriate methods in MainController.
     * @param args Refers to the string argument from the command line.
     */
    public static void main(String [] args) throws SQLException {
        MainController run = new MainController();
//        int value = run.filesExist();
//        if (value == 0) {
//            run.fileQ0();
//        }
//        else if (value == 1) {
//            run.fileQ1();
//        }
//        else if (value == 2) {
//            run.fileQ2();
//        }
//        else if (value == 3) {
//            run.fileQ3();
//        }
//        else if (value == 4) {
//            run.fileQ4();
//        }
//        else if (value == 5) {
//            run.fileQ5();
//        }
        run.run();
    }
}
