package Exception;

public class InvalidFilePath extends Exception {
    public InvalidFilePath() {
        super("Path has not been set or the path is invalid!");
    }
}
