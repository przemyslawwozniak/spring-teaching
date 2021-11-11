package pl.sda.springdemo.mapper;

import pl.sda.springdemo.dto.AddOfferDto;
import pl.sda.springdemo.dto.OfferDto;
import pl.sda.springdemo.response.OfferTileResponse;
import pl.sda.springdemo.model.Offer;

import java.util.List;

public interface OffersMapper {

    Offer mapFromDtoToDomain(OfferDto source);

    List<OfferTileResponse> mapFromDomainToTileDto(List<Offer> source);

    Offer mapFromAddOfferDtoToDomain(AddOfferDto source);

}
