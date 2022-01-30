package pl.sda.springdemo.janusz.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.sda.springdemo.janusz.dto.CarOfferTileDto;
import pl.sda.springdemo.janusz.model.CarOffer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarOffersMapper {

    List<CarOfferTileDto> map(List<CarOffer> source);  //po prostu iteruje po kolekcji i wola odpowiednia metode map (nizej)

    CarOfferTileDto map(CarOffer source);

    @AfterMapping
    default void afterCarOfferToTile(CarOffer source, @MappingTarget CarOfferTileDto target) {
        target.setCity(source.getDealer().getAddress().getCity());
        target.setCarModelName(source.getCarModel().getName());
    }

}
