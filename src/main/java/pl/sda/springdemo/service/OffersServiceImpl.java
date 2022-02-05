package pl.sda.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.config.OffersServiceConfigurationPropertiesHolder;
import pl.sda.springdemo.dto.RecentOffersQuerySpecsDto;
import pl.sda.springdemo.exception.DbResourceNotFoundException;
import pl.sda.springdemo.exception.OfferNotFoundException;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.model.Subcategory;
import pl.sda.springdemo.repository.OffersRepository;
import pl.sda.springdemo.repository.SubcategoriesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "offers.page.size")   //metoda nr 1 - bean samodzielnie zarzadza konfiguracja (posiada pole z wartosciami konfiguracji)
public class OffersServiceImpl implements OffersService {

    private int pageSize = 10;  //metoda nr 1 - domyslna wartosc

    private final SubcategoriesRepository subcategoriesRepository;
    private final OffersRepository offersRepository;
    private final OffersServiceConfigurationPropertiesHolder propertiesHolder;  //metoda nr 2 - external bean posiada cala konfiguracje

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
        var pageReq = PageRequest.of(0, pageSize, Sort.by("publishedDate").descending());   //metoda nr 1
        return offersRepository.findAll(pageReq).getContent();
    }

    public List<Offer> getRecentOffers(String subcategoryName) {
        var pageReq = PageRequest.of(0, propertiesHolder.getPage().getSize(), Sort.by("publishedDate").descending());   //metoda nr 2
        return offersRepository.findBySubcategoryName(subcategoryName, pageReq).getContent();
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
        var offer = getOfferByIdAndValidateUser(id);
        offer.update(offerUpdate);
        return offersRepository.save(offer);
    }

    public Offer updateOffer(Offer offerUpdate, Offer target) {
        target.update(offerUpdate);
        return offersRepository.save(target);
    }

    public Offer getOfferByIdAndValidateUser(Long id) {
        checkOfferExist(id);
        return offersRepository.findById(id).get();
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
