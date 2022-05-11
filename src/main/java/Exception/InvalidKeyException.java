package Exception;

public class InvalidKeyException extends Exception {
    public InvalidKeyException() {
        super("Key is invalid!");
    }
}