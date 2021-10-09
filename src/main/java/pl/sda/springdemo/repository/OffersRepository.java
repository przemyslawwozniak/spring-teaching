package pl.sda.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.model.Offer;

@Repository
public interface OffersRepository extends JpaRepository<Offer, Long> {

}
