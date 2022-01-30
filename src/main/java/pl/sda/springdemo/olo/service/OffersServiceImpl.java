package pl.sda.springdemo.olo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.olo.dto.RecentOffersQuerySpecsDto;
import pl.sda.springdemo.olo.exception.DbResourceNotFoundException;
import pl.sda.springdemo.olo.exception.OfferNotFoundException;
import pl.sda.springdemo.olo.exception.SubcategoryNotFoundException;
import pl.sda.springdemo.janusz.model.Offer;
import pl.sda.springdemo.janusz.model.Subcategory;
import pl.sda.springdemo.olo.repository.OffersRepository;
import pl.sda.springdemo.olo.repository.SubcategoriesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OffersServiceImpl implements OffersService {

    private final int PAGE_SIZE = 10;

    private final SubcategoriesRepository subcategoriesRepository;
    private final OffersRepository offersRepository;

    public Map<String, List<Subcategory>> getMainCategoriesDisplayNamesToSubcategoriesMap() {
        Map<String, List<Subcategory>> outputMap = new HashMap<>();
        var subcategories = subcategoriesRepository.findAll();

        for(var mainCategory : Subcategory.MainCategory.values()) {
            var matchingSubcategories = subcategories.stream()
                    .filter(subcategory -> mainCategory.equals(subcategory.getMainCategory()))
                    .collect(Collectors.toList());
            outputMap.put(mainCategory.getDisplayName(), matchingSubcategories);
        }

        return outputMap;
    }

    public List<Offer> getRecentOffers() {
        var pageReq = PageRequest.of(0, PAGE_SIZE, Sort.by("publishedDate").descending());
        return offersRepository.findAll(pageReq).getContent();
    }

    public List<Offer> getRecentOffers(String subcategoryName) {
        validateSubcategoryName(subcategoryName);
        var pageReq = PageRequest.of(0, PAGE_SIZE, Sort.by("publishedDate").descending());
        return offersRepository.findBySubcategoryName(subcategoryName, pageReq).getContent();
    }

    private void validateSubcategoryName(String subcategoryName) {
        if(subcategoriesRepository.existsByName(subcategoryName)) {
            //do nothing
        }
        else {
            throw new SubcategoryNotFoundException(subcategoryName);
        }
    }

    public List<Offer> getRecentOffers(RecentOffersQuerySpecsDto querySpecs) {
        var pageReq = PageRequest.of(querySpecs.getPage(), querySpecs.getOffersPerPage(), Sort.by("publishedDate").descending());
        return offersRepository.findBySubcategoryName(querySpecs.getSubcategoryName(), pageReq).getContent();
    }
/*
    public Optional<Offer> getOffer(Long id) {
        return offersRepository.findById(id);
    }
*/
    public Offer getOffer(Long id) {
        checkOfferExist(id);
        return offersRepository.findById(id).get(); //jesli uzyjemy getById(id) bedzie 500 - wiecej https://stackoverflow.com/a/58490797/3673353
    }

    public Offer addOffer(Offer offer) {
        return offersRepository.save(offer);
    }

    public Offer updateOffer(Offer offerUpdate, Long id) {
        checkOfferExist(id);
        var persistedOffer = offersRepository.findById(id).get();
        persistedOffer = persistedOffer.update(offerUpdate);
        return offersRepository.save(persistedOffer);
    }

    public void deleteOffer(Long id) {
        try {
            offersRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
            //zmieniamy z checked exception na not checked aby nie pisac try-catch na sciezce wyjatku
            throw new DbResourceNotFoundException(id.toString());
        }
    }

    public void checkOfferExist(Long id) {
        if(! offersRepository.existsById(id)) {
            throw new OfferNotFoundException(id);
        }
    }

}
