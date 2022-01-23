package pl.sda.springdemo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import pl.sda.springdemo.olo.model.Offer;
import pl.sda.springdemo.olo.model.Subcategory;
import pl.sda.springdemo.olo.repository.OffersRepository;
import pl.sda.springdemo.olo.repository.SubcategoriesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //pozwala oznaczyć non-static method adnotacją @BeforeAll
public class OffersRepositoryTest {

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private SubcategoriesRepository subcategoriesRepository;

    @BeforeAll
    void setup() {
        prepareTestData();
    }

    @Test
    void finds_offers_in_given_cities() {
        //given
        var cities = Arrays.asList("Warszawa", "Lublin");

        //when
        var foundOffers = offersRepository.findByCityIn(cities);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(5);
    }

    @Test
    void counts_offers_in_given_cities() {
        //given
        var cities = Arrays.asList("Warszawa", "Lublin");

        //when
        var numberOfFoundOffers = offersRepository.countOffersByCityIn(cities);

        //then
        Assertions.assertThat(numberOfFoundOffers).isEqualTo(5);
    }

    @Test
    void finds_offers_with_matched_title_substring() {
        //given
        var titleSubstring = "laptop";

        //when
        var foundOffers = offersRepository.findByTitleContainingIgnoreCase(titleSubstring);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(4);
    }

    @Test
    void finds_offers_within_price_range() {
        //given
        var priceLowerBound = BigDecimal.valueOf(39.99);
        var priceUpperBound = BigDecimal.valueOf(200);

        //when
        var foundOffers = offersRepository.findByPriceBetween(priceLowerBound, priceUpperBound);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(2);
    }

    @Test
    void finds_offers_with_matched_title_and_within_given_price_range() {
        //given
        var titleSubstring = "laptop";
        var priceLowerBound = BigDecimal.valueOf(3000);
        var priceUpperBound = BigDecimal.valueOf(5000);

        //when
        var foundOffers = offersRepository.findByTitleContainingIgnoreCaseAndPriceBetween(titleSubstring, priceLowerBound, priceUpperBound);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(1);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("Laptop Lenovo");
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - @ Q U E R Y - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    @Test
    void finds_offers_with_matched_title_and_within_given_price_range_jpql() {
        //given
        var titleSubstring = "laptop";
        var priceLowerBound = BigDecimal.valueOf(3000);
        var priceUpperBound = BigDecimal.valueOf(5000);

        //when
        var foundOffers = offersRepository.findByTitleLikeIgnoreCaseAndPriceBetween(titleSubstring, priceLowerBound, priceUpperBound);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(1);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("Laptop Lenovo");
    }

    @Test
    void finds_offers_with_matched_title_and_within_given_price_range_native() {
        //given
        var titleSubstring = "Laptop";
        var priceLowerBound = BigDecimal.valueOf(3000);
        var priceUpperBound = BigDecimal.valueOf(5000);

        //when
        var foundOffers = offersRepository.nativeFindByTitleLikeAndPriceBetween(titleSubstring, priceLowerBound, priceUpperBound);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(1);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("Laptop Lenovo");
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - S O R T I N G - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    @Test
    void finds_offers_with_matched_title_substring_and_order_by_price_asc() {
        //given
        var titleSubstring = "laptop";

        //when
        var foundOffers = offersRepository.findByTitleContainingIgnoreCaseOrderByPrice(titleSubstring);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(4);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("laptop asus");
    }

    @Test
    void finds_offers_with_matched_title_substring_and_order_by_price_desc() {
        //given
        var titleSubstring = "laptop";

        //when
        var foundOffers = offersRepository.findByTitleContainingIgnoreCaseOrderByPriceDesc(titleSubstring);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(4);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("laptop MacBook Pro 16 inch late 2019");
    }

    @Test
    void finds_all_offers_sorted_by_price_asc() {
        //given

        //when
        var foundOffers = offersRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));   //wskazujemy pole modelu

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(6);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("Książka \"Wojna w kosmosie\" Jacek Bartosiak");
        Assertions.assertThat(foundOffers.get(5).getTitle()).isEqualTo("laptop MacBook Pro 16 inch late 2019");
    }

