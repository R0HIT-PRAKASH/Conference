/**
 * Refers to the controller class that can run the entire program. It calls the MainController, when run, which calls
 * all of the other classes.
 */
public class ProgramMain {

    /**
     * This method is responsible for calling the appropriate methods in MainController.
     * @param args Refers to the string argument from the command line.
     */
    public static void main(String [] args){
        MainController run = new MainController();
        if (run.filesExist() == 0) {
            run.fileQUserMssgExists();
        }
        else if (run.filesExist() == 1) {
            run.fileQUserMssgRoomsExists();
        }
        else if (run.filesExist() == 2) {
            run.fileQAllExists();
        }
        run.run();
    }
}
