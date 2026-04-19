package exceptions;

public class MovementException extends Exception {  // ← CHECKED!
    public MovementException(String message) {
        super(message);
    }
}