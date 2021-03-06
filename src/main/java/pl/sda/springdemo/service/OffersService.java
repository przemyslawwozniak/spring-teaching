package pl.sda.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.springdemo.model.Subcategory;
import pl.sda.springdemo.repository.SubcategoriesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OffersService {

    private final SubcategoriesRepository subcategoriesRepository;

    public Map<String, List<Subcategory>> getMainCategoriesDisplayNamesToSubcategoriesMap() {
        Map<String, List<Subcategory>> outputMap = new HashMap<>();
        var subcategories = subcategoriesRepository.getAllSubcategories();

        for(var mainCategory : Subcategory.MainCategory.values()) {
            var matchingSubcategories = subcategories.stream()
                    .filter(subcategory -> mainCategory.equals(subcategory.getMainCategory()))
                    .collect(Collectors.toList());
            outputMap.put(mainCategory.getDisplayName(), matchingSubcategories);
        }

        return outputMap;
    }

}
