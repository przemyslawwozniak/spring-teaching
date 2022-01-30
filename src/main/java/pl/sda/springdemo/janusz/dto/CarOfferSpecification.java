package pl.sda.springdemo.janusz.dto;

import lombok.Data;
import pl.sda.springdemo.janusz.model.CarBodyType;
import pl.sda.springdemo.janusz.model.CarModel;
import pl.sda.springdemo.janusz.model.FuelType;
import pl.sda.springdemo.janusz.model.GearboxType;

import java.math.BigDecimal;

@Data
public class CarOfferSpecification {

    String text;
    CarModel.CarBrand brand;
    GearboxType gearbox;
    short engineCapLb;
    short engineCapUb;
    short prodYLb;
    short prodYUb;
    FuelType[] fuelTypes;
    CarBodyType[] carBodyTypes;
    BigDecimal priceLb;
    BigDecimal priceUb;

}
