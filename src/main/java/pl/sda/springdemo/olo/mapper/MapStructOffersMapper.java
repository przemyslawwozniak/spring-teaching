package pl.sda.springdemo.olo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.springdemo.olo.dto.AddOfferDto;
import pl.sda.springdemo.olo.dto.OfferDto;
import pl.sda.springdemo.olo.model.Offer;
import pl.sda.springdemo.olo.repository.SubcategoriesRepository;

@Mapper(componentModel = "spring")
/*
Wskazuje, że spośród beanów implementujących OffersMapper to ten powinien być wstrzyknięty
Ale MapStruct generuje klasę MapStructOffersMapperImpl więc musimy użyć @Qualifier w miejscu wstrzyknięcia
 */
//@Primary
public abstract class MapStructOffersMapper implements OffersMapper {   //najczęściej interface, ale tak łatwiej wstrzyknąć bean

    @Autowired
    protected SubcategoriesRepository subcategoriesRepository;

    @Mapping(target = "city", source = "localization")
    @Mapping(target = "subcategory", expression = "java( subcategoriesRepository.getById(source.getSubcategoryId()) )")   //'expression' oznacza koniecznosc wprowadzania zmian recznie + brak walidacji
    public abstract Offer mapFromDtoToDomain(OfferDto source);

    @Mapping(target = "subcategory", expression = "java( subcategoriesRepository.findByName(source.getSubcategoryName()) )")
    public abstract Offer mapFromAddOfferDtoToDomain(AddOfferDto source);

}
