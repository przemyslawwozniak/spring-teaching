package pl.sda.springdemo.janusz.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.sda.springdemo.janusz.model.CarBodyType;
import pl.sda.springdemo.janusz.model.CarModel;
import pl.sda.springdemo.janusz.model.CarOffer;
import pl.sda.springdemo.janusz.model.CarOffer_;
import pl.sda.springdemo.janusz.model.FuelType;
import pl.sda.springdemo.janusz.model.GearboxType;

import java.math.BigDecimal;

public class CarOfferSpecifications {

    private final static BigDecimal TEN_BILLION = BigDecimal.valueOf(10000000L);
    private final static short MAX_SHORT = 32767;

    public static Specification<CarOffer> titleContainsIgnoringCase(String text) {
        return (root, query, criteriaBuilder) -> {
            if(text != null && !text.isBlank()) {
                return criteriaBuilder.like(criteriaBuilder.lower( root.get(CarOffer_.TITLE) ), "%" + text.toLowerCase() + "%");
            }
                return criteriaBuilder.and();
        };
    }

    public static Specification<CarOffer> isBrand(CarModel.CarBrand brand) {
        return (root, query, criteriaBuilder) -> {
            if(brand != null) {
                return criteriaBuilder.equal(root.get(CarOffer_.CAR_BRAND), brand);
            }
            else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<CarOffer> isBrand(String brand) {
        return (root, query, criteriaBuilder) -> {
            if(brand != null && !brand.isBlank()) {
                try {
                    return criteriaBuilder.equal(
                            root.get(CarOffer_.CAR_BRAND),
                            CarModel.CarBrand.valueOf(brand));
                }
                catch(IllegalArgumentException ex) {
                    return criteriaBuilder.and();
                }
            }
            else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<CarOffer> hasGearboxType(GearboxType gearboxType) {
        return (root, query, criteriaBuilder) -> {
            if(gearboxType != null) {
                return criteriaBuilder.equal(root.get(CarOffer_.GEARBOX_TYPE), gearboxType);
            }
            else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<CarOffer> hasPriceBetween(BigDecimal lowerBoundary, BigDecimal upperBoundary) {
        return (root, query, criteriaBuilder) -> {
            if(lowerBoundary != null && upperBoundary != null) {
                return criteriaBuilder.between(root.get(CarOffer_.PRICE), lowerBoundary, upperBoundary);
            }
            else if(lowerBoundary != null) {    //zdefiniowal tylko dolna granice
                return criteriaBuilder.between(root.get(CarOffer_.PRICE), lowerBoundary, TEN_BILLION);
            }
            else if(upperBoundary != null) {    //zdefiniowal tylko gorna granice
                return criteriaBuilder.between(root.get(CarOffer_.PRICE), BigDecimal.ZERO, upperBoundary);
            }
            else {  //nie uzywa tego kryterium wyszukiwania
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<CarOffer> hasEngineCapacityBetween(short lowerBoundary, short upperBoundary) {
        return (root, query, criteriaBuilder) -> {
            //short to typ prosty, jesli nie user nie przekaze tej wartosci, bedzie mial 0
            if(lowerBoundary != 0 && upperBoundary != 0) {  //obsluzy tez case upperBoundary != 0
                return criteriaBuilder.between(root.get(CarOffer_.ENGINE_CAPACITY), lowerBoundary, upperBoundary);
            }
            else if(lowerBoundary != 0) {
                return criteriaBuilder.between(root.get(CarOffer_.ENGINE_CAPACITY), lowerBoundary, MAX_SHORT);
            }
            /*
            else if(upperBoundary != 0) {
                return criteriaBuilder.between(root.get(CarOffer_.ENGINE_CAPACITY), 0, upperBoundary);
            }
            */
            else {  //oba maja wartosc 0 => user nie korzysta z tego wyszukiwania
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<CarOffer> hasProductionYearBetween(short lowerBoundary, short upperBoundary) {
        return (root, query, criteriaBuilder) -> {
            if(lowerBoundary != 0 && upperBoundary != 0) {  //obsluzy tez case upperBoundary != 0
                return criteriaBuilder.between(root.get(CarOffer_.PRODUCTION_YEAR), lowerBoundary, upperBoundary);
            }
            else if(lowerBoundary != 0) {
                return criteriaBuilder.between(root.get(CarOffer_.PRODUCTION_YEAR), lowerBoundary, MAX_SHORT);
            }
            else {  //oba maja wartosc 0 => user nie korzysta z tego wyszukiwania
                return criteriaBuilder.and();
            }
        };
    }

    public static  Specification<CarOffer> hasFuelTypeIn(FuelType... fuelTypes) {
        return (root, query, cb) -> {
            if (fuelTypes != null && fuelTypes.length != 0) {
                return root.get(CarOffer_.FUEL_TYPE).in(fuelTypes);
            } else {
                // always-true predicate, means that no filtering would be applied
                return cb.and();
            }
        };
    }

    public static  Specification<CarOffer> hasCarBodyTypeIn(CarBodyType... carBodyTypes) {
        return (root, query, cb) -> {
            if (carBodyTypes != null && carBodyTypes.length != 0) {
                return root.get(CarOffer_.CAR_BODY_TYPE).in(carBodyTypes);
            } else {
                return cb.and();
            }
        };
    }

}
