package pl.sda.springdemo.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.sda.springdemo.exception.OfferNotFoundException;
import pl.sda.springdemo.response.ErrorResponse;

@RestControllerAdvice
public class GlobalRestControllerErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OfferNotFoundException.class)
    ErrorResponse handleOfferNotFoundException(final OfferNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

}
