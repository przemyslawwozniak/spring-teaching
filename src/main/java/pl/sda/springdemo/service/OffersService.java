package pl.sda.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.dto.RecentOffersQuerySpecsDto;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.model.Subcategory;
import pl.sda.springdemo.repository.OffersRepository;
import pl.sda.springdemo.repository.SubcategoriesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OffersService {

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
        var pageReq = PageRequest.of(0, PAGE_SIZE, Sort.by("publishedDate").descending());
        return offersRepository.findBySubcategoryName(subcategoryName, pageReq).getContent();
    }

    public List<Offer> getRecentOffers(RecentOffersQuerySpecsDto querySpecs) {
        var pageReq = PageRequest.of(querySpecs.getPage(), querySpecs.getOffersPerPage(), Sort.by("publishedDate").descending());
        return offersRepository.findBySubcategoryName(querySpecs.getSubcategoryName(), pageReq).getContent();
    }

    public Optional<Offer> getOffer(Long id) {
        return offersRepository.findById(id);
    }

}
