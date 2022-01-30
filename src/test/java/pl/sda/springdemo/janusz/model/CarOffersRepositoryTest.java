package pl.sda.springdemo.janusz.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.sda.springdemo.janusz.repository.CarOffersRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarOffersRepositoryTest {

    @Autowired
    private CarOffersRepository carOffersRepository;

    @Test
    void gets_offers_for_given_dealer() {
        //given

        //when
        var offers_d1 = carOffersRepository.findByDealer_Name("dealer_1");
        var offers_nonexd = carOffersRepository.findByDealer_Name("no such dealer");

        //then
        Assertions.assertThat(offers_d1).isNotEmpty();
        Assertions.assertThat(offers_d1).hasSize(2);
        Assertions.assertThat(offers_nonexd).isEmpty();
    }

    @Test
    void gets_offers_for_given_body_type_and_price_between() {
        //when
        var offers_suvs_max_60k = carOffersRepository.findByCarBodyTypeAndPriceBetween(CarBodyType.SUV, BigDecimal.ZERO, BigDecimal.valueOf(60000L));
        var offers_sedans_min_40k = carOffersRepository.findByCarBodyTypeAndPriceBetween(CarBodyType.SEDAN, BigDecimal.valueOf(40000L), BigDecimal.valueOf(100000L));

        //then
        Assertions.assertThat(offers_suvs_max_60k).isNotEmpty();
        Assertions.assertThat(offers_suvs_max_60k).hasSize(1);
        Assertions.assertThat(offers_suvs_max_60k.get(0).getTitle()).isEqualTo("Kia Sportage");
        Assertions.assertThat(offers_sedans_min_40k).isEmpty();
    }

    @Test
    void gets_offers_in_city_sorted_by_publishedDate_desc() {
        //when
        var offers_warsaw_by_date_desc = carOffersRepository.findByDealer_Address_City("Warszawa", Sort.by(Sort.Direction.DESC, CarOffer_.PUBLISHED_DATE));

        //then
        Assertions.assertThat(offers_warsaw_by_date_desc).isNotEmpty();
        Assertions.assertThat(offers_warsaw_by_date_desc).hasSize(2);
        Assertions.assertThat(offers_warsaw_by_date_desc.get(0).getTitle()).isEqualTo("MB W211");
        Assertions.assertThat(offers_warsaw_by_date_desc.get(1).getTitle()).isEqualTo("Audi A4 B9");
    }

    @Test
    void gets_offers_in_city_sorted_by_price_asc() {
        //when
        var offers_lublin_by_price_asc = carOffersRepository.findByDealer_Address_City("Lublin", Sort.by(Sort.Direction.ASC, CarOffer_.PRICE));

        //then
        Assertions.assertThat(offers_lublin_by_price_asc).isNotEmpty();
        Assertions.assertThat(offers_lublin_by_price_asc).hasSize(2);
        Assertions.assertThat(offers_lublin_by_price_asc.get(0).getTitle()).isEqualTo("Kia Sportage");
        Assertions.assertThat(offers_lublin_by_price_asc.get(1).getTitle()).isEqualTo("Mazda CX-9");
    }

    @Test
    void gets_offers_for_brand_and_model_in_price_range() {
        //given
        additionalTestData();

        //when
        var found_offers = carOffersRepository.findByCarBrandAndCarModel_NameAndPriceBetween(CarModel.CarBrand.PORSCHE, "Cayenne", BigDecimal.valueOf(80000L), BigDecimal.valueOf(120000L));

        //then
        Assertions.assertThat(found_offers).isNotEmpty();
        Assertions.assertThat(found_offers).hasSize(2);
    }

    @Test
    void gets_offers_in_city_sorted_by_price_asc_paged() {
        //when
        var offers_lublin_by_price_asc = carOffersRepository.findByDealer_Address_City("Lublin", PageRequest.of(1, 1, Sort.by(Sort.Direction.ASC, CarOffer_.PRICE)));

        //then
        Assertions.assertThat(offers_lublin_by_price_asc).isNotEmpty();
        Assertions.assertThat(offers_lublin_by_price_asc).hasSize(1);
        Assertions.assertThat(offers_lublin_by_price_asc.get(0).getTitle()).isEqualTo("Mazda CX-9");
    }

    @Test
    void gets_offers_by_specification() {
        //given
        additionalTestData();

        //when
        FuelType[] fuelTypes = {FuelType.PETROL, FuelType.LPG, FuelType.HYBRID};
        CarBodyType[] carBodyTypes = {CarBodyType.SUV, CarBodyType.CABRIO};

        var offers_found = carOffersRepository.findWithSpecification(
                "porsche", CarModel.CarBrand.PORSCHE, GearboxType.AUTOMATIC,
                (short)1000, (short)2000, (short)2015, (short)2022,
                fuelTypes, carBodyTypes,
                BigDecimal.valueOf(50000L), BigDecimal.valueOf(200000L));

        Assertions.assertThat(offers_found).isNotEmpty();
        Assertions.assertThat(offers_found).hasSize(1);
        Assertions.assertThat(offers_found.get(0).getTitle()).isEqualTo("Porsche Cayenne 2017");
    }

    @BeforeAll
    void setup() {
        prepareTestData();
    }

    void prepareTestData() {
        var dealer1 = createDealer("dealer_1", "Warszawa");
        var dealer2 = createDealer("dealer_2", "Lublin");

        var offer1_d1 = createCarOffer(dealer1, "MB W211", 35000L, CarBodyType.SEDAN, LocalDateTime.of(2022, 1, 23, 10, 0), CarModel.CarBrand.MERCEDES, "E Klasse", (short)2008);
        var offer2_d1 = createCarOffer(dealer1, "Audi A4 B9", 60000L, CarBodyType.COMBI, LocalDateTime.of(2021, 12, 31, 23, 59), CarModel.CarBrand.AUDI, "A4", (short)2008);

        var offer3_d2 = createCarOffer(dealer2, "Mazda CX-9", 100000L, CarBodyType.SUV, LocalDateTime.of(2021, 10, 3, 10, 0), CarModel.CarBrand.MAZDA, "CX-9", (short)2008);
        var offer4_d2 = createCarOffer(dealer2, "Kia Sportage", 59000L, CarBodyType.SUV, LocalDateTime.of(2021, 5, 2, 10, 0), CarModel.CarBrand.KIA, "Sportage", (short)2008);

        carOffersRepository.saveAll(Arrays.asList(offer1_d1, offer2_d1, offer3_d2, offer4_d2));
    }

    void additionalTestData() {
        var dealer3 = createDealer("dealer_3", "Katowice");

        var offer1_d3 = createCarOffer(dealer3, "Porsche Cayenne 2017", 190000L, CarBodyType.SUV, LocalDateTime.of(2022, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Cayenne", (short)2017);
        var offer2_d3 = createCarOffer(dealer3, "Porsche Cayenne 2014", 120000L, CarBodyType.SUV, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Cayenne", (short)2014);
        var offer3_d3 = createCarOffer(dealer3, "Porsche Cayenne 2010", 80000L, CarBodyType.SUV, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Cayenne", (short)2010);
        var offer4_d3 = createCarOffer(dealer3, "Porsche Boxter 2010", 65000L, CarBodyType.CABRIO, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Boxter", (short)2010);
        var offer5_d3 = createCarOffer(dealer3, "Porsche Boxter 2004", 35000L, CarBodyType.CABRIO, LocalDateTime.of(2021, 1, 23, 10, 0), CarModel.CarBrand.PORSCHE, "Boxter", (short)2004);

        carOffersRepository.saveAll(Arrays.asList(offer1_d3, offer2_d3, offer3_d3, offer4_d3, offer5_d3));
    }

    private Dealer createDealer(String dealerName, String city) {
        var dealer = new Dealer();
        dealer.setName(dealerName);
        dealer.setAddress(new Address("Sezamkowa","25A",1,"01-001",city));
        dealer.setDesc("Used car dealer");
        dealer.setContactData(new ContactData("500-000-000","januszex@gmail.com"));
        return dealer;
    }

    private CarOffer createCarOffer(Dealer dealer, String title, Long price, CarBodyType bodyType, LocalDateTime publishedDate, CarModel.CarBrand carBrand, String carModelName, short prodY) {
        var offer = new CarOffer();
        offer.setTitle(title);
        offer.setDesc("Stan idealny");
        offer.setCarCondition(CarCondition.SECOND_HAND);
        offer.setCarBodyType(bodyType);
        var brand = carBrand;
        offer.setCarBrand(brand);
        offer.setCarModel(new CarModel(carModelName, brand));
        offer.setEngineCapacity((short)2000);
        offer.setDesc("Polecam, bardzo dobre auto");
        offer.setEnginePower((short)186);
        offer.setCarMileageInKm(170000);
        offer.setFuelType(FuelType.PETROL);
        offer.setGearboxType(GearboxType.AUTOMATIC);
        offer.setPrice(BigDecimal.valueOf(price));
        offer.setProductionYear(prodY);
        offer.setPublishedDate(publishedDate);
        offer.setDealer(dealer);
        return offer;
    }

}
