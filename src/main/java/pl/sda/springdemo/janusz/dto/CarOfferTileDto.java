package pl.sda.springdemo.janusz.dto;

import lombok.Data;
import pl.sda.springdemo.janusz.model.CarModel;
import pl.sda.springdemo.janusz.model.FuelType;

import java.math.BigDecimal;

@Data
public class CarOfferTileDto {

    private Long offerId;
    private String title;
    private BigDecimal price;
    private CarModel.CarBrand carBrand;
    private String carModelName;
    private short productionYear;
    private FuelType fuelType;
    private String city;

}
