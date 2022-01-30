package pl.sda.springdemo.olo.exception;

public class DbResourceNotFoundException extends RuntimeException {

    private static final String MSG = "Database resource not found.";
    private static final String MSG_PARAM = "Database resource not found for id = %s";

    public DbResourceNotFoundException() {
        super(MSG);
    }

    public DbResourceNotFoundException(String message) {
        super(String.format(MSG_PARAM, message));
    }
}
