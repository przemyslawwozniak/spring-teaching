package pl.sda.springdemo.janusz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.janusz.model.CarOffer;

@Repository
public interface CarOffersRepository extends JpaRepository<CarOffer, Long> {

}
