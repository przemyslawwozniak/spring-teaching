package pl.sda.springdemo.janusz.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;
import pl.sda.springdemo.janusz.exceptions.SortFieldUnappliableException;
import pl.sda.springdemo.janusz.model.CarOffer_;

import java.util.Arrays;

@Data
public class CarOfferSort {

    private final Sort.Direction direction;
    private final String fieldName;

    public CarOfferSort(Sort.Direction direction, String fieldName) {
        validateFieldName(fieldName);
        this.fieldName = fieldName;
        this.direction = direction;
    }

    public void validateFieldName(String fieldName) {
        if(Arrays.asList(POSSIBLE_SORT_FIELDS).contains(fieldName)) {
            throw new SortFieldUnappliableException(fieldName);
        }
    }

    private final static String[] POSSIBLE_SORT_FIELDS = {
            CarOffer_.PRODUCTION_YEAR,
            CarOffer_.CAR_MILEAGE_IN_KM,
            CarOffer_.PRICE,
            CarOffer_.PUBLISHED_DATE
    };

}
