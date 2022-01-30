package pl.sda.springdemo.janusz.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.sda.springdemo.janusz.dto.CarOfferTile;
import pl.sda.springdemo.janusz.model.CarOffer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarOffersMapper {

    List<CarOfferTile> map(List<CarOffer> source);  //po prostu iteruje po kolekcji i wola odpowiednia metode map (nizej)

    CarOfferTile map(CarOffer source);

    @AfterMapping
    default void afterCarOfferToTile(CarOffer source, @MappingTarget CarOfferTile target) {
        target.setCity(source.getDealer().getAddress().getCity());
        target.setCarModelName(source.getCarModel().getName());
    }

}
