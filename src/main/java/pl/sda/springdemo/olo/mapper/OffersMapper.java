package pl.sda.springdemo.olo.mapper;

import pl.sda.springdemo.olo.dto.OfferDto;
import pl.sda.springdemo.olo.model.Offer;

public interface OffersMapper {

    Offer mapFromDtoToDomain(OfferDto source);

}
