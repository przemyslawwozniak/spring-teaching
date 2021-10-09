package pl.sda.springdemo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.sda.springdemo.model.Offer;
import pl.sda.springdemo.model.Subcategory;
import pl.sda.springdemo.model.User;
import pl.sda.springdemo.repository.OffersRepository;
import pl.sda.springdemo.repository.UsersRepository;

import java.math.BigDecimal;
import java.util.Arrays;

//@SpringBootTest
@DataJpaTest
public class EntitiesRelationsTest {

    @Autowired
    private OffersRepository offersRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void user_adds_offers() {
        //given
        var user = User.builder()
                .email("code.addict@gmail.com")
                .phone("111222333") //brak walidacji! i co teraz?
                .password("mysecret")
                .build();

        var subcategory = Subcategory.builder()
                .name("Mercedes-Benz")
                .mainCategory(Subcategory.MainCategory.MOTO)
                .build();

        var offer = Offer.builder()
                .title("Mercedes W211 sedan igła - Niemiec płakał jak sprzedawał!")
                .description("Jeśli szukają Państwo niezawodnego, prestiżowego auta z możliwością założenia instalacji LPG...")
                .city("Warszawa")
                .price(BigDecimal.valueOf(30000))
                .subcategory(subcategory)   //relacja ale na poziomie obiektu JVM
                .user(user)
                .build();
        //when
        offer = offersRepository.save(offer);

        //then
        Assertions.assertThat(offer.getId()).isNotNull();
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(subcategory.getId()).isNotNull();

        var persistedOfferOptional = offersRepository.findById(offer.getId());
        Assertions.assertThat(persistedOfferOptional.isPresent()).isTrue();

        var persistedOffer = persistedOfferOptional.get();
        Assertions.assertThat(persistedOffer.getTitle()).isEqualTo(offer.getTitle());
        //przedyskutowac linijke ponizej
        //Assertions.assertThat(persistedOffer.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @Sql({"/offer_postload_test_data.sql"})
    void postload_is_called_for_offer_entity() {
        //given

        //when
        var user = usersRepository.getById(100L);
        var offer = offersRepository.getById(150L);

        //then
        Assertions.assertThat(offer.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void user_observes_offers() {
        //given
        var offer1 = Offer.builder()
                .title("bmw e36")
                .city("Białystok")
                .build();
        var offer2 = Offer.builder()
                .title("bmw 5 2014")
                .city("Gdańsk")
                .build();
        var offer3 = Offer.builder()
                .title("mazda 6 2018")
                .city("Warszawa")
                .build();

        offersRepository.saveAll(Arrays.asList(offer1, offer2, offer3));

        var user1 = User.builder()
                .email("bmw4ever@gmail.com")
                .observedOffers(Arrays.asList(offer1, offer2))
                .build();
        var user2 = User.builder()
                .email("whatever@gmail.com")
                .observedOffers(Arrays.asList(offer1, offer2, offer3))
                .build();

        usersRepository.saveAll(Arrays.asList(user1, user2));

        //when
        var bmwFan = usersRepository.getById(1L);
        var allCarFan = usersRepository.getById(2L);

        //then
        Assertions.assertThat(bmwFan.getObservedOffers()).isNotEmpty();
        Assertions.assertThat(bmwFan.getObservedOffers()).hasSize(2);

        Assertions.assertThat(allCarFan.getObservedOffers()).isNotEmpty();
        Assertions.assertThat(allCarFan.getObservedOffers()).hasSize(3);
    }

}
