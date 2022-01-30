package pl.sda.springdemo.olo.mapper;

import pl.sda.springdemo.olo.dto.AddOfferDto;
import pl.sda.springdemo.olo.dto.OfferDto;
import pl.sda.springdemo.olo.response.OfferTileResponse;
import pl.sda.springdemo.janusz.model.Offer;

import java.util.List;

public interface OffersMapper {

    Offer mapFromDtoToDomain(OfferDto source);

    List<OfferTileResponse> mapFromDomainToTileDto(List<Offer> source);

    Offer mapFromAddOfferDtoToDomain(AddOfferDto source);

}
