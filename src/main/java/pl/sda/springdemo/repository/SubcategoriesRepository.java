package pl.sda.springdemo.repository;

import org.springframework.stereotype.Component;
import pl.sda.springdemo.model.Subcategory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class SubcategoriesRepository {

    private final static List<Subcategory> SUBCATEGORIES = Collections.emptyList();

    public List<Subcategory> getAllSubcategories() {
        return SUBCATEGORIES;
    }

    public Optional<Subcategory> getById(String id) {
        return SUBCATEGORIES.stream()
                .filter(subcategory -> id.equals(subcategory.getId()))
                .findFirst();
    }

}
