package exceptions;

public class UserAlreadyLoggedException extends Exception {
    private static final long serialVersionUID = 1L;

    public UserAlreadyLoggedException() {
        super("Logged user in the system!");
    }

    public UserAlreadyLoggedException(String message) {
        super(message);
    }

    public UserAlreadyLoggedException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyLoggedException(String message, Throwable cause) {
        super(message, cause);
    }
}
