package pl.sda.springdemo.mapper;

import pl.sda.springdemo.dto.AddOfferDto;
import pl.sda.springdemo.dto.OfferDto;
import pl.sda.springdemo.dto.OfferTileDto;
import pl.sda.springdemo.model.Offer;

import java.util.List;

public interface OffersMapper {

    Offer mapFromDtoToDomain(OfferDto source);

    List<OfferTileDto> mapFromDomainToTileDto(List<Offer> source);

    Offer mapFromAddOfferDtoToDomain(AddOfferDto source);

}
