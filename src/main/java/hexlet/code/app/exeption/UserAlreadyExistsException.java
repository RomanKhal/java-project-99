package hexlet.code.app.exeption;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("User already exists.");
    }
}
