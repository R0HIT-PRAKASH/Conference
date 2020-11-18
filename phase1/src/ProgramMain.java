public class ProgramMain {

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
