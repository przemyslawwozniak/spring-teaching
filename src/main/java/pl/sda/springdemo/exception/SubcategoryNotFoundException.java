package pl.sda.springdemo.exception;

public class SubcategoryNotFoundException extends RuntimeException {

    private static final String MSG = "Subcategory not found.";
    private static final String MSG_PARAM = "Subcategory not found for name = %s";

    public SubcategoryNotFoundException() {
        super(MSG);
    }

    public SubcategoryNotFoundException(String message) {
        super(String.format(MSG_PARAM, message));
    }
}
