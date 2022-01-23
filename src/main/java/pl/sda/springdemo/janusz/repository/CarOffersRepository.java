package pl.sda.springdemo.janusz.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.janusz.model.CarBodyType;
import pl.sda.springdemo.janusz.model.CarOffer;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarOffersRepository extends JpaRepository<CarOffer, Long> {

    //szukaj ogloszen dla wskazanego dealera
    List<CarOffer> findByDealer_Name(String dealerName);

    //szukaj dla podanego przedzialu cenowego i auto odpowiedniego typu (SUV etc.)
    List<CarOffer> findByCarBodyTypeAndPriceBetween(CarBodyType carBody, BigDecimal priceFrom, BigDecimal priceTo);

    //szukaj w danym miescie, zastosuj dowolne przekazane sortowanie po polach CarOffer
    List<CarOffer> findByDealer_Address_City(String city, Sort sort);

}
