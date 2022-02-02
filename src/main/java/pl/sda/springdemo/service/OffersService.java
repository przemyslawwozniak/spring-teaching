package pl.sda.springdemo.service;

import pl.sda.springdemo.dto.RecentOffersQuerySpecsDto;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.model.Subcategory;

import java.util.List;
import java.util.Map;

public interface OffersService {

    Map<String, List<Subcategory>> getMainCategoriesDisplayNamesToSubcategoriesMap();
    List<Offer> getRecentOffers();
    List<Offer> getRecentOffers(String subcategoryName);
    List<Offer> getRecentOffers(RecentOffersQuerySpecsDto querySpecs);
    Offer getOffer(Long id);
    Offer addOffer(Offer offer);
    Offer updateOffer(Offer offerUpdate, Long id);
    void deleteOffer(Long id);
    void checkOfferExist(Long id);

}