package pl.sda.springdemo.olo.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.sda.springdemo.janusz.model.Offer;
import pl.sda.springdemo.janusz.model.Offer_;
import pl.sda.springdemo.janusz.model.Subcategory;

import java.math.BigDecimal;

public class OfferSpecifications {

    public static Specification<Offer> titleContainsIgnoringCase(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower( root.get(Offer_.TITLE) ), "%" + text.toLowerCase() + "%");
    }

    public static Specification<Offer> belongsToSubcategory(Subcategory subcategory) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Offer_.SUBCATEGORY), subcategory);
    }

    public static Specification<Offer> inCity(String city) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Offer_.CITY), city);
    }

    public static Specification<Offer> hasPriceBetween(BigDecimal lowerBoundary, BigDecimal upperBoundary) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Offer_.PRICE), lowerBoundary, upperBoundary);
    }

}
