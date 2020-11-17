public class ProgramMain {

    public static void main(String [] args){
        MainController run = new MainController();
        if (run.filesExist() == 0) {
            run.fileQuestionUserOnlyExists();
        }
        else if (run.filesExist() == 1) {
            run.fileQuestion();
        }
        run.run();
    }
}
