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

    public static Specification<CarOffer> titleContainsIgnoringCase(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower( root.get(CarOffer_.TITLE) ), "%" + text.toLowerCase() + "%");
    }

    public static Specification<CarOffer> isBrand(CarModel.CarBrand brand) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CarOffer_.CAR_BRAND), brand);
    }

    public static Specification<CarOffer> hasGearboxType(GearboxType gearboxType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CarOffer_.GEARBOX_TYPE), gearboxType);
    }

    public static Specification<CarOffer> hasPriceBetween(BigDecimal lowerBoundary, BigDecimal upperBoundary) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(CarOffer_.PRICE), lowerBoundary, upperBoundary);
    }

    public static Specification<CarOffer> hasEngineCapacityBetween(short lowerBoundary, short upperBoundary) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(CarOffer_.ENGINE_CAPACITY), lowerBoundary, upperBoundary);
    }

    public static Specification<CarOffer> hasProductionYearBetween(short lowerBoundary, short upperBoundary) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(CarOffer_.PRODUCTION_YEAR), lowerBoundary, upperBoundary);
    }
/*
    public static Specification<CarOffer> hasFuelTypeIn(List<FuelType> fuelTypes) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get(CarOffer_.FUEL_TYPE), fuelTypes);
    }
*/
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
