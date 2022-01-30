package pl.sda.springdemo.janusz.exceptions;

public class SortFieldUnappliableException extends RuntimeException {

    private static final String MSG_PARAM = "Sort field ' %s ' not possible";

    public SortFieldUnappliableException(String fieldName) {
        super(String.format(MSG_PARAM, fieldName));
    }

}
