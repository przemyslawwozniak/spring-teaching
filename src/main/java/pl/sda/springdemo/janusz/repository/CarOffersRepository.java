package pl.sda.springdemo.janusz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.janusz.model.CarBodyType;
import pl.sda.springdemo.janusz.model.CarModel;
import pl.sda.springdemo.janusz.model.CarOffer;
import pl.sda.springdemo.janusz.model.FuelType;
import pl.sda.springdemo.janusz.model.GearboxType;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.hasCarBodyTypeIn;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.hasEngineCapacityBetween;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.hasFuelTypeIn;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.hasGearboxType;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.hasPriceBetween;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.hasProductionYearBetween;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.isBrand;
import static pl.sda.springdemo.janusz.repository.specification.CarOfferSpecifications.titleContainsIgnoringCase;

@Repository
public interface CarOffersRepository extends JpaRepository<CarOffer, Long>, JpaSpecificationExecutor<CarOffer> {

    //szukaj ogloszen dla wskazanego dealera po nazwie
    List<CarOffer> findByDealer_Name(String dealerName);

    //szukaj ogloszen dla wskazanego dealera po dealerId
    List<CarOffer> findByDealer_DealerId(Long dealerId);

    //szukaj dla podanego przedzialu cenowego i auto odpowiedniego typu (SUV etc.)
    List<CarOffer> findByCarBodyTypeAndPriceBetween(CarBodyType carBody, BigDecimal priceFrom, BigDecimal priceTo);

    //szukaj w danym miescie, zastosuj dowolne przekazane sortowanie po polach CarOffer
    List<CarOffer> findByDealer_Address_City(String city, Sort sort);

    //j/w + Stronicowanie
    List<CarOffer> findByDealer_Address_City(String city, Pageable pageable);

    //szukaj dla marka, model w przedziale cenowym
    List<CarOffer> findByCarBrandAndCarModel_NameAndPriceBetween(CarModel.CarBrand carBrand, String carModelName, BigDecimal priceFrom, BigDecimal priceTo);

    default List<CarOffer> findWithSpecification(String text, CarModel.CarBrand brand, GearboxType gearbox,
                                                 short engineCapLb, short engineCapUb,
                                                 short prodYLb, short prodYUb,
                                                 FuelType[] fuelTypes, CarBodyType[] carBodyTypes,
                                                 BigDecimal priceLb, BigDecimal priceUb) {
        return this.findAll(
                where(titleContainsIgnoringCase(text)
                        .and(isBrand(brand))
                        .and(hasGearboxType(gearbox))
                        .and(hasEngineCapacityBetween(engineCapLb, engineCapUb)
                        .and(hasProductionYearBetween(prodYLb, prodYUb)
                        .and(hasFuelTypeIn(fuelTypes)
                        .and(hasCarBodyTypeIn(carBodyTypes))
                        .and(hasPriceBetween(priceLb, priceUb))))))
        );
    }

}
