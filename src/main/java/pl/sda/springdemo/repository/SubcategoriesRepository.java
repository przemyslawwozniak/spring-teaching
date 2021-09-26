package pl.sda.springdemo.repository;

import org.springframework.stereotype.Component;
import pl.sda.springdemo.model.Subcategory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static pl.sda.springdemo.model.Subcategory.MainCategory.*;

@Component
public class SubcategoriesRepository {

    private final static List<Subcategory> SUBCATEGORIES;

    public List<Subcategory> getAllSubcategories() {
        return SUBCATEGORIES;
    }

    public Optional<Subcategory> getById(String id) {
        return SUBCATEGORIES.stream()
                .filter(subcategory -> id.equals(subcategory.getId()))
                .findFirst();
    }

    static {
        SUBCATEGORIES = Arrays.asList(
                new Subcategory("MOTO_BMW", "BMW", MOTO),
                new Subcategory("MOTO_MERC", "Mercedes-Benz", MOTO),
                new Subcategory("REALEST_HOUSES", "Houses", REAL_ESTATE),
                new Subcategory("REALEST_AP", "Apartments", REAL_ESTATE),
                new Subcategory("JOB_IT", "IT", JOB),
                new Subcategory("HOMEG_FLOURS", "Flours", HOME_GARDEN),
                new Subcategory("ELECTR_PC", "Computers", ELECTRONICS),
                new Subcategory("FASH_TROUS", "Trousers", FASHION),
                new Subcategory("FASH_JACK", "Jackets", FASHION),
                new Subcategory("ANIM_CAT", "Cats", ANIMALS),
                new Subcategory("ANIM_DOG", "Dogs", ANIMALS),
                new Subcategory("BABY_TOYS", "Toys", BABY),
                new Subcategory("SPOHO_FOOTBALL", "Football", SPORTS_HOBBY),
                new Subcategory("MUEDU_GUIT", "Guitars", MUSIC_EDU)
        );
    }

}
