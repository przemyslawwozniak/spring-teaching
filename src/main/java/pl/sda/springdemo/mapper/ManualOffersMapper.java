package pl.sda.springdemo.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.springdemo.dto.OfferDto;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.repository.SubcategoriesRepository;

@Component  //oznaczam jako bean, aby móc wstrzykiwać inne beany
@RequiredArgsConstructor
public class ManualOffersMapper implements OffersMapper {

    private final SubcategoriesRepository subcategoriesRepository;

    public Offer mapFromDtoToDomain(OfferDto source) {
        Offer target = new Offer();

        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setLocalization(source.getLocalization());
        target.setEmail(source.getEmail());
        target.setPhone(source.getPhone());

        target.setSubcategory(subcategoriesRepository.getById(source.getSubcategoryId()).get());

        return target;
    }

}
