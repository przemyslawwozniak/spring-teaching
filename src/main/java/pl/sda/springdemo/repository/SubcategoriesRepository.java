package pl.sda.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.model.Subcategory;

@Repository
public interface SubcategoriesRepository extends JpaRepository<Subcategory, Long> {

    Subcategory findByName(String name);

}
