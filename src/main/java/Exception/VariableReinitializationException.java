package Exception;

public class VariableReinitializationException extends Exception {
    public VariableReinitializationException() {
        super("Variable name cannot be reinitialized!");
    }
}