    @Test
    void finds_offers_in_given_cities_sorted_by_title() {
        //given
        var cities = Arrays.asList("Warszawa", "Lublin");

        //when
        var foundOffers = offersRepository.findByCityIn(cities, Sort.by("title"));

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(5);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("Klocki Lego Duplo 10505 duża farma");
        Assertions.assertThat(foundOffers.get(4).getTitle()).isEqualTo("laptop asus");  //małe 'l' ma większy numer ASCII niż duże 'L'
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - S P E C I F I C A T I O N - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    @Test
    void finds_offers_with_matched_title_and_within_given_price_range_specification() {
        //given
        var titleSubstring = "laptop";
        var priceLowerBound = BigDecimal.valueOf(3000);
        var priceUpperBound = BigDecimal.valueOf(5000);

        //when
        var foundOffers = offersRepository.findByTitleLikeIgnoreCaseAndPriceBetweenUsingSpecification(titleSubstring, priceLowerBound, priceUpperBound);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(1);
        Assertions.assertThat(foundOffers.get(0).getTitle()).isEqualTo("Laptop Lenovo");
    }

    @Test
    void complex_specification_based_query() {
        //given
        var titleSubstring = "laptop";
        var priceLowerBound = BigDecimal.valueOf(1000);
        var priceUpperBound = BigDecimal.valueOf(7000);
        var subcategory = subcategoriesRepository.findByName("Computers").get();
        var city = "Warszawa";

        //when
        var foundOffers = offersRepository
                .findByTitleLikeIgnoreCaseAndInCategoryAndInCityAndHasPriceBetweenOrderedByPublishedDateDesc(
                        titleSubstring, subcategory, city, priceLowerBound, priceUpperBound);

        //then
        Assertions.assertThat(foundOffers).isNotEmpty();
        Assertions.assertThat(foundOffers).hasSize(2);
    }


    void prepareTestData() {
        var computersSubcategory = Subcategory.builder()
                .name("Computers")
                .mainCategory(Subcategory.MainCategory.ELECTRONICS)
                .build();
        var booksSubcategory = Subcategory.builder()
                .name("Books")
                .mainCategory(Subcategory.MainCategory.SPORTS_HOBBY)
                .build();
        var toysSubcategory = Subcategory.builder()
                .name("Toys")
                .mainCategory(Subcategory.MainCategory.BABY)
                .build();

        //mamy kaskadowosc, kategorie zapisza sie wraz z zapisem ofert
        //subcategoriesRepository.saveAll(Arrays.asList(computersSubcategory, booksSubcategory, toysSubcategory));

        //city, title, price, published
        var offer1 = Offer.builder()
                .title("Laptop Lenovo")
                .subcategory(computersSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(4500))
                .publishedDate(LocalDate.of(2021, 5, 2))
                .build();
        var offer2 = Offer.builder()
                .title("laptop asus")
                .subcategory(computersSubcategory)
                .city("Lublin")
                .price(BigDecimal.valueOf(1200))
                .build();
        var offer3 = Offer.builder()
                .title("Książka \"Wojna w kosmosie\" Jacek Bartosiak")
                .subcategory(booksSubcategory)
                .city("Kraków")
                .price(BigDecimal.valueOf(39.99))
                .build();
        var offer4 = Offer.builder()
                .title("Klocki Lego Duplo 10505 duża farma")
                .subcategory(toysSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(199.99))
                .build();
        var offer5 = Offer.builder()
                .title("laptop MacBook Pro 16 inch late 2019")
                .subcategory(computersSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(9700))
                .publishedDate(LocalDate.of(2021, 5, 2))
                .build();
        var offer6 = Offer.builder()
                .title("laptop MacBook Air z procesorem M1")
                .subcategory(computersSubcategory)
                .city("Warszawa")
                .price(BigDecimal.valueOf(6900))
                .publishedDate(LocalDate.of(2021, 11, 7))
                .build();

        offersRepository.saveAll(Arrays.asList(offer1, offer2, offer3, offer4, offer5, offer6));
    }

}
