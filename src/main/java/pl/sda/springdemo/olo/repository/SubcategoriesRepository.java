package pl.sda.springdemo.olo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.janusz.model.Subcategory;

@Repository
public interface SubcategoriesRepository extends JpaRepository<Subcategory, Long> {

    Subcategory findByName(String name);
    boolean existsByName(String name);

}
