package pl.sda.springdemo.mapper;

import pl.sda.springdemo.dto.OfferDto;
import pl.sda.springdemo.model.Offer;

public interface OffersMapper {

    Offer mapFromDtoToDomain(OfferDto source);

}
