package pl.sda.springdemo.olo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.olo.model.Subcategory;

import java.util.Optional;

@Repository
public interface SubcategoriesRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findByName(String name);

}