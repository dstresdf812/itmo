package exceptions;

public class MovementException extends Exception {

    public MovementException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Ошибка движения: " + super.getMessage();
    }
}