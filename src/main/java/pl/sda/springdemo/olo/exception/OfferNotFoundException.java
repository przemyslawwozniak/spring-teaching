package pl.sda.springdemo.olo.exception;

import lombok.Data;

@Data
public class OfferNotFoundException extends RuntimeException {

    private static final String MSG_PARAM = "Offer id = %d not found";

    public OfferNotFoundException(Long id) {
        super(String.format(MSG_PARAM, id));
    }

}
