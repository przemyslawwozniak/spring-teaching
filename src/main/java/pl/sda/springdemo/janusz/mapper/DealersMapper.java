package pl.sda.springdemo.janusz.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.sda.springdemo.janusz.dto.DealerTileDto;
import pl.sda.springdemo.janusz.model.Dealer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DealersMapper {

    List<DealerTileDto> map(List<Dealer> source);

    DealerTileDto map(Dealer source);

    @AfterMapping
    default void afterDealerToTile(Dealer source, @MappingTarget DealerTileDto target) {
        target.setCity(source.getAddress().getCity());
        target.setPhone(source.getContactData().getPhone());
    }

}
