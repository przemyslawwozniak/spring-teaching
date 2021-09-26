package pl.sda.springdemo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.springdemo.dto.OfferDto;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.repository.SubcategoriesRepository;

@Mapper(componentModel = "spring")
/*
Wskazuje, że spośród beanów implementujących OffersMapper to ten powinien być wstrzyknięty
Ale MapStruct generuje klasę MapStructOffersMapperImpl więc musimy użyć @Qualifier w miejscu wstrzyknięcia
 */
//@Primary
public abstract class MapStructOffersMapper implements OffersMapper {   //najczęściej interface, ale tak łatwiej wstrzyknąć bean

    @Autowired
    protected SubcategoriesRepository subcategoriesRepository;

    @Mapping(target = "subcategory", expression = "java( subcategoriesRepository.getById(source.getSubcategoryId()).get() )")
    public abstract Offer mapFromDtoToDomain(OfferDto source);

}
