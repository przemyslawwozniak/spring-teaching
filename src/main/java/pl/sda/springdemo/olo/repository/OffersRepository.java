package pl.sda.springdemo.olo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.olo.model.Offer;
import pl.sda.springdemo.olo.model.Offer_;
import pl.sda.springdemo.olo.model.Subcategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static pl.sda.springdemo.olo.repository.specification.OfferSpecifications.*;

@Repository
public interface OffersRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {

    List<Offer> findByCityIn(Collection<String> cities);    //dlaczego używam Collection jako argumentu?
    List<Offer> findByTitleContainingIgnoreCase(String matchedText);
    List<Offer> findByPriceBetween(BigDecimal lowerBound, BigDecimal upperBound);
    List<Offer> findByPublishedDateAfter(LocalDate publishedDate);

    long countOffersByCityIn(Collection<String> cities);    //count

    List<Offer> findByTitleContainingIgnoreCaseAndPriceBetween(String title, BigDecimal priceLb, BigDecimal priceUb);

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - @ Q U E R Y - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    @Query("SELECT o FROM Offer o WHERE o.title LIKE %:titleSubstring% AND o.price BETWEEN :priceLb AND :priceUb")
    List<Offer> findByTitleCaseAndPriceBetween(@Param("titleSubstring") String titleSubstring,
                                               @Param("priceLb") BigDecimal priceLb,
                                               @Param("priceUb") BigDecimal priceUb);

    @Query("SELECT o FROM Offer o WHERE LOWER(o.title) LIKE LOWER( CONCAT('%', :titleSubstring, '%') ) AND o.price BETWEEN :priceLb AND :priceUb")
    List<Offer> findByTitleLikeIgnoreCaseAndPriceBetween(@Param("titleSubstring") String titleSubstring,
                                                         @Param("priceLb") BigDecimal priceLb,
                                                         @Param("priceUb") BigDecimal priceUb);

    @Query(value = "SELECT o FROM Offer o INNER JOIN o.subcategory sc WHERE sc.name = :subcategoryName")
    Page<Offer> findBySubcategoryName(@Param("subcategoryName") String subcategoryName, Pageable pageable);

    @Query(value = "SELECT o FROM Offer o INNER JOIN o.subcategory sc WHERE sc.name = :subcategoryName")
    List<Offer> findBySubcategoryName(@Param("subcategoryName") String subcategoryName);

    @Query(value = "SELECT * FROM offers AS o WHERE o.title LIKE %:titleSubstring% AND o.price BETWEEN :priceLb AND :priceUb",
           nativeQuery = true)
    List<Offer> nativeFindByTitleLikeAndPriceBetween(@Param("titleSubstring") String titleSubstring,
                                                     @Param("priceLb") BigDecimal priceLb,
                                                     @Param("priceUb") BigDecimal priceUb);

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - S O R T I N G - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    List<Offer> findByTitleContainingIgnoreCaseOrderByPrice(String matchedText);    //default ASC = ascending = rosnąco
    List<Offer> findByTitleContainingIgnoreCaseOrderByPriceDesc(String matchedText);    //DESC = descending = malejąco

    List<Offer> findByCityIn(Collection<String> cities, Sort sort);

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - S P E C I F I C A T I O N - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    default List<Offer> findByTitleLikeIgnoreCaseAndPriceBetweenUsingSpecification(String text, BigDecimal priceLb, BigDecimal priceUb) {
        return this.findAll(
                where(titleContainsIgnoringCase(text)
                    .and(hasPriceBetween(priceLb, priceUb)))
        );
    }

    default List<Offer> findByTitleLikeIgnoreCaseAndInCategoryAndInCityAndHasPriceBetweenOrderedByPublishedDateDesc(
            String text, Subcategory subcategory, String city, BigDecimal priceLb, BigDecimal priceUb
    ) {
        return this.findAll(
                where(titleContainsIgnoringCase(text)
                        .and(hasPriceBetween(priceLb, priceUb))
                        .and(belongsToSubcategory(subcategory))
                        .and(inCity(city))),
                Sort.by(Sort.Direction.DESC, Offer_.PUBLISHED_DATE) //zamiast użyć String wprost, używamy metadata descriptor
        );
    }

}
