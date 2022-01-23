package pl.sda.springdemo.olo.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.springdemo.olo.dto.OfferDto;
import pl.sda.springdemo.olo.model.Offer;
import pl.sda.springdemo.olo.repository.SubcategoriesRepository;

@Component  //oznaczam jako bean, aby móc wstrzykiwać inne beany
@RequiredArgsConstructor
public class ManualOffersMapper implements OffersMapper {

    private final SubcategoriesRepository subcategoriesRepository;

    //uwidacznia sie wada "recznie pisanych" mapperow - brak dopasowania do zmieniajacego sie kodu (co MapStruct na pewnym poziomie zapewnia)
    public Offer mapFromDtoToDomain(OfferDto source) {/*
        Offer target = new Offer();

        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setCity(source.getLocalization());   //tu musiałem zmienić ręcznie z target.localization na target.city
        target.setEmail(source.getEmail());
        target.setPhone(source.getPhone());

        target.setSubcategory(subcategoriesRepository.getById(source.getSubcategoryId()).get());

        return target;*/
        return null;
    }

}
