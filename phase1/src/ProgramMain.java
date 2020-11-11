public class ProgramMain {

    public static void main(String [] args){
        MainController run = new MainController();
        if (run.filesExist()) {
            run.fileQuestion();
        }
        run.run();
    }
}
